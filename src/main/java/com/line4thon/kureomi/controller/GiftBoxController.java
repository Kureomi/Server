package com.line4thon.kureomi.controller;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.service.GiftBoxService;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class GiftBoxController {

    private final GiftBoxService giftBoxService;


    @PostMapping("/api/v1/kureomi/create")
    public Long createGiftBox(@RequestBody GiftBoxSaveRequestDto requestDto) {
        GiftBox giftBox = giftBoxService.createGiftBox(requestDto);
        return giftBox.getId();
    }
}
