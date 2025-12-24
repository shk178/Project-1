package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

import lombok.Getter;

@Getter
public class OrderItem {
    private long orderItemId;
    private Money price;
    private int quantity;
    private Money amount;
    private int menuItemId;
}
