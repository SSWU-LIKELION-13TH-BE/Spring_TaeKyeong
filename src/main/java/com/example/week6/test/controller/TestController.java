package com.example.week6.test.controller;

import com.example.week6.apiPayload.code.SuccessStatus;
import com.example.week6.apiPayload.dto.ApiResponse;
import com.example.week6.test.dto.SampleRequestDto;
import com.example.week6.test.dto.TestResponse;
import com.example.week6.test.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    // 성공 응답 테스트
    @GetMapping("/hello")
    public ApiResponse<TestResponse> hello() {
        return ApiResponse.of(SuccessStatus._OK, new TestResponse("Hello, API!"));
    }

    // 예외 발생 테스트
    @GetMapping("/error")
    public ApiResponse<TestResponse> error(@RequestParam(required = false) Integer flag) {
        testService.checkFlag(flag);
        return ApiResponse.of(SuccessStatus._OK, new TestResponse("정상 처리되었습니다."));
    }

    // 유효성 검사 테스트
    @PostMapping("/validate")
    public ApiResponse<String> validate(@Valid @RequestBody SampleRequestDto dto) {
        return ApiResponse.of(SuccessStatus._OK, "통과되었습니다");
    }
}
