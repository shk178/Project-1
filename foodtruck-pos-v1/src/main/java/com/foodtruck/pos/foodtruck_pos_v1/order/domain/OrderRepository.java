package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

import java.time.LocalDateTime;

public interface OrderRepository {
    Order save(Order order);

    Order findBy(String endOrderNo);

    int findLastWaitingNoBy(LocalDateTime orderDate);
}
