package com.line4thon.kureomi.domain.photo;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String fileUrl;

    private String data;

    @ManyToOne
    @JoinColumn(name = "giftBox_id")
    private GiftBox giftBox;

    @Builder
    public Photo(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void setGiftBox(GiftBox giftBox) {
        this.giftBox = giftBox;
    }
}
