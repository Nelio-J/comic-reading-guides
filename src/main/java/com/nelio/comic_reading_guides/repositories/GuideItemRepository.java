package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideItemRepository extends CrudRepository<GuideItemEntity, Long>, PagingAndSortingRepository<GuideItemEntity, Long> {
}
