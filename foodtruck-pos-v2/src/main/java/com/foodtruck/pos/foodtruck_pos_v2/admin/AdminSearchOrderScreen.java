package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import com.foodtruck.pos.foodtruck_pos_v2.order.OrderHandler;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminSearchOrderScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminOrderScreen adminOrderScreen;
    private final OrderHandler orderHandler;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 주문 조회 ]");
        cpl("1. 주문 기간으로 조회");
        cpl("2. 주문 상태로 조회");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> searchOrderByDate();
            case "2" -> searchOrderByState();
            case "4" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminOrderScreen;
    }
    private Screen searchOrderByDate() {
        orderHandler.searchOrderByDate();
        return this;
    }
    private Screen searchOrderByState() {
        orderHandler.searchOrderByState();
        return this;
    }
}
