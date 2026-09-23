package com.nasim.costumer_service.dto;

import com.nasim.costumer_service.domain.Genre;

public record MovieDto(Integer id,String name, Integer releaseYear, Genre genre) {
}
