package com.likelion.session.service.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    // 덧셈 +
    public int add(int number1, int number2) {
        return number1+number2;
    }

    // 뺄셈 -
    public int minus(int number1, int number2) {return number1-number2;}

    // 곱셈 *
    public int multiply(int number1, int number2) {
        return number1*number2;
    }

    // 나눗셈 /
    public int division(int number1, int number2) {
        return number1/number2;
    }
}
