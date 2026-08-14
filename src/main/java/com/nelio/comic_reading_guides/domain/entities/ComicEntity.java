package com.nelio.comic_reading_guides.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comics")
public class ComicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comic_id_seq")
    private Long id;

    private String title;

    private String publisher;

    private Integer publicationYear;

    @ManyToMany()
    @JoinTable(
            name = "comic_writers",
            joinColumns = @JoinColumn(name = "comic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Set<PersonEntity> writers;

    @ManyToMany()
    @JoinTable(
            name = "comic_artists",
            joinColumns = @JoinColumn(name = "comic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private Set<PersonEntity> artists;

    @ManyToMany()
    @JoinTable(
            name = "comic_characters",
            joinColumns = @JoinColumn(name = "comic_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id")
    )
    private Set<CharacterEntity> characters;

}
