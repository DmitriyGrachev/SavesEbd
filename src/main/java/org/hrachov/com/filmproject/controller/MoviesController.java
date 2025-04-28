package org.hrachov.com.filmproject.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hrachov.com.filmproject.model.Comment;
import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.dto.*;
import org.hrachov.com.filmproject.model.specification.MovieSpecification;
import org.hrachov.com.filmproject.repository.MovieRepository;
import org.hrachov.com.filmproject.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    public MoviesController(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<PagedResponseDTO<MovieDTO>> getMovies(
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagedResponseDTO<MovieDTO> movies = movieService.getAllMoviesWithPageable(sortBy, sortDir, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(movies);

    }

    @PostMapping("/search")
    public ResponseEntity<List<Movie>> getMovieByGenre(@RequestBody SearchRequestDto searchRequestDto) {

        Specification<Movie> spec = MovieSpecification.withFilters(searchRequestDto);
        List<Movie> movies = movieRepository.findAll(spec);
        return ResponseEntity.ok(movies);
    }

    public static MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDescription(movie.getDescription());
        dto.setDirector(movie.getDirector());
        dto.setRating(movie.getRating());
        dto.setDuration(movie.getDuration());
        dto.setSource(movie.getSource());
        dto.setPoster(movie.getPosterPath());
        // Convert genres to DTOs

        List<GenreDTO> genreDTOs = movie.getGenres().stream()
                .map(genre -> new GenreDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
        dto.setGenres(genreDTOs);

        return dto;
    }


}
