package org.hrachov.com.filmproject.service.impl;

import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.dto.MovieDTO;
import org.hrachov.com.filmproject.model.dto.PagedResponseDTO;
import org.hrachov.com.filmproject.repository.MovieRepository;
import org.hrachov.com.filmproject.service.MovieService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.hrachov.com.filmproject.controller.MoviesController.convertToDTO;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public Movie getMovieById(long id) {
        return movieRepository.getReferenceById(id);
    }
    public List<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
    public List<Movie> getAllMovies(String sortBy, String sortDir) {

        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDir).orElse(Sort.Direction.ASC);
        return movieRepository.findAll(Sort.by(direction, sortBy));
    }
    public PagedResponseDTO<MovieDTO> getAllMoviesWithPageable(String sortBy, String sortDir, int page, int size) {
        // Валидация параметров
        List<String> validSortFields = Arrays.asList("title", "releaseYear", "rating", "duration");
        if (!validSortFields.contains(sortBy.toLowerCase())) {
            sortBy = "title";
        }
        if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
            sortDir = "asc";
        }

        // Определяем направление сортировки
        Sort.Direction direction = Sort.Direction.fromString(sortDir.toUpperCase());

        // Создаём объект Pageable
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Получаем страницу фильмов
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        // Преобразуем в DTO
        List<MovieDTO> movieDTOs = moviePage.getContent().stream()
                .map(movie -> convertToDTO(movie))
                .collect(Collectors.toList());
        // Создаём объект ответа
        return new PagedResponseDTO<>(
                movieDTOs,
                moviePage.getNumber(),
                moviePage.getSize(),
                moviePage.getTotalElements(),
                moviePage.getTotalPages()
        );
    }
}
