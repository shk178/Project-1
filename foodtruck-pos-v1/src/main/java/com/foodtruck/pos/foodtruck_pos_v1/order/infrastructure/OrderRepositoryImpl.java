package com.foodtruck.pos.foodtruck_pos_v1.order.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.order.domain.Order;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderItem;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcClient jdbcClient;
    @Override
    public Order save(Order order) {
        int updatedRow = updateOrder(order);
        if (updatedRow == 0) {
            insertOrder(order);
        }
        return order;
    }
    @Override
    public Order findBy(String endOrderNo) {
        return null;
    }
    @Override
    public int findLastWaitingNoBy(LocalDateTime orderDate) {
        /**
         * (orderDate) 주문 시점: 25.12.25 12:59:33
         * (startDate) 주문 대기번호 카운팅 시작 시점: 25.12.25
         * (atStartOfDay) 25.12.25 00:00
         * (endDate) 주문 대기번호 카운팅 종료 시점: 25.12.26 23:59:59
         */
        LocalDate startDate = orderDate.toLocalDate();
        LocalDateTime endDate = startDate.atStartOfDay().plusDays(1).minusSeconds(1);
        return jdbcClient
                .sql("SELECT MAX(waiting_no) FROM orders WHERE order_date BETWEEN :startDate And :endDate")
                .param("startDate", startDate)
                .param("endDate", endDate)
                .query(Integer.class)
                .optional()
                .orElse(0);
    }

    private int updateOrder(Order order) {
        return jdbcClient
                .sql("UPDATE orders SET order_state = :orderState WHERE order_no = :orderNo")
                .param("orderState", order.getOrderState().name())
                .param("orderNo", order.getOrderNo().number())
                .update(); // 업데이트 된 row 개수를 리턴한다.
    }

    private void insertOrder(Order order) {
        String queryForOrder = """
                    INSERT INTO ORDERS (order_no, total_amount, order_state, order_date, waiting_no)
                    VALUES (:orderNo, :totalAmount, :orderState, :orderDate, :waitingNo)
                """;
        int insertedRow =
                jdbcClient.sql(queryForOrder)
                        .param("orderNo", order.getOrderNo().number())
                        .param("totalAmount", order.getTotalAmount().value())
                        .param("orderState", order.getOrderState().getState())
                        .param("orderDate", order.getOrderDate())
                        .param("waitingNo", order.getWaitingNo())
                        .update();
        if (insertedRow > 0) {
            List<OrderItem> orderItems = order.getOrderItems();
            String queryForOrderItem = """
                        INSERT INTO ORDER_ITEM (order_item_name, price, quantity, amount, order_no, menu_item_id)
                        VALUE (:orderItemName, :price, :quantity, :amount, :orderNo, :menuItemId)
                    """;
            orderItems.forEach(orderItem ->
                    jdbcClient.sql(queryForOrderItem)
                            .param("orderItemName", orderItem.getOrderItemName())
                            .param("price", orderItem.getPrice().value())
                            .param("quantity", orderItem.getQuantity())
                            .param("amount", orderItem.getAmount().value())
                            .param("orderNo", order.getOrderNo().number())
                            .param("menuItemId", orderItem.getMenuItemId())
                            .update()
            );
        }
    }
}
