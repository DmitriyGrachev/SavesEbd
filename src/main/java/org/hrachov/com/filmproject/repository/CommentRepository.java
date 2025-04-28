package org.hrachov.com.filmproject.repository;

import org.hrachov.com.filmproject.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comments WHERE film_id = ?1" , nativeQuery = true)
    List<Comment> findAllByMovieId(long movieId);

    Page<Comment> findAllByFilm_Id(long id, Pageable pageable);

    Comment getCommentById(Long id);
}
