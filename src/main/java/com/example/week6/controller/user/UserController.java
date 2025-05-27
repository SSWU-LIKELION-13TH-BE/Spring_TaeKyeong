package com.example.week6.controller.user;

import com.example.week6.dto.user.*;
import com.example.week6.entity.user.User;
import com.example.week6.security.JwtTokenProvider;
import com.example.week6.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<String> changePassword(@RequestBody UserPasswordChangeRequestDTO requestDTO,
                                                 HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }

        String userId = jwtTokenProvider.getUserId(token);
        try {
            userService.changePassword(userId, requestDTO);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}