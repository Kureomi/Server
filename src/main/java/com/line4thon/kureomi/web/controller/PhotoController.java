package com.line4thon.kureomi.web.controller;

import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping("/api/v1/kureomi/photo")
    public ResponseEntity<List<Long>> savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        List<Long> photoIdList = photoService.uploadPhotos(photos);
        return new ResponseEntity<>(photoIdList, HttpStatus.CREATED);
    }
}
