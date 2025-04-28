package org.hrachov.com.filmproject.repository;

import org.hrachov.com.filmproject.model.Serial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialRepository extends JpaRepository<Serial, Long> {
}
