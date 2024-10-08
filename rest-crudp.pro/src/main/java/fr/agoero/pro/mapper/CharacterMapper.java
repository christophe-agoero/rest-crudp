package fr.agoero.pro.mapper;

import fr.agoero.pro.domain.Character;
import fr.agoero.pro.dto.request.CharacterCreateDto;
import fr.agoero.pro.dto.request.CharacterUpdateDto;
import fr.agoero.pro.dto.response.CharacterDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe utilitaire de mapping entre Character et CharacterDto
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterMapper {

    /**
     * Permet de construire une liste CharacterDto à partir d'une liste de Character
     *
     * @param characterList
     * @return List<CharacterDto>
     */
    public static List<CharacterDto> buildDtoListFromEntityList(List<Character> characterList) {
        return characterList.stream()
                            .map(CharacterMapper::buildDtoFromEntity)
                            .toList();
    }

    /**
     * Permet de construire un CharacterDto à partir d'un Character
     *
     * @param character
     * @return CharacterDto
     */
    public static CharacterDto buildDtoFromEntity(Character character) {
        return new CharacterDto(
                character.getId(),
                character.getCode(),
                character.getFirstname(),
                character.getLastname(),
                character.getAppearance()
        );
    }

    /**
     * Permet de construire un Character à partir d'un CharacterCreateDto
     *
     * @param characterCreateDto
     * @return Character
     */
    public static Character buildEntityFromCreateDto(CharacterCreateDto characterCreateDto) {
        return Character.builder()
                        .code(characterCreateDto.code())
                        .firstname(characterCreateDto.firstname())
                        .lastname(characterCreateDto.lastname())
                        .appearance(characterCreateDto.appearance())
                        .build();
    }

    /**
     * Permet de construire un Character à partir d'un CharacterUpdateDto
     *
     * @param characterUpdateDto
     * @return Character
     */
    public static Character buildEntityFromUpdateDto(CharacterUpdateDto characterUpdateDto, int id) {
        return Character.builder()
                        .id(id)
                        .code(characterUpdateDto.code())
                        .firstname(characterUpdateDto.firstname())
                        .lastname(characterUpdateDto.lastname())
                        .appearance(characterUpdateDto.appearance())
                        .build();
    }

}
