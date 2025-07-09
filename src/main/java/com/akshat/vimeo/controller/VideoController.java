package com.akshat.vimeo.controller;


//import com.akshat.vimeouploader.service.VimeoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vimeo")
@RequiredArgsConstructor
public class VideoController {

    //private final VimeoUploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideoToVimeo(@RequestParam("file") MultipartFile file) {
        try {
            //String response = uploadService.uploadVideo(file);
            String response = "";
            return ResponseEntity.ok("Uploaded to Vimeo successfully.\nResponse: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}

