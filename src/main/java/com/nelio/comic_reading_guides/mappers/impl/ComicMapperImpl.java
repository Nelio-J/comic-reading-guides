package com.nelio.comic_reading_guides.mappers.impl;

import com.nelio.comic_reading_guides.domain.dto.ComicDto;
import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ComicMapperImpl implements Mapper<ComicEntity, ComicDto> {

    private final ModelMapper modelMapper;

    public ComicMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ComicDto mapTo(ComicEntity comicEntity) {
        return modelMapper.map(comicEntity, ComicDto.class);
    }

    @Override
    public ComicEntity mapFrom(ComicDto comicDto) {
        return modelMapper.map(comicDto, ComicEntity.class);
    }
}
