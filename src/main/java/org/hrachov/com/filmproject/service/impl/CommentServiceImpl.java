package org.hrachov.com.filmproject.service.impl;

import org.hrachov.com.filmproject.model.Comment;
import org.hrachov.com.filmproject.model.Movie;
import org.hrachov.com.filmproject.model.Reaction;
import org.hrachov.com.filmproject.model.User;
import org.hrachov.com.filmproject.repository.CommentRepository;
import org.hrachov.com.filmproject.repository.ReactionRepository;
import org.hrachov.com.filmproject.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ReactionRepository reactionRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ReactionRepository reactionRepository) {
        this.commentRepository = commentRepository;
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByMovie(Movie movie){
        return commentRepository.findAllByMovieId(movie.getId());
    }
    @Override
    public Page<Comment> getCommentsByMovie(Movie movie, Pageable pageable){
        return commentRepository.findAllByFilm_Id(movie.getId(), pageable);
    }
    @Override
    public Comment getCommentById(long id) {
        return commentRepository.getCommentById(id);
    }
    @Override
    public Reaction addReaction(User user, Comment comment, Reaction.ReactionType type) {
        Optional<Reaction> existing = reactionRepository.findByUserAndComment(user, comment);
        if (existing.isPresent()) {
            Reaction reaction = existing.get();
            if (reaction.getType() == type) {
                reactionRepository.delete(reaction); // Удаляем, если тот же тип
                return null;
            } else {
                reaction.setType(type); // Меняем тип
                return reactionRepository.save(reaction);
            }
        } else {
            Reaction reaction = new Reaction();
            reaction.setUser(user);
            reaction.setComment(comment);
            reaction.setType(type);
            reaction.setCreatedAt(LocalDateTime.now());
            return reactionRepository.save(reaction);
        }
    }
    @Override
    public long getLikesCount(Comment comment) {
        return reactionRepository.countByCommentAndType(comment, Reaction.ReactionType.LIKE);
    }
    @Override
    public long getDislikesCount(Comment comment) {
        return reactionRepository.countByCommentAndType(comment, Reaction.ReactionType.DISLIKE);
    }
}
