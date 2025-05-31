package com.example.week6.test.service;

import com.example.week6.apiPayload.code.ErrorStatus;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void checkFlag(Integer flag) {
        if (flag != null && flag == 1){
        throw new com.example.project.apiPayload.exception.GeneralException(ErrorStatus.TEMP_EXCEPTION);
    }
}
}