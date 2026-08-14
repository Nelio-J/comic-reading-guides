package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.TestDataUtil;
import com.nelio.comic_reading_guides.domain.dto.PersonDto;
import com.nelio.comic_reading_guides.domain.entities.PersonEntity;
import com.nelio.comic_reading_guides.services.PersonService;
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
public class PersonControllerIntegrationTests {

    private PersonService personService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public PersonControllerIntegrationTests(PersonService personService, MockMvc mockMvc) {
        this.personService = personService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePersonReturnsHttp201Created() throws Exception {
        PersonDto testPersonA = TestDataUtil.createTestPersonDtoA();
        testPersonA.setId(null);
        String json = objectMapper.writeValueAsString(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePersonReturnsSavedPerson() throws Exception {
        PersonDto testPersonA = TestDataUtil.createTestPersonDtoA();
        testPersonA.setId(null);
        String json = objectMapper.writeValueAsString(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Chip Zdarsky")
        );
    }

    @Test
    public void testThatGetAllPersonsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllPersonsReturnsPagesOfPersons() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        personService.save(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").value(testPersonA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("Chip Zdarsky")
        );
    }

    @Test
    public void testThatGetPersonReturnsHttpStatus200WhenPersonExist() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        personService.save(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetPersonReturnsHttpStatus404WhenPersonDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/persons/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetPersonReturnsSavedPerson() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        personService.save(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testPersonA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Chip Zdarsky")
        );
    }

    @Test
    public void testThatFullUpdatePersonReturnsHttpStatus200WhenPersonExist() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        PersonEntity savedPerson = personService.save(testPersonA);

        PersonDto testPersonDtoA = TestDataUtil.createTestPersonDtoA();
        String json = objectMapper.writeValueAsString(testPersonDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/persons/" +  savedPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdatePersonReturnsHttpStatus404WhenPersonDoesNotExist() throws Exception {
        PersonDto testPersonA = TestDataUtil.createTestPersonDtoA();
        String json = objectMapper.writeValueAsString(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/persons/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdatePersonUpdatesExistingPerson() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        PersonEntity savedPerson = personService.save(testPersonA);

        PersonDto testPersonDtoB = TestDataUtil.createTestPersonDtoB();
        testPersonDtoB.setId(savedPerson.getId());
        String json = objectMapper.writeValueAsString(testPersonDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/persons/" +  savedPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPerson.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testPersonDtoB.getName())
        );
    }

    @Test
    public void testThatPartialUpdatePersonReturnsHttpStatus200WhenPersonExist() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        PersonEntity savedPerson = personService.save(testPersonA);

        PersonDto testPersonDtoA = TestDataUtil.createTestPersonDtoA();
        testPersonDtoA.setName("Steve Ditko");
        String json = objectMapper.writeValueAsString(testPersonDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/persons/" +  savedPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdatePersonReturnsHttpStatus404WhenPersonDoesNotExist() throws Exception {
        PersonDto testPersonA = TestDataUtil.createTestPersonDtoA();
        String json = objectMapper.writeValueAsString(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/persons/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdatePersonUpdatesExistingPerson() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        PersonEntity savedPerson = personService.save(testPersonA);

        PersonDto testPersonDtoA = TestDataUtil.createTestPersonDtoA();
        testPersonDtoA.setName("Steve Ditko");
        String json = objectMapper.writeValueAsString(testPersonDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/persons/" +  savedPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPerson.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testPersonDtoA.getName())
        );
    }

    @Test
    public void testThatDeletePersonReturnsHttpStatus204WhenPersonDoesExist() throws Exception {
        PersonEntity testPersonA = TestDataUtil.createTestPersonEntityA();
        PersonEntity savedPerson = personService.save(testPersonA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/persons/" + savedPerson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeletePersonReturnsHttpStatus204WhenPersonDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/persons/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
