package com.foodtruck.pos.foodtruck_pos_v1.order.service;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItemRepository;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.service.MenuItemFindService;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderItem;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderRepository;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderPlaceService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemFindService menuItemFindService;
    private final OrderRepository orderRepository;
    private final MenuItemFindService menuItemFindService;

    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        /**
         * 1. OrderRequest를 OrderItems으로 변환
         * 2. 주문 번호 생성
         * 3. 주문 대기 번호 생성
         * 4. Order 객체 생성
         * 5. 결제 처리
         * 6. 주문 정보 저장
         */
        List<OrderItem> orderItems = toOrderItems(orderRequest);
        LocalDateTime orderDate = LocalDateTime.now();
        OrderNo orderNo = new OrderNo(orderRepository.createNewOrderNo(orderDate));
        int waitingNo = orderRepository.findLastWaitingNoBy(orderDate) + 1;
        Order order = new Order(
                orderNo, Order.State.ORDER_RECEIVED,
                orderDate, orderItems, waitingNo
        );
        // 결제 인증(사용 가능한 카드인지) 요청
        PaymentAuthenticationResult authenticationResult = paymentRequestService.requestPaymentAuthentication(orderNo, order.getTotalAmount());
        // 결제 인증 요청 성공 시 주문 접수
        order.place(authenticationResult.paymentKey());
        return orderRepository.save(order);
    }

    private List<OrderItem> toOrderItems(OrderRequest orderRequest) {
        return orderRequest.orderFoods()
                .stream()
                .map(orderFood -> {
                    int menuItemId = orderFood.menuItemId();
                    MenuItem foundMenuItem = menuItemFindService.findMenuItemBy(menuItemId);
                    return OrderItem.create(
                            foundMenuItem.getMenuItemName(),
                            new Money(foundMenuItem.getPrice()),
                            orderFood.quantity(),
                            menuItemId
                    );
                })
                .toList();
    }
}
