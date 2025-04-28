package org.hrachov.com.filmproject.service;

import org.hrachov.com.filmproject.model.Comment;
import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.Reaction;
import org.hrachov.com.filmproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> getCommentsByMovie(Movie id);
    Page<Comment> getCommentsByMovie(Movie movie, Pageable pageable);
    Comment getCommentById(long id);

    Reaction addReaction(User user, Comment comment, Reaction.ReactionType type);

    long getLikesCount(Comment comment);

    long getDislikesCount(Comment comment);
}
