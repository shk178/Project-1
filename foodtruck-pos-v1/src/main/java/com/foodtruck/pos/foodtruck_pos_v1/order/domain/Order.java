package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
public class Order {
    private final OrderNo orderNo;
    private final Money totalAmount;
    private final State orderState;
    private final LocalDateTime orderDate;
    private final List<OrderItem> orderItems;
    private final int waitingNo;

    public Order(OrderNo orderNo, State orderState, LocalDateTime orderDate, List<OrderItem> orderItems, int waitingNo) {
        this.orderNo = orderNo;
        this.orderState = validateOrderState(orderState);
        this.orderDate = validateOrderDate(orderDate);
        this.orderItems = validateOrderItems(orderItems);
        this.waitingNo = validateWaitingNo(waitingNo);
        this.totalAmount = calculateTotalAmount(orderItems);
    }

    public void place(String paymentKey) {
        EventPublisher.publish(new OrderPlacedEvent(paymentKey, orderNo, totalAmount));
    }

    private State validateOrderState(State orderState) {
        Objects.requireNonNull(orderState, "주문 상태는 필수입니다");
        return orderState;
    }
    private LocalDateTime validateOrderDate(LocalDateTime orderDate) {
        Objects.requireNonNull(orderDate, "주문 날짜는 필수입니다");
        return orderDate;
    }
    private List<OrderItem> validateOrderItems(List<OrderItem> orderItems) {
        Objects.requireNonNull(orderItems, "주문 아이템은 필수입니다");
        validateAtLeastOneOrderItem(orderItems);
        return orderItems;
    }
    private Money calculateTotalAmount(List<OrderItem> orderItems) {
        int totalAmount = orderItems.stream()
                .mapToInt(orderItem -> orderItem.getAmount().value())
                .sum();
        return new Money(totalAmount);
    }

    private int validateWaitingNo(int waitingNo) {
        if (waitingNo <= 0) {
            throw new IllegalArgumentException("주문 대기 번호는 0보다 커야 합니다");
        }
        return waitingNo;
    }

    private void validateAtLeastOneOrderItem(List<OrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            throw new IllegalArgumentException("최소 하나 이상의 음식을 주문해야 합니다");
        }
    }

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
