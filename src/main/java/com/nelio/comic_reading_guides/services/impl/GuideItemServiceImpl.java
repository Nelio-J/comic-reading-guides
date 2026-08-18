package com.nelio.comic_reading_guides.services.impl;

import com.nelio.comic_reading_guides.domain.dto.GuideItemDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import com.nelio.comic_reading_guides.exceptions.ResourceNotFoundException;
import com.nelio.comic_reading_guides.repositories.GuideItemRepository;
import com.nelio.comic_reading_guides.services.ComicService;
import com.nelio.comic_reading_guides.services.GuideItemService;
import com.nelio.comic_reading_guides.services.GuideService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuideItemServiceImpl implements GuideItemService {

    private final GuideItemRepository guideItemRepository;
    private final GuideService guideService;
    private final ComicService comicService;

    public GuideItemServiceImpl(GuideItemRepository guideItemRepository,  GuideService guideService, ComicService comicService) {
        this.guideItemRepository = guideItemRepository;
        this.guideService = guideService;
        this.comicService = comicService;
    }

    @Override
    @Transactional
    public GuideItemEntity save(Long guideId, GuideItemDto guideItemDto) {
        GuideEntity guideEntity = guideService.findOne(guideId).orElseThrow(() -> new ResourceNotFoundException("Guide not found"));
        Long comicId = guideItemDto.getComic();
        ComicEntity comicEntity = comicService.findOne(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found"));

        guideItemDto.setPosition(guideEntity.getItems().size() + 1);

        GuideItemEntity guideItemEntity = GuideItemEntity.builder()
                .position(guideItemDto.getPosition())
                .guide(guideEntity)
                .comic(comicEntity)
                .build();

        guideEntity.addItem(guideItemEntity);
        return guideItemRepository.save(guideItemEntity);
    }

    @Override
    public Page<GuideItemEntity> findAll(Long guideId, Pageable pageable) {
        guideService.findOne(guideId).orElseThrow(() -> new ResourceNotFoundException("Guide not found"));
        return guideItemRepository.findByGuideIdOrderByPositionAsc(guideId, pageable);
    }

    @Override
    public Optional<GuideItemEntity> findOne(Long guideId, Long itemId) {
        guideService.findOne(guideId).orElseThrow(() -> new ResourceNotFoundException("Guide not found"));
        return guideItemRepository.findByGuideIdAndId(guideId, itemId);
    }

    @Override
    public GuideItemEntity update(Long guideId, Long itemId, GuideItemDto guideItemDto) {
        guideItemDto.setId(itemId);
        GuideEntity guideEntity = guideService.findOne(guideId).orElseThrow(() -> new ResourceNotFoundException("Guide not found"));
        Long comicId = guideItemDto.getComic();
        ComicEntity comicEntity = comicService.findOne(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found"));
        GuideItemEntity guideItemEntity = guideItemRepository.findByGuideIdAndId(guideId, itemId).orElseThrow(() -> new ResourceNotFoundException("Guide item not found"));

        guideItemEntity.setPosition(guideEntity.shiftPosition(guideItemEntity.getPosition(), guideItemDto.getPosition()));
        guideItemEntity.setGuide(guideEntity);
        guideItemEntity.setComic(comicEntity);

        return guideItemRepository.save(guideItemEntity);
    }

    @Override
    public GuideItemEntity partialUpdate(Long guideId, Long itemId, GuideItemDto guideItemDto) {
        guideItemDto.setId(itemId);

        return guideItemRepository.findByGuideIdAndId(guideId, itemId).map(existingGuideItem -> {
            Optional.ofNullable(guideItemDto.getPosition()).ifPresent(position -> {
                GuideEntity guideEntity = guideService.findOne(guideId).orElseThrow(() -> new ResourceNotFoundException("Guide not found"));
                existingGuideItem.setPosition(guideEntity.shiftPosition(existingGuideItem.getPosition(), guideItemDto.getPosition()));
            });
            Optional.ofNullable(guideItemDto.getComic()).ifPresent(comicId -> {
                ComicEntity comicEntity = comicService.findOne(comicId).orElseThrow(() -> new ResourceNotFoundException("Comic not found"));
                existingGuideItem.setComic(comicEntity);
            });
            return guideItemRepository.save(existingGuideItem);
        }).orElseThrow(() -> new ResourceNotFoundException("Guide item not found"));
    }

    //The delete method is different from other entities because we have to first check if an item is in the requested guide.
    //However, just like our other delete methods, we always want to return http status 204 instead of a 'Guide not found' error, regardless if the requested item exists or not.
    //This way you don't expose information about the database to the front-end.
    //The custom delete method in the repository modifies the database instead of just finding something, which is why the @Transactional is needed here.
    @Override
    @Transactional
    public void delete(Long guideId, Long itemId) {
        guideItemRepository.findByGuideIdAndId(guideId, itemId).ifPresent(guideItemEntity -> {
            guideItemEntity.getGuide().shiftPosition(guideItemEntity.getPosition(), guideItemEntity.getGuide().getItems().size());
            guideItemEntity.getGuide().removeItem(guideItemEntity);
        });
        guideItemRepository.deleteByGuideIdAndId(guideId, itemId);
    }

}
