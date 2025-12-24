package com.foodtruck.pos.foodtruck_pos_v1.order.handler;

import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderRequest;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderResult;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSales;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.service.OrderSalesQueryService;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.dto.OrderSummary;
import com.foodtruck.pos.foodtruck_pos_v1.order.query.service.OrderSummaryQueryService;
import com.foodtruck.pos.foodtruck_pos_v1.order.service.OrderPlaceService;
import com.foodtruck.pos.foodtruck_pos_v1.order.service.OrderStateUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderHandler {
    private final OrderPlaceService orderPlaceService;
    private final OrderStateUpdateService orderStateUpdateService;
    private final OrderSummaryQueryService orderSummaryQueryService;
    private final OrderSalesQueryService orderSalesQueryService;

    public OrderResult requestOrder(OrderRequest orderRequest) {
        return null;
    }
    public void updateOrderState(String endOrderNo, Order.State orderState) {

    }
    public List<OrderSummary> getOrderSummaries() {
        return null;
    }
    public List<OrderSales> getDailyOrderSales(LocalDate localDate) {
        return null;
    }
    public List<OrderSales> getMonthlyOrderSales(int year) {
        return null;
    }
}
