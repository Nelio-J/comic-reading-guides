package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends JpaRepository<ComicEntity, Long> {
}
