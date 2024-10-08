package fr.agoero.pro.controller;

import fr.agoero.pro.dto.request.CharacterCreateDto;
import fr.agoero.pro.dto.request.CharacterPatchCodeDto;
import fr.agoero.pro.dto.request.CharacterUpdateDto;
import fr.agoero.pro.dto.response.CharacterDto;
import fr.agoero.pro.dto.response.CharacterListDto;
import fr.agoero.pro.dto.response.PageDto;
import fr.agoero.pro.exception.common.CustomErrorResponseException;
import fr.agoero.pro.mapper.CharacterMapper;
import fr.agoero.pro.service.CharacterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static fr.agoero.pro.controller.util.CharacterControllerUtil.getPage;
import static fr.agoero.pro.domain.Character.CODE_REGEX;
import static fr.agoero.pro.exception.common.ErrorResponseExceptionEnum.INVALID_PAGINATION_MAX_SIZE;
import static fr.agoero.pro.exception.common.InvalidPaginationExceptionConstants.INVALID_PAGINATION_MAX_SIZE_DETAIL;
import static fr.agoero.pro.exception.common.ValidationConstants.PATTERN;
import static fr.agoero.pro.mapper.CharacterListMapper.buildListDtoFromEntityList;
import static fr.agoero.pro.mapper.CharacterMapper.*;
import static fr.agoero.pro.mapper.PageMapper.buildPageDtoFromPage;
import static fr.agoero.pro.repository.metamodel.Character_.ID;
import static fr.agoero.pro.util.UrlConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;


/**
 * Contrôleur pour les personnages
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = CHARACTERS_URI)
@Slf4j
public class CharacterController {

    public static final int PAGE_DEFAULT_SIZE = 10;
    public static final int PAGE_MAX_SIZE = 20;
    public static final int PAGE_DEFAULT_PAGE = 0;
    public static final String PAGE_DEFAULT_ATTRIBUTE = ID;

    private final CharacterService characterService;

    /**
     * Récupérer tous les personnages
     *
     * @return List<CharacterDto>
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CharacterListDto> findAll() {
        return ResponseEntity.ok().body(buildListDtoFromEntityList(characterService.findAll()));
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
    @SuppressWarnings("DataFlowIssue")
    public ResponseEntity<Void> create(@Valid @RequestBody CharacterCreateDto characterCreateDto) {
        var character = characterService.create(buildEntityFromCreateDto(characterCreateDto));
        return ResponseEntity
                .created(URI.create(
                                fromMethodCall(on(this.getClass()).findById(character.getId()))
                                        .build()
                                        .getPath()
                        )
                )
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

    /**
     * Juste pour montrer handleConstraintViolationException
     *
     * @return Void
     */
    @GetMapping(value = "test/{code}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> testHandleConstraintViolationException(
            @PathVariable @Pattern(regexp = CODE_REGEX, message = PATTERN) String code
    ) {
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupérer les personnages en paginée
     *
     * @param pageable
     * @param paramMap
     * @return PageDto<CharacterDto>
     */
    @GetMapping(value = CHARACTER_PAGE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageDto<CharacterDto>> findAllPageCriteria(
            @SuppressWarnings("DefaultAnnotationParam")
            @SortDefault(sort = PAGE_DEFAULT_ATTRIBUTE)
            @PageableDefault(value = PAGE_DEFAULT_SIZE, page = PAGE_DEFAULT_PAGE) Pageable pageable,
            @RequestParam Map<String, String> paramMap) {
        if (pageable.getPageSize() > PAGE_MAX_SIZE) {
            throw new CustomErrorResponseException(
                    INVALID_PAGINATION_MAX_SIZE,
                    String.format(
                            INVALID_PAGINATION_MAX_SIZE_DETAIL,
                            PAGE_MAX_SIZE
                    )
            );
        }
        return ResponseEntity.ok(
                buildPageDtoFromPage(
                        getPage(pageable, paramMap, characterService),
                        CharacterMapper::buildDtoListFromEntityList
                )
        );
    }


}
