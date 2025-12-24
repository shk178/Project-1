package com.foodtruck.pos.foodtruck_pos_v1.order.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcClient jdbcClient;
    @Override
    public Order save(Order order) {
        return null;
    }
    @Override
    public Order findBy(String endOrderNo) {
        return null;
    }
    @Override
    public int findLastWaitingNoBy(LocalDateTime orderDate) {
        return 0;
    }
}
