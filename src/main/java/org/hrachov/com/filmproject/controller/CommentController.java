package org.hrachov.com.filmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hrachov.com.filmproject.model.*;
import org.hrachov.com.filmproject.model.dto.CommentDTO;
import org.hrachov.com.filmproject.service.CommentService;
import org.hrachov.com.filmproject.service.MovieService;
import org.hrachov.com.filmproject.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*") // Для теста, потом укажи порт фронтенда
public class CommentController {
    private final CommentService commentService;
    private final MovieService movieService;
    private final UserService userService;

    public CommentController(CommentService commentService, MovieService movieService, UserService userService) {
        this.commentService = commentService;
        this.movieService = movieService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addComment (@RequestBody CommentDTO commentDTO) {

        if (commentDTO.getText() == null || commentDTO.getText().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment text cannot be empty");
        }

        Film film = movieService.getMovieById(commentDTO.getFilmId());

        if (film == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }

        User user = userService.findUserById(commentDTO.getUserId());

        if (commentDTO.getUserId() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setFilm(film);
        comment.setUser(user);
        // time устанавливается через @CreationTimestamp
        Comment savedComment = commentService.addComment(comment);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Comment added");
        response.put("commentId", savedComment.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getAllComments(@RequestParam(required = false) Long movieId,
                                                           @RequestParam(required = true, defaultValue = "0") int page,
                                                           @RequestParam(required = true, defaultValue = "5") int size,
                                                           @RequestParam(required = true,defaultValue = "time,desc") String sort) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        String[] parts = sort.split(",");

        Sort sortDir = Sort.by(Sort.Direction.fromString(parts[1]),parts[0]);

        Pageable pageable = PageRequest.of(page, size, sortDir);
        Page<Comment> commentPage = commentService.getCommentsByMovie(movie, pageable);
        Page<CommentDTO> commentDTOPage = commentPage.map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setUserId(comment.getUser().getId());
            dto.setFilmId(comment.getFilm().getId());
            dto.setText(comment.getText());
            dto.setTime(comment.getTime());
            dto.setLikes(commentService.getLikesCount(comment));
            dto.setDislikes(commentService.getDislikesCount(comment));
            return dto;
        });

        return ResponseEntity.ok(commentDTOPage);
    }
    @PostMapping("/{commentId}/reactions")
    public ResponseEntity<Map<String, String>> addReaction(
            @PathVariable Long commentId,
            @RequestBody Map<String, String> request
    ) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        User user = userService.findUserById(1L); // Хардкод для теста
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }
        String type = request.get("type");
        if (!type.equals("LIKE") && !type.equals("DISLIKE")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid reaction type");
        }

        Reaction.ReactionType reactionType = Reaction.ReactionType.valueOf(type);
        commentService.addReaction(user, comment, reactionType);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Reaction added");
        return ResponseEntity.ok(response);
    }
}