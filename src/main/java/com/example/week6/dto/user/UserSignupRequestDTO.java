package com.example.week6.dto.user;

import lombok.Data;

@Data
public class UserSignupRequestDTO {
    private String userId;
    private String password;
    private String name;
    private String profileImage;
}
