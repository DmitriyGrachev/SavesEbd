package org.hrachov.com.filmproject.repository;

import org.hrachov.com.filmproject.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    Page<Film> findAll(Specification<Film> spec, Pageable pageable);
}
