package org.hrachov.com.filmproject.service;

import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.dto.MovieDTO;
import org.hrachov.com.filmproject.model.dto.PagedResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();

    Movie getMovieById(long id);

    public List<Movie> getMovieByTitle(String title);

    public List<Movie> getAllMovies(String sortBy, String sortDir);
    public PagedResponseDTO<MovieDTO> getAllMoviesWithPageable(String sortBy, String sortDir, int page, int size);
}
