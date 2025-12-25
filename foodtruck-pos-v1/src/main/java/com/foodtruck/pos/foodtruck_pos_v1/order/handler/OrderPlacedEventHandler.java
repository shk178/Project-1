package com.foodtruck.pos.foodtruck_pos_v1.order.handler;

import com.foodtruck.pos.foodtruck_pos_v1.payment.service.PaymentRegisterService;
import com.foodtruck.pos.foodtruck_pos_v1.payment.service.PaymentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class OrderPlacedEventHandler {
    private final PaymentRequestService paymentRequestService;
    private final PaymentRegisterService paymentRegisterService;
    // 이벤트를 publish한 쪽의 활성 트랜잭션이 commit된 후에 실행된다.
    @TransactionalEventListener(
            classes = OrderPlacedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(OrderPlacedEvent orderPlacedEvent) {
        // 결제 승인 요청
        PaymentApproveResult approveResult = paymentRequestService.requestPaymentApproval(orderPlacedEvent.paymentKey(),
                orderPlacedEvent.orderNo(),
                orderPlacedEvent.amount());
        // 결제 정보 등록
        paymentRegisterService.registerPayment(approverResult.toEntity());
    }
}
