package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideRepository extends CrudRepository<GuideEntity, Long>, PagingAndSortingRepository<GuideEntity, Long> {
}
