package fr.agoero.basic.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static fr.agoero.basic.domain.Character.CODE_REGEX;
import static fr.agoero.basic.exception.common.ValidationConstants.NOT_BLANK;
import static fr.agoero.basic.exception.common.ValidationConstants.PATTERN;


@Valid
public record CharacterPatchCodeDto(
        @NotBlank(message = NOT_BLANK)
        @Pattern(regexp = CODE_REGEX, message = PATTERN)
        String code
) {
}
