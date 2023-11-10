package com.line4thon.kureomi.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String userName;
    private String uniqueUrl;

    public UserDto(String userName, String uniqueUrl) {
        this.userName = userName;
        this.uniqueUrl = uniqueUrl;
    }
}
