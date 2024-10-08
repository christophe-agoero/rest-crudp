package fr.agoero.pro.controller.util;

import fr.agoero.pro.domain.Character;
import fr.agoero.pro.exception.common.CustomErrorResponseException;
import fr.agoero.pro.service.CharacterService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static fr.agoero.pro.controller.util.PageUtil.*;
import static fr.agoero.pro.exception.common.ErrorResponseExceptionEnum.INVALID_PAGINATION_ATTRIBUT;
import static fr.agoero.pro.exception.common.InvalidPaginationExceptionConstants.INVALID_PAGINATION_ATTRIBUT_DETAIL;
import static fr.agoero.pro.repository.metamodel.Character_.*;
import static fr.agoero.pro.repository.specification.CharacterSpecification.*;
import static fr.agoero.pro.repository.specification.util.SpecificationUtil.initOrAndSpecification;


/**
 * Classe utilitaire pour les controllers Character
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterControllerUtil {

    private static final List<String> ATTRIBUT_LIST = List.of(ID, CODE, FIRSTNAME, LASTNAME, APPEARANCE);

    /**
     * Permet de renvoyer une page de Character
     *
     * @param pageable
     * @param paramMap
     * @param characterService
     * @return une page de Character
     * @throws CustomErrorResponseException si les attributs de pagination de tri, filtre sont invalides
     */
    public static Page<Character> getPage(
            Pageable pageable, Map<String, String> paramMap, CharacterService characterService) {
        Specification<Character> specification = null;
        for (Entry<String, String> entry : paramMap.entrySet()) {
            switch (entry.getKey()) {
                case ID ->
                        specification = initOrAndSpecification(specification, idEqual(paramMap.get(ID)));
                case CODE ->
                        specification = initOrAndSpecification(specification, codeEqual(paramMap.get(CODE)));
                case FIRSTNAME ->
                        specification = initOrAndSpecification(specification, firstnameEqual(paramMap.get(FIRSTNAME)));
                case LASTNAME ->
                        specification = initOrAndSpecification(specification, lastnameEqual(paramMap.get(LASTNAME)));
                case APPEARANCE ->
                        specification = initOrAndSpecification(specification, appearanceEqual(
                                        checkValueAndGet(
                                                APPEARANCE,
                                                paramMap.get(APPEARANCE),
                                                PageUtil::stringToIntPrimitive,
                                                Character.class,
                                                Integer.class)
                                )
                        );
                default -> {
                    if (!isPageParameter(entry.getKey())) {
                        throw new CustomErrorResponseException(
                                INVALID_PAGINATION_ATTRIBUT,
                                String.format(
                                        INVALID_PAGINATION_ATTRIBUT_DETAIL,
                                        entry.getKey(),
                                        Character.class.getSimpleName()
                                ));
                    }
                }
            }
        }
        checkSortAttribut(pageable.getSort(), Character.class, ATTRIBUT_LIST);
        return characterService.findAllPageSpecification(specification, pageable);
    }

}
