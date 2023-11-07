package com.line4thon.kureomi.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUniqueUrl(String uniqueUrl);
    User findByEmail(String email);
}
