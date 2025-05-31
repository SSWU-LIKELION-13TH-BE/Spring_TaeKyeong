package com.example.week6.controller.user;

import com.example.week6.apiPayload.code.SuccessStatus;
import com.example.week6.apiPayload.dto.ApiResponse;
import com.example.week6.dto.user.UserLoginRequestDTO;
import com.example.week6.dto.user.UserLoginResponseDTO;
import com.example.week6.dto.user.UserSignupRequestDTO;
import com.example.week6.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody UserSignupRequestDTO requestDTO) {
        userService.signup(requestDTO);
        return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO) {
        UserLoginResponseDTO response = userService.login(requestDTO);
        return ResponseEntity.ok(response);
    }
}
