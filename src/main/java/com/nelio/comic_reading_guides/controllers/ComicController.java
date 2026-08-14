package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.domain.dto.ComicDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import com.nelio.comic_reading_guides.services.ComicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ComicController {

    private final ComicService comicService;
    private final Mapper<ComicEntity, ComicDto> comicMapper;

    public ComicController(ComicService comicService, Mapper<ComicEntity, ComicDto> comicMapper) {
        this.comicService = comicService;
        this.comicMapper = comicMapper;
    }

    @PostMapping(path = "/comics")
    public ResponseEntity<ComicDto> createComic(@RequestBody ComicDto comicDto) {
        ComicEntity comicEntity = comicMapper.mapFrom(comicDto);
        ComicEntity savedComicEntity = comicService.save(comicEntity);
        return new ResponseEntity<>(comicMapper.mapTo(savedComicEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/comics")
    public Page<ComicDto> getAllComics(Pageable pageable) {
        Page<ComicEntity> comics = comicService.findAll(pageable);
        return comics.map(comicMapper::mapTo);
    }

    @GetMapping(path = "/comics/{id}")
    public ResponseEntity<ComicDto> getComic(@PathVariable("id") Long id) {
        Optional<ComicEntity> foundComic = comicService.findOne(id);
        return foundComic.map(ComicEntity -> {
            ComicDto comicDto = comicMapper.mapTo(ComicEntity);
            return new ResponseEntity<>(comicDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/comics/{id}")
    public ResponseEntity<ComicDto> updateComic(@PathVariable("id") Long id, @RequestBody ComicDto comicDto) {
        if(!comicService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        comicDto.setId(id);
        ComicEntity comicEntity = comicMapper.mapFrom(comicDto);
        ComicEntity updatedComicEntity = comicService.save(comicEntity);
        return new ResponseEntity<>(comicMapper.mapTo(updatedComicEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/comics/{id}")
    public ResponseEntity<ComicDto> partialUpdateComic(@PathVariable("id") Long id, @RequestBody ComicDto comicDto) {
        if(!comicService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        comicDto.setId(id);
        ComicEntity comicEntity = comicMapper.mapFrom(comicDto);
        ComicEntity updatedComicEntity = comicService.partialUpdate(id, comicEntity);
        return new  ResponseEntity<>(comicMapper.mapTo(updatedComicEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/comics/{id}")
    public ResponseEntity<ComicDto> deleteComic(@PathVariable("id") Long id) {
        comicService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
