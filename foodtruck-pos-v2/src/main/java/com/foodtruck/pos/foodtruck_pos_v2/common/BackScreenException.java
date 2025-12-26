package com.foodtruck.pos.foodtruck_pos_v2.common;

public class BackScreenException extends RuntimeException {
    public BackScreenException() {
        super();
    }

    public BackScreenException(Throwable cause) {
        super(cause);
    }

    public BackScreenException(String message) {
        super(message);
    }

    public BackScreenException(String message, Throwable cause) {
        super(message, cause);
    }
}
