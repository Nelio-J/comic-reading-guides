package com.nelio.comic_reading_guides;

import com.nelio.comic_reading_guides.domain.dto.CharacterDto;
import com.nelio.comic_reading_guides.domain.dto.PersonDto;
import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import com.nelio.comic_reading_guides.domain.entities.PersonEntity;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static CharacterEntity createTestCharacterEntityA() {
        return CharacterEntity.builder()
                .name("Spider-Man")
                .build();
    }

    public static CharacterDto createTestCharacterDtoA() {
        return CharacterDto.builder()
                .name("Spider-Man")
                .build();
    }

    public static CharacterEntity createTestCharacterEntityB() {
        return CharacterEntity.builder()
                .name("Batman")
                .build();
    }

    public static CharacterDto createTestCharacterDtoB() {
        return CharacterDto.builder()
                .name("Batman")
                .build();
    }

    public static PersonEntity createTestPersonEntityA() {
        return PersonEntity.builder()
                .name("Chip Zdarsky")
                .build();
    }

    public static PersonDto createTestPersonDtoA() {
        return PersonDto.builder()
                .name("Chip Zdarsky")
                .build();
    }

    public static PersonEntity createTestPersonEntityB() {
        return PersonEntity.builder()
                .name("Mark Bagley")
                .build();
    }

    public static PersonDto createTestPersonDtoB() {
        return PersonDto.builder()
                .name("Mark Bagley")
                .build();
    }
}
