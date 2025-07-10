package com.akshat.vimeo.controller;

import com.akshat.vimeo.service.VimeoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vimeo")
@RequiredArgsConstructor
public class VideoController {

    private final VimeoUploadService uploadService;

    @PostMapping("/upload/{fileName}")
    public ResponseEntity<String> uploadVideo(@PathVariable String fileName) {
        try {
            String videoUri = uploadService.uploadFromLocalDirectory(fileName);
            return ResponseEntity.ok(" Uploaded to Vimeo: " + videoUri);
        } catch (Exception e) {
            // Check for Vimeo 401 error
            if (e.getMessage() != null && e.getMessage().contains("401")) {
                return ResponseEntity.status(401).body(" Upload failed: Unauthorized (401). Please check your Vimeo access token.");
            }
            return ResponseEntity.status(500).body(" Upload failed: " + e.getMessage());
        }
    }
}
