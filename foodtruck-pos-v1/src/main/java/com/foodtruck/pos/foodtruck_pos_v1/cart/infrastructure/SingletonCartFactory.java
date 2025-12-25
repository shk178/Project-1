package com.foodtruck.pos.foodtruck_pos_v1.cart.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.Cart;

public class SingletonCartFactory {
    private static final int CART_ID = 1;
    private final static Cart cartInstance = new Cart(CART_ID);

    private SingletonCartFactory() {}
    public static Cart getInstance() {
        return cartInstance;
    }
}
