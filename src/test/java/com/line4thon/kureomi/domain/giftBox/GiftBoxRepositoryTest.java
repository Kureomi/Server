package com.line4thon.kureomi.domain.giftBox;

import com.line4thon.kureomi.domain.photo.Photo;
import com.line4thon.kureomi.domain.photo.PhotoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GiftBoxRepositoryTest {

    @Autowired
    GiftBoxRepository giftBoxRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @AfterEach
    public void cleanup() {
        giftBoxRepository.deleteAll();
    }

    @Test
    public void 포토꾸러미_불러오기() {
        // given
        String writer = "Santa";
        String message = "메리 크리스마스";
        Photo photo = new Photo("sample.jpg", "adjivodifds-zjdifpsvnd.jpg");

        GiftBox giftBox = GiftBox.builder()
                .writer(writer)
                .message(message)
                .build();

        giftBox.addPhoto(photo);
        giftBoxRepository.save(giftBox);

        // when
        List<GiftBox> giftBoxList = giftBoxRepository.findAll();

        // then
        GiftBox giftBox1 = giftBoxList.get(0);
        assertThat(giftBox1.getWriter()).isEqualTo(writer);
        assertThat(giftBox1.getMessage()).isEqualTo(message);
    }
}
