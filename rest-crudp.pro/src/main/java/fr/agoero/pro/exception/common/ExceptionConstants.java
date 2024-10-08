package fr.agoero.pro.exception.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionConstants {


    public static final String SUFFIX_TYPE = "https://api.jug.fr/errors/";
    public static final String CONSTRAINT_VIOLATION_TYPE = SUFFIX_TYPE + "%s-constraint-violation";
    public static final String NOT_FOUND_TYPE = SUFFIX_TYPE + "%s-not-found";
    public static final String ALREADY_EXIST_TYPE = SUFFIX_TYPE + "%s-already-exist";
    public static final String CONSTRAINT_VIOLATION_TITLE = "%s-CONSTRAINT-VIOLATION";
    public static final String NOT_FOUND_TITLE = "%s-NOT-FOUND";
    public static final String ALREADY_EXIST_TITLE = "%s-ALREADY-EXIST";

    public static URI buildConstraintViolationType(String resourceName) {
        return URI.create(String.format(CONSTRAINT_VIOLATION_TYPE, resourceName.toLowerCase()));
    }

    public static URI buildNotFoundType(String resourceName) {
        return URI.create(String.format(NOT_FOUND_TYPE, resourceName.toLowerCase()));
    }

    public static URI buildAlreadyExistType(String resourceName) {
        return URI.create(String.format(ALREADY_EXIST_TYPE, resourceName.toLowerCase()));
    }

    public static String buildConstraintViolationTitle(String resourceName) {
        return String.format(CONSTRAINT_VIOLATION_TITLE, resourceName.toUpperCase());
    }

    public static String buildNotFoundTitle(String resourceName) {
        return String.format(NOT_FOUND_TITLE, resourceName.toUpperCase());
    }

    public static String buildAlreadyExistTitle(String resourceName) {
        return String.format(ALREADY_EXIST_TITLE, resourceName.toUpperCase());
    }
}