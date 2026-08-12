package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.TestDataUtil;
import com.nelio.comic_reading_guides.domain.dto.CharacterDto;
import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import com.nelio.comic_reading_guides.services.CharacterService;
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
public class CharacterControllerIntegrationTests {

    private CharacterService characterService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public CharacterControllerIntegrationTests(CharacterService characterService, MockMvc mockMvc) {
        this.characterService = characterService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateCharacterReturnsHttp201Created() throws Exception {
        CharacterDto testCharacterA = TestDataUtil.createTestCharacterDtoA();
        String json = objectMapper.writeValueAsString(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateCharacterReturnsSavedCharacter() throws Exception {
        CharacterDto testCharacterA = TestDataUtil.createTestCharacterDtoA();
        String json = objectMapper.writeValueAsString(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Spider-Man")
        );
    }

    @Test
    public void testThatGetAllCharactersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllCharactersReturnsPagesOfCharacters() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        characterService.save(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").value(testCharacterA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("Spider-Man")
        );
    }

    @Test
    public void testThatGetCharacterReturnsHttpStatus200WhenCharacterExist() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        characterService.save(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/characters/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetCharacterReturnsHttpStatus404WhenCharacterDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/characters/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetCharacterReturnsSavedCharacter() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        characterService.save(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/characters/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testCharacterA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Spider-Man")
        );
    }

    @Test
    public void testThatFullUpdateCharacterReturnsHttpStatus200WhenCharacterExist() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        CharacterEntity savedCharacter = characterService.save(testCharacterA);

        CharacterDto testCharacterDtoA = TestDataUtil.createTestCharacterDtoA();
        String json = objectMapper.writeValueAsString(testCharacterDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/characters/" +  savedCharacter.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateCharacterReturnsHttpStatus404WhenCharacterDoesNotExist() throws Exception {
        CharacterDto testCharacterA = TestDataUtil.createTestCharacterDtoA();
        String json = objectMapper.writeValueAsString(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/characters/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateCharacterUpdatesExistingCharacter() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        CharacterEntity savedCharacter = characterService.save(testCharacterA);

        CharacterDto testCharacterDtoB = TestDataUtil.createTestCharacterDtoB();
        testCharacterDtoB.setId(savedCharacter.getId());
        String json = objectMapper.writeValueAsString(testCharacterDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/characters/" +  savedCharacter.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCharacter.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testCharacterDtoB.getName())
        );
    }

    @Test
    public void testThatPartialUpdateCharacterReturnsHttpStatus200WhenCharacterExist() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        CharacterEntity savedCharacter = characterService.save(testCharacterA);

        CharacterDto testCharacterDtoA = TestDataUtil.createTestCharacterDtoA();
        testCharacterA.setName("SPIDER-MAN");
        String json = objectMapper.writeValueAsString(testCharacterDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/characters/" +  savedCharacter.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateCharacterReturnsHttpStatus404WhenCharacterDoesNotExist() throws Exception {
        CharacterDto testCharacterA = TestDataUtil.createTestCharacterDtoA();
        String json = objectMapper.writeValueAsString(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/characters/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateCharacterUpdatesExistingCharacter() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        CharacterEntity savedCharacter = characterService.save(testCharacterA);

        CharacterDto testCharacterDtoA = TestDataUtil.createTestCharacterDtoA();
        testCharacterDtoA.setName("SPIDER-MAN");
        String json = objectMapper.writeValueAsString(testCharacterDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/characters/" +  savedCharacter.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCharacter.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testCharacterDtoA.getName())
        );
    }

    @Test
    public void testThatDeleteCharacterReturnsHttpStatus204WhenCharacterDoesExist() throws Exception {
        CharacterEntity testCharacterA = TestDataUtil.createTestCharacterEntityA();
        CharacterEntity savedCharacter = characterService.save(testCharacterA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/characters/" + savedCharacter.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteCharacterReturnsHttpStatus204WhenCharacterDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/characters/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
