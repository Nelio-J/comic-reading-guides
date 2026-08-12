package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.entities.ComicEntity;

public interface ComicService {
    ComicEntity save(ComicEntity comicEntity);
}
