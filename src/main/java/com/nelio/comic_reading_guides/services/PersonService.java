package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.entities.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonService {
    PersonEntity save(PersonEntity personEntity);

    Page<PersonEntity> findAll(Pageable pageable);

    Optional<PersonEntity> findOne(Long id);

    boolean isExists(Long id);

    PersonEntity partialUpdate(Long id, PersonEntity personEntity);

    void delete(Long id);
}
