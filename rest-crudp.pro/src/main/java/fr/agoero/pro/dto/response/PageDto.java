package fr.agoero.pro.dto.response;

import java.util.List;

public record PageDto<D>(
        List<D> content,
        int page,
        int size,
        String sort,
        int pageElements,
        long totalElements,
        int totalPages) {
}
