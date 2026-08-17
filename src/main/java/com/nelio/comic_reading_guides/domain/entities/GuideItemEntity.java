package com.nelio.comic_reading_guides.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "guide_items")
public class GuideItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guide_item_id_seq")
    private Long id;

    private int position;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private GuideEntity guide;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    private ComicEntity comic;

}
