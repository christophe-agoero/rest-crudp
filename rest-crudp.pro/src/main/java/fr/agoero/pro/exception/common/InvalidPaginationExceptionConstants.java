package fr.agoero.pro.exception.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;

import static fr.agoero.pro.exception.common.ExceptionConstants.SUFFIX_TYPE;

/**
 * Classe de constante pour les exceptions de pagination
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InvalidPaginationExceptionConstants {

    // ATTRIBUT
    public static final URI INVALID_PAGINATION_ATTRIBUT_TYPE = URI.create(SUFFIX_TYPE + "invalid-pagination-attribute");
    public static final String INVALID_PAGINATION_ATTRIBUT_TITLE = "INVALID-PAGINATION-ATTRIBUTE";
    public static final String INVALID_PAGINATION_ATTRIBUT_DETAIL = "L'attribut %s n'existe pas dans la ressource %s";
    // VALUE
    public static final URI INVALID_PAGINATION_VALUE_TYPE = URI.create(SUFFIX_TYPE + "invalid-pagination-value");
    public static final String INVALID_PAGINATION_VALUE_TITLE = "INVALID-PAGINATION-VALUE";
    public static final String INVALID_PAGINATION_VALUE_DETAIL = "L'attribut %s de la ressource %s a une valeur invalide," +
            "  classe attendue : %s valeur : %s";
    // MAX SIZE
    public static final URI INVALID_PAGINATION_MAX_SIZE_TYPE = URI.create(SUFFIX_TYPE + "invalid-pagination-max-size");
    public static final String INVALID_PAGINATION_MAX_SIZE_TITLE = "INVALID-PAGINATION-MAX-SIZE";
    public static final String INVALID_PAGINATION_MAX_SIZE_DETAIL = "doit être inférieur ou égal à %d";

}