package com.line4thon.kureomi.domain.user;

import com.line4thon.kureomi.domain.giftBox.GiftBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "kureomi_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String uniqueUrl;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GiftBox giftBox;

    @Builder
    public User (String userName, String email, String password, String uniqueUrl) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.uniqueUrl = uniqueUrl;
    }

    public void setUniqueUrl(String uniqueUrl) {
        this.uniqueUrl = uniqueUrl;
    }

}
