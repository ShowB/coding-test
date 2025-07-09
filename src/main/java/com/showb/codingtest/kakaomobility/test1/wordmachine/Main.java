package com.showb.codingtest.kakaomobility.test1.wordmachine;

import java.util.*;

public class Main {
    public static void main(String... args) {
        Input input = new Input();

        Solution solution = new Solution();
        int result = solution.solution(input.input());

        Printer printer = new Printer(result);
        printer.print();
    }

    record Input(
            String input
    ) {
        public Input() {
            this(scan());
        }

        private static String scan() {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }
    }

    record Extractor(
            String input
    ) {
        public String[] extract() {
            return input.split(" ");
        }
    }

    record Validator() {
        public static boolean validateInput(String target) {
            Extractor extractor = new Extractor(target);
            String[] extractedTarget = extractor.extract();

            for (String s : extractedTarget) {
                int inputNum;

                try {
                    inputNum = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    if (Objects.equals(s, "-") || Objects.equals(s, "+") || Objects.equals(s, "DUP") || Objects.equals(s, "POP")) {
                        return true;
                    }

                    return false;
                }

                if (0 > inputNum || inputNum > 1048575) {
                    return false;
                }
            }

            return true;
        }
    }

    static class Solution {
        private static final int ERROR_CODE = -1;

        public int solution(String S) {
            boolean validated = Validator.validateInput(S);

            if (!validated) {
                return -1;
            }

            Extractor extractor = new Extractor(S);
            String[] extracteds = extractor.extract();

            Deque<Integer> deque = new ArrayDeque<>();

            for (String extracted : extracteds) {
                int inputNum;

                try {
                    inputNum = Integer.parseInt(extracted);
                    deque.push(inputNum);
                    continue;
                } catch (NumberFormatException ignored) {
                    // 숫자가 아니면 아래 로직에서 처리
                }

                if (Objects.equals(extracted, "DUP")) {
                    if (deque.isEmpty()) {
                        return ERROR_CODE;
                    }

                    deque.push(deque.peek());
                    continue;
                }

                if (Objects.equals(extracted, "POP")) {
                    if (deque.isEmpty()) {
                        return ERROR_CODE;
                    }

                    deque.pop();
                    continue;
                }

                if (Objects.equals(extracted, "+")) {
                    if (deque.size() < 2) {
                        return ERROR_CODE;
                    }

                    int result = deque.pop() + deque.pop();

                    if (result > 1048575) {
                        return ERROR_CODE;
                    }

                    deque.push(result);
                    continue;
                }

                if (Objects.equals(extracted, "-")) {
                    if (deque.size() < 2) {
                        return ERROR_CODE;
                    }

                    int difference = deque.pop() - deque.pop();

                    if (difference < 0) {
                        return ERROR_CODE;
                    }

                    deque.push(difference);
                }
            }

            if (deque.isEmpty()) {
                return ERROR_CODE;
            }

            return deque.peek();
        }
    }

    static class Printer {
        private final int result;

        public Printer(int result) {
            this.result = result;
        }

        public void print() {
            System.out.println(result);
        }
    }
}
