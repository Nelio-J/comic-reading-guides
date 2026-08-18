package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.TestDataUtil;
import com.nelio.comic_reading_guides.domain.dto.GuideItemDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import com.nelio.comic_reading_guides.services.ComicService;
import com.nelio.comic_reading_guides.services.GuideItemService;
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
public class GuideItemControllerIntegrationTests {

    private GuideItemService guideItemService;

    private GuideService guideService;

    private ComicService comicService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public GuideItemControllerIntegrationTests(GuideItemService guideItemService, GuideService guideService, ComicService comicService, MockMvc mockMvc) {
        this.guideItemService = guideItemService;
        this.guideService = guideService;
        this.comicService = comicService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateGuideItemReturnsHttp201Created() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        String json = objectMapper.writeValueAsString(testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/guides/" + savedGuide.getId() + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateGuideItemReturnsSavedGuideItem() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        String json = objectMapper.writeValueAsString(testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/guides/" + savedGuide.getId() + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.position").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.comic").value(1)
        );
    }

    @Test
    public void testThatGetAllGuideItemsReturnsHttpStatus200() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/" + savedGuide.getId() + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllGuideItemsReturnsPagesOfGuideItems() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        guideItemService.save(savedGuide.getId(), testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/" + savedGuide.getId() + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].position").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].comic").value(1)
        );
    }

    @Test
    public void testThatGetGuideItemReturnsHttpStatus200WhenGuideItemExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        guideItemService.save(savedGuide.getId(), testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/" + savedGuide.getId() + "/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetGuideItemReturnsHttpStatus404WhenGuideItemDoesNotExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/" + savedGuide.getId() + "/items/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetGuideItemReturnsSavedGuideItem() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        guideItemService.save(savedGuide.getId(), testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/guides/" + savedGuide.getId() + "/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.position").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.comic").value(1)
        );
    }

    @Test
    public void testThatFullUpdateGuideItemReturnsHttpStatus200WhenGuideItemExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);
        ComicEntity testComicB = TestDataUtil.createTestComicEntityB();
        ComicEntity savedComicB = comicService.save(testComicB);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        GuideItemEntity savedGuideItem = guideItemService.save(savedGuide.getId(), testGuideItemA);

        GuideItemDto testGuideItemB = TestDataUtil.createTestGuideItemDtoB();
        testGuideItemB.setComic(savedComicB.getId());
        String json = objectMapper.writeValueAsString(testGuideItemB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/guides/" + savedGuide.getId() + "/items/" +  savedGuideItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateGuideItemReturnsHttpStatus404WhenGuideItemDoesNotExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        String json = objectMapper.writeValueAsString(testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/guides/" + savedGuide.getId() + "/items/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateGuideItemUpdatesExistingGuideItem() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);
        ComicEntity testComicB = TestDataUtil.createTestComicEntityB();
        ComicEntity savedComicB = comicService.save(testComicB);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        GuideItemEntity savedGuideItem = guideItemService.save(savedGuide.getId(), testGuideItemA);

        GuideItemDto testGuideItemB = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemB.setComic(savedComic.getId());
        guideItemService.save(savedGuide.getId(), testGuideItemA);

        GuideItemDto testGuideItemC = TestDataUtil.createTestGuideItemDtoB();
        testGuideItemC.setComic(savedComicB.getId());
        String json = objectMapper.writeValueAsString(testGuideItemC);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/guides/" + savedGuide.getId() + "/items/" +  savedGuideItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedGuideItem.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.position").value(testGuideItemC.getPosition())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.comic").value(testGuideItemC.getComic())
        );
    }

    @Test
    public void testThatPartialUpdateGuideItemReturnsHttpStatus200WhenGuideItemExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);
        TestDataUtil.createTestComicEntityB();
        ComicEntity savedComicB = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        GuideItemEntity savedGuideItem = guideItemService.save(savedGuide.getId(), testGuideItemA);

        GuideItemDto testGuideItemB = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemB.setComic(savedComicB.getId());
        String json = objectMapper.writeValueAsString(testGuideItemB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/guides/" + savedGuide.getId() + "/items/" +  savedGuideItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateGuideItemReturnsHttpStatus404WhenGuideItemDoesNotExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        String json = objectMapper.writeValueAsString(testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/guides/" + savedGuide.getId() + "/items/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateGuideItemUpdatesExistingGuideItem() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);
        TestDataUtil.createTestComicEntityB();
        ComicEntity savedComicB = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        GuideItemEntity savedGuideItem = guideItemService.save(savedGuide.getId(), testGuideItemA);

        GuideItemDto testGuideItemB = TestDataUtil.createTestGuideItemDtoB();
        testGuideItemB.setComic(savedComicB.getId());
        String json = objectMapper.writeValueAsString(testGuideItemB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/guides/" + savedGuide.getId() + "/items/" +  savedGuideItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedGuideItem.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.position").value(testGuideItemA.getPosition())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.comic").value(testGuideItemB.getComic())
        );
    }

    @Test
    public void testThatDeleteGuideItemReturnsHttpStatus204WhenGuideItemDoesExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        GuideItemDto testGuideItemA = TestDataUtil.createTestGuideItemDtoA();
        testGuideItemA.setComic(savedComic.getId());
        GuideItemEntity savedGuideItem = guideItemService.save(savedGuide.getId(), testGuideItemA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/guides/" + savedGuide.getId() + "/items/" + savedGuideItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteGuideItemReturnsHttpStatus204WhenGuideItemDoesNotExist() throws Exception {
        GuideEntity testGuideA = TestDataUtil.createTestGuideEntityA();
        GuideEntity savedGuide = guideService.save(testGuideA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/guides/" + savedGuide.getId() + "/items/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
