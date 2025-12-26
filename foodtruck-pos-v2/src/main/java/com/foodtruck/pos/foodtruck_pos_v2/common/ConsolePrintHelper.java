package com.foodtruck.pos.foodtruck_pos_v2.common;

public class ConsolePrintHelper {
    public static void cp(String value) {
        System.out.print(value);
    }
    public static void cpl() {
        System.out.println();
    }
    public static void cpl(String value) {
        System.out.println(value);
    }
    public static void cpf(String format, Object ... args) {
        System.out.printf(format, args);
    }
}
