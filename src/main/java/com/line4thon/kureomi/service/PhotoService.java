package com.line4thon.kureomi.service;

import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.photo.PhotoRepository;
import com.line4thon.kureomi.web.dto.PhotoInfoDto;
import com.line4thon.kureomi.web.dto.PhotoTestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final GreenEyeService greenEyeService;

    @Transactional
    public PhotoTestResponseDto uploadPhotos(MultipartFile[] photos) {
        List<PhotoInfoDto> photoInfoList = new ArrayList<>();

        for (MultipartFile file : photos) {
            Photo photo = uploadPhoto(file);

            PhotoInfoDto photoInfoDto = PhotoInfoDto.builder()
                    .photo_id(photo.getId())
                    .fileUrl(photo.getFileUrl())
                    .build();

            photoInfoList.add(photoInfoDto);
        }

        return new PhotoTestResponseDto(photoInfoList);
    }

    @Transactional
    public Photo uploadPhoto(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            String filename = file.getOriginalFilename();
            String fileUrl = createFileUrl(filename);
            String uploadDir = "./src/main/resources/static/images";

            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(fileUrl);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String data = encodeFileToBase64String(filePath.toString());
            boolean isImageValid = greenEyeService.testPhoto(file, data);
            log.info("이미지 응답 true/false{}", isImageValid);

            if (isImageValid) {
                Photo photo = Photo.builder()
                        .fileName(filename)
                        .fileUrl(fileUrl)
                        .build();
                photoRepository.save(photo);
                return photo;
            } else {
                String defaultImagePath = "./src/main/resources/static/images/default.png";
                Path defaultImage = Paths.get(defaultImagePath);

                Photo defaultPhoto = Photo.builder()
                        .fileName("default.png")
                        .fileUrl("default.png")
                        .build();
                photoRepository.save(defaultPhoto);
                return defaultPhoto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private String encodeFileToBase64String(String filePath) throws Exception {
        byte[] data = Files.readAllBytes(Path.of(filePath));
        return Base64.getEncoder().encodeToString(data);
    }

    private String truncateData(byte[] data) {
        int maxLength = 255;
        if (data.length > maxLength) {
            byte[] truncatedData = Arrays.copyOf(data, maxLength);
            return Base64.getEncoder().encodeToString(truncatedData);
        } else {
            return Base64.getEncoder().encodeToString(data);
        }
    }
}
