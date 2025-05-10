package org.hrachov.com.filmproject.controller;

import org.hrachov.com.filmproject.model.Comment;
import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.dto.CommentDTO;
import org.hrachov.com.filmproject.model.dto.GenreDTO;
import org.hrachov.com.filmproject.model.dto.MovieDTO;
import org.hrachov.com.filmproject.repository.MovieRepository;
import org.hrachov.com.filmproject.service.CommentService;
import org.hrachov.com.filmproject.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;
    private final CommentService commentService;

    public MovieController(CommentService commentService, MovieService movieService) {
        this.commentService = commentService;
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        // Convert to DTO to avoid Hibernate proxy serialization issues
        List<Comment> comments = commentService.getCommentsByMovie(movie);
        MovieDTO movieDTO = convertToDTO(movie, comments);

        return ResponseEntity.ok(movieDTO);
    }

    public static MovieDTO convertToDTO(Movie movie, List<Comment> comments) {
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

        // Convert comments to DTOs
        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setText(comment.getText());
                    commentDTO.setTime(comment.getTime());
                    return commentDTO;
                })
                .sorted(Comparator.comparing(CommentDTO::getTime).reversed())
                .collect(Collectors.toList());
        dto.setComments(commentDTOs);

        // Convert genres to DTOs
        List<GenreDTO> genreDTOs = movie.getGenres().stream()
                .map(genre -> new GenreDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
        dto.setGenres(genreDTOs);

        return dto;
    }
}