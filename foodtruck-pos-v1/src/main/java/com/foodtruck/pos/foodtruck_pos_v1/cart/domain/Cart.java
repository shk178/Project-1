package com.foodtruck.pos.foodtruck_pos_v1.cart.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private final List<CartItem> cartItemList = new ArrayList<>();
}
