package fr.agoero.basic.exception.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Classe de constante pour les exceptions de validation
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstants {


    public static final String NOT_BLANK = "ne doit pas être vide";
    public static final String PATTERN_REGEX_VALUE = "{regexp}";
    public static final String PATTERN = "doit correspondre à " + PATTERN_REGEX_VALUE;

}