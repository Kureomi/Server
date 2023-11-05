package com.line4thon.kureomi.web.dto;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import lombok.Getter;

@Getter
public class GiftBoxListResponseDto {
    private Long giftBox_id;

    public GiftBoxListResponseDto(GiftBox entity) {
        this.giftBox_id = entity.getId();
    }
}
