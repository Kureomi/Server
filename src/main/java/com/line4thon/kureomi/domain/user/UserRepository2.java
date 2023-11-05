package com.line4thon.kureomi.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository2 extends JpaRepository<User2, Long> {
    User2 findByUniqueUrl(String uniqueUrl);
}
