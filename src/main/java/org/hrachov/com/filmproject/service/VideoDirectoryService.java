package org.hrachov.com.filmproject.service;

import org.hrachov.com.filmproject.model.youtube.VideoDirectory;

import java.util.List;

public interface VideoDirectoryService {
    List<VideoDirectory> getAllVideoDirectories();
    VideoDirectory getVideoDirectorieById(int id);
}
