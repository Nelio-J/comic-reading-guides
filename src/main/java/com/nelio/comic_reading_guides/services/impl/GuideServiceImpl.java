package com.nelio.comic_reading_guides.services.impl;

import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import com.nelio.comic_reading_guides.repositories.GuideRepository;
import com.nelio.comic_reading_guides.services.GuideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuideServiceImpl implements GuideService {

    private final GuideRepository guideRepository;

    public GuideServiceImpl(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    @Override
    public GuideEntity save(GuideEntity guideEntity) {
        return guideRepository.save(guideEntity);
    }

    @Override
    public Page<GuideEntity> findAll(Pageable pageable) {
        return guideRepository.findAll(pageable);
    }

    @Override
    public Optional<GuideEntity> findOne(Long id) {
        return guideRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return guideRepository.existsById(id);
    }

    @Override
    public GuideEntity update(Long id, GuideEntity guideEntity) {
        return guideRepository.findById(id).map(existingGuide -> {
            existingGuide.setTitle(guideEntity.getTitle());
            existingGuide.setDescription(guideEntity.getDescription());

            return guideRepository.save(existingGuide);
        }).orElseThrow(() -> new RuntimeException("Guide not found"));
    }

    @Override
    public GuideEntity partialUpdate(Long id, GuideEntity guideEntity) {
        return guideRepository.findById(id).map(existingGuide -> {
            Optional.ofNullable(guideEntity.getTitle()).ifPresent(existingGuide::setTitle);
            Optional.ofNullable(guideEntity.getDescription()).ifPresent(existingGuide::setDescription);
            return guideRepository.save(existingGuide);
        }).orElseThrow(() -> new RuntimeException("Guide not found"));
    }

    @Override
    public void delete(Long id) {
        guideRepository.deleteById(id);
    }

    public void addItem(GuideItemEntity guideItemEntity) {

    }

//    public void removeItem(GuideItemEntity guideItemEntity) {
//
//    }

}
