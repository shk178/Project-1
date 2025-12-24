package com.foodtruck.pos.foodtruck_pos_v1.order.query.service;

import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSales;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.port.OrderSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderSalesQueryService {
    private final OrderSalesRepository orderSalesRepository;
    public List<OrderSales> findDailyOrderSales(LocalDate localDate) {
        return null;
    }
    public List<OrderSales> findMonthlyOrderSales(int year) {
        return null;
    }
}
