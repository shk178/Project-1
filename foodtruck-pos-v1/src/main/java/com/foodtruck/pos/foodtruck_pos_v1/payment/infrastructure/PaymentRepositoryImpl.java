package com.foodtruck.pos.foodtruck_pos_v1.payment.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.Payment;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JdbcClient jdbcClient;
    @Override
    public Payment save(Payment payment) {
        int updatedRows = updatePayment(payment);
        if (updatedRows == 0) {
            insertPayment(payment);
        }
        return payment;
    }
    @Override
    public Payment findById(long paymentId) {
        return jdbcClient
                .sql("SELECT * FROM payment WHERE payment_id = :paymentId")
                .param("paymentId", paymentId)
                .query(new PaymentRowMapper())
                .optional()
                .orElseThrow(NotFountPaymentException::new);
    }
    private int updatePayment(Payment payment) {
        return jdbcClient
                .sql("UPDATE payment SET payment_state = :paymentState WHERE payment_id = :paymentId")
                .param("paymentState", payment.getState().name())
                .param("paramId", payment.getPaymentId())
                .update();
    }
    private void insertPayment(Payment payment) {
        jdbcClient
                .sql("INSERT INTO payment (payment_key, amount, payment_state, order_no)")
                .param("payment_key", payment.getPaymentKey())
                .param("amount", payment.getAmount())
                .param("payment_state", payment.getState())
                .param("order_no", payment.getOrderNo())
                .update();
    }
}
