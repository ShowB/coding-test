package com.showb.codingtest.baekjoon.dog;

public class Main {
    public static void main(String... args) {
        String result = "|\\_/|\n" +
                "|q p|   /}\n" +
                "( 0 )\"\"\"\\\n" +
                "|\"^\"`    |\n" +
                "||_/=\\\\__|";

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Printer {
        private final String result;

        public Printer(String result) {
            this.result = result;
        }

        public void print() {
            System.out.println(result);
        }
    }
}
