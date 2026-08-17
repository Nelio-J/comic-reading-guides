package com.nelio.comic_reading_guides.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuideItemDto {

    private Long id;

    private Integer position;

    private Long comic;

}
