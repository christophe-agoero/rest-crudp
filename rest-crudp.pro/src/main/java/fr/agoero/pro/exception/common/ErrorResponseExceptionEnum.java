package fr.agoero.pro.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static fr.agoero.pro.exception.CharacterExceptionConstants.*;
import static fr.agoero.pro.exception.common.InvalidPaginationExceptionConstants.*;
import static org.springframework.http.HttpStatus.*;


/**
 * Enumeration des réponses d'erreurs
 */
@AllArgsConstructor
@Getter
public enum ErrorResponseExceptionEnum {

    // INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR_RESPONSE(INTERNAL_SERVER_ERROR, null, null),
    // PAGINATION
    // attribut
    INVALID_PAGINATION_ATTRIBUT(BAD_REQUEST, INVALID_PAGINATION_ATTRIBUT_TYPE, INVALID_PAGINATION_ATTRIBUT_TITLE),
    // value
    INVALID_PAGINATION_VALUE(BAD_REQUEST, INVALID_PAGINATION_VALUE_TYPE, INVALID_PAGINATION_VALUE_TITLE),
    // max size
    INVALID_PAGINATION_MAX_SIZE(BAD_REQUEST, INVALID_PAGINATION_MAX_SIZE_TYPE, INVALID_PAGINATION_MAX_SIZE_TITLE),
    // CHARACTER
    // not found
    CHARACTER_NOT_FOUND(NOT_FOUND, CHARACTER_NOT_FOUND_TYPE, CHARACTER_NOT_FOUND_TITLE),
    // constraint violation
    CHARACTER_CONSTRAINT_VIOLATION(BAD_REQUEST, CHARACTER_CONSTRAINT_VIOLATION_TYPE, CHARACTER_CONSTRAINT_VIOLATION_TITLE),
    ;

    private final HttpStatus httpStatus;
    private final URI type;
    private final String title;

}