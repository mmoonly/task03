package com.epam.dzmitry.calculator;

public interface Calculator {

    int calculate(String expression);

    String convertToPostfix(String infixExpr);

    int evaluatePostfix(String postfixExpr);
}
