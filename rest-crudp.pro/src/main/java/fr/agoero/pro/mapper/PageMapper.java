package fr.agoero.pro.mapper;

import fr.agoero.pro.dto.response.PageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageMapper {

    /**
     * Permet de construire un PageDto à partir d'un Page
     *
     * @param page
     * @param toDtoListFunction
     * @return PageDto
     */
    public static <E, D> PageDto<D> buildPageDtoFromPage(Page<E> page, ToDtoListFunction<E, D> toDtoListFunction) {
        return new PageDto<>(
                toDtoListFunction.build(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getSort().toString(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages());

    }

}
