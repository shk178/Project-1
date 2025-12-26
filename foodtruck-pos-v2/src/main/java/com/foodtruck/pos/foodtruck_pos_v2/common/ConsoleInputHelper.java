package com.foodtruck.pos.foodtruck_pos_v2.common;

import java.util.Scanner;

public class ConsoleInputHelper implements AutoCloseable {
    private final static Scanner scanner = new Scanner(System.in);
    public static String ciq() {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals(":q")) {
                throw new BackScreenException();
            }
            return input;
        }
        return "";
    }
    public static String ci() {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            return input;
        }
        return "";
    }
    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
