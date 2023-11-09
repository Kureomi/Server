package com.line4thon.kureomi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoTestRequestDto {
    private String name;
    private String data;

    @Builder
    public PhotoTestRequestDto(String name, String data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", data'" + data + '\'' +
                '}';
    }
}
