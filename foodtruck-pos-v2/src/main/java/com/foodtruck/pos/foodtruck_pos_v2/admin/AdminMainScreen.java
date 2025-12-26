package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminMainScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminAuthenticationScreen adminAuthenticationScreen;
    private final AdminMenuScreen adminMenuScreen;
    private final AdminOrderScreen adminOrderScreen;
    private final AdminPaymentScreen adminPaymentScreen;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("1. 메뉴 관리");
        cpl("2. 주문 관리");
        cpl("3. 결제 관리");
        cpl("4. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> adminMenuScreen;
            case "2" -> adminOrderScreen;
            case "3" -> adminPaymentScreen;
            case "4" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminAuthenticationScreen;
    }
}
