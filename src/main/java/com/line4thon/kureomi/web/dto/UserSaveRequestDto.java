package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String userName;
    private String email;
    private String password;
    private String uniqueUrl;

    public UserSaveRequestDto(String userName, String email, String password, String uniqueUrl) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.uniqueUrl = uniqueUrl;
    }

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .uniqueUrl(uniqueUrl)
                .build();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
