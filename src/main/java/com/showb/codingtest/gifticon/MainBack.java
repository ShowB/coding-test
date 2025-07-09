package com.showb.codingtest.gifticon;

import java.util.Arrays;
import java.util.Scanner;

public class MainBack {
    public static void main(String... args) {
        Input input = new Input();
        Calculator calculator = new Calculator(input.getRemainExpirationDates(), input.getRemainUsingDates());
        long result = calculator.calculate();

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Input {
        private final int[] remainExpirationDates;
        private final int[] remainUsingDates;

        public Input() {
            Scanner scanner = new Scanner(System.in);

            int giftconCnt = scanner.nextInt();
            this.remainExpirationDates = new int[giftconCnt];
            this.remainUsingDates = new int[giftconCnt];

            for (int i = 0; i < giftconCnt; i++) {
                this.remainExpirationDates[i] = scanner.nextInt();
            }

            for (int i = 0; i < giftconCnt; i++) {
                this.remainUsingDates[i] = scanner.nextInt();
            }
        }

        public int[] getRemainExpirationDates() {
            return remainExpirationDates;
        }

        public int[] getRemainUsingDates() {
            return remainUsingDates;
        }
    }


    static class Calculator {
        private final int[] remainExpirationDates;
        private final int[] remainUsingDates;

        public Calculator(int[] remainExpirationDates, int[] remainUsingDates) {
            this.remainExpirationDates = remainExpirationDates;
            this.remainUsingDates = remainUsingDates;
        }

        public long calculate() {
            int giftconCnt = remainExpirationDates.length;

            long result = 0;
            for (int i = 0; i < giftconCnt; i++) {
                while (remainUsingDates[i] > remainExpirationDates[i]) {
                    // 유효기간이 사용하기로 한 날짜보다 앞서면 그렇지 않게 될 때 까지 유효기간을 30일씩 연장
                    remainExpirationDates[i] += 30;
                    result++;
                }
            }

            // 가장 앞선 기프티콘 사용 계획 일자가 가장 적은 기한을 가질 수 있도록 나머지를 30일씩 연장 처리
            int minUsingDate = Arrays.stream(this.remainUsingDates)
                    .min()
                    .orElse(0);

            int minExpirationDate = Arrays.stream(this.remainExpirationDates)
                    .min()
                    .orElse(0);

            int exceptDelayIndex = this.getExceptDelayIndex(giftconCnt, minUsingDate, minExpirationDate);

            while (-1 != exceptDelayIndex) {
                for (int i = 0; i < giftconCnt; i++) {
                    // exceptDelayIndex 를 제외한 나머지 중
                    if (i == exceptDelayIndex) {
                        continue;
                    }

                    // exceptDelayIndex 의 유효기간보다 짧은 기프티콘은 모두 30일씩 연장 처리
                    if (remainExpirationDates[exceptDelayIndex] > remainExpirationDates[i]) {
                        remainExpirationDates[i] += 30;
                        result++;
                    }
                }

                minUsingDate = Arrays.stream(this.remainUsingDates)
                        .min()
                        .orElse(0);

                minExpirationDate = Arrays.stream(this.remainExpirationDates)
                        .min()
                        .orElse(0);

                exceptDelayIndex = this.getExceptDelayIndex(giftconCnt, minUsingDate, minExpirationDate);
            }

            return result;
        }

        private int getExceptDelayIndex(int giftconCnt, int minUsingDate, int minExpirationDate) {
            int result = -1;

            for (int i = 0; i < giftconCnt; i++) {
                // 사용일자는 가장 앞선 기프티콘이나, 기한이 가장 앞서지 않았을 경우엔 해당 기프티콘의 인덱스를 exceptDelayIndex 에 저장
                if (remainUsingDates[i] == minUsingDate && remainExpirationDates[i] != minExpirationDate) {
                    result = i;
                    break;
                }
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
