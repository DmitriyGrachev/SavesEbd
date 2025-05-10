package org.hrachov.com.filmproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hrachov.com.filmproject.model.Film;
import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.Serial;
import org.hrachov.com.filmproject.model.dto.FilmDTO;
import org.hrachov.com.filmproject.repository.FilmRepository;
import org.hrachov.com.filmproject.service.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.hrachov.com.filmproject.model.specification.FilmSpecification.getAllFilmsNew;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final ObjectMapper objectMapper;

    public FilmServiceImpl(FilmRepository filmRepository, ObjectMapper objectMapper) {
        this.filmRepository = filmRepository;
        this.objectMapper = objectMapper;
    }

    public List<Film> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        return films;
    }
    public Film getFilmById(long id) {
        //TODO
        return filmRepository.findById(id).orElse(null);
    }public List<FilmDTO> getAllNewFilms() {
        Specification<Film> spec = getAllFilmsNew();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "releaseYear"));
        Page<Film> page = filmRepository.findAll(spec, pageable);
        List<Film> films = page.getContent();
        System.out.println("Found " + films.size() + " new films");
        return films.stream()
                .map(film -> {
                    FilmDTO dto = objectMapper.convertValue(film, FilmDTO.class);
                    if (film instanceof Movie movie) {
                        dto.setType("movie");
                        dto.setPoster(movie.getPosterPath());
                    } else if (film instanceof Serial serial) {
                        dto.setType("serial");
                        dto.setPoster(serial.getPosterPath() != null ? serial.getPosterPath() : "/images/inception.jpg");
                    } else {
                        dto.setType("film");
                        dto.setPoster("/images/default.jpg");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}