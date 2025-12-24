package com.foodtruck.pos.foodtruck_pos_v1.cart.domain;

public interface CartRepository {
    void save(Cart cart);
    Cart findById(int cartId);
    void clear(int cartId);
}
