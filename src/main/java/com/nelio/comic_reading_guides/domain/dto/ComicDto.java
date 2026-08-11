package com.nelio.comic_reading_guides.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComicDto {

    private Long id;

    private String title;

    private String publisher;

    private int year;

    private Set<PersonDto> writers;

    private Set<PersonDto> artists;

    private Set<CharacterDto> characters;

}
