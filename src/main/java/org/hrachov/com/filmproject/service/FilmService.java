package org.hrachov.com.filmproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hrachov.com.filmproject.model.Film;
import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.Serial;
import org.hrachov.com.filmproject.model.dto.FilmDTO;
import org.hrachov.com.filmproject.repository.FilmRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.hrachov.com.filmproject.model.specification.FilmSpecification.getAllFilmsNew;

public interface FilmService {
     List<Film> getAllFilms();
     Film getFilmById(long id);
     List<FilmDTO> getAllNewFilms();
}