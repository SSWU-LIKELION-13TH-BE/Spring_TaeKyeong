package com.example.week6.service.user;

import com.example.project.apiPayload.exception.GeneralException;
import com.example.week6.apiPayload.code.ErrorStatus;
import com.example.week6.dto.user.*;
import com.example.week6.entity.user.User;
import com.example.week6.repository.User.UserRepository;
import com.example.week6.security.JwtTokenProvider;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //회원가입(비밀번호 암호화 후 저장)
    public void signup(UserSignupRequestDTO requestDTO) {

        if (userRepository.findByUserId(requestDTO.getUserId()).isPresent()) {
            throw new GeneralException(ErrorStatus.USERNAME_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUserId(requestDTO.getUserId());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setName(requestDTO.getName());
        user.setProfileImage(requestDTO.getProfileImage());
        userRepository.save(user);
    }

    //로그인(ID/PW 검증 후 JWT 발급)
    public UserLoginResponseDTO login(UserLoginRequestDTO requestDTO) {
        User user = userRepository.findByUserId(requestDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found with ID " + requestDTO.getUserId()));
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = jwtTokenProvider.createToken(user.getUserId());
        return new UserLoginResponseDTO(user.getUserId(), token);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID " + userId));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();

    }

    public UserInfoResponseDTO getMyInfo(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("User not found : " + userId));
        return new UserInfoResponseDTO(user.getUserId(), user.getName(), user.getProfileImage());
    }

    // 비밀번호 바꾸기
    public void changePassword(String userId, UserPasswordChangeRequestDTO requestDTO) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

        // 예외 처리 1: 현재 비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDTO.getCurrentPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }

        // 예외 처리 2: 새 비밀번호 확인 불일치
        if (!requestDTO.getNewPassword().equals(requestDTO.getConfirmPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_MISMATCH);
        }

        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        userRepository.save(user);
    }
}