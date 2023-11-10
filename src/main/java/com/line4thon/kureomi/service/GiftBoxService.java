package com.line4thon.kureomi.service;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.giftBox.GiftBoxRepository;
import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.user.UserRepository;
import com.line4thon.kureomi.web.dto.GiftBoxListResponseDto;
import com.line4thon.kureomi.web.dto.GiftBoxResponseDto;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import com.line4thon.kureomi.web.exception.CustomError;
import com.line4thon.kureomi.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GiftBoxService {
    private final GiftBoxRepository giftBoxRepository;
    private final PhotoService photoService;
    private final UserRepository userRepository;

    @Transactional
    public GiftBox createGiftBox(String uniqueUrl, GiftBoxSaveRequestDto requestDto) {
        User user = userRepository.findByUniqueUrl(uniqueUrl);
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
    }

    @Transactional(readOnly = true)
    public List<GiftBoxListResponseDto> findAllDesc(String uniqueUrl) {
        User user = userRepository.findByUniqueUrl(uniqueUrl);
        return giftBoxRepository.findAllByUserDESC().stream()
                .map(gitBox -> new GiftBoxListResponseDto(gitBox, user.getUserName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GiftBoxResponseDto findById(String uniqueUrl, Long id) {
        User user = userRepository.findByUniqueUrl(uniqueUrl);
        GiftBox entity = giftBoxRepository.findByUserAndId(user, id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다. id="+id));

        return new GiftBoxResponseDto(entity);
    }
}
