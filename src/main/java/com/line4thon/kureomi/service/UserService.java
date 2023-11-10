package com.line4thon.kureomi.service;
import com.line4thon.kureomi.domain.user.User;
import com.line4thon.kureomi.domain.user.UserRepository;
import com.line4thon.kureomi.web.dto.LoginRequestDto;
import com.line4thon.kureomi.web.dto.LoginResponseDto;
import com.line4thon.kureomi.web.dto.SignupRequestDto;
import com.line4thon.kureomi.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignupRequestDto requestDto) {
        User user = requestDto.toEntity();

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("회원가입이 되어 있지 않습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(user.getUserName(), user.getUniqueUrl());
        return loginResponseDto;
    }

    public UserDto getUserDtoByUniqueUrl(String uniqueUrl) {
        User user = userRepository.findByUniqueUrl(uniqueUrl);

        if (user != null) {
            return new UserDto(user.getUserName(), user.getUniqueUrl());
        } else {
            return null;
        }
    }

    public boolean checkPassword(User user, String rawPassword) {
        String encodedPassword = user.getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
