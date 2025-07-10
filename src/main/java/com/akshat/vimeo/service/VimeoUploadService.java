package com.akshat.vimeo.service;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VimeoUploadService {

    private final Vimeo vimeo;

    @Value("${vimeo.video-dir}")
    private String videoDirectory;

    public String uploadFromLocalDirectory(String fileName) throws IOException, VimeoException {
        File file = new File(videoDirectory + File.separator + fileName);

        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
        }

        return vimeo.addVideo(file); // this uses tus under the hood
    }
}
