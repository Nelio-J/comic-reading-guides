package com.nelio.comic_reading_guides.services;

import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GuideService {
        GuideEntity save(GuideEntity guideEntity);

        Page<GuideEntity> findAll(Pageable pageable);

        Optional<GuideEntity> findOne(Long id);

        boolean isExists(Long id);

        GuideEntity update(Long id, GuideEntity guideEntity);

        GuideEntity partialUpdate(Long id, GuideEntity guideEntity);

        void delete(Long id);
}
