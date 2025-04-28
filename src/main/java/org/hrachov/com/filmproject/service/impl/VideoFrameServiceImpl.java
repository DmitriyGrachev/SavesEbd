package org.hrachov.com.filmproject.service.impl;

import org.hrachov.com.filmproject.model.youtube.VideoFrame;
import org.hrachov.com.filmproject.repository.VideoFrameRepository;
import org.hrachov.com.filmproject.service.VideoFrameService;
import org.springframework.stereotype.Service;

@Service
public class VideoFrameServiceImpl implements VideoFrameService {
    private VideoFrameRepository videoFrameRepository;
    public VideoFrameServiceImpl(VideoFrameRepository videoFrameRepository) {
        this.videoFrameRepository = videoFrameRepository;
    }

    public VideoFrame getVideoFrame(int videoId) {
        return videoFrameRepository.findById(videoId).get();
    }
}
