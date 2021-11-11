package com.epam.dzmitry.calculator.impl;

import com.epam.dzmitry.calculator.Calculator;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorImpl implements Calculator {

    @Override
    public int calculate(String expression) {
        return evaluatePostfix(convertToPostfix(expression));
    }

    @Override
    public String convertToPostfix(String infixExpr) {
        List<String> exprElements = splitExpression(infixExpr);
        Deque<Operator> operatorsStack = new LinkedList<>();
        StringBuilder resultBuilder = new StringBuilder();

        processInfixExpression(exprElements, operatorsStack, resultBuilder);
        return resultBuilder.toString();
    }

    @Override
    public int evaluatePostfix(String postfixExpr) {
        String[] elements = postfixExpr.split("\\s");
        Deque<Integer> resultStack = new LinkedList<>();

        for (String element : elements) {
            if (!isOperator(element)) {
                resultStack.push(Integer.parseInt(element));
            } else {
                executeArithmeticOperation(resultStack, element);
            }
        }
        return resultStack.pop();
    }

    private List<String> splitExpression(String expr) {
        List<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("[-+*/()]|[\\d]+");
        Matcher matcher = pattern.matcher(expr);
        while (matcher.find()) {
            elements.add(expr.substring(matcher.start(), matcher.end()));
        }
        return elements;
    }

    private boolean isOperator(String element) {
        if (Character.isDigit(element.charAt(0))) {
            return false;
        }
        return takeOperator(element.charAt(0)) != Operator.NOT_OPERATOR;
    }

    private Operator takeOperator(char operator) {
        for (Operator op : Operator.values()) {
            if (op.getOperator() == operator) {
                return op;
            }
        }
        return Operator.NOT_OPERATOR;
    }

    private boolean operatorGreaterOrEqual(Operator op1, Operator op2) {
        return op1.getPriority() >= op2.getPriority();
    }

    private void executeArithmeticOperation(Deque<Integer> resultStack, String element) {
        Operator currOperator = takeOperator(element.charAt(0));
        int operand2 = resultStack.pop();
        int operand1 = resultStack.pop();
        switch (currOperator) {
            case PLUS -> resultStack.push(operand1 + operand2);
            case MINUS -> resultStack.push(operand1 - operand2);
            case MULTIPLY -> resultStack.push(operand1 * operand2);
            case DIVISION -> resultStack.push(operand1 / operand2);
        }
    }

    private void processInfixExpression(List<String> exprElements, Deque<Operator> operatorsStack, StringBuilder resultBuilder) {
        for (String element : exprElements) {
            if (isOperator(element)) {
                processAllOperators(operatorsStack, resultBuilder, element);
            } else {
                resultBuilder.append(element);
                resultBuilder.append(" ");
            }
        }
        while (!operatorsStack.isEmpty()) {
            resultBuilder.append(operatorsStack.pop().getOperator());
            resultBuilder.append(" ");
        }
    }

    private void processAllOperators(Deque<Operator> operatorsStack, StringBuilder resultBuilder, String element) {
        Operator currentOp = takeOperator(element.charAt(0));
        switch (currentOp) {
            case PLUS, MINUS, MULTIPLY, DIVISION -> processBaseOperator(operatorsStack, resultBuilder, currentOp);
            case LEFT_BRACKET -> operatorsStack.push(currentOp);
            case RIGHT_BRACKET -> processRightBracketOperator(operatorsStack, resultBuilder);
        }
    }

    private void processBaseOperator(Deque<Operator> operatorsStack, StringBuilder resultBuilder, Operator currentOp) {
        while (!operatorsStack.isEmpty() && operatorsStack.peek() != Operator.LEFT_BRACKET &&
                operatorGreaterOrEqual(operatorsStack.peek(), currentOp)) {

            resultBuilder.append(operatorsStack.pop().getOperator());
            resultBuilder.append(" ");
        }
        operatorsStack.push(currentOp);
    }

    private void processRightBracketOperator(Deque<Operator> operatorsStack, StringBuilder resultBuilder) {
        while (!operatorsStack.isEmpty() && operatorsStack.peek() != Operator.LEFT_BRACKET) {
            resultBuilder.append(operatorsStack.pop().getOperator());
            resultBuilder.append(" ");
        }
        if (!operatorsStack.isEmpty()) {
            operatorsStack.pop();
        }
    }
}