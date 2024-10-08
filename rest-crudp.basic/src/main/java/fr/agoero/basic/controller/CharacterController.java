package fr.agoero.basic.controller;

import fr.agoero.basic.dto.request.CharacterCreateDto;
import fr.agoero.basic.dto.request.CharacterPatchCodeDto;
import fr.agoero.basic.dto.request.CharacterUpdateDto;
import fr.agoero.basic.dto.response.CharacterDto;
import fr.agoero.basic.mapper.CharacterMapper;
import fr.agoero.basic.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static fr.agoero.basic.mapper.CharacterMapper.*;
import static fr.agoero.basic.util.UrlConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Contrôleur pour les personnages
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = CHARACTERS_URI)
@Slf4j
public class CharacterController {

    private final CharacterService characterService;

    /**
     * Récupérer tous les personnages
     *
     * @return List<CharacterDto>
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CharacterDto>> findAll() {
        return ResponseEntity.ok(
                characterService.findAll()
                                .stream()
                                .map(CharacterMapper::buildDtoFromEntity)
                                .toList()
        );
    }

    /**
     * Récupérer un personnage par son identifiant
     *
     * @param id
     * @return CharacterDto
     */
    @GetMapping(value = CHARACTER_BY_ID, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CharacterDto> findById(@PathVariable int id) {
        return ResponseEntity.ok(buildDtoFromEntity(characterService.findById(id)));
    }

    /**
     * Créer un personnage
     *
     * @param characterCreateDto
     * @return Void
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody CharacterCreateDto characterCreateDto) {
        var character = characterService.create(buildEntityFromCreateDto(characterCreateDto));
        return ResponseEntity
                .created(URI.create(CHARACTERS_URI + "/" + character.getId()))
                .build();
    }

    /**
     * Modifier un personnage
     *
     * @param characterUpdateDto
     * @param id
     * @return Void
     */
    @PutMapping(value = CHARACTER_BY_ID, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(
            @Valid @RequestBody CharacterUpdateDto characterUpdateDto,
            @PathVariable int id) {
        characterService.update(buildEntityFromUpdateDto(characterUpdateDto, id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Modifier le nom d'un personnage
     *
     * @param characterPatchCodeDto
     * @param id
     * @return Void
     */
    @PatchMapping(value = CHARACTER_PATCH_CODE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchCode(
            @Valid @RequestBody CharacterPatchCodeDto characterPatchCodeDto,
            @PathVariable int id) {
        characterService.patchCode(id, characterPatchCodeDto.code());
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprimer un personnage par son identifiant
     *
     * @param id
     * @return Void
     */
    @DeleteMapping(CHARACTER_BY_ID)
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
