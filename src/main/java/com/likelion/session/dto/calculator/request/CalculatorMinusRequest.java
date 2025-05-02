package com.likelion.session.dto.calculator.request;

public class CalculatorMinusRequest {
        private final int number1;
        private final int number2;

        // 생성자
        public CalculatorMinusRequest(int number1, int number2) {
            this.number1=number1;
            this.number2=number2;
        }

        // Geeter method
        public int getNumber1() {return number1;}
        public int getNumber2() {
            return number2;
        }
}