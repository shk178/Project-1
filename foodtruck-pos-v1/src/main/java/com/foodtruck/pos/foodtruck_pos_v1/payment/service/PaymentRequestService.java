package com.foodtruck.pos.foodtruck_pos_v1.payment.service;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;
import com.foodtruck.pos.foodtruck_pos_v1.payment.domain.PaymentHttpService;

public class PaymentRequestService {
    private final PaymentHttpService paymentHttpService;
    // 결제 인증 요청: 유효한 카드인지
    public PaymentAuthenticateResult requestPaymentAuthentication(OrderNo orderNo, Money amount) {
        PaymentAuthenticateRequest authenticateRequest =
                new PaymentAuthenticateRequest(
                        "1234-5678-1234-5678",
                        LocalDate.of(2026, 12, 25),
                        111,
                        orderNo
                        ,amount
                );
        PaymentAuthenticateResult authenticateResult =
                paymentHttpService.postPaymentAuthentication(authenticateRequest);
        authenticateResult.hasAuthenticationSucceeded();
        return authenticateResult;
    }
    // 결제 승인 요청
    public PaymentApproveResult requestPaymentApproval(String paymentKey, OrderNo orderNo, Money amount) {
        PaymentApproveRequest approvalRequest = new PaymentApproveRequest(paymentKey, orderNo, amount);
        return paymentHttpService.postPaymentApproval(approvalRequest);
    }
}
