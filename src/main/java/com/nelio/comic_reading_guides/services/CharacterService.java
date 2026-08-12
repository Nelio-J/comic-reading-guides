package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CharacterService {
    CharacterEntity save(CharacterEntity characterEntity);

    Page<CharacterEntity> findAll(Pageable pageable);

    Optional<CharacterEntity> findOne(Long id);

    boolean isExists(Long id);

    CharacterEntity partialUpdate(Long id, CharacterEntity characterEntity);

    void delete(Long id);
}
