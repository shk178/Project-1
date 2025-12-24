package com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer;

public class ConsolePrinter {
    public static void println() {
        System.out.println();
    }
    public static void println(String value) {
        System.out.println(value);
    }
    public static void printf(String format, Object ... args) {
        System.out.printf(format, args);
    }
}
