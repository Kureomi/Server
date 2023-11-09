package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.photo.Photo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GreeneyeRequestDto {
    private String version = "V1";
    private String requestId;
    private Long timestamp;
    private List<PhotoTestRequestDto> images;

    @Builder
    public GreeneyeRequestDto(String requestId, Long timestamp, List<PhotoTestRequestDto> images) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.images = images;
    }

    @Override
    public String toString() {
        return "GreeneyeRequestDto{" +
                "version='" + version + '\'' +
                ", requestId='" + requestId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
