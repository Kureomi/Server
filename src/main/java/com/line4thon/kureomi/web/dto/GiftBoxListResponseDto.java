package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import lombok.Getter;

@Getter
public class GiftBoxListResponseDto {
    private Long giftBox_id;
    private String userName;

    public GiftBoxListResponseDto(GiftBox entity, String userName) {
        this.giftBox_id = entity.getId();
        this.userName = userName;
    }
}
