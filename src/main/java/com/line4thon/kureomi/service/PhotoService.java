package com.line4thon.kureomi.service;

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

    @Value("${upload.dir}")
    private String uploadDir;

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
}
