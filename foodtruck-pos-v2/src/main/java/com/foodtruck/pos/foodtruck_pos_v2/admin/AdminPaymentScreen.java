package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminPaymentScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminMainScreen adminMainScreen;
    private final AdminSearchPaymentScreen adminSearchPaymentScreen;
    private final AdminManagePaymentScreen adminManagePaymentScreen;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 결제 관리 ]");
        cpl("1. 결제 조회");
        cpl("2. 결제 추가/수정/삭제");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> searchPayment();
            case "2" -> managePayment();
            case "3" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminMainScreen;
    }
    private Screen searchPayment() {
        return adminSearchPaymentScreen;
    }
    private Screen managePayment() {
        return adminManagePaymentScreen;
    }
}
