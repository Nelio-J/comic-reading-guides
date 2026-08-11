package com.nelio.comic_reading_guides.mappers.impl;

import com.nelio.comic_reading_guides.domain.dto.GuideDto;
import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GuideMapperImpl implements Mapper<GuideEntity, GuideDto> {

    private ModelMapper modelMapper;

    public GuideMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GuideDto mapTo(GuideEntity guideEntity) {
        return modelMapper.map(guideEntity, GuideDto.class);
    }

    @Override
    public GuideEntity mapFrom(GuideDto guideDto) {
        return modelMapper.map(guideDto, GuideEntity.class);
    }
}
