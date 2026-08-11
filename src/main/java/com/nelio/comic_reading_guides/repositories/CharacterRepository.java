package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
}
