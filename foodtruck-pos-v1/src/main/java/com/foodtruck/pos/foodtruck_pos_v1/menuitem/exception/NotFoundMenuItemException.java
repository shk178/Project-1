package com.foodtruck.pos.foodtruck_pos_v1.menuitem.exception;

import com.foodtruck.pos.foodtruck_pos_v1.common.exception.ExceptionEnum;

public class NotFoundMenuItemException extends RuntimeException {
    public NotFoundMenuItemException() {
        super(ExceptionEnum.NOT_FOUND_MENU_ITEM.getMessage());
    }
}
