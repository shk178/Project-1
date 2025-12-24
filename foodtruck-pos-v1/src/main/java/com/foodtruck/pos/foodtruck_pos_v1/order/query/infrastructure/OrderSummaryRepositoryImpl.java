package com.foodtruck.pos.foodtruck_pos_v1.order.query.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSummary;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.port.OrderSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSummaryRepositoryImpl implements OrderSummaryRepository {
    private final JdbcClient jdbcClient;

    @Override
    public List<OrderSummary> findAll() {
        return null;
    }
}
