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
}
