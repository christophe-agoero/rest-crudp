package fr.agoero.basic.exception.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.net.URI;

import static fr.agoero.basic.exception.CharacterExceptionConstants.CHARACTER_NOT_FOUND_TITLE;
import static fr.agoero.basic.exception.CharacterExceptionConstants.CHARACTER_NOT_FOUND_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


/**
 * Enumeration des réponses d'erreurs
 */
@AllArgsConstructor
@Getter
public enum ErrorResponseExceptionEnum {

    // INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR_RESPONSE(INTERNAL_SERVER_ERROR, null, null),
    // CHARACTER
    // not found
    CHARACTER_NOT_FOUND(NOT_FOUND, CHARACTER_NOT_FOUND_TYPE, CHARACTER_NOT_FOUND_TITLE),
    ;

    private final HttpStatus httpStatus;
    private final URI type;
    private final String title;

}