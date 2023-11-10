package com.line4thon.kureomi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PhotoTestResponseDto {
    private List<PhotoInfoDto> photoInfoList;

    @Builder
    public PhotoTestResponseDto(List<PhotoInfoDto> photoInfoList) {
        this.photoInfoList = photoInfoList;
    }
}
