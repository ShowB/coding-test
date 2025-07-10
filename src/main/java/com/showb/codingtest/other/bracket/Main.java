package com.showb.codingtest.other.bracket;

import java.util.*;

public class Main {
    public static void main(String... args) {
        Input input = new Input();
        BracketValidator bracketValidator = new BracketValidator(input.line);
        ValidationResult result = bracketValidator.validate();

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Input {
        private final String line;

        public Input() {
            Scanner scanner = new Scanner(System.in);

            this.line = scanner.nextLine();
        }
    }

    static class BracketValidator {
        private final String input;

        public BracketValidator(String input) {
            this.input = input;
        }

        public ValidationResult validate() {
            Deque<IndexAndChar> stack = new ArrayDeque<>();

            char[] inputArray = input.toCharArray();
            for (int i = 0; i < inputArray.length; i++) {
                char c = inputArray[i];

                switch (c) {
                    case '(', '{', '[', '<':
                        stack.push(new IndexAndChar(i, c));
                        break;
                    case ')', '}', ']', '>':
                        if (stack.isEmpty()) {
                            return ValidationResult.failToClose(i, c);
                        }

                        IndexAndChar pop = stack.pop();

                        if ((c == ')' && pop.value != '(') ||
                                (c == '}' && pop.value != '{') ||
                                (c == ']' && pop.value != '[') ||
                                (c == '>' && pop.value != '<')) {
                            return ValidationResult.failToClose(i, c);
                        }

                        break;
                    default:
                        break;
                }
            }

            if (!stack.isEmpty()) {
                IndexAndChar pop = stack.pop();
                return ValidationResult.failToOpen(pop.index, pop.value);
            }

            return ValidationResult.pass();
        }
    }

    static class IndexAndChar {
        private final int index;
        private final char value;

        public IndexAndChar(int index, char value) {
            this.index = index;
            this.value = value;
        }
    }

    static class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;
        private final Integer errorPosition;
        private final Character problematicChar;

        public ValidationResult(
                boolean isValid,
                String errorMessage,
                Integer errorPosition,
                Character problematicChar
        ) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
            this.errorPosition = errorPosition;
            this.problematicChar = problematicChar;
        }

        public static ValidationResult pass() {
            return new ValidationResult(true, null, null, null);
        }

        public static ValidationResult failToClose(int errorPosition, char problematicChar) {
            return new ValidationResult(false, "매칭되지 않는 닫는 괄호", errorPosition, problematicChar);
        }

        public static ValidationResult failToOpen(int errorPosition, char problematicChar) {
            return new ValidationResult(false, "매칭되지 않는 여는 괄호", errorPosition, problematicChar);
        }

        @Override
        public String toString() {
            if (this.isValid) {
                return "통과되었습니다.";
            }

            return String.format("%s: \"매칭오류 '%s Position %s'\"", this.errorMessage, this.problematicChar, this.errorPosition);
        }
    }

    static class Printer {
        private final ValidationResult result;

        public Printer(ValidationResult result) {
            this.result = result;
        }

        public void print() {
            System.out.println(result.toString());
        }
    }
}
