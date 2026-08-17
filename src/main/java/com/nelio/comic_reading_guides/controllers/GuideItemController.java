package com.nelio.comic_reading_guides.controllers;

import com.nelio.comic_reading_guides.domain.dto.GuideItemDto;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import com.nelio.comic_reading_guides.services.GuideItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GuideItemController {
    private final GuideItemService guideItemService;
    private final Mapper<GuideItemEntity, GuideItemDto> guideItemMapper;

    public GuideItemController(GuideItemService guideItemService, Mapper<GuideItemEntity, GuideItemDto> guideItemMapper) {
        this.guideItemService = guideItemService;
        this.guideItemMapper = guideItemMapper;
    }

    //Instead of using full comic entities in our requests, we only send the id of the comic. The GuideItemDto now only requires a position and comic id in its request
    //Because of this, it has been decided to send the DTO directly to the service layer. The service layer handles the logic for creating the entity.
    //The mapper for GuideItem has also been adjusted since the Entity and DTO have different structures.
    @PostMapping(path = "/guides/{guideId}/items")
    public ResponseEntity<GuideItemDto> createGuideItem(@PathVariable("guideId") Long guideId, @RequestBody GuideItemDto guideItemDto) {
        GuideItemEntity savedGuideItemEntity = guideItemService.save(guideId, guideItemDto);
        return new ResponseEntity<>(guideItemMapper.mapTo(savedGuideItemEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/guides/{guideId}/items")
    public Page<GuideItemDto> getAllGuideItems(@PathVariable("guideId") Long guideId, Pageable pageable) {
        Page<GuideItemEntity> guideItems = guideItemService.findAll(guideId, pageable);
        return guideItems.map(guideItemMapper::mapTo);
    }

    @GetMapping(path = "/guides/{guideId}/items/{itemId}")
    public ResponseEntity<GuideItemDto> getGuideItem(@PathVariable("guideId") Long guideId, @PathVariable("itemId") Long itemId) {
        Optional<GuideItemEntity> foundGuideItem = guideItemService.findOne(guideId, itemId);
        return foundGuideItem.map(GuideItemEntity -> {
            GuideItemDto guideItemDto = guideItemMapper.mapTo(GuideItemEntity);
            return new ResponseEntity<>(guideItemDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Guide items use two ids, guideId and (guide)itemId. Because of this, we decided to check whether an item exists in the service layer using a findByGuideIdAndId helper method.
    //This will check if both the guide and guide item exist.
    @PutMapping(path = "/guides/{guideId}/items/{itemId}")
    public ResponseEntity<GuideItemDto> updateGuideItem(@PathVariable("guideId") Long guideId, @PathVariable("itemId") Long itemId, @RequestBody GuideItemDto guideItemDto) {
        GuideItemEntity updatedGuideItemEntity = guideItemService.update(guideId, itemId, guideItemDto);
        return new ResponseEntity<>(guideItemMapper.mapTo(updatedGuideItemEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/guides/{guideId}/items/{itemId}")
    public ResponseEntity<GuideItemDto> partialUpdateGuideItem(@PathVariable("guideId") Long guideId, @PathVariable("itemId") Long itemId, @RequestBody GuideItemDto guideItemDto) {
        guideItemDto.setId(itemId);
        GuideItemEntity updatedGuideItemEntity = guideItemService.partialUpdate(guideId, itemId, guideItemDto);
        return new  ResponseEntity<>(guideItemMapper.mapTo(updatedGuideItemEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/guides/{guideId}/items/{itemId}")
    public ResponseEntity<GuideItemDto> deleteGuideItem(@PathVariable("guideId") Long guideId, @PathVariable("itemId") Long itemId) {
        guideItemService.delete(guideId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
