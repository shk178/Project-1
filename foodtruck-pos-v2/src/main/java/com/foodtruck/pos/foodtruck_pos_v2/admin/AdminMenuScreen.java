package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminMenuScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminMainScreen adminMainScreen;
    private final AdminManageMenuScreen adminManageMenuScreen;
    private final AdminManageMenuItemScreen adminManageMenuItemScreen;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 메뉴 관리 ]");
        cpl("1. 메뉴 추가/수정/삭제");
        cpl("2. 메뉴 아이템 추가/수정/삭제");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> manageMenu();
            case "2" -> manageMenuItem();
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
    private Screen manageMenu() {
        return adminManageMenuScreen;
    }
    private Screen manageMenuItem() {
        return adminManageMenuItemScreen;
    }
}
