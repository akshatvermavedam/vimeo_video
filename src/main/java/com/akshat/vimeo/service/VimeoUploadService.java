package com.akshat.vimeo.service;

import com.akshat.vimeo.controller.VideoController;
import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoException;
import com.clickntap.vimeo.VimeoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class VimeoUploadService {

    private final Vimeo vimeo;

    @Value("${vimeo.video-dir}")
    private String videoDirectory;

    public String uploadWithCustomMetadata(
            File file,
            String title,
            String description,
            String license,
            String tags,
            String language,
            List<String> categories,
            List<Map<String, String>> credits,
            List<String> contentRating,
            String privacyView,
            String privacyEmbed,
            String privacyPassword
    ) throws IOException, VimeoException {
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
        }
        Map<String, String> uploadMetadata = new HashMap<>();
        uploadMetadata.put("name", title);
        if (tags != null) uploadMetadata.put("tags", tags);
        // Do NOT put privacy map or any Map/complex object here!
        // uploadMetadata.put("privacy", privacy); // <-- This would cause a ClassCastException

        String videoUri = vimeo.addVideo(file, title, uploadMetadata);
        log.info("\uD83D\uDCFA Uploaded video. URI: {}", videoUri);

        // PATCH with advanced metadata
        Map<String, Object> fullMetadata = new HashMap<>();
        if (description != null && !description.trim().isEmpty()) {
            fullMetadata.put("description", description);
        }
        if (license != null && !license.trim().isEmpty()) {
            fullMetadata.put("license", license);
        }
        if (language != null && !language.trim().isEmpty()) {
            fullMetadata.put("language", language);
        }
        // Convert complex objects to JSON strings to avoid casting errors
        if (categories != null && !categories.isEmpty()) {
            org.json.JSONArray categoriesJson = new org.json.JSONArray(categories);
            fullMetadata.put("categories", categoriesJson.toString());
        }
        if (credits != null && !credits.isEmpty()) {
            org.json.JSONArray creditsJson = new org.json.JSONArray();
            for (Map<String, String> credit : credits) {
                org.json.JSONObject creditObj = new org.json.JSONObject(credit);
                creditsJson.put(creditObj);
            }
            fullMetadata.put("credits", creditsJson.toString());
        }
        if (contentRating != null && !contentRating.isEmpty()) {
            org.json.JSONArray contentRatingJson = new org.json.JSONArray(contentRating);
            fullMetadata.put("content_rating", contentRatingJson.toString());
        }
        // Add privacy settings to PATCH only
        Map<String, Object> privacy = new HashMap<>();
        privacy.put("view", privacyView);
        privacy.put("embed", privacyEmbed);
        if ("password".equals(privacyView) && privacyPassword != null && !privacyPassword.trim().isEmpty()) {
            privacy.put("password", privacyPassword);
        }
        fullMetadata.put("privacy", privacy);

        if (!fullMetadata.isEmpty()) {
            try {
                VimeoResponse patchResp = vimeo.patch(videoUri, fullMetadata, null);
                log.info("\uD83D\uDD27 Patched video metadata. Response: {}", patchResp.getJson().toString(2));
            } catch (Exception e) {
                log.error("Failed to patch video metadata: {}", e.getMessage());
                // Continue without failing the upload
            }
        }

        return videoUri;
    }

    public Object uploadPullWithMetadata(
            String videoUrl,
            String title,
            String description,
            String license,
            String tags,
            String language,
            List<String> categories,
            List<Map<String, String>> credits,
            List<String> contentRating,
            String privacyView,
            String privacyEmbed,
            String privacyPassword
    ) throws IOException, VimeoException {
        // Build payload for pull upload
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> upload = new HashMap<>();
        upload.put("approach", "pull");
        upload.put("link", videoUrl);
        payload.put("upload", upload);
        payload.put("name", title);
        if (description != null) payload.put("description", description);
        if (license != null) payload.put("license", license);
        if (tags != null) payload.put("tags", tags);
        if (language != null) payload.put("language", language);
        if (categories != null) payload.put("categories", categories);
        if (credits != null) payload.put("credits", credits);
        if (contentRating != null) payload.put("content_rating", contentRating);
        // Add privacy settings
        Map<String, Object> privacy = new HashMap<>();
        privacy.put("view", privacyView);
        privacy.put("embed", privacyEmbed);
        if ("password".equals(privacyView) && privacyPassword != null) {
            privacy.put("password", privacyPassword);
        }
        payload.put("privacy", privacy);
        // POST to /me/videos
        VimeoResponse response = vimeo.post("/me/videos", payload, null);
        String videoUri = response.getJson().getString("uri");
        log.info("\uD83D\uDCFA Pull uploaded video. URI: {}", videoUri);
        return videoUri;
    }

    // Upload thumbnail and set as default
    public void setThumbnail(Object videoUri, File thumbnailFile) throws IOException, VimeoException {
        // Step 1: POST to /pictures
        JSONObject payload = new JSONObject();
        payload.put("type", "custom");
        VimeoResponse response = vimeo.post(videoUri + "/pictures", payload, null);

        String pictureUri = response.getJson().getString("uri");

        // Step 2: Upload the actual image file
        //vimeo.uploadPicture(pictureUri, thumbnailFile);

        // Step 3: Set as default
        Map<String, Object> setActive = new HashMap<>();
        setActive.put("active", true);
        vimeo.patch(pictureUri, setActive, null);
    }

    public Object getVideoDetails(String videoId) throws IOException, VimeoException {
        // Use the SDK's getVideoInfo or get method
        VimeoResponse response = vimeo.getVideoInfo("/videos/" + videoId);
        return response.getJson().toMap();
    }

    public void updateVideo(String videoId, VideoController.UploadRequest request, MultipartFile file) throws Exception {
        String videoUri = "/videos/" + videoId;

        // 1. If file is provided, replace the video file
        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("vimeo-update-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Vimeo does not support direct file replacement; you must re-upload and update metadata.
            // Option 1: Delete old video, upload new, and update metadata (not ideal, loses videoId)
            // Option 2: Use PATCH to update metadata only (recommended for most use cases)
            // Option 3: If your Vimeo plan supports replacing video files, use the replace endpoint.

            // If your SDK supports replace, use:
            // vimeo.replaceVideo(videoUri, tempFile);

            // Otherwise, just PATCH metadata (see below)
            tempFile.delete();
        }

        // 2. Update metadata
        Map<String, Object> patchData = new HashMap<>();
        patchData.put("name", request.getTitle());
        patchData.put("description", request.getDescription());
        patchData.put("license", request.getLicense());
        patchData.put("tags", request.getTags());
        patchData.put("language", request.getLanguage());

        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            patchData.put("categories", new org.json.JSONArray(request.getCategories()).toString());
        }
        if (request.getCredits() != null && !request.getCredits().isEmpty()) {
            org.json.JSONArray creditsJson = new org.json.JSONArray();
            for (Map<String, String> credit : request.getCredits()) {
                creditsJson.put(new org.json.JSONObject(credit));
            }
            patchData.put("credits", creditsJson.toString());
        }
        if (request.getContentRating() != null && !request.getContentRating().isEmpty()) {
            patchData.put("content_rating", new org.json.JSONArray(request.getContentRating()).toString());
        }

        Map<String, Object> privacy = new HashMap<>();
        privacy.put("view", request.getPrivacyView());
        privacy.put("embed", request.getPrivacyEmbed());
        if ("password".equals(request.getPrivacyView()) && request.getPrivacyPassword() != null) {
            privacy.put("password", request.getPrivacyPassword());
        }
        patchData.put("privacy", privacy);

        vimeo.patch(videoUri, patchData, null);
    }

    public void updateVideoMetadata(String videoId, com.akshat.vimeo.controller.VideoController.UploadRequest request) throws IOException, VimeoException {
        String videoUri = "/videos/" + videoId;
        Map<String, Object> patchData = new HashMap<>();
        if (request.getTitle() != null) patchData.put("name", request.getTitle());
        if (request.getDescription() != null) patchData.put("description", request.getDescription());
        if (request.getLicense() != null) patchData.put("license", request.getLicense());
        if (request.getTags() != null) patchData.put("tags", request.getTags());
        if (request.getLanguage() != null) patchData.put("language", request.getLanguage());
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            org.json.JSONArray categoriesJson = new org.json.JSONArray(request.getCategories());
            patchData.put("categories", categoriesJson.toString());
        }
        if (request.getCredits() != null && !request.getCredits().isEmpty()) {
            org.json.JSONArray creditsJson = new org.json.JSONArray();
            for (Map<String, String> credit : request.getCredits()) {
                org.json.JSONObject creditObj = new org.json.JSONObject(credit);
                creditsJson.put(creditObj);
            }
            patchData.put("credits", creditsJson.toString());
        }
        if (request.getContentRating() != null && !request.getContentRating().isEmpty()) {
            org.json.JSONArray contentRatingJson = new org.json.JSONArray(request.getContentRating());
            patchData.put("content_rating", contentRatingJson.toString());
        }
        // Flatten privacy fields for PATCH
        if (request.getPrivacyView() != null) patchData.put("privacy.view", request.getPrivacyView());
        if (request.getPrivacyEmbed() != null) patchData.put("privacy.embed", request.getPrivacyEmbed());
        if ("password".equals(request.getPrivacyView()) && request.getPrivacyPassword() != null) {
            patchData.put("privacy.password", request.getPrivacyPassword());
        }
        // Do NOT use nested privacy map
        // patchData.put("privacy", privacy);
        vimeo.patch(videoUri, patchData, null);
    }

    public void deleteVideo(String videoId) throws IOException {
        String videoUri = "/videos/" + videoId;
        vimeo.removeVideo(videoUri);
    }
}