package com.line4thon.kureomi.controller;

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
public class GiftBoxController {

    private final GiftBoxService giftBoxService;

    @PostMapping("/api/v1/kureomi/create")
    public Long createGiftBox(@RequestBody GiftBoxSaveRequestDto requestDto) {
        GiftBox giftBox = giftBoxService.createGiftBox(requestDto);
        return giftBox.getId();
    }

    @GetMapping("/api/v1/kureomi/all")
    public List<GiftBoxListResponseDto> getGiftBoxList() {
        return giftBoxService.findAllDesc();
    }

    @GetMapping("/api/v1/kureomi/{id}")
    public GiftBoxResponseDto findById(@PathVariable Long id) {
        return giftBoxService.findById(id);
    }
}
