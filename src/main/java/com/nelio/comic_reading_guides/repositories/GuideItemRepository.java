package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuideItemRepository extends CrudRepository<GuideItemEntity, Long>, PagingAndSortingRepository<GuideItemEntity, Long> {

    Page<GuideItemEntity> findByGuideIdOrderByPositionAsc(Long guideId, Pageable pageable);

    Optional<GuideItemEntity> findByGuideIdAndId(Long guideId, Long itemId);

    void deleteByGuideIdAndId(Long guideId, Long itemId);
}
