//package com.akshat.vimeo.controller;
//
//import com.akshat.vimeo.service.VimeoUploadService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.File;
//import java.util.List;
//
//@RestController
//@RequestMapping("/vimeo")
//@RequiredArgsConstructor
//public class VideoController {
//
//    @Autowired
//    private final VimeoUploadService uploadService;
//    @Value("${vimeo.video-dir}")
//    private String videoDirectory;
//
//
//    @PostMapping("/upload/{fileName}")
//    public ResponseEntity<String> uploadVideo(@PathVariable String fileName) {
//        try {
//            String videoUri = uploadService.uploadFromLocalDirectory(fileName);
//            return ResponseEntity.ok(" Uploaded to Vimeo: " + videoUri);
//        } catch (Exception e) {
//            // Check for Vimeo 401 error
//            if (e.getMessage() != null && e.getMessage().contains("401")) {
//                return ResponseEntity.status(401).body(" Upload failed: Unauthorized (401). Please check your Vimeo access token.");
//            }
//            return ResponseEntity.status(500).body(" Upload failed: " + e.getMessage());
//        }
//    }
//    @PostMapping("/upload/form/{fileName}")
//    public ResponseEntity<String> uploadVideoFormBased(@PathVariable String fileName) {
//        try {
//            String videoUri = uploadService.uploadFormBased(fileName);
//            return ResponseEntity.ok("Form-based upload success: " + videoUri);
//        } catch (Exception e) {
//            if (e.getMessage() != null && e.getMessage().contains("401")) {
//                return ResponseEntity.status(401).body("Unauthorized (401). Check access token.");
//            }
//            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
//        }
//    }
//
//    // VideoController.java
//    @PostMapping("/upload/pull")
//    public ResponseEntity<String> uploadViaPull(
//            @RequestParam String videoUrl,
//            @RequestParam(required = false, defaultValue = "Pulled Video") String videoName) {
//
//        try {
//            String videoUri = uploadService.uploadPullUrl(videoUrl, videoName);
//            return ResponseEntity.ok("Video pulled and uploaded to Vimeo: " + videoUri);
//        } catch (Exception e) {
//            if (e.getMessage().contains("401")) {
//                return ResponseEntity.status(401).body("Unauthorized. Check Vimeo access token.");
//            }
//            return ResponseEntity.status(500).body("Pull upload failed: " + e.getMessage());
//        }
//    }
//
//
//    @PostMapping("/upload/custom")
//    public ResponseEntity<String> uploadWithCustomDetails(
//            @RequestParam String fileName,
//            @RequestParam String title,
//            @RequestParam(required = false) String description,
//            @RequestParam(required = false) String license,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String language
//    )
//    {
//        try {
//            File file = new File(videoDirectory + File.separator + fileName);
//            String videoUri = uploadService.uploadWithCustomMetadata(file, title, description, license, tags, language);
//            return ResponseEntity.ok("Video uploaded with URI: " + videoUri);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
//        }
//    }
//
//}
//package com.akshat.vimeo.controller;
//
//import com.akshat.vimeo.service.VimeoUploadService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.File;
//import java.util.List;
//
//@RestController
//@RequestMapping("/vimeo")
//@RequiredArgsConstructor
//public class VideoController {
//
//    @Autowired
//    private final VimeoUploadService uploadService;
//    @Value("${vimeo.video-dir}")
//    private String videoDirectory;
//
//
//    @PostMapping("/upload/{fileName}")
//     public ResponseEntity<String> uploadVideo(@PathVariable String fileName) {
//        try {
//            String videoUri = uploadService.uploadFromLocalDirectory(fileName);
//            return ResponseEntity.ok(" Uploaded to Vimeo: " + videoUri);
//        } catch (Exception e) {
//            // Check for Vimeo 401 error
//            if (e.getMessage() != null && e.getMessage().contains("401")) {
//                return ResponseEntity.status(401).body(" Upload failed: Unauthorized (401). Please check your Vimeo access token.");
//            }
//            return ResponseEntity.status(500).body(" Upload failed: " + e.getMessage());
//        }
//    }
//    @PostMapping("/upload/form/{fileName}")
//    public ResponseEntity<String> uploadVideoFormBased(@PathVariable String fileName) {
//        try {
//            String videoUri = uploadService.uploadFormBased(fileName);
//            return ResponseEntity.ok("Form-based upload success: " + videoUri);
//        } catch (Exception e) {
//            if (e.getMessage() != null && e.getMessage().contains("401")) {
//                return ResponseEntity.status(401).body("Unauthorized (401). Check access token.");
//            }
//            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
//        }
//    }
//
//    // VideoController.java
//    @PostMapping("/upload/pull")
//    public ResponseEntity<String> uploadViaPull(
//            @RequestParam String videoUrl,
//            @RequestParam(required = false, defaultValue = "Pulled Video") String videoName) {
//
//        try {
//            String videoUri = uploadService.uploadPullUrl(videoUrl, videoName);
//            return ResponseEntity.ok("Video pulled and uploaded to Vimeo: " + videoUri);
//        } catch (Exception e) {
//            if (e.getMessage().contains("401")) {
//                return ResponseEntity.status(401).body("Unauthorized. Check Vimeo access token.");
//            }
//            return ResponseEntity.status(500).body("Pull upload failed: " + e.getMessage());
//        }
//    }
//
//
//    @PostMapping("/upload/custom")
//    public ResponseEntity<String> uploadWithCustomDetails(
//            @RequestParam String fileName,
//            @RequestParam String title,
//            @RequestParam(required = false) String description,
//            @RequestParam(required = false) String license,
//            @RequestParam(required = false) String tags,
//            @RequestParam(required = false) String language
//    )
//    {
//        try {
//            File file = new File(videoDirectory + File.separator + fileName);
//            String videoUri = uploadService.uploadWithCustomMetadata(file, title, description, license, tags, language);
//            return ResponseEntity.ok("Video uploaded with URI: " + videoUri);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
//        }
//    }
//
//}

