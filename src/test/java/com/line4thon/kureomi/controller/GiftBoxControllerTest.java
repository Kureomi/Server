package com.line4thon.kureomi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.line4thon.kureomi.domain.giftBox.GiftBox;
import com.line4thon.kureomi.domain.giftBox.GiftBoxRepository;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GiftBoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GiftBoxRepository giftBoxRepository;

    @AfterEach
    public void tearDown() throws Exception {
        giftBoxRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void 포토꾸러미_등록() throws Exception {
        // given
        String writer = "Santa";
        String message = "Merry Christmas";
        List<Long> photoIdList = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L);

        GiftBoxSaveRequestDto requestDto = GiftBoxSaveRequestDto.builder()
                .writer(writer)
                .message(message)
                .photoIdList(photoIdList)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/kureomi/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // then
        List<GiftBox> savedGiftBoxes = giftBoxRepository.findAll();
        assertThat(savedGiftBoxes).isNotNull();
        assertThat(savedGiftBoxes.get(0).getWriter()).isEqualTo(writer);
        assertThat(savedGiftBoxes.get(0).getMessage()).isEqualTo(message);
        assertThat(savedGiftBoxes.get(0).getPhotos()).hasSize(6);
    }
}
