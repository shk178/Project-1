package com.foodtruck.pos.foodtruck_pos_v1.order.query.port;

import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSales;

import java.time.LocalDate;
import java.util.List;

public interface OrderSalesRepository {
    List<OrderSales> findByDaily(LocalDate localDate);

    List<OrderSales> findByMonthly(int year);
}
