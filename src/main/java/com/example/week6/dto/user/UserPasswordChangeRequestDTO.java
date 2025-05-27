package com.example.week6.dto.user;

import lombok.Getter;

@Getter
public class UserPasswordChangeRequestDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
