package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.TestDataUtil;
import com.nelio.comic_reading_guides.domain.dto.GuideDto;
import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.services.GuideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GuideControllerIntegrationTests {

    private GuideService guideService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public GuideControllerIntegrationTests(GuideService guideService, MockMvc mockMvc) {
        this.guideService = guideService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateGuideReturnsHttp201Created() throws Exception {
        GuideDto testGuideA = TestDataUtil.createTestGuideDtoA();
        String json = objectMapper.writeValueAsString(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/guides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateGuideReturnsSavedGuide() throws Exception {
        GuideDto testGuideA = TestDataUtil.createTestGuideDtoA();
        String json = objectMapper.writeValueAsString(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/guides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Spider-Man Starter Guide")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("This is a guide for Spider-Man newcomers")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.items").isEmpty()
        );
    }

    @Test
    public void testThatGetAllGuidesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllGuidesReturnsPagesOfGuides() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").value(testGuideA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value("Spider-Man Starter Guide")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].description").value("This is a guide for Spider-Man newcomers")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].items").isEmpty()
        );
    }

    @Test
    public void testThatGetGuideReturnsHttpStatus200WhenGuideExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetGuideReturnsHttpStatus404WhenGuideDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetGuideReturnsSavedGuide() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testGuideA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Spider-Man Starter Guide")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("This is a guide for Spider-Man newcomers")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.items").isEmpty()
        );
    }

    @Test
    public void testThatFullUpdateGuideReturnsHttpStatus200WhenGuideExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        GuideDto testGuideDtoA = TestDataUtil.createTestGuideDtoA();
        String json = objectMapper.writeValueAsString(testGuideDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/guides/" +  savedGuide.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateGuideReturnsHttpStatus404WhenGuideDoesNotExist() throws Exception {
        GuideDto testGuideA = TestDataUtil.createTestGuideDtoA();
        String json = objectMapper.writeValueAsString(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/guides/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateGuideUpdatesExistingGuide() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        GuideDto testGuideDtoB = TestDataUtil.createTestGuideDtoB();
        testGuideDtoB.setId(savedGuide.getId());
        String json = objectMapper.writeValueAsString(testGuideDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/guides/" +  savedGuide.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testGuideA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testGuideDtoB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(testGuideDtoB.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.items").isEmpty()
        );
    }

    @Test
    public void testThatPartialUpdateGuideReturnsHttpStatus200WhenGuideExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        GuideDto testGuideDtoA = TestDataUtil.createTestGuideDtoA();
        testGuideDtoA.setTitle("Updated Spider-Man Reading Guide");
        String json = objectMapper.writeValueAsString(testGuideDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/guides/" +  savedGuide.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateGuideReturnsHttpStatus404WhenGuideDoesNotExist() throws Exception {
        GuideDto testGuideA = TestDataUtil.createTestGuideDtoA();
        String json = objectMapper.writeValueAsString(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/guides/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateGuideUpdatesExistingGuide() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        GuideDto testGuideDtoA = TestDataUtil.createTestGuideDtoA();
        testGuideDtoA.setTitle("Updated Spider-Man Reading Guide");
        String json = objectMapper.writeValueAsString(testGuideDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/guides/" +  savedGuide.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedGuide.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testGuideDtoA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(testGuideDtoA.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.items").isEmpty()
        );
    }

    @Test
    public void testThatDeleteGuideReturnsHttpStatus204WhenGuideDoesExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/guides/" + savedGuide.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteGuideReturnsHttpStatus204WhenGuideDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/guides/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
