package com.line4thon.kureomi.web.controller;

import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.user.UserRepository;
import com.line4thon.kureomi.service.UserService;
import com.line4thon.kureomi.web.dto.LoginRequestDto;
import com.line4thon.kureomi.web.dto.LoginResponseDto;
import com.line4thon.kureomi.web.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.line4thon.kureomi.service.GenerateUniqueUrl.generateUniqueUrl;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @CrossOrigin(origins="http://localhost:3000")
    @PostMapping("/api/v1/kureomi/signup")
    public String createUser(@RequestBody SignupRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
            throw new IllegalArgumentException();
        }

        User user = userService.createUser(requestDto);
        user.setUniqueUrl(generateUniqueUrl());
        userRepository.save(user);

        String finalUrl = "http://photoKureomi.com/" + user.getUniqueUrl();
        return finalUrl;
    }

    @PostMapping("/api/v1/kureomi/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
