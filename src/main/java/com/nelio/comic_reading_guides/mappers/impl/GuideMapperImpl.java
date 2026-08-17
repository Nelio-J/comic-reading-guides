package com.nelio.comic_reading_guides.mappers.impl;

import com.nelio.comic_reading_guides.domain.dto.GuideDto;
import com.nelio.comic_reading_guides.domain.entities.GuideEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GuideMapperImpl implements Mapper<GuideEntity, GuideDto> {

    private final ModelMapper modelMapper;
    private final GuideItemMapperImpl guideItemMapper;

    public GuideMapperImpl(ModelMapper modelMapper, GuideItemMapperImpl guideItemMapper) {
        this.modelMapper = modelMapper;
        this.guideItemMapper = guideItemMapper;

        modelMapper.typeMap(
                GuideDto.class,
                GuideEntity.class
                ).addMappings(mapper ->
                mapper.skip(GuideEntity::setItems)
        );
    }

    //Since guides use a list of GuideItems, we need to call the GuideItemMapper to set the items
    @Override
    public GuideDto mapTo(GuideEntity guideEntity) {
        GuideDto guideDto = modelMapper.map(guideEntity, GuideDto.class);
        guideDto.setItems(
                guideEntity.getItems()
                        .stream()
                        .map(guideItemMapper::mapTo)
                        .collect(Collectors.toList())
        );

        return guideDto;
    }

    @Override
    public GuideEntity mapFrom(GuideDto guideDto) {
        return modelMapper.map(guideDto, GuideEntity.class);
    }
}
