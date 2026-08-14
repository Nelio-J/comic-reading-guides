package com.nelio.comic_reading_guides.services.impl;

import com.nelio.comic_reading_guides.domain.entities.PersonEntity;
import com.nelio.comic_reading_guides.repositories.PersonRepository;
import com.nelio.comic_reading_guides.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonEntity save(PersonEntity personEntity) {
        return personRepository.save(personEntity);
    }

    @Override
    public Page<PersonEntity> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Optional<PersonEntity> findOne(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return personRepository.existsById(id);
    }

    @Override
    public PersonEntity partialUpdate(Long id, PersonEntity personEntity) {
        return personRepository.findById(id).map(existingPerson -> {
            Optional.ofNullable(personEntity.getName()).ifPresent(existingPerson::setName);
            return personRepository.save(existingPerson);
        }).orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonEntity findOrCreateByName(PersonEntity personEntity) {
        return personRepository.findByName(personEntity.getName())
                        .orElseGet(() -> personRepository.save(personEntity));
    }

}
