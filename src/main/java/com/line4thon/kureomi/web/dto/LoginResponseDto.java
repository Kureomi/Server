package com.line4thon.kureomi.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginResponseDto {
    String userName;
    String uniqueUrl;

    @Builder
    public LoginResponseDto(String userName, String uniqueUrl) {
        this.userName = userName;
        this.uniqueUrl = uniqueUrl;
    }
}
