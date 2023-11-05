package com.line4thon.kureomi.domain.giftBox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiftBoxRepository extends JpaRepository<GiftBox, Long> {
    @Query("SELECT box FROM GiftBox box ORDER BY box.id DESC")
    List<GiftBox> findAllDesc();
}
