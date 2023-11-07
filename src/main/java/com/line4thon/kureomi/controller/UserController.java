package com.line4thon.kureomi.controller;

import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.user.UserRepository;
import com.line4thon.kureomi.service.UserService;
import com.line4thon.kureomi.web.dto.GiftBoxSaveRequestDto;
import com.line4thon.kureomi.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.line4thon.kureomi.service.GenerateUniqueUrl.generateUniqueUrl;

@RequiredArgsConstructor
@RestController
@Validated
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/api/v1/kureomi/signup")
    public String createUser(@RequestBody @Validated UserSaveRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
            throw new IllegalArgumentException();
        }

        User user = userService.createUser(requestDto);
        user.setUniqueUrl(generateUniqueUrl());
        userRepository.save(user);

        String finalUrl = "http://photoKureomi.com/" + user.getUniqueUrl();
        return finalUrl;
    }

    // @PostMapping("/api/v1/kureomi/login")
}
