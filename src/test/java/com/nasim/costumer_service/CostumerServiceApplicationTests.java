package com.nasim.costumer_service;

import com.nasim.costumer_service.client.MovieClient;
import com.nasim.costumer_service.domain.Genre;
import com.nasim.costumer_service.dto.CustomerDto;
import com.nasim.costumer_service.dto.GenreUpdateRequest;
import com.nasim.costumer_service.dto.MovieDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;
import java.util.List;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.cloud.aws.secretsmanager.enabled=false"
)

class CostumerServiceApplicationTests {
	@MockitoBean(name = "movieRestClient")
	private RestClient client;
	@MockitoBean
	private MovieClient movieClient;

	@Value("${local.server.port}")
	private int port;

	private RestClient restClient;

	@BeforeEach
	void setUp() {
		log.info("the server port: {}",port);
		restClient = RestClient.builder()
				.baseUrl("http://localhost:" + port)
				.build();
	}

	private static final Logger log= LoggerFactory.getLogger(CostumerServiceApplicationTests.class);
	@Test
	void contextLoads() {
	}
	@Test
	void health(){
		var responseEntity= restClient.get().uri(
						"/actuator/health"
				).retrieve()
				.toEntity(new ParameterizedTypeReference<Object>(){});
		Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
	}
	public void customerWithMovies(){
		Mockito.when(movieClient.getAllMovieByGenre(Mockito.any(Genre.class))).thenReturn(
				List.of(
						new MovieDto(1,"movie-1",1987,Genre.ACTION),
						new MovieDto(2,"movie-2",1987,Genre.ACTION)
				)
		);

		var responseEntity= restClient.get().uri(
						"/api/customers/1"
				).retrieve()
				.toEntity(new ParameterizedTypeReference<CustomerDto>(){});
		Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		var customer=responseEntity.getBody();
		Assertions.assertNotNull(customer);
		Assertions.assertEquals(2,customer.recommendedMovies().size());
	}
	@Test
	void customerNotFoundScenario() {
		var responseEntity = restClient.get()
				.uri("/api/customers/1000")
				.retrieve()
				.onStatus(
						status -> status.value() == HttpStatus.NOT_FOUND.value(),
						(request, response) -> {
						}
				)
				.toEntity(ProblemDetail.class);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		var problemDetail = responseEntity.getBody();
		Assertions.assertNotNull(problemDetail);
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
		Assertions.assertEquals("Customer not found", problemDetail.getTitle());
		Assertions.assertEquals("the customer with id=1000 not found", problemDetail.getDetail());

		log.info("customer not found: {}", problemDetail);
	}
	@Test
	public void updategenre(){
		GenreUpdateRequest request=new GenreUpdateRequest(Genre.DRAMA);
		var responseEntity= restClient.patch().uri(
						"/api/customers/1/genre"
				).contentType(MediaType.APPLICATION_JSON)
				.body(request)
				.retrieve().toEntity(
						new ParameterizedTypeReference<Object>() {
						}
				);
		Assertions.assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
	}

}
