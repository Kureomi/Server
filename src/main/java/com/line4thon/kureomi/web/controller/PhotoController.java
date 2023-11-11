package com.line4thon.kureomi.web.controller;

import com.line4thon.kureomi.service.PhotoService;
import com.line4thon.kureomi.web.dto.PhotoInfoDto;
import com.line4thon.kureomi.web.dto.PhotoTestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping("/api/v1/kureomi/photo")
    public PhotoTestResponseDto savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        PhotoTestResponseDto photoInfo = photoService.uploadPhotos(photos);
        return photoInfo;
    }

//    @CrossOrigin
//    @GetMapping("/image")
//    public ResponseEntity<?> returnImage(@RequestParam String imageName) {
//        String path = ""; //이미지가 저장된 위치
//        Resource resource = new FileSystemResource(path + imageName);
//        return new ResponseEntity<>(resource, HttpStatus.OK);
//    }

    private String imagePath="src/main/resources/static/images/"; // application.properties에서 설정된 이미지 경로를 가져옵니다.

    @GetMapping("/image")
    public ResponseEntity<Resource> returnImage(@RequestParam String imageName) {
        String fullPath = Paths.get(imagePath, imageName).toString();
        Resource resource = new FileSystemResource(fullPath);

        // Content-Type 설정
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + imageName + "\"")
                .header("Content-Type", "image/png") // 이미지 타입에 맞게 설정
                .body(resource);
    }

}
