package com.foodtruck.pos.foodtruck_pos_v1.order.dto;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderItem;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResult(
        OrderNo orderNo,
        LocalDateTime orderDate,
        List<OrderItem> orderItems,
        Money totalAmount,
        int waitingNo
) {
}
