package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivideNumbers {

    int result;

    public int divideTwoNumbers(int number1, int number2) {
        return result = number1 / number2;
    }


    public int getResult() {
        return result;
    }
}