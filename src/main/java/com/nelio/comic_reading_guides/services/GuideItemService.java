package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.dto.GuideItemDto;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GuideItemService {
    GuideItemEntity save(Long guideId, GuideItemDto guideItemDto);

    Page<GuideItemEntity> findAll(Long guideId, Pageable pageable);

    Optional<GuideItemEntity> findOne(Long guideId, Long itemId);

    GuideItemEntity update(Long guideId, Long itemId, GuideItemDto guideItemDto);

    GuideItemEntity partialUpdate(Long guideId, Long itemId, GuideItemDto guideItemDto);

    void delete(Long guideId, Long itemId);

}
