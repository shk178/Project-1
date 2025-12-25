package com.foodtruck.pos.foodtruck_pos_v1.payment.domain;

import com.foodtruck.pos.foodtruck_pos_v1.common.vo.Money;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderNo;
import lombok.Builder;
import lombok.Getter;

@Builder(builderMethodName = "innerBuilder")
@Getter
public class Payment {
    private long paymentId;
    private String paymentKey;
    private OrderNo orderNo;
    private Money amount;
    private State state;

    public static PaymentBuilder builder(String paymentKey, OrderNo orderNo, Money amount, State state) {
        return innerBuilder()
                .paymentKey(paymentKey)
                .orderNo(orderNo)
                .amount(amount)
                .state(state);
    }

    @Getter
    public enum State {
        UNPAID("결제 전"),
        IN_PROGRESS("결제 진행 중"),
        PENDING("결제 대기 중"),
        AUTHENTICATION_SUCCEEDED("결제 인증 성공"),
        AUTHENTICATION_FAILED("결제 인증 실패"),
        APPROVAL_SUCCEEDED("결제 승인 성공"),
        APPROVAL_FAILED("결제 승인 실패"),
        CANCEL("결제 취소");
        private final String state;
        State(String state) {
            this.state = state;
        }
    }
}
