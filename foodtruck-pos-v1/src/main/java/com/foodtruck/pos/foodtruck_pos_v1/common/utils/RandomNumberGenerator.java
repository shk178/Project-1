package com.foodtruck.pos.foodtruck_pos_v1.common.utils;

import java.util.Random;

public class RandomNumberGenerator {
    private final static Random random = new Random();
    public static int generate(int length) {
        int powerOfTen = (int) Math.pow(10, length - 1);
        return random.nextInt(
                (powerOfTen * 10) - powerOfTen
        ) + powerOfTen;
    }
}
