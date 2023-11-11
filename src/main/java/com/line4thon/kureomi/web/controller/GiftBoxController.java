package com.line4thon.kureomi.web.controller;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.service.GiftBoxService;
import com.line4thon.kureomi.web.dto.GiftBoxListResponseDto;
import com.line4thon.kureomi.web.dto.GiftBoxResponseDto;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class  GiftBoxController {

    private final GiftBoxService giftBoxService;

    @PostMapping("/api/v1/kureomi/{uniqueUrl}/create")
    public Long createGiftBox(@PathVariable String uniqueUrl, @RequestBody GiftBoxSaveRequestDto requestDto) {
        GiftBox giftBox = giftBoxService.createGiftBox(uniqueUrl, requestDto);
        return giftBox.getId();
    }

    @GetMapping("/api/v1/kureomi/{uniqueUrl}")
    public List<GiftBoxListResponseDto> getGiftBoxList(@PathVariable String uniqueUrl) {
        return giftBoxService.findAllDesc(uniqueUrl);
    }

    @GetMapping("/api/v1/kureomi/{uniqueUrl}/{id}")
    public GiftBoxResponseDto findById(@PathVariable String uniqueUrl, @PathVariable Long id) {
        return giftBoxService.findById(uniqueUrl, id);
    }
}
