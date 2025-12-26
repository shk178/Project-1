package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import com.foodtruck.pos.foodtruck_pos_v2.payment.PaymentHandler;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminManagePaymentScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminPaymentScreen adminPaymentScreen;
    private final PaymentHandler paymentHandler;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 결제 추가/수정/삭제 ]");
        readPayments();
        cpl("1. 결제 추가");
        cpl("2. 결제 수정");
        cpl("3. 결제 삭제");
        cpl("4. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> createPayment();
            case "2" -> updatePayment();
            case "3" -> deletePayment();
            case "4" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminPaymentScreen;
    }
    private void readPayments() {
        paymentHandler.readPayments();
    }
    private Screen createPayment() {
        paymentHandler.createPayment();
        cpl("결제가 추가됐습니다.");
        return this;
    }
    private Screen updatePayment() {
        paymentHandler.updatePayment();
        cpl("결제가 수정됐습니다.");
        return this;
    }
    private Screen deletePayment() {
        paymentHandler.deletePayment();
        cpl("결제가 삭제됐습니다.");
        return this;
    }
}
