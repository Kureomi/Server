package com.line4thon.kureomi.web.controller;

import com.line4thon.kureomi.service.PhotoService;
import com.line4thon.kureomi.web.dto.PhotoInfoDto;
import com.line4thon.kureomi.web.dto.PhotoTestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping("/api/v1/kureomi/photo")
    public PhotoTestResponseDto savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        PhotoTestResponseDto photoInfo = photoService.uploadPhotos(photos);
        return photoInfo;
    }
}
