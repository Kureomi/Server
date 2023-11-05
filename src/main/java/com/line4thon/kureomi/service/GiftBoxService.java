package com.line4thon.kureomi.service;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.giftBox.GiftBoxRepository;
import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.user.UserRepository;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import com.line4thon.kureomi.web.exception.CustomError;
import com.line4thon.kureomi.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GiftBoxService {
    private final GiftBoxRepository giftBoxRepository;
    private final PhotoService photoService;
    private final UserRepository userRepository;

    @Transactional
    public GiftBox createGiftBox(GiftBoxSaveRequestDto requestDto) {
        try {
            User user = userRepository.findByUniqueUrl(requestDto.getUserUniqueUrl());
            GiftBox giftBox = requestDto.toEntity(user);

            List<Long> photoIdList = requestDto.getPhotoIdList();
            if (photoIdList != null) {
                for (Long photoId : photoIdList) {
                    Photo photo = photoService.getPhotoById(photoId);
                    if (photo != null) {
                        giftBox.addPhoto(photo);
                    }
                }
            }
            return giftBoxRepository.save(giftBox);
        } catch (UserNotFoundException e) {
            throw new CustomError(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
