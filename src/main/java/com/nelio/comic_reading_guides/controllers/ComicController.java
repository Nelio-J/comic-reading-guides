package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.domain.dto.ComicDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import com.nelio.comic_reading_guides.services.ComicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComicController {

    private ComicService comicService;
    private Mapper<ComicEntity, ComicDto> comicMapper;

    public ComicController(ComicService comicService, Mapper<ComicEntity, ComicDto> comicMapper) {
        this.comicService = comicService;
        this.comicMapper = comicMapper;
    }

    @PostMapping(path = "/comics")
    public ResponseEntity<ComicDto> createComic(@RequestBody ComicDto comic) {
        ComicEntity comicEntity = comicMapper.mapFrom(comic);
        ComicEntity savedComicEntity = comicService.save(comicEntity);
        return new ResponseEntity<>(comicMapper.mapTo(savedComicEntity), HttpStatus.CREATED);
    }
}
