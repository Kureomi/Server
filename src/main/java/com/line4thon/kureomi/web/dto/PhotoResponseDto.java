package com.line4thon.kureomi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PhotoResponseDto {
    private Long photo_id;
    private String fileName;

    @Builder
    public PhotoResponseDto(Long photo_id, String fileName) {
        this.photo_id = photo_id;
        this.fileName = fileName;
    }
}
