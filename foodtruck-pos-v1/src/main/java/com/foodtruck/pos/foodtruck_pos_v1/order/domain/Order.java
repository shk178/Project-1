package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {
    private OrderNo orderNo;
    private Money totalAmount;
    private State orderState;
    private LocalDateTime orderDate;
    private List<OrderItem> orderItems;
    private int waitingNo;

    @Getter
    public enum State {
        ORDER_RECEIVED("주문 접수"),
        ORDER_COMPLETE("주문 처리 완료"),
        ORDER_CANCEL("주문 취소");
        private final String state;
        State(String state) {
            this.state = state;
        }
    }
}
