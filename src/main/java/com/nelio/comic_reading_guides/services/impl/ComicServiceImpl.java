package com.nelio.comic_reading_guides.services.impl;

import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.repositories.ComicRepository;
import com.nelio.comic_reading_guides.services.ComicService;
import org.springframework.stereotype.Service;

@Service
public class ComicServiceImpl implements ComicService {

    private ComicRepository comicRepository;

    public ComicServiceImpl(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    @Override
    public ComicEntity save(ComicEntity comicEntity) {
        return comicRepository.save(comicEntity);
    }
}
