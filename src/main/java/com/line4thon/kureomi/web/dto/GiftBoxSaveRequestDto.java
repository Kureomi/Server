package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GiftBoxSaveRequestDto {
    private String writer;
    private String message;
    private List<Long> photoIdList;

    @Builder
    public GiftBoxSaveRequestDto(String writer, String message, List<Long> photoIdList) {
        this.writer = writer;
        this.message = message;
        this.photoIdList = photoIdList;
    }

    public GiftBox toEntity(User user) {
        return GiftBox.builder()
                .writer(writer)
                .message(message)
                .user(user)
                .build();
    }
}
