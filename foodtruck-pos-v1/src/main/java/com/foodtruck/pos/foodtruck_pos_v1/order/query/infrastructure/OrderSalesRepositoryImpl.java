package com.foodtruck.pos.foodtruck_pos_v1.order.query.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSales;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.port.OrderSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSalesRepositoryImpl implements OrderSalesRepository {
    private final JdbcClient jdbcClient;

    @Override
    public List<OrderSales> findByDaily(LocalDate localDate) {
        return null;
    }
    @Override
    public List<OrderSales> findByMonthly(int year) {
        return null;
    }
}
