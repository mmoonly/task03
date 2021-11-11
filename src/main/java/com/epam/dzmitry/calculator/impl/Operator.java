package com.epam.dzmitry.calculator.impl;

public enum Operator {

    PLUS('+', 1),
    MINUS('-', 1),
    MULTIPLY('*', 2),
    DIVISION('/', 2),
    LEFT_BRACKET('(', 0),
    RIGHT_BRACKET(')', 0),
    NOT_OPERATOR('x', -1);

    private final char operator;
    private final int priority;

    Operator(char operator, int priority) {
        this.operator = operator;
        this.priority = priority;
    }

    public char getOperator() {
        return operator;
    }

    public int getPriority() {
        return priority;
    }
}
