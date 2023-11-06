package com.line4thon.kureomi.config;

import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class UrlAccessDecision {
    private final UserRepository userRepository;

    public boolean checkAccess(Authentication authentication, String uniqueUrl) {
        String currentUserEmail = authentication.getName();
        User user = userRepository.findByUniqueUrl(uniqueUrl);

        if (user != null && user.getEmail().equals(currentUserEmail)) {
            return true;
        }
        return false;
    }
}
