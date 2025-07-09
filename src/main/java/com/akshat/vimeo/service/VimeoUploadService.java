package com.akshat.vimeo.service;

import com.akshat.vimeo.config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class VimeoUploadService {

    private final AppConfig config;

    private final ObjectMapper mapper = new ObjectMapper();

    public void uploadAllVideos() throws Exception {
        File folder = new File(config.getVideoDir());
        if (!folder.exists()) throw new RuntimeException("Video directory not found: " + config.getVideoDir());

        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                System.out.println("Uploading: " + file.getName());
                //uploadVideo(file);
            }
        }

    }
}
