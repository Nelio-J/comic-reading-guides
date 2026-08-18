package com.nelio.comic_reading_guides;

import com.nelio.comic_reading_guides.domain.dto.*;
import com.nelio.comic_reading_guides.domain.entities.*;

import java.util.List;
import java.util.Set;

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

    public static ComicEntity createTestComicEntityA() {
        return ComicEntity.builder()
                .title("Spider-Man: Life Story")
                .publisher("Marvel Comics")
                .publicationYear(2019)
                .writers(Set.of(createTestPersonEntityA()))
                .artists(Set.of(createTestPersonEntityB()))
                .characters(Set.of(createTestCharacterEntityA()))
                .build();
    }

    public static ComicDto createTestComicDtoA() {
        return ComicDto.builder()
                .title("Spider-Man: Life Story")
                .publisher("Marvel Comics")
                .publicationYear(2019)
                .writers(Set.of(createTestPersonDtoA()))
                .artists(Set.of(createTestPersonDtoB()))
                .characters(Set.of(createTestCharacterDtoA()))
                .build();
    }

    public static ComicEntity createTestComicEntityB() {
        return ComicEntity.builder()
                .title("Absolute Batman")
                .publisher("DC Comics")
                .publicationYear(2024)
                .writers(Set.of(createTestPersonEntityB()))
                .artists(Set.of(createTestPersonEntityA()))
                .characters(Set.of(createTestCharacterEntityB()))
                .build();
    }

    public static ComicDto createTestComicDtoB() {
        return ComicDto.builder()
                .title("Absolute Batman")
                .publisher("DC Comics")
                .publicationYear(2024)
                .writers(Set.of(createTestPersonDtoB()))
                .artists(Set.of(createTestPersonDtoA()))
                .characters(Set.of(createTestCharacterDtoB()))
                .build();
    }

    public static GuideEntity createTestGuideEntityA() {
        return GuideEntity.builder()
                .title("Spider-Man Starter Guide")
                .description("This is a guide for Spider-Man newcomers")
                .build();
    }

    public static GuideDto createTestGuideDtoA() {
        return GuideDto.builder()
                .title("Spider-Man Starter Guide")
                .description("This is a guide for Spider-Man newcomers")
                .build();
    }

    public static GuideEntity createTestGuideEntityB() {
        return GuideEntity.builder()
                .title("Batman Starter Guide")
                .description("This is a guide for Batman first-time readers")
                .build();
    }

    public static GuideDto createTestGuideDtoB() {
        return GuideDto.builder()
                .title("Batman Starter Guide")
                .description("This is a guide for Batman first-time readers")
                .build();
    }

    public static GuideItemEntity createTestGuideItemEntityA() {
        return GuideItemEntity.builder()
                .position(1)
                .guide(createTestGuideEntityA())
                .comic(createTestComicEntityA())
                .build();
    }

    public static GuideItemDto createTestGuideItemDtoA() {
        return GuideItemDto.builder()
                .position(1)
                .comic(createTestComicEntityA().getId())
                .build();
    }

    public static GuideItemEntity createTestGuideItemEntityB() {
        return GuideItemEntity.builder()
                .position(2)
                .guide(createTestGuideEntityB())
                .comic(createTestComicEntityB())
                .build();
    }

    public static GuideItemDto createTestGuideItemDtoB() {
        return GuideItemDto.builder()
                .position(2)
                .comic(createTestComicEntityB().getId())
                .build();
    }

}
