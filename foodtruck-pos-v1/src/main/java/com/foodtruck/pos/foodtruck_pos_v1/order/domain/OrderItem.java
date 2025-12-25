package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(builderMethodName = "innerBuilder", access = AccessLevel.PRIVATE, toBuilder = true)
@Getter
public class OrderItem {
    private long orderItemId;
    private String orderItemName;
    private Money price;
    private int quantity;
    private Money amount;
    private int menuItemId;

    // 필수 값 초기화한 객체 생성 위해 빌더 패턴 재정의
    public static OrderItem create(String orderItemName, Money price, int quantity, int menuItemId) {
        return innerBuilder()
                .orderItemName(orderItemName)
                .price(price)
                .quantity(quantity)
                .menuItemId(menuItemId)
                .amount(calculateAmount(price, quantity))
                .build();
    }

    public OrderItem withOrderItemId(long orderItemId) {
        return this.toBuilder()
                .orderItemId(orderItemId)
                .build();
    }

    private static Money calculateAmount(Money price, int quantity) {
        return price.multiply(quantity);
    }
}
