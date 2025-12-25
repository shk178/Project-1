package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

public record OrderNo(String number) {
    public OrderNo {
        if (number == null || number.length() != 24) {
            throw new IllegalArgumentException("주문 번호는 24자리여야 합니다");
        }
    }
}
