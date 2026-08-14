package com.nelio.comic_reading_guides.services.impl;

import com.nelio.comic_reading_guides.domain.entities.CharacterEntity;
import com.nelio.comic_reading_guides.repositories.CharacterRepository;
import com.nelio.comic_reading_guides.services.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public CharacterEntity save(CharacterEntity characterEntity) {
        return characterRepository.save(characterEntity);
    }

    @Override
    public Page<CharacterEntity> findAll(Pageable pageable) {
        return characterRepository.findAll(pageable);
    }

    @Override
    public Optional<CharacterEntity> findOne(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return characterRepository.existsById(id);
    }

    @Override
    public CharacterEntity partialUpdate(Long id, CharacterEntity characterEntity) {
        return characterRepository.findById(id).map(existingCharacter -> {
            Optional.ofNullable(characterEntity.getName()).ifPresent(existingCharacter::setName);
            return characterRepository.save(existingCharacter);
        }).orElseThrow(() -> new RuntimeException("Character not found"));
    }

    @Override
    public void delete(Long id) {
        characterRepository.deleteById(id);
    }

    @Override
    public CharacterEntity findOrCreateByName(CharacterEntity characterEntity) {
        return characterRepository.findByName(characterEntity.getName())
                .orElseGet(() ->  characterRepository.save(characterEntity));
    }

}
