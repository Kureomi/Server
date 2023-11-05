package com.line4thon.kureomi;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        // 비밀번호를 해시화하여 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 사용자 역할 할당 (예: "ROLE_USER")
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/login"; // 로그인 페이지로 리다이렉트
    }
}
