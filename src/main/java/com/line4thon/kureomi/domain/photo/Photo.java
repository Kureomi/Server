package com.line4thon.kureomi.domain.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.line4thon.kureomi.domain.giftBox.GiftBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "giftBox_id")
    private GiftBox giftBox;

    @Builder
    public Photo(String fileName) {
        this.fileName = fileName;
    }

    public void setStoredFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setGiftBox(GiftBox giftBox) {
        this.giftBox = giftBox;
    }
}
