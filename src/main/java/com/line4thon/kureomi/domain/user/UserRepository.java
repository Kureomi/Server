package com.line4thon.kureomi.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUniqueUrl(String uniqueUrl);
}
