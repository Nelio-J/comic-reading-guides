package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends CrudRepository<CharacterEntity, Long>, PagingAndSortingRepository<CharacterEntity, Long> {

    Optional<CharacterEntity> findByName(String name);
}
