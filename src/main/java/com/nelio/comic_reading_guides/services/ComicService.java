package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.dto.ComicDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ComicService {
    ComicEntity save(ComicEntity comicEntity);

    Page<ComicEntity> findAll(Pageable pageable);

    Optional<ComicEntity> findOne(Long id);

    boolean isExists(Long id);

    ComicEntity partialUpdate(Long id, ComicEntity comicEntity);

    void delete(Long id);
}
