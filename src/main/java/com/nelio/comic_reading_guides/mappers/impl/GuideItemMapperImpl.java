package com.nelio.comic_reading_guides.mappers.impl;

import com.nelio.comic_reading_guides.domain.dto.GuideItemDto;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GuideItemMapperImpl implements Mapper<GuideItemEntity, GuideItemDto> {

    private final ModelMapper modelMapper;

    //Since the Entity and DTO have different structures, we need to manually handle mismatches.
    //https://modelmapper.org/getting-started/#handling-mismatches
    public GuideItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.typeMap(
                GuideItemEntity.class,
                GuideItemDto.class
        ).addMappings(mapper ->
                mapper.skip(GuideItemDto::setComic)
        );
    }

    @Override
    public GuideItemDto mapTo(GuideItemEntity guideItemEntity) {
        GuideItemDto guideItemDto = modelMapper.map(guideItemEntity, GuideItemDto.class);
        guideItemDto.setComic(guideItemEntity.getComic().getId());
        return guideItemDto;
    }

    @Override
    public GuideItemEntity mapFrom(GuideItemDto guideItemDto) {
        return modelMapper.map(guideItemDto, GuideItemEntity.class);
    }
}
