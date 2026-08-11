package com.nelio.comic_reading_guides.mappers.impl;

import com.nelio.comic_reading_guides.domain.dto.GuideItemDto;
import com.nelio.comic_reading_guides.domain.entities.GuideItemEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GuideItemMapperImpl implements Mapper<GuideItemEntity, GuideItemDto> {

    private ModelMapper modelMapper;

    public GuideItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GuideItemDto mapTo(GuideItemEntity guideItemEntity) {
        return modelMapper.map(guideItemEntity, GuideItemDto.class);
    }

    @Override
    public GuideItemEntity mapFrom(GuideItemDto guideItemDto) {
        return modelMapper.map(guideItemDto, GuideItemEntity.class);
    }
}
