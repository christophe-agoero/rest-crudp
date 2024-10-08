package fr.agoero.pro.mapper;

import fr.agoero.pro.domain.Character;
import fr.agoero.pro.dto.response.CharacterListDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static fr.agoero.pro.mapper.CharacterMapper.buildDtoListFromEntityList;

/**
 * Classe utilitaire de mapping entre Character et CharacterListDto
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterListMapper {

    /**
     * Permet de construire un CharacterListDto à partir d'une liste de Character
     *
     * @param characterList
     * @return CharacterListDto
     */
    public static CharacterListDto buildListDtoFromEntityList(List<Character> characterList) {
        return new CharacterListDto(
                buildDtoListFromEntityList(characterList),
                characterList.size()
        );
    }

}
