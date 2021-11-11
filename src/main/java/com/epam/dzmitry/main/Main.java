package com.epam.dzmitry.main;

import com.epam.dzmitry.calculator.Calculator;
import com.epam.dzmitry.calculator.impl.CalculatorImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Calculator calculator = new CalculatorImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression");
        String expression = scanner.nextLine();
        System.out.println("Result = " + calculator.calculate(expression));
    }
}
