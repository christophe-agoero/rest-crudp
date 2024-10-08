package fr.agoero.pro.repository.specification;


import fr.agoero.pro.domain.Character;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static fr.agoero.pro.repository.metamodel.Character_.*;

/**
 * Classe de spécification pour Character
 */
@SuppressWarnings("rawtypes")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterSpecification {

    public static Specification<Character> idEqual(final String id) {
        return (root, query, criteriaBuilder) -> getIdEqual(id, root, criteriaBuilder);
    }

    static Predicate getIdEqual(final String id, final From from, final CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(from.get(ID), id);
    }

    public static Specification<Character> codeEqual(final String code) {
        return (root, query, criteriaBuilder) -> getCodeEqual(code, root, criteriaBuilder);
    }

    static Predicate getCodeEqual(final String code, final From from, final CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(from.get(CODE), code);
    }

    public static Specification<Character> firstnameEqual(final String firstname) {
        return (root, query, criteriaBuilder) -> getFirstnameEqual(firstname, root, criteriaBuilder);
    }

    static Predicate getFirstnameEqual(final String firstname, final From from, final CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(from.get(FIRSTNAME), firstname);
    }

    public static Specification<Character> lastnameEqual(final String lastname) {
        return (root, query, criteriaBuilder) -> getLastnameEqual(lastname, root, criteriaBuilder);
    }

    static Predicate getLastnameEqual(final String lastname, final From from, final CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(from.get(LASTNAME), lastname);
    }

    public static Specification<Character> appearanceEqual(final int appearance) {
        return (root, query, criteriaBuilder) -> getAppearanceEqual(appearance, root, criteriaBuilder);
    }

    static Predicate getAppearanceEqual(final int appearance, final From from, final CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(from.get(APPEARANCE), appearance);
    }

}
