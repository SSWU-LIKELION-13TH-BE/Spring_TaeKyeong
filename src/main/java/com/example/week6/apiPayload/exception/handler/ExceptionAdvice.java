package com.example.week6.apiPayload.exception.handler;


import com.example.week6.apiPayload.code.ErrorStatus;
import com.example.week6.apiPayload.dto.ApiResponse;
import com.example.week6.apiPayload.dto.ErrorReasonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    //GeneralException 처리
    @ExceptionHandler(com.example.project.apiPayload.exception.GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(com.example.project.apiPayload.exception.GeneralException e) {
        ErrorReasonDTO reason = e.getErrorReasonHttpStatus();
        return new ResponseEntity<>(ApiResponse.onFailure(reason.getCode
                (), reason.getMessage(), null), reason.getHttpStatus());
    }

    //@Valid 유효성 검사 실패 처리
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(), "Validation Error", errors), HttpStatus.BAD_REQUEST);
    }

// 그 외 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(), e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


