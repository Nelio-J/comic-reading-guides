package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends CrudRepository<ComicEntity, Long>, PagingAndSortingRepository<ComicEntity, Long> {
}
