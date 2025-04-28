package org.hrachov.com.filmproject.service.impl;

import org.hrachov.com.filmproject.model.Film;
import org.hrachov.com.filmproject.repository.FilmRepository;
import org.hrachov.com.filmproject.service.FilmService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        return films;
    }
    public Film getFilmById(long id) {
        //TODO
        return filmRepository.findById(id).orElse(null);
    }
}
