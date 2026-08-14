package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.domain.dto.CharacterDto;
import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import com.nelio.comic_reading_guides.services.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CharacterController {

    private final CharacterService characterService;
    private final Mapper<CharacterEntity, CharacterDto> characterMapper;

    public CharacterController(CharacterService characterService, Mapper<CharacterEntity, CharacterDto> characterMapper) {
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    @PostMapping(path = "/characters")
    public ResponseEntity<CharacterDto> createCharacter(@RequestBody CharacterDto characterDto) {
        CharacterEntity characterEntity = characterMapper.mapFrom(characterDto);
        CharacterEntity savedCharacterEntity = characterService.save(characterEntity);
        return new ResponseEntity<>(characterMapper.mapTo(savedCharacterEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/characters")
    public Page<CharacterDto> getAllCharacters(Pageable pageable) {
        Page<CharacterEntity> characters = characterService.findAll(pageable);
        return characters.map(characterMapper::mapTo);
    }

    @GetMapping(path = "/characters/{id}")
    public ResponseEntity<CharacterDto> getCharacter(@PathVariable("id") Long id) {
        Optional<CharacterEntity> foundCharacter = characterService.findOne(id);
        return foundCharacter.map(CharacterEntity -> {
            CharacterDto characterDto = characterMapper.mapTo(CharacterEntity);
            return new ResponseEntity<>(characterDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/characters/{id}")
    public ResponseEntity<CharacterDto> updateCharacter(@PathVariable("id") Long id, @RequestBody CharacterDto characterDto) {
        if(!characterService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        characterDto.setId(id);
        CharacterEntity characterEntity = characterMapper.mapFrom(characterDto);
        CharacterEntity updatedCharacterEntity = characterService.save(characterEntity);
        return new ResponseEntity<>(characterMapper.mapTo(updatedCharacterEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/characters/{id}")
    public ResponseEntity<CharacterDto> partialUpdateCharacter(@PathVariable("id") Long id, @RequestBody CharacterDto characterDto) {
        if(!characterService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        characterDto.setId(id);
        CharacterEntity characterEntity = characterMapper.mapFrom(characterDto);
        CharacterEntity updatedCharacterEntity = characterService.partialUpdate(id, characterEntity);
        return new  ResponseEntity<>(characterMapper.mapTo(updatedCharacterEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/characters/{id}")
    public ResponseEntity<CharacterDto> deleteCharacter(@PathVariable("id") Long id) {
        characterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
