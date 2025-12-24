package com.foodtruck.pos.foodtruck_pos_v1.order.service;

import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderStateUpdateService {
    private final OrderRepository orderRepository;

    @Transactional
    public void updateOrderState(String endOrderNo, Order.State orderState) {

    }
}
