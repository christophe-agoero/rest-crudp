package fr.agoero.basic.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;

import static fr.agoero.basic.exception.common.ExceptionConstants.buildNotFoundTitle;
import static fr.agoero.basic.exception.common.ExceptionConstants.buildNotFoundType;

/**
 * Classe de constante pour les exceptions Character
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterExceptionConstants {

    public static final String CHARACTER_NOT_FOUND_DETAIL = "Le personnage avec l'identifiant' %d n'a pas été trouvé";
    private static final String RESOURCE_NAME = Character.class.getSimpleName();
    // not found
    public static final URI CHARACTER_NOT_FOUND_TYPE = buildNotFoundType(RESOURCE_NAME);
    public static final String CHARACTER_NOT_FOUND_TITLE = buildNotFoundTitle(RESOURCE_NAME);
}