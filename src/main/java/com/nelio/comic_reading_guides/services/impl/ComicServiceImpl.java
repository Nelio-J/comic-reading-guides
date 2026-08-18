package com.nelio.comic_reading_guides.services.impl;


import com.nelio.comic_reading_guides.domain.entities.ComicEntity;
import com.nelio.comic_reading_guides.exceptions.ResourceNotFoundException;
import com.nelio.comic_reading_guides.repositories.ComicRepository;
import com.nelio.comic_reading_guides.services.CharacterService;
import com.nelio.comic_reading_guides.services.ComicService;
import com.nelio.comic_reading_guides.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComicServiceImpl implements ComicService {

    private final ComicRepository comicRepository;
    private final PersonService personService;
    private final CharacterService characterService;

    public ComicServiceImpl(ComicRepository comicRepository, PersonService personService, CharacterService characterService) {
        this.comicRepository = comicRepository;
        this.personService = personService;
        this.characterService = characterService;
    }

    @Override
    public ComicEntity save(ComicEntity comicEntity) {
        comicEntity.setWriters(
                comicEntity.getWriters().stream()
                        .map(personService::findOrCreateByName)
                        .collect(Collectors.toSet())
        );

        comicEntity.setArtists(
                comicEntity.getArtists().stream()
                        .map(personService::findOrCreateByName)
                        .collect(Collectors.toSet())
        );

        comicEntity.setCharacters(
                comicEntity.getCharacters().stream()
                        .map(characterService::findOrCreateByName)
                        .collect(Collectors.toSet())
        );

        return comicRepository.save(comicEntity);
    }

    @Override
    public Page<ComicEntity> findAll(Pageable pageable) { return comicRepository.findAll(pageable); }

    @Override
    public Optional<ComicEntity> findOne(Long id) { return comicRepository.findById(id); }

    @Override
    public boolean isExists(Long id) { return comicRepository.existsById(id); }

    //Alter/separate writers, artists and characters later so it sends a full list.
    //This way you can append new characters to the existing list or remove some from the list without deleting all the characters.
    @Override
    public ComicEntity partialUpdate(Long id, ComicEntity comicEntity) {
        return comicRepository.findById(id).map(existingComic -> {
            Optional.ofNullable(comicEntity.getTitle()).ifPresent(existingComic::setTitle);
            Optional.ofNullable(comicEntity.getPublisher()).ifPresent(existingComic::setPublisher);
            Optional.ofNullable(comicEntity.getPublicationYear()).ifPresent(existingComic::setPublicationYear);
            Optional.ofNullable(comicEntity.getWriters()).ifPresent(writers -> existingComic.setWriters(
                    writers.stream().map(personService::findOrCreateByName).collect(Collectors.toSet())
            ));
            Optional.ofNullable(comicEntity.getArtists()).ifPresent(artists -> existingComic.setArtists(
                    artists.stream().map(personService::findOrCreateByName).collect(Collectors.toSet())
            ));
            Optional.ofNullable(comicEntity.getCharacters()).ifPresent(characters -> existingComic.setCharacters(
                    characters.stream().map(characterService::findOrCreateByName).collect(Collectors.toSet())
            ));
            return comicRepository.save(existingComic);
        }).orElseThrow(() -> new ResourceNotFoundException("Comic not found!"));
    }

    @Override
    public void delete(Long id) {
        comicRepository.deleteById(id);
    }
}
