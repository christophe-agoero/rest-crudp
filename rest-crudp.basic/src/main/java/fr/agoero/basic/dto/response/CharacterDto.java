package fr.agoero.basic.dto.response;

public record CharacterDto(
        int id,

        String code,

        String firstname,

        String lastname,

        int appearance

) {
}
