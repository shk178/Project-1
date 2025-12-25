package com.foodtruck.pos.foodtruck_pos_v1.payment.dto;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.Payment;

public record PaymentAuthenticationResult(String paymentKey, OrderNo orderNo, Money amount, Payment.State state) {
    public void hasAuthenticationSucceeded() {
        if (!this.state().equals(Payment.State.AUTHENTICATION_SUCCEEDED)) {
            throw new PaymentAuthenticationFailedException();
        }
    }
}
