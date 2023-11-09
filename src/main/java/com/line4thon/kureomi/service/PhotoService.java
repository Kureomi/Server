package com.line4thon.kureomi.service;

import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.photo.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    @Transactional
    public List<Long> uploadPhotos(MultipartFile[] photos) {
        List<Long> photoIdList = new ArrayList<>();

        for (MultipartFile file : photos) {
            Photo photo = uploadPhoto(file);

            if (photo != null) {
                photoIdList.add(photo.getId());

                // Get the URL of the uploaded photo and check if it's improper
                String imageUrl = photo.getFileUrl(); // Assuming fileUrl contains the image URL
                boolean isImproper = isImproperPhotos(imageUrl);

                if (!isImproper) {
                    logger.info("Improper photo detected! Deleting photo with ID: {}", photo.getId());

                    // Delete the improper photo
                    //deletePhoto(photo.getId());
                    // Remove the photo ID from the list
                    photoIdList.remove(photo.getId());
                }
            }
        }

        return photoIdList;
    }

    @Transactional
    public Photo uploadPhoto(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String filename = file.getOriginalFilename();
        String fileUrl = createFileUrl(filename);

        Photo photo = new Photo();
        photo.setFileName(filename);
        photo.setFileUrl(fileUrl);
        return photoRepository.save(photo);
    }

    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    private String createFileUrl(String fileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(fileName);
        String fileUrl= uuid + ext;

        return fileUrl;
    }

    private String extractExt(String filename) {
        int idx = filename.lastIndexOf(".");
        String ext = filename.substring(idx);

        return ext;
    }

    @Value("${greenEyeApiUrl}")
    private String greenEyeApiUrl;

    @Value("${greenEyeSecret}")
    private String greenEyeSecret;

    public boolean isImproperPhotos(String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-GREEN-EYE-SECRET", greenEyeSecret);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("version", "V1");
        requestBody.put("requestId", UUID.randomUUID().toString());
        requestBody.put("timestamp", System.currentTimeMillis());

        List<Map<String, Object>> images = new ArrayList<>();
        Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("name", "demo");
        imageInfo.put("url", imageUrl);
        images.add(imageInfo);
        requestBody.put("images", images);

        try {
            RequestEntity<Map<String, Object>> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, new URI(greenEyeApiUrl));
            ResponseEntity<Map> responseEntity = restTemplate.exchange(requestEntity, Map.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> result = responseEntity.getBody();
                if (result != null && result.containsKey("images")) {
                    List<Map<String, Object>> imagesResult = (List<Map<String, Object>>) result.get("images");
                    if (!imagesResult.isEmpty()) {
                        Map<String, Object> imageResult = imagesResult.get(0);
                        if (imageResult.containsKey("result")) {
                            Map<String, Object> resultData = (Map<String, Object>) imageResult.get("result");

                            // Find the category with the highest confidence
                            String maxCategory = findMaxCategory(resultData);

                            // Check if the highest confidence category is "porn"
                            return "porn".equals(maxCategory);
                        }
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace(); // Handle URI syntax exception
        } catch (Exception e) {
            e.printStackTrace(); // Handle other exceptions as needed
        }

        return false; // Default to safe if the response is not as expected.
    }

    private String findMaxCategory(Map<String, Object> resultData) {
        double maxConfidence = 0.0;
        String maxCategory = "";

        for (Map.Entry<String, Object> entry : resultData.entrySet()) {
            String category = entry.getKey();
            Map<String, Object> categoryData = (Map<String, Object>) entry.getValue();
            double confidence = (Double) categoryData.get("confidence");

            if (confidence > maxConfidence) {
                maxConfidence = confidence;
                maxCategory = category;
            }
        }

        return maxCategory;
    }


    public void deletePhoto(Long photoId) { //파일 디렉토리 수정
        try {
            // Retrieve the photo by ID
            Photo photo = getPhotoById(photoId);

            if (photo != null) {
                // Assuming fileUrl contains the image URL
                String fileUrl = photo.getFileUrl();

                Path filePath = Paths.get("/your/storage/directory/" + fileUrl); // Adjust the path to your storage directory
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file deletion errors as needed
        }
    }


}
