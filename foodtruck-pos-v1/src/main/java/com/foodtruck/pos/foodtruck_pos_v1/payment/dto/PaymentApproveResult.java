package com.foodtruck.pos.foodtruck_pos_v1.payment.dto;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.Payment;

public record PaymentApproveResult(String paymentKey, OrderNo orderNo, Money totalAmount, Payment.State state) {
    public Payment toEntity() {
        return Payment
                .builder(this.paymentKey, this.orderNo, this.totalAmount, this.state)
                .build();
    }
}
