package org.hrachov.com.filmproject.service;

import org.hrachov.com.filmproject.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    Film getFilmById(long id);
}
