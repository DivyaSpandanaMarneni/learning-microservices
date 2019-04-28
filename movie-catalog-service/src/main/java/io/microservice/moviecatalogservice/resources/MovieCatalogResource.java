package io.microservice.moviecatalogservice.resources;

import io.microservice.moviecatalogservice.models.CatalogItem;
import io.microservice.moviecatalogservice.models.Movie;
import io.microservice.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // RestTemplate restTemplate = new RestTemplate();

        List<Rating> ratings = Arrays.asList(
                new Rating("Movie1234", 4),
                new Rating("Movie2345", 2),
                new Rating("Movie3412", 3),
                new Rating("Movie4123", 4)
        );

        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);

            return new CatalogItem(movie.getName(), movie.getMovieId(), rating.getRating());
        })
        .collect(Collectors.toList());

        // get all related movie ids

        // for each movie id, call movie info service and get details

        // put them all together
    }
}
