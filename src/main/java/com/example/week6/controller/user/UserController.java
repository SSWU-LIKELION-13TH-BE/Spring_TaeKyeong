package com.example.week6.controller.user;

import com.example.week6.apiPayload.code.SuccessStatus;
import com.example.week6.apiPayload.dto.ApiResponse;
import com.example.week6.dto.user.*;
import com.example.week6.security.JwtTokenProvider;
import com.example.week6.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    // ✅회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDTO requestDTO) {
        userService.signup(requestDTO);
        return ResponseEntity.ok("회원가입 성공");
}
    // ✅로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO) {
        UserLoginResponseDTO response = userService.login(requestDTO);
        return ResponseEntity.ok(response);
    }

    // 'me' 엔드포인트
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDTO> getMyInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = jwtTokenProvider.getUserId(token);
        UserInfoResponseDTO userInfo = userService.getMyInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    // 비밀번호 변경
    // UserController.java
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody UserPasswordChangeRequestDTO requestDTO,
                                                 HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.onFailure("UNAUTHORIZED", "유효하지 않은 토큰입니다.", null));
        }

        String userId = jwtTokenProvider.getUserId(token);
            userService.changePassword(userId, requestDTO);
            return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, "비밀번호가 변경되었습니다."));

    }

}