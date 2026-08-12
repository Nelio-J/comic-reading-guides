package com.nelio.comic_reading_guides.repositories;

import com.nelio.comic_reading_guides.domain.entities.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long>, PagingAndSortingRepository<PersonEntity, Long> {
}
