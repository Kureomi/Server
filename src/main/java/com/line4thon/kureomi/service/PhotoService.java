package com.line4thon.kureomi.service;

import com.line4thon.kureomi.*;
import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.photo.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final GreeneyeApiClient greeneyeApiClient;

    @Value("${upload.dir}")
    private String uploadDir;

    //수정
    @Value("${greeneye.api.invokeUrl}")
    private String greeneyeInvokeUrl;

    @Value("${greeneye.api.secretKey}")
    private String greeneyeSecretKey;

    @Transactional
    public List<Long> uploadPhotos(MultipartFile[] photos) {
        List<Long> photoIdList = new ArrayList<>();

        for (MultipartFile file : photos) {
            Photo photo = uploadPhoto(file);
            photoIdList.add(photo.getId());
        }

        return photoIdList;
    }

    @Transactional
    public Photo uploadPhoto(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Photo photo = new Photo();
            photo.setfileName(fileName);
            return photoRepository.save(photo);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload photo: " + e.getMessage());
        }
    }

    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }


    private void analyzeAndProcessPhoto(Photo photo) {
        String imageUrl = "https://your-server-url.com/uploads/" + photo.getFileName(); // 이미지 URL 구성

        // Greeneye API 호출 및 결과 처리
        GreeneyeApiResponse response = greeneyeApiClient.sendImageForAnalysis(imageUrl);

        // 'porn' 카테고리의 confidence 값
        double pornConfidence = getHighestConfidence(response, "porn");

        // 다른 카테고리의 confidence 값도 확인
        double adultConfidence = getHighestConfidence(response, "adult");
        double normalConfidence = getHighestConfidence(response, "normal");
        double sexyConfidence = getHighestConfidence(response, "sexy");

        // 'porn' 카테고리의 confidence 값이 가장 높을 경우에만 업로드 거부
        if (pornConfidence > adultConfidence && pornConfidence > normalConfidence && pornConfidence > sexyConfidence) {
            // 'porn' 카테고리가 가장 확률이 높은 경우
            // 업로드 거부 또는 처리 로직 추가
        } else {
            // 다른 카테고리가 확률이 높거나 모든 카테고리의 confidence가 0 이하인 경우
            // 원하는 동작 수행
        }
    }

    private double getHighestConfidence(GreeneyeApiResponse response, String category) {
        for (GreeneyeImage image : response.getImages()) {
            GreeneyeResult result = image.getResult();
            if (result != null) {
                GreeneyeCategory greeneyeCategory = null;
                if ("porn".equals(category)) {
                    greeneyeCategory = result.getPorn();
                } else if ("adult".equals(category)) {
                    greeneyeCategory = result.getAdult();
                } else if ("normal".equals(category)) {
                    greeneyeCategory = result.getNormal();
                } else if ("sexy".equals(category)) {
                    greeneyeCategory = result.getSexy();
                }

                if (greeneyeCategory != null) {
                    double confidence = greeneyeCategory.getConfidence();
                    if (confidence > 0) {
                        return confidence;
                    }
                }
            }
        }
        return 0.0; // 해당 카테고리가 없거나 모든 confidence 값이 0 이하인 경우
    }
}
