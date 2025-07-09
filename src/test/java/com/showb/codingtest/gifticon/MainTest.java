package com.showb.codingtest.gifticon;

import java.security.SecureRandom;
import java.util.StringJoiner;

class MainTest {
    public static void main(String[] args) {
        System.out.println("test running...");

        SecureRandom random = new SecureRandom();

        String[] strings = new String[3];

        int gifticonCnt = random.nextInt(10);

        strings[0] = String.valueOf(gifticonCnt);

        StringJoiner secondLineJoiner = new StringJoiner(" ");
        StringJoiner thirdLineJoiner = new StringJoiner(" ");
        for (int i = 0; i < gifticonCnt; i++) {
            secondLineJoiner.add(String.valueOf(random.nextInt(100)));
            thirdLineJoiner.add(String.valueOf(random.nextInt(100)));
        }

        strings[1] = secondLineJoiner.toString();
        strings[2] = thirdLineJoiner.toString();

        System.out.println("----- Input -----");
        System.out.println("gifticon count: \t\t\t" + strings[0]);
        System.out.println("expiration remain date: \t" + strings[1]);
        System.out.println("will use remain date: \t\t" + strings[2]);
        System.out.println("-----------------");

        Main.main(strings);
    }
}
