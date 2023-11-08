package com.line4thon.kureomi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PhotoResponseDto {
    private Long photo_id;
    private String fileUrl;

    @Builder
    public PhotoResponseDto(Long photo_id, String fileUrl) {
        this.photo_id = photo_id;
        this.fileUrl = fileUrl;
    }
}
