package com.example.week6.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignupRequestDTO {
    @JsonProperty("username")
    @NotBlank(message = "아이디는 필수임")
    @Size(min=2, message="아이디는 최소 2자 이상임")
    private String userId;

    @NotBlank(message = "비밀번호는 필수임")
    @Size(min=8, message="비밀번호는 최소 8자 이상임")
    private String password;

    @NotBlank(message = "이름은 필수임")
    private String name;
    private String profileImage;
}
