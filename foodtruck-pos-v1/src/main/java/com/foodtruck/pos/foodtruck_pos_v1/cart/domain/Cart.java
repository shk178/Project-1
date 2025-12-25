package com.foodtruck.pos.foodtruck_pos_v1.cart.domain;

import com.foodtruck.pos.foodtruck_pos_v1.cart.exception.NotFoundCartItemException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private final int cartId;
    private final List<CartItem> cartItemList = new ArrayList<>();

    public Cart(int cartId) {
        this.cartId = cartId;
    }

    public void addCartItem(CartItem newItem) {
        // 이미 카트에 추가된 CartItem이라면 수량을 업데이트
        if (contains(cartItemList)) {
            CartItem updatedCartItem = updateCartItemQuantity(newItem);
            cartItemList.remove(newItem);
            cartItemList.add(updatedCartItem);
        } else {
            cartItemList.add(newItem);
        }
    }

    private boolean contains(CartItem newItem) {
        return this.cartItemList
                .stream()
                .anyMatch(existingItem ->
                        isSameItem(existingItem, newItem));
    }

    private boolean isSameItem(CartItem existingItem, CartItem newItem) {
        return existingItem.equals(newItem);
    }

    private CartItem updateCartItemQuantity(CartItem newItem) {
        return cartItemList.stream()
                .filter(existingItem -> isSameItem(existingItem, newItem))
                .map(existingItem -> new CartItem(existingItem.menuItem(), existingItem.quantity() + newItem.quantity())
                )
                .findFirst()
                .orElseThrow(new NotFoundCartItemException());
    }

    public int calculatePaymentDueAmount() {
        return this.cartItemList
                .stream()
                .map(this::calculateTotalPricePerMenuItem)
                .mapToInt(n -> n)
                .sum();
    }

    private int calculateTotalPricePerMenuItem(CartItem cartItem) {
        return cartItem.totalPrice();
    }
}
