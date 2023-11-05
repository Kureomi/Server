package com.line4thon.kureomi.service;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.giftBox.GiftBoxRepository;
import com.line4thon.kureomi.domain.user.User2;
import com.line4thon.kureomi.domain.user.UserRepository2;
import com.line4thon.kureomi.web.dto.GiftBoxListResponseDto;
import com.line4thon.kureomi.web.dto.GiftBoxResponseDto;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import com.line4thon.kureomi.web.exception.CustomError;
import com.line4thon.kureomi.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GiftBoxService {
    private final GiftBoxRepository giftBoxRepository;
    private final PhotoService photoService;
    private final UserRepository2 userRepository2;

    @Transactional
    public GiftBox createGiftBox(GiftBoxSaveRequestDto requestDto) {
        try {
            User2 user = userRepository2.findByUniqueUrl(requestDto.getUserUniqueUrl());
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

    @Transactional(readOnly = true)
    public List<GiftBoxListResponseDto> findAllDesc() {
        // 유저에 맞게 해야 함
        return giftBoxRepository.findAllDesc().stream()
                .map(GiftBoxListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GiftBoxResponseDto findById(Long id) {
        GiftBox entity = giftBoxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id="+id));

        return new GiftBoxResponseDto(entity);
    }
}
