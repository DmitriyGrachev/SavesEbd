package org.hrachov.com.filmproject.controller;

import org.hrachov.com.filmproject.service.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hrachov.com.filmproject.model.dto.FilmDTO;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmsController {

    private final FilmService filmService;

    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/newFilms")
    public ResponseEntity<List<FilmDTO>> newFilms() {
        return ResponseEntity.ok(filmService.getAllNewFilms());
    }
}