package fr.agoero.pro.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlConstants {

    // CHARACTERS
    public static final String CHARACTERS_URI = "/characters";
    public static final String CHARACTER_BY_ID = "/{id}";
    public static final String CHARACTER_PATCH_CODE = CHARACTER_BY_ID + "/code";
    public static final String CHARACTER_PAGE = "/page";


}
