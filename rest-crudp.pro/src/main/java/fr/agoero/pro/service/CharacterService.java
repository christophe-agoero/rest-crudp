package fr.agoero.pro.service;

import fr.agoero.pro.domain.Character;
import fr.agoero.pro.exception.common.CustomErrorResponseException;
import fr.agoero.pro.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fr.agoero.pro.exception.CharacterExceptionConstants.CHARACTER_NOT_FOUND_DETAIL;
import static fr.agoero.pro.exception.common.ErrorResponseExceptionEnum.*;
import static fr.agoero.pro.exception.common.InvalidPaginationExceptionConstants.INVALID_PAGINATION_MAX_SIZE_DETAIL;
import static fr.agoero.pro.util.ValidationUtil.checkAttributAndThrow;
import static fr.agoero.pro.util.ValidationUtil.checkObjectAndThrow;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

    // ATTENTION cette valeur doit être supérieure ou égale à la valeur du controller
    public static final int PAGE_MAX_SIZE = 100;

    private final CharacterRepository characterRepository;

    /**
     * Méthode permettant de retourner la liste de personnage
     *
     * @return la liste de personnage
     */
    public List<Character> findAll() {
        log.info("Récupération de tous les personnages");
        return characterRepository.findAll();
    }

    /**
     * Méthode permettant de retourner un personnage par identifiant
     *
     * @param id
     * @return un personnage
     */
    public Character findById(int id) {
        log.info("Récupération du personnage {}", id);
        return characterRepository.findById(id).orElseThrow(
                () -> new CustomErrorResponseException(
                        CHARACTER_NOT_FOUND,
                        String.format(CHARACTER_NOT_FOUND_DETAIL, id)
                )
        );
    }

    /**
     * Méthode permettant de créer un personnage
     *
     * @param character
     * @return character
     */
    @Transactional
    public Character create(Character character) {
        log.info("Création du personnage {}", character.getCode());
        checkObjectAndThrow(CHARACTER_CONSTRAINT_VIOLATION, character);
        return characterRepository.persist(character);
    }

    /**
     * Méthode permettant de mettre à jour un personnage
     *
     * @param character
     * @return character
     */
    @Transactional
    public Character update(Character character) {
        log.info("Mise à jour du personnage {}", character.getId());
        checkObjectAndThrow(CHARACTER_CONSTRAINT_VIOLATION, character);
        // gestion existe
        findById(character.getId());
        return characterRepository.merge(character);
    }

    /**
     * Méthode permettant de mettre à jour le code d'un personnage
     *
     * @param id
     * @param code
     * @return character
     */
    @Transactional
    public Character patchCode(int id, String code) {
        log.info("Mise à jour du code du personnage {}", id);
        checkAttributAndThrow(CHARACTER_CONSTRAINT_VIOLATION, Character.class, "code", code);
        // gestion existe et récupération
        var character = findById(id);
        character.setCode(code);
        return character;
    }

    /**
     * Méthode permettant de supprimer une personnage
     *
     * @param id
     */
    @Transactional
    public void delete(int id) {
        log.info("Suppression du personnage {}", id);
        characterRepository.deleteById(id);
    }

    /**
     * Méthode permettant de retourner une page de propriétés
     *
     * @param specification
     * @param pageable
     * @return une page de propriétés
     */
    public Page<Character> findAllPageSpecification(Specification<Character> specification, Pageable pageable) {
        log.info("Recherche une page de  propriétés");
        if (pageable.getPageSize() > PAGE_MAX_SIZE) {
            throw new CustomErrorResponseException(
                    INVALID_PAGINATION_MAX_SIZE,
                    String.format(
                            INVALID_PAGINATION_MAX_SIZE_DETAIL,
                            PAGE_MAX_SIZE
                    )
            );
        }
        return characterRepository.findAll(specification, pageable);
    }

}
