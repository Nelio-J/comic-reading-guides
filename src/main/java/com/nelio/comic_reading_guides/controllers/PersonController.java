package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.domain.dto.PersonDto;
import com.nelio.comic_reading_guides.domain.entities.PersonEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import com.nelio.comic_reading_guides.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {

    private final PersonService personService;
    private final Mapper<PersonEntity, PersonDto> personMapper;

    public PersonController(PersonService personService, Mapper<PersonEntity, PersonDto> personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @PostMapping(path = "/persons")
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        PersonEntity personEntity = personMapper.mapFrom(personDto);
        PersonEntity savedPersonEntity = personService.save(personEntity);
        return new ResponseEntity<>(personMapper.mapTo(savedPersonEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/persons")
    public Page<PersonDto> getAllPersons(Pageable pageable) {
        Page<PersonEntity> persons = personService.findAll(pageable);
        return persons.map(personMapper::mapTo);
    }

    @GetMapping(path = "/persons/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Long id) {
        Optional<PersonEntity> foundPerson = personService.findOne(id);
        return foundPerson.map(PersonEntity -> {
            PersonDto personDto = personMapper.mapTo(PersonEntity);
            return new ResponseEntity<>(personDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/persons/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable("id") Long id, @RequestBody PersonDto personDto) {
        if(!personService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personDto.setId(id);
        PersonEntity personEntity = personMapper.mapFrom(personDto);
        PersonEntity updatedPersonEntity = personService.save(personEntity);
        return new ResponseEntity<>(personMapper.mapTo(updatedPersonEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/persons/{id}")
    public ResponseEntity<PersonDto> partialUpdatePerson(@PathVariable("id") Long id, @RequestBody PersonDto personDto) {
        if(!personService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personDto.setId(id);
        PersonEntity personEntity = personMapper.mapFrom(personDto);
        PersonEntity updatedPersonEntity = personService.partialUpdate(id, personEntity);
        return new  ResponseEntity<>(personMapper.mapTo(updatedPersonEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/persons/{id}")
    public ResponseEntity<PersonDto> deletePerson(@PathVariable("id") Long id) {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
