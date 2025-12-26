package com.foodtruck.pos.foodtruck_pos_v2.payment;

public record Payment(
        Long paymentId,
        String paymentKey,
        Integer amount,
        String paymentState,
        String orderNo
) {}
