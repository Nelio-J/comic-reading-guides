package com.nelio.comic_reading_guides.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "items")
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

    @OneToMany(mappedBy = "guide", orphanRemoval = true)
    @Builder.Default
    private List<GuideItemEntity> items = new ArrayList<>();

    public void addItem(GuideItemEntity guideItemEntity) {
        items.removeIf(guideItemEntity::equals);
        items.add(guideItemEntity);
        guideItemEntity.setGuide(this);
    }

    public void removeItem(GuideItemEntity guideItemEntity) {
        items.remove(guideItemEntity);
        guideItemEntity.setGuide(null);
    }

    public int shiftPosition(int oldPosition, int newPosition) {
        if (newPosition < 1) {
            newPosition = 1;
        } else if (newPosition > items.size()) {
            newPosition = items.size();
        }

        GuideItemEntity movingItem = items.stream()
                .filter(item -> item.getPosition() == oldPosition)
                .findFirst()
                .orElse(null);

        if (movingItem == null) {
            return oldPosition;
        }

        for (GuideItemEntity item : items) {
            if (item.getPosition() < oldPosition && item.getPosition() >= newPosition) {
                item.setPosition(item.getPosition() + 1);
            } else if (item.getPosition() > oldPosition && item.getPosition() <= newPosition) {
                item.setPosition(item.getPosition() - 1);
            }
        }

        movingItem.setPosition(newPosition);

        return newPosition;
    }
}
