package com.foodtruck.pos.foodtruck_pos_v1.order.domain;

import com.foodtruck.pos.foodtruck_pos_v1.common.utils.RandomNumberGenerator;

import java.time.LocalDateTime;

public interface OrderRepository {
    Order save(Order order);

    Order findBy(String endOrderNo);

    int findLastWaitingNoBy(LocalDateTime orderDate);

    default String createNewOrderNo(LocalDateTime orderDate) {
        int randomNo = RandomNumberGenerator.generate(6);
        return String.format("%tY%<tm%<td%<tH%<tM%<tS%<tL-%d", orderDate, randomNo);
    }
}
