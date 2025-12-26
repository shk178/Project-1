package com.foodtruck.pos.foodtruck_pos_v2.order;

import java.time.LocalDateTime;

public record Order(
        String orderNo,
        Integer totalAmount,
        String orderState,
        LocalDateTime orderDate,
        Integer waitingNo
) {}
