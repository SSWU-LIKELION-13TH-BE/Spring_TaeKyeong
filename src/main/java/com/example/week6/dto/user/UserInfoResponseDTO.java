package com.example.week6.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDTO {
    private String userId;
    private String name;
    private String profileImage;
}
