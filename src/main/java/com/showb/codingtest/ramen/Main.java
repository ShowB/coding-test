package com.showb.codingtest.ramen;

import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Input input = new Input();
        Calculator calculator = new Calculator(input.getFactoryCnt(), input.getRamenCnt());
        long result = calculator.calculate();

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Input {
        private final int factoryCnt;
        private final int[] ramenCnt;

        public Input() {
            Scanner scanner = new Scanner(System.in);

            this.factoryCnt = scanner.nextInt();
            this.ramenCnt = new int[factoryCnt];

            for (int i = 0; i < factoryCnt; i++) {
                ramenCnt[i] = scanner.nextInt();
            }
        }

        public int getFactoryCnt() {
            return factoryCnt;
        }

        public int[] getRamenCnt() {
            return ramenCnt;
        }
    }


    static class Calculator {
        private final int factoryCnt;
        private final int[] ramenCnt;

        public Calculator(int factoryCnt, int[] ramenCnt) {
            this.factoryCnt = factoryCnt;
            this.ramenCnt = ramenCnt;
        }

        public long calculate() {
            long totalCost = 0;

            for (int i = 0; i < this.factoryCnt; i++) {
                if (this.ramenCnt[i] == 0) {
                    continue;
                }

                if (i + 2 < this.factoryCnt) {
                    if (this.ramenCnt[i + 1] > this.ramenCnt[i + 2]) {
                        int minCnt = Math.min(this.ramenCnt[i], this.ramenCnt[i + 1] - this.ramenCnt[i + 2]);
                        totalCost += (long) minCnt * 5;
                        this.ramenCnt[i] -= minCnt;
                        this.ramenCnt[i + 1] -= minCnt;
                    }

                    int minCnt = Math.min(this.ramenCnt[i], Math.min(this.ramenCnt[i + 1], this.ramenCnt[i + 2]));
                    totalCost += (long) minCnt * 7;
                    this.ramenCnt[i] -= minCnt;
                    this.ramenCnt[i + 1] -= minCnt;
                    this.ramenCnt[i + 2] -= minCnt;
                }

                if (i + 1 < this.factoryCnt) {
                    int minCnt = Math.min(this.ramenCnt[i], this.ramenCnt[i + 1]);
                    totalCost += (long) minCnt * 5;
                    this.ramenCnt[i] -= minCnt;
                    this.ramenCnt[i + 1] -= minCnt;
                }

                totalCost += (long) this.ramenCnt[i] * 3;
                this.ramenCnt[i] = 0;
            }

            return totalCost;
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
