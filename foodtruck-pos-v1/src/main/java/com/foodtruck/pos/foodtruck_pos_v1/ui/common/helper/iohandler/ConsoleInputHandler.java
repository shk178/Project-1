package com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.iohandler;

import com.foodtruck.pos.foodtruck_pos_v1.ui.common.exception.BackToPreviousScreenException;

import java.util.Scanner;

public class ConsoleInputHandler implements AutoCloseable {
    private final static Scanner scanner = new Scanner(System.in);

    public static String inputValue() {
        if (hasNextLine()) {
            String value = scanner.nextLine();
            preCheck(value);
            return value;
        }
        return "";
    }

    private static boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    private static void preCheck(String value) {
        if (shouldBackToPrevious(value)) {
            throw new BackToPreviousScreenException();
        }
    }

    private static boolean shouldBackToPrevious(String value) {
        return value.equals(":q");
    }

    @Override
    public void close() {
        scanner.close();
    }
}
