package fr.agoero.pro.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static fr.agoero.pro.domain.Character.CODE_REGEX;
import static fr.agoero.pro.exception.common.ValidationConstants.NOT_BLANK;
import static fr.agoero.pro.exception.common.ValidationConstants.PATTERN;


@Valid
public record CharacterCreateDto(
        @NotBlank(message = NOT_BLANK)
        @Pattern(regexp = CODE_REGEX, message = PATTERN)
        String code,

        String firstname,

        @NotBlank
        String lastname,

        @NotNull
        Integer appearance
) {
}
