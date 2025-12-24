package com.foodtruck.pos.foodtruck_pos_v1.order.service;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItemRepository;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderRepository;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderPlaceService {
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        return null;
    }
}
