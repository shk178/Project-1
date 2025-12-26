package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import com.foodtruck.pos.foodtruck_pos_v2.payment.PaymentHandler;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminSearchPaymentScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminPaymentScreen adminPaymentScreen;
    private final PaymentHandler paymentHandler;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 결제 조회 ]");
        cpl("1. 결제 기간으로 조회");
        cpl("2. 결제 상태로 조회");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> searchPaymentByDate();
            case "2" -> searchPaymentByState();
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
    private Screen searchPaymentByDate() {
        paymentHandler.searchPaymentByDate();
        return this;
    }
    private Screen searchPaymentByState() {
        paymentHandler.searchPaymentByState();
        return this;
    }
}
