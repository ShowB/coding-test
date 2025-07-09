package com.showb.codingtest.baekjoon.gifticon;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Input input;

        if (args.length == 0) {
            input = new Input();
        } else {
            input = new Input(args);
        }

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

            int gifticonCnt = scanner.nextInt();
            this.remainExpirationDates = new int[gifticonCnt];
            this.remainUsingDates = new int[gifticonCnt];

            for (int i = 0; i < gifticonCnt; i++) {
                this.remainExpirationDates[i] = scanner.nextInt();
            }

            for (int i = 0; i < gifticonCnt; i++) {
                this.remainUsingDates[i] = scanner.nextInt();
            }
        }

        public Input(String... args) {
            String firstLine = args[0];
            String secondLine = args[1];
            String thirdLine = args[2];

            int gifticonCnt = Integer.parseInt(firstLine);

            this.remainExpirationDates = new int[gifticonCnt];
            this.remainUsingDates = new int[gifticonCnt];

            String[] secondLineSplits = secondLine.split(" ");
            String[] thirdLineSplits = thirdLine.split(" ");

            for (int i = 0; i < gifticonCnt; i++) {
                this.remainExpirationDates[i] = Integer.parseInt(secondLineSplits[i]);
                this.remainUsingDates[i] = Integer.parseInt(thirdLineSplits[i]);
            }
        }

        public int[] getRemainExpirationDates() {
            return remainExpirationDates;
        }

        public int[] getRemainUsingDates() {
            return remainUsingDates;
        }
    }


    static class Gifticon implements Comparable<Gifticon> {
        int expirationDate;
        int useDate;

        public Gifticon(int expirationDate, int useDate) {
            this.expirationDate = expirationDate;
            this.useDate = useDate;
        }

        @Override
        public int compareTo(Gifticon other) {
            return this.useDate - other.useDate;
        }
    }

    static class Calculator {
        private final Gifticon[] gifticons;

        public Calculator(int[] remainExpirationDates, int[] remainUsingDates) {
            int gifticonCnt = remainExpirationDates.length;

            this.gifticons = new Gifticon[gifticonCnt];
            for (int i = 0; i < gifticonCnt; i++) {
                gifticons[i] = new Gifticon(remainExpirationDates[i], remainUsingDates[i]);
            }
        }

        public long calculate() {
            long result = 0;

            // 사용계획일이 짧은 순서부터 정렬
            Arrays.sort(gifticons);

            // 모든 기프티콘의 유효기간이 사용계획일자보다 짧으면 그렇지 않게 될 만큼 유효기간 연장 처리
            for (Gifticon current : gifticons) {
                if (current.expirationDate < current.useDate) {
                    int diff = current.useDate - current.expirationDate;

                    int delayCnt = diff / 30;
                    if (diff % 30 > 0) {
                        delayCnt++;
                    }

                    result += delayCnt;
                    current.expirationDate += delayCnt * 30;
                }
            }

            // 앞선 기프티콘보다 사용계획일자는 큰데 유효기간이 짧은 기프티콘들은 그러지 않게 될 때 까지 유효기간 연장 처리
            for (int i = 0; i < gifticons.length; i++) {
                Gifticon current = gifticons[i];
                for (int j = i + 1; j < gifticons.length; j++) {
                    Gifticon next = gifticons[j];

                    if (current.useDate < next.useDate && current.expirationDate > next.expirationDate) {
                        int diff = current.expirationDate - next.expirationDate;

                        int delayCnt = diff / 30;
                        if (diff % 30 > 0) {
                            delayCnt++;
                        }

                        result += delayCnt;
                        next.expirationDate += delayCnt * 30;
                    }
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
