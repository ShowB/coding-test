package com.showb.codingtest.baekjoon.treasure;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Input input = new Input();
        Calculator calculator = new Calculator(input.arrayA, input.arrayB);
        long result = calculator.calculate();

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Input {
        private final int[] arrayA;
        private final int[] arrayB;

        public Input() {
            Scanner scanner = new Scanner(System.in);

            int arrayCnt = scanner.nextInt();
            this.arrayA = new int[arrayCnt];
            this.arrayB = new int[arrayCnt];

            for (int i = 0; i < arrayCnt; i++) {
                arrayA[i] = scanner.nextInt();
            }

            for (int i = 0; i < arrayCnt; i++) {
                arrayB[i] = scanner.nextInt();
            }
        }
    }


    static class Calculator {
        private final int[] arrayA;
        private final int[] arrayB;

        public Calculator(int[] arrayA, int[] arrayB) {
            this.arrayA = arrayA;
            this.arrayB = arrayB;
        }

        public long calculate() {
            long result = 0;

            Arrays.sort(arrayA);
            Arrays.sort(arrayB);

            for (int i = 0; i < arrayB.length / 2; i++) {
                int temp = arrayB[i];
                arrayB[i] = arrayB[arrayB.length - 1 - i];
                arrayB[arrayB.length - 1 - i] = temp;
            }

            for (int i = 0; i < arrayA.length; i++) {
                result += (long) arrayA[i] * arrayB[i];
            }

            return result;
        }
    }

    static class Printer {
        private final long result;

        public Printer(long result) {
            this.result = result;
        }

        public void print() {
            System.out.println(result);
        }
    }
}
