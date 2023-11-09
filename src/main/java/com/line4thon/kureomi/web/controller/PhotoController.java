package com.line4thon.kureomi.web.controller;

import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PhotoController {
    private final PhotoService photoService;

//    @PostMapping("/api/v1/kureomi/photo")
//    public ResponseEntity<List<Long>> savePhotos(@RequestParam("photos") MultipartFile[] photos) {
//        List<Long> photoIdList = photoService.uploadPhotos(photos);
//        return new ResponseEntity<>(photoIdList, HttpStatus.CREATED);
//}

    @PostMapping("/api/v1/kureomi/photo")
    public ResponseEntity<List<Long>> savePhotos(@RequestParam("photos") MultipartFile[] photos) {
        List<Long> photoIdList = new ArrayList<>();
        int inappropriateCount = 0;

        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                Photo savedPhoto = photoService.uploadPhoto(photo);

                // Check if the image is inappropriate
                if (photoService.isImproperPhotos(savedPhoto.getFileUrl())) {
                    inappropriateCount++;
                    // Delete the inappropriate image
                    photoService.deletePhoto(savedPhoto.getId());
                } else {
                    photoIdList.add(savedPhoto.getId());
                }

                // If 2 or more inappropriate images are found, stop processing
                if (inappropriateCount >= 2) {
                    // You can implement further actions here, such as notifying the user.
                    break;
                }
            }
        }

        if (inappropriateCount >= 2) {
            // Handle the case where there are too many inappropriate images.
            // You can return an error response or take appropriate actions.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(photoIdList, HttpStatus.CREATED);
    }




}
