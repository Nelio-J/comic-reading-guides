package com.nelio.comic_reading_guides.mappers.impl;

import com.nelio.comic_reading_guides.domain.dto.CharacterDto;
import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import com.nelio.comic_reading_guides.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapperImpl implements Mapper<CharacterEntity, CharacterDto> {

    private ModelMapper modelMapper;

    public CharacterMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CharacterDto mapTo(CharacterEntity characterEntity) {
        return modelMapper.map(characterEntity, CharacterDto.class);
    }

    @Override
    public CharacterEntity mapFrom(CharacterDto characterDto) {
        return modelMapper.map(characterDto, CharacterEntity.class);
    }
}
