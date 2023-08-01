package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDivideNumbers {

        DivideNumbers divideNumbers;

        @BeforeEach
        public void setDivideNumbers() {
            divideNumbers = new DivideNumbers();
        }

        @BeforeEach
        public void arithmeticException(){
            divideNumbers.testDivideByZeroThrowsException();
        }

        //Positive test divide two numbers (15/3)
        @Test
        public void testPositiveDivideTwoNumbers15And3AndReturnsCorrectResult() {
            divideNumbers.divideTwoNumbers(15, 3);
            Assertions.assertEquals(5, divideNumbers.getResult());
        }

        //Positive test divide two numbers (12/4)
        @Test
        public void testPositiveDivideTwoNumbers12And4AndReturnsCorrectResult() {
            divideNumbers.divideTwoNumbers(12, 4);
            Assertions.assertEquals(3, divideNumbers.getResult());
        }

        //Negative test divide two numbers (-15/0)
        @Test
        public void testNegativeDivideTwoNumbers15And0AndReturnsCorrectResult() {
            divideNumbers.divideTwoNumbers(-15, 0);
            Assertions.assertEquals(0, divideNumbers.getResult());
        }

        //Negative test divide two numbers (12/0)
        @Test
        public void testNegativeDivideTwoNumbers12And0AndReturnsCorrectResult() {
            divideNumbers.divideTwoNumbers(12, 0);
            Assertions.assertEquals(0, divideNumbers.getResult());
        }
    }

