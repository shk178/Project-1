package com.foodtruck.pos.foodtruck_pos_v2.order;

public record OrderItem(
        Long orderItemId,
        String orderItemName,
        Integer price,
        Short quantity,
        Integer amount,
        String orderNo,
        Long menuItemId
) {}
