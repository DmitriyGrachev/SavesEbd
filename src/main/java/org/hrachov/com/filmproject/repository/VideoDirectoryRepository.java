package org.hrachov.com.filmproject.repository;

import org.hrachov.com.filmproject.model.youtube.VideoDirectory;
import org.hrachov.com.filmproject.service.VideoDirectoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface VideoDirectoryRepository extends JpaRepository<VideoDirectory, Integer> {

    VideoDirectory getVideoDirectoryById(int id);
}
