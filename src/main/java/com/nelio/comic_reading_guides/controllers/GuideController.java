package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.domain.dto.GuideDto;
import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import com.nelio.comic_reading_guides.services.GuideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GuideController {

    private final GuideService guideService;
    private final Mapper<GuideEntity, GuideDto> guideMapper;

    public GuideController(GuideService guideService, Mapper<GuideEntity, GuideDto> guideMapper) {
        this.guideService = guideService;
        this.guideMapper = guideMapper;
    }

    @PostMapping(path = "/guides")
    public ResponseEntity<GuideDto> createGuide(@RequestBody GuideDto guideDto) {
        GuideEntity guideEntity = guideMapper.mapFrom(guideDto);
        GuideEntity savedGuideEntity = guideService.save(guideEntity);
        return new ResponseEntity<>(guideMapper.mapTo(savedGuideEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/guides")
    public Page<GuideDto> getAllGuides(Pageable pageable) {
        Page<GuideEntity> guides = guideService.findAll(pageable);
        return guides.map(guideMapper::mapTo);
    }

    @GetMapping(path = "/guides/{id}")
    public ResponseEntity<GuideDto> getGuide(@PathVariable("id") Long id) {
        Optional<GuideEntity> foundGuide = guideService.findOne(id);
        return foundGuide.map(GuideEntity -> {
            GuideDto guideDto = guideMapper.mapTo(GuideEntity);
            return new ResponseEntity<>(guideDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/guides/{id}")
    public ResponseEntity<GuideDto> updateGuide(@PathVariable("id") Long id, @RequestBody GuideDto guideDto) {
        if(!guideService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        guideDto.setId(id);
        GuideEntity guideEntity = guideMapper.mapFrom(guideDto);
        GuideEntity updatedGuideEntity = guideService.update(id, guideEntity);
        return new ResponseEntity<>(guideMapper.mapTo(updatedGuideEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/guides/{id}")
    public ResponseEntity<GuideDto> partialUpdateGuide(@PathVariable("id") Long id, @RequestBody GuideDto guideDto) {
        if(!guideService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        guideDto.setId(id);
        GuideEntity guideEntity = guideMapper.mapFrom(guideDto);
        GuideEntity updatedGuideEntity = guideService.partialUpdate(id, guideEntity);
        return new  ResponseEntity<>(guideMapper.mapTo(updatedGuideEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/guides/{id}")
    public ResponseEntity<GuideDto> deleteGuide(@PathVariable("id") Long id) {
        guideService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
