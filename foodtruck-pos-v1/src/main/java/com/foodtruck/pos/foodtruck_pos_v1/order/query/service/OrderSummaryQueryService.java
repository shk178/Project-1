package com.foodtruck.pos.foodtruck_pos_v1.order.query.service;

import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSummary;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.port.OrderSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderSummaryQueryService {
    private final OrderSummaryRepository orderSummaryRepository;
    @Transactional(readOnly = true)
    public List<OrderSummary> findOrderSummaries() {
        return null;
    }
}
