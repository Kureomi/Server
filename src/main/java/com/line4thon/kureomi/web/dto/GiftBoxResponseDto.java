package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.photo.Photo;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GiftBoxResponseDto {
    private Long giftBox_id;
    private String writer;
    private String message;
    private List<PhotoResponseDto> photos;

    public GiftBoxResponseDto(GiftBox entity) {
        this.giftBox_id = entity.getId();
        this.writer = entity.getWriter();
        this.message = entity.getMessage();
        this.photos = convertPhotoDto(entity.getPhotos());
    }

    public List<PhotoResponseDto> convertPhotoDto(List<Photo> photos) {
        return photos.stream().map(photo -> new PhotoResponseDto(photo.getId(), photo.getFileName()))
                .collect(Collectors.toList());
    }
}
