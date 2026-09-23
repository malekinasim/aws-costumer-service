package com.nasim.costumer_service.dto;
import com.nasim.costumer_service.domain.Genre;
import java.util.List;

public record CustomerDto(Integer id, String title,
                          Genre favoriteGenre, List<MovieDto> recommendedMovies){
}
