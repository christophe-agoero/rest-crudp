package fr.agoero.pro.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static fr.agoero.pro.util.DtoConstants.CHARACTER_LIST_DTO;


public record CharacterListDto(
        @JsonProperty(CHARACTER_LIST_DTO)
        List<CharacterDto> characterDtoList,
        int total) {
}
