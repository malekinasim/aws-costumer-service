package com.nasim.costumer_service.client;

import com.nasim.costumer_service.domain.Genre;
import com.nasim.costumer_service.dto.MovieDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import java.util.List;

@Component
public class MovieClient {
    private static final Logger log= LoggerFactory.getLogger(MovieClient.class);
    private  final RestClient client;

    public MovieClient(@Qualifier("movieRestClient") RestClient client) {
        this.client = client;
    }

    public  List<MovieDto> getAllMovieByGenre(Genre genre) {
        log.info("genre: {}",genre);
        var list=  client.get()
                .uri("/api/movies/{genre}",genre)
                .retrieve()
                .body(new ParameterizedTypeReference<List<MovieDto>>() {});
        log.info("receive movie: {} ",list);
        return  list;
    }
}
