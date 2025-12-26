package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminOrderScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminMainScreen adminMainScreen;
    private final AdminSearchOrderScreen adminSearchOrderScreen;
    private final AdminManageOrderScreen adminManageOrderScreen;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 주문 관리 ]");
        cpl("1. 주문 조회");
        cpl("2. 주문 추가/수정/삭제");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> searchOrder();
            case "2" -> manageOrder();
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
    private Screen searchOrder() {
        return adminSearchOrderScreen;
    }
    private Screen manageOrder() {
        return adminManageOrderScreen;
    }
}
