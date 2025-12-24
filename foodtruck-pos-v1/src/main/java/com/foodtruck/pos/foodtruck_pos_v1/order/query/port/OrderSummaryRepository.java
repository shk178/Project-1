package com.foodtruck.pos.foodtruck_pos_v1.order.query.port;

import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSummary;

import java.util.List;

public interface OrderSummaryRepository {
    List<OrderSummary> findAll();
}
