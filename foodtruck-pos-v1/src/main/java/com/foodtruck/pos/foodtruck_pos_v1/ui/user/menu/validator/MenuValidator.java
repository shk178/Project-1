package com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.validator;

import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.validator.NumberValidator.isValidNumber;

public class MenuValidator {
    public static void validateMenuItemNo(String menuItemNo, int menuItemCount) {
        if (!isValidNumber(menuItemNo)) {
            throw new InvalidMenuItemNoException();
        }
        int itemNo = Integer.parseInt(menuItemNo);
        if (itemNo < 1 || itemNo > menuItemCount) {
            throw new InvalidMenuItemNoException();
        }
    }
    public static void validateMenuItemAmount(String amount) {
        if (!isValidNumber(amount)) {
            throw new InvalidOrderAmountException();
        }
        int validAmount = Integer.parseInt(amount);
        if (validAmount <= 0) {
            throw new InvalidOrderAmountException();
        }
    }
}
