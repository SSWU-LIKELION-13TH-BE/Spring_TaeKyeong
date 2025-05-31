package com.example.week6.apiPayload.code;

import com.example.week6.apiPayload.dto.ErrorReasonDTO;
import com.example.week6.apiPayload.dto.ReasonDTO;

public interface BaseErrorCode {
    ErrorReasonDTO getReason();
    ErrorReasonDTO getReasonHttpStatus();

}
