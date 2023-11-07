package com.line4thon.kureomi.config;

import com.line4thon.kureomi.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // h2 사용 가능하게
                .authorizeRequests(auth -> auth
                        .antMatchers("/", "/h2-console/**").permitAll()
                        .antMatchers("/api/v1/kureomi/**").permitAll()
                        .antMatchers("/api/v1/kureomi/{id}").access("@urlAccessDecision.checkAccess(authentication, #id)")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UrlAccessDecision urlAccessDecision() {
        return new UrlAccessDecision(userRepository);
    }
}
