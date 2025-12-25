package com.foodtruck.pos.foodtruck_pos_v1.payment.service;

import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.Payment;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.PaymentHttpService;
import com.foodtruck.pos.foodtruck_pos_v1.payment.dto.PaymentAuthenticationResult;
import org.springframework.stereotype.Service;

@Service
public class MockPaymentHttpService implements PaymentHttpService {
    @Override
    public PaymentAuthenticationResult postPaymentAuthentication(PaymentAuthenticateRequest requestBody) {
        return new PaymentAuthenticationResult(
                "aXux01lmxz0kelaexRmIliM",
                requestBody.orderNo(),
                requestBody.amount(),
                Payment.State.AUTHENTICATION_SUCCEEDED
        );
    }

    @Override
    public PaymentApproveResult postPaymentApproval(PaymentApproveRequest requestBody) {
        return new PaymentApproveResult(
                requestBody.paymentKey(),
                requestBody.orderNo(),
                requestBody.amount(),
                Payment.State.APPROVAL_SUCCEEDED
        );
    }
}
