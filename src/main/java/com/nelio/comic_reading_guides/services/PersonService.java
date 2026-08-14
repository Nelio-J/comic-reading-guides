package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.dto.PersonDto;
import com.nelio.comic_reading_guides.domain.entities.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface PersonService {
    PersonEntity save(PersonEntity personEntity);

    Page<PersonEntity> findAll(Pageable pageable);

    Optional<PersonEntity> findOne(Long id);

    boolean isExists(Long id);

    PersonEntity partialUpdate(Long id, PersonEntity personEntity);

    void delete(Long id);

    PersonEntity findOrCreateByName(PersonEntity personEntity);

}
