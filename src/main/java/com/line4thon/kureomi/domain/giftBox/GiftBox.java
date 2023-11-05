package com.line4thon.kureomi.domain.giftBox;

import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.photo.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class GiftBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "giftBox_id")
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "TEXT")
    private String message;

    @OneToMany(mappedBy = "giftBox", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @Builder
    public GiftBox(String writer, String message, User owner) {
        this.writer = writer;
        this.message = message;
        this.owner = owner;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setGiftBox(this);
    }
}
