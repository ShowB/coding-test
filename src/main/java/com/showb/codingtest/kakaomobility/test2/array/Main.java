//package com.showb.codingtest.kakaomobility.test2.array;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String... args) {
//        Input input = new Input();
//
//        Solution solution = new Solution();
//        int result = solution.solution(input.input());
//
//        Printer printer = new Printer(result);
//        printer.print();
//    }
//
//    record Input(
//            int[] input
//    ) {
//        public Input() {
//            this(scan());
//        }
//
//        private static int[] scan() {
//            Scanner scanner = new Scanner(System.in);
//            String[] strings = scanner.nextLine()
//                    .split(" ");
//
//            int[] result = new int[strings.length];
//
//            for (int i = 0; i < strings.length; i++) {
//                result[i] = Integer.parseInt(strings[i]);
//            }
//
//            return result;
//        }
//    }
//
//    static class Solution {
//        public int solution(int[] A) {
//            int result = 0;
//
//            long totalPlus = 0;
//            for (int i = 1; i < A.length; i++) {
//                long curr = A[i] + totalPlus;
//                long prev = A[i - 1];
//
//                if (curr <= prev) {
//                    long diff = prev - curr + 1;
//                    A[i] += (int) diff;
//
//                    totalPlus += diff;
//                    result++;
//                }
//            }
//
//            return result;
//        }
//
//    }
//
//    static class Printer {
//        private final int result;
//
//        public Printer(int result) {
//            this.result = result;
//        }
//
//        public void print() {
//            System.out.println(result);
//        }
//    }
//}