package com.akshat.vimeo.controller;
import com.akshat.vimeo.service.VimeoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/vimeo")
@RequiredArgsConstructor
public class VideoController {

    @Autowired
    private final VimeoUploadService uploadService;
    @Value("${vimeo.video-dir}")
    private String videoDirectory;

    @PostMapping("/upload/custom")
    public ResponseEntity<String> uploadWithCustomDetails(
            @RequestParam String fileName,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String license,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String categories, // JSON array string
            @RequestParam(required = false) String credits,    // JSON array string
            @RequestParam(required = false) String contentRating, // JSON array string
            @RequestParam(required = false, defaultValue = "anybody") String privacyView,
            @RequestParam(required = false, defaultValue = "public") String privacyEmbed,
            @RequestParam(required = false) String privacyPassword,
            @RequestParam(required = false) MultipartFile thumbnail
    ) {
        try {
            // Parse JSON string fields robustly
            List<String> categoriesList = null;
            if (categories != null && !categories.isEmpty()) {
                org.json.JSONArray arr = new org.json.JSONArray(categories);
                categoriesList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) categoriesList.add(arr.getString(i));
            }
            List<Map<String, String>> creditsList = null;
            if (credits != null && !credits.isEmpty()) {
                org.json.JSONArray arr = new org.json.JSONArray(credits);
                creditsList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    org.json.JSONObject obj = arr.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    for (String key : obj.keySet()) map.put(key, obj.getString(key));
                    creditsList.add(map);
                }
            }
            List<String> contentRatingList = null;
            if (contentRating != null && !contentRating.isEmpty()) {
                org.json.JSONArray arr = new org.json.JSONArray(contentRating);
                contentRatingList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) contentRatingList.add(arr.getString(i));
            }

            String videoUri = uploadService.uploadWithCustomMetadata(
                    new File(videoDirectory + File.separator + fileName), title, description, license, tags, language,
                    categoriesList, creditsList, contentRatingList,
                    privacyView, privacyEmbed, privacyPassword
            );

            // Handle thumbnail if provided
            if (thumbnail != null && !thumbnail.isEmpty()) {
                File tempThumb = java.nio.file.Files.createTempFile("vimeo-thumb-", thumbnail.getOriginalFilename()).toFile();
                thumbnail.transferTo(tempThumb);
                //uploadService.setThumbnail(videoUri, tempThumb);
                tempThumb.delete();
            }

            return ResponseEntity.ok("Video uploaded with URI: " + videoUri);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/upload/custom/json")
    public ResponseEntity<String> uploadWithCustomDetailsJson(@RequestBody UploadRequest request) {
        try {
            // Parse JSON string fields robustly
            List<String> categoriesList = null;
            if (request.getCategories() != null && !request.getCategories().isEmpty()) {
                categoriesList = request.getCategories();
            }
            List<Map<String, String>> creditsList = null;
            if (request.getCredits() != null && !request.getCredits().isEmpty()) {
                creditsList = request.getCredits();
            }
            List<String> contentRatingList = null;
            if (request.getContentRating() != null && !request.getContentRating().isEmpty()) {
                contentRatingList = request.getContentRating();
            }

            String videoUri = uploadService.uploadWithCustomMetadata(
                    new File(videoDirectory + File.separator + request.getFileName()), 
                    request.getTitle(), 
                    request.getDescription(), 
                    request.getLicense(), 
                    request.getTags(), 
                    request.getLanguage(),
                    categoriesList, creditsList, contentRatingList,
                    request.getPrivacyView(), request.getPrivacyEmbed(), request.getPrivacyPassword()
            );

            return ResponseEntity.ok("Video uploaded with URI: " + videoUri);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    // Request DTO for JSON payload
    public static class UploadRequest {
        private String fileName;
        private String title;
        private String description;
        private String license;
        private String tags;
        private String language;
        private List<String> categories;
        private List<Map<String, String>> credits;
        private List<String> contentRating;
        private String privacyView = "anybody";
        private String privacyEmbed = "public";
        private String privacyPassword;

        // Getters and Setters
        public String getFileName() { return fileName; }
        public void setFileName(String fileName) { this.fileName = fileName; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getLicense() { return license; }
        public void setLicense(String license) { this.license = license; }
        
        public String getTags() { return tags; }
        public void setTags(String tags) { this.tags = tags; }
        
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
        
        public List<String> getCategories() { return categories; }
        public void setCategories(List<String> categories) { this.categories = categories; }
        
        public List<Map<String, String>> getCredits() { return credits; }
        public void setCredits(List<Map<String, String>> credits) { this.credits = credits; }
        
        public List<String> getContentRating() { return contentRating; }
        public void setContentRating(List<String> contentRating) { this.contentRating = contentRating; }
        
        public String getPrivacyView() { return privacyView; }
        public void setPrivacyView(String privacyView) { this.privacyView = privacyView; }
        
        public String getPrivacyEmbed() { return privacyEmbed; }
        public void setPrivacyEmbed(String privacyEmbed) { this.privacyEmbed = privacyEmbed; }
        
        public String getPrivacyPassword() { return privacyPassword; }
        public void setPrivacyPassword(String privacyPassword) { this.privacyPassword = privacyPassword; }
    }

    @PostMapping("/upload/pull")
    public ResponseEntity<Object> uploadViaPull(
            @RequestParam String videoUrl,
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String license,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String categories, // JSON array string
            @RequestParam(required = false) String credits,    // JSON array string
            @RequestParam(required = false) String contentRating, // JSON array string
            @RequestParam(required = false, defaultValue = "anybody") String privacyView,
            @RequestParam(required = false, defaultValue = "public") String privacyEmbed,
            @RequestParam(required = false) String privacyPassword,
            @RequestParam(required = false) MultipartFile thumbnail
    ) {
        try {
            // Parse JSON string fields robustly
            List<String> categoriesList = null;
            if (categories != null && !categories.isEmpty()) {
                org.json.JSONArray arr = new org.json.JSONArray(categories);
                categoriesList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) categoriesList.add(arr.getString(i));
            }
            List<Map<String, String>> creditsList = null;
            if (credits != null && !credits.isEmpty()) {
                org.json.JSONArray arr = new org.json.JSONArray(credits);
                creditsList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    org.json.JSONObject obj = arr.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();
                    for (String key : obj.keySet()) map.put(key, obj.getString(key));
                    creditsList.add(map);
                }
            }
            List<String> contentRatingList = null;
            if (contentRating != null && !contentRating.isEmpty()) {
                org.json.JSONArray arr = new org.json.JSONArray(contentRating);
                contentRatingList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) contentRatingList.add(arr.getString(i));
            }

            Object videoUri = uploadService.uploadPullWithMetadata(
                    videoUrl, title, description, license, tags, language,
                    categoriesList, creditsList, contentRatingList,
                    privacyView, privacyEmbed, privacyPassword
            );

            // Handle thumbnail if provided
            if (thumbnail != null && !thumbnail.isEmpty()) {
                File tempThumb = java.nio.file.Files.createTempFile("vimeo-thumb-", thumbnail.getOriginalFilename()).toFile();
                thumbnail.transferTo(tempThumb);
                uploadService.setThumbnail(videoUri, tempThumb);
                tempThumb.delete();
            }

            return ResponseEntity.ok("Video pulled and uploaded to Vimeo: " + videoUri);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Pull upload failed: " + e.getMessage());
        }
    }

    @PutMapping("/update/{videoId}")
    public ResponseEntity<String> updateVideoJson(
            @PathVariable String videoId,
            @RequestBody UploadRequest request
    ) {
        try {
            uploadService.updateVideoMetadata(videoId, request);
            return ResponseEntity.ok("Video metadata updated for /videos/" + videoId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Update failed: " + e.getMessage());
        }
    }

    @GetMapping("/get/{videoId}")
    public ResponseEntity<Object> getVideoDetails(@PathVariable String videoId) {
        try {
            Object details = uploadService.getVideoDetails(videoId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch video details: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{videoId}")
    public ResponseEntity<String> deleteVideo(@PathVariable String videoId) {
        try {
            uploadService.deleteVideo(videoId);
            return ResponseEntity.ok("Video deleted: /videos/" + videoId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Delete failed: " + e.getMessage());
        }
    }
}