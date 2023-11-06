package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
//import com.line4thon.kureomi.domain.user.User;
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
    private String userUniqueUrl;

    @Builder
    public GiftBoxSaveRequestDto(String writer, String message, List<Long> photoIdList, String userUniqueUrl) {
        this.writer = writer;
        this.message = message;
        this.photoIdList = photoIdList;
        this.userUniqueUrl = userUniqueUrl;
    }

    public GiftBox toEntity(User2 owner) {
        return GiftBox.builder()
                .writer(writer)
                .message(message)
                .owner(owner)
                .build();
    }
}
