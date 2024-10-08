package fr.agoero.basic.service;

import fr.agoero.basic.domain.Character;
import fr.agoero.basic.exception.common.CustomErrorResponseException;
import fr.agoero.basic.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fr.agoero.basic.exception.CharacterExceptionConstants.CHARACTER_NOT_FOUND_DETAIL;
import static fr.agoero.basic.exception.common.ErrorResponseExceptionEnum.CHARACTER_NOT_FOUND;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

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
        return characterRepository.save(character);
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
        // gestion existe
        findById(character.getId());
        return characterRepository.save(character);
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
        // gestion existe et récupération
        var character = findById(id);
        character.setCode(code);
        return characterRepository.save(character);
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

}
