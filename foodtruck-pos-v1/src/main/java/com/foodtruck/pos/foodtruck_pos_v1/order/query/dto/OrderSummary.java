package com.foodtruck.pos.foodtruck_pos_v1.order.query.dto;

import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.Payment;

import java.time.LocalDateTime;
import java.util.List;

public record OrderSummary(
        int waitingNo,
        OrderNo orderNo,
        LocalDateTime orderDate,
        Order.State orderState,
        Payment.State paymentState,
        List<OrderItemSummary> orderItemSummaries
) {
}
