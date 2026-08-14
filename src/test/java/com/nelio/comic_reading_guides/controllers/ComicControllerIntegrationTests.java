package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.TestDataUtil;
import com.nelio.comic_reading_guides.domain.dto.ComicDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.services.ComicService;
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

import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ComicControllerIntegrationTests {

    private ComicService comicService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public ComicControllerIntegrationTests(ComicService comicService, MockMvc mockMvc) {
        this.comicService = comicService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateComicReturnsHttp201Created() throws Exception {
        ComicDto testComicA = TestDataUtil.createTestComicDtoA();
        String json = objectMapper.writeValueAsString(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/comics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateComicReturnsSavedComic() throws Exception {
        ComicDto testComicA = TestDataUtil.createTestComicDtoA();
        String json = objectMapper.writeValueAsString(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/comics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Spider-Man: Life Story")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publisher").value("Marvel Comics")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publicationYear").value(2019)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.writers[0].name").value("Chip Zdarsky")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.artists[0].name").value("Mark Bagley")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.characters[0].name").value("Spider-Man")
        );
    }

    @Test
    public void testThatGetAllComicsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/comics")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllComicsReturnsPagesOfComics() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        comicService.save(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/comics")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").value(testComicA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value("Spider-Man: Life Story")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].publisher").value("Marvel Comics")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].publicationYear").value(2019)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].writers[0].name").value("Chip Zdarsky")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].artists[0].name").value("Mark Bagley")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].characters[0].name").value("Spider-Man")
        );
    }

    @Test
    public void testThatGetComicReturnsHttpStatus200WhenComicExist() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        comicService.save(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/comics/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetComicReturnsHttpStatus404WhenComicDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/comics/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetComicReturnsSavedComic() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        comicService.save(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/comics/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testComicA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Spider-Man: Life Story")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publisher").value("Marvel Comics")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publicationYear").value(2019)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.writers[0].name").value("Chip Zdarsky")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.artists[0].name").value("Mark Bagley")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.characters[0].name").value("Spider-Man")
        );
    }

    @Test
    public void testThatFullUpdateComicReturnsHttpStatus200WhenComicExist() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        ComicDto testComicDtoA = TestDataUtil.createTestComicDtoA();
        String json = objectMapper.writeValueAsString(testComicDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/comics/" +  savedComic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateComicReturnsHttpStatus404WhenComicDoesNotExist() throws Exception {
        ComicDto testComicA = TestDataUtil.createTestComicDtoA();
        String json = objectMapper.writeValueAsString(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/comics/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateComicUpdatesExistingComic() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        ComicDto testComicDtoB = TestDataUtil.createTestComicDtoB();
        testComicDtoB.setId(savedComic.getId());
        String json = objectMapper.writeValueAsString(testComicDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/comics/" +  savedComic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedComic.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testComicDtoB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publisher").value(testComicDtoB.getPublisher())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publicationYear").value(testComicDtoB.getPublicationYear())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.writers[0].name").value(testComicDtoB.getWriters().iterator().next().getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.artists[0].name").value(testComicDtoB.getArtists().iterator().next().getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.characters[0].name").value(testComicDtoB.getCharacters().iterator().next().getName())
        );
    }

    @Test
    public void testThatPartialUpdateComicReturnsHttpStatus200WhenComicExist() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        ComicDto testComicDtoA = TestDataUtil.createTestComicDtoA();
        testComicDtoA.setTitle("The Amazing Spider-Man");
        String json = objectMapper.writeValueAsString(testComicDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/comics/" +  savedComic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateComicReturnsHttpStatus404WhenComicDoesNotExist() throws Exception {
        ComicDto testComicA = TestDataUtil.createTestComicDtoA();
        String json = objectMapper.writeValueAsString(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/comics/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateComicUpdatesExistingComic() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        ComicDto testComicDtoA = TestDataUtil.createTestComicDtoA();
        testComicDtoA.setTitle("The Amazing Spider-Man");
        testComicDtoA.setWriters(Set.of(TestDataUtil.createTestPersonDtoB()));
        String json = objectMapper.writeValueAsString(testComicDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/comics/" +  savedComic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedComic.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testComicDtoA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publisher").value(testComicDtoA.getPublisher())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publicationYear").value(testComicDtoA.getPublicationYear())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.writers[0].name").value(testComicDtoA.getWriters().iterator().next().getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.artists[0].name").value(testComicDtoA.getArtists().iterator().next().getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.characters[0].name").value(testComicDtoA.getCharacters().iterator().next().getName())
        );
    }

    @Test
    public void testThatDeleteComicReturnsHttpStatus204WhenComicDoesExist() throws Exception {
        ComicEntity testComicA = TestDataUtil.createTestComicEntityA();
        ComicEntity savedComic = comicService.save(testComicA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/comics/" + savedComic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteComicReturnsHttpStatus204WhenComicDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/comics/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
