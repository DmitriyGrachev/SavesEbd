package org.hrachov.com.filmproject.service.impl;

import org.hrachov.com.filmproject.model.youtube.VideoDirectory;

import org.hrachov.com.filmproject.service.VideoDirectoryService;
import org.hrachov.com.filmproject.repository.VideoDirectoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoDirectoryServiceImpl implements VideoDirectoryService {

    private VideoDirectoryRepository videoDirectoryRepository;

    public VideoDirectoryServiceImpl(VideoDirectoryRepository videoDirectoryRepository) {
        this.videoDirectoryRepository = videoDirectoryRepository;
    }

    public List<VideoDirectory> getAllVideoDirectories() {
        return videoDirectoryRepository.findAll();
    }

    public VideoDirectory getVideoDirectorieById(int id){
        return videoDirectoryRepository.getVideoDirectoryById(id);
    }
}
