package com.foodtruck.pos.foodtruck_pos_v1.cart.dto;

public record CartItemResult(int menuItemId, String menuItemName, int quantity, int totalPrice) {
}
