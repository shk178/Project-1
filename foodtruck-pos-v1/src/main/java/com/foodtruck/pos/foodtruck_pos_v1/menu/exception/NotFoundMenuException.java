package com.foodtruck.pos.foodtruck_pos_v1.menu.exception;

import com.foodtruck.pos.foodtruck_pos_v1.common.exception.ExceptionEnum;

public class NotFoundMenuException extends RuntimeException {
    public NotFoundMenuException() {
        super(ExceptionEnum.NOT_FOUND_MENU.getMessage());
    }
}
