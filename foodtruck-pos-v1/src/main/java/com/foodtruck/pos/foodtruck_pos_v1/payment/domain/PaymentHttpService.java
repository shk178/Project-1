package com.foodtruck.pos.foodtruck_pos_v1.payment.domain;

import com.foodtruck.pos.foodtruck_pos_v1.payment.dto.PaymentAuthenticationResult;

public interface PaymentHttpService {
    PaymentAuthenticationResult postPaymentAuthentication(PaymentAuthenticateRequest requestBody);
    PaymentApproveResult postPaymentApproval(PaymentApproveRequest requestBody);
}
