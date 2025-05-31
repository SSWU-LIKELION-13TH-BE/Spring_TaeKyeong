package com.example.week6.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 로그인 요청 DTO
@Data
public class UserLoginRequestDTO {
    @JsonProperty("username")
    private String userId;
    private String password;
}
