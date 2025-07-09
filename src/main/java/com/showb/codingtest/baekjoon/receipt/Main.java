package com.showb.codingtest.baekjoon.receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Input input = new Input();

        Validator validator = new Validator(input.getTotalCost(), input.getGoodsList());
        String result = validator.validate();

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Input {
        private final int totalCost;
        private final List<Goods> goodsList;

        public Input() {
            Scanner scanner = new Scanner(System.in);

            this.totalCost = scanner.nextInt();
            int qty = scanner.nextInt();

            this.goodsList = new ArrayList<>(qty);

            for (int i = 0; i < qty; i++) {
                int cost = scanner.nextInt();
                int quantity = scanner.nextInt();

                Goods goods = new Goods(cost, quantity);
                this.goodsList.add(goods);
            }
        }

        public int getTotalCost() {
            return totalCost;
        }

        public List<Goods> getGoodsList() {
            return goodsList;
        }
    }

    static class Goods {
        private final int cost;
        private final int qty;

        public Goods(int cost, int qty) {
            this.cost = cost;
            this.qty = qty;
        }

        public int getCost() {
            return cost;
        }

        public int getQty() {
            return qty;
        }
    }

    static class Validator {
        private final List<Goods> goodsList;
        private final int totalCost;

        public Validator(int totalCost, List<Goods> goodsList) {
            this.totalCost = totalCost;
            this.goodsList = goodsList;
        }

        public String validate() {
            int calculatedCost = 0;
            for (Goods goods : this.goodsList) {
                calculatedCost += goods.getCost() * goods.getQty();
            }

            if (calculatedCost == this.totalCost) {
                return "Yes";
            }

            return "No";
        }
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
