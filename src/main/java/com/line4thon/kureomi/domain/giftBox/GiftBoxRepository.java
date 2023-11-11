package com.line4thon.kureomi.domain.giftBox;

import com.line4thon.kureomi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GiftBoxRepository extends JpaRepository<GiftBox, Long> {
    @Query("SELECT g FROM GiftBox g WHERE g.user.user_id = g.user.user_id ORDER BY g.id DESC")
    List<GiftBox> findAllByUserDESC();

    List<GiftBox> findAllByUserOrderByCreatedAtDesc(User user);

    Optional<GiftBox> findByUserAndId(User user, Long id);
}
