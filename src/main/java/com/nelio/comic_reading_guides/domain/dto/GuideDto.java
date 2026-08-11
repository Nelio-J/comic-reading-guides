package com.nelio.comic_reading_guides.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuideDto {

    private Long id;

    private String title;

    private String description;

    private List<GuideItemDto> items;
}
