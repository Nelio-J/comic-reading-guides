package com.nelio.comic_reading_guides.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "persons")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
    private Long id;

    private String name;

}