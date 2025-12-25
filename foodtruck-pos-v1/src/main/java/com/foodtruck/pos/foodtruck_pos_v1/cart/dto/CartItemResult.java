package com.foodtruck.pos.foodtruck_pos_v1.cart.dto;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartItem;

import java.util.List;

public record CartItemResult(int menuItemId, String menuItemName, int quantity, int totalPrice) {
    public static List<CartItemResult> from(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem ->
                        new CartItemResult(
                                cartItem.menuItem().getMenuItemId(),
                                cartItem.menuItem().getMenuItemName(),
                                cartItem.quantity(),
                                cartItem.totalPrice()))
                .toList();
    }
}
