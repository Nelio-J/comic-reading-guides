package com.nelio.comic_reading_guides.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "guides")
public class GuideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guide_id_seq")
    private Long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "guide")
    private List<GuideItemEntity> items;
}
