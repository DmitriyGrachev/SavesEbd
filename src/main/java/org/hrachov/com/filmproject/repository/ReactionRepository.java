package org.hrachov.com.filmproject.repository;

import org.hrachov.com.filmproject.model.Comment;
import org.hrachov.com.filmproject.model.Reaction;
import org.hrachov.com.filmproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByUserAndComment(User user, Comment comment);
    long countByCommentAndType(Comment comment, Reaction.ReactionType type);
}
