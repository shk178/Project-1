package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import com.foodtruck.pos.foodtruck_pos_v2.menu.MenuHandler;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminManageMenuItemScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminMenuScreen adminMenuScreen;
    private final MenuHandler menuHandler;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 메뉴 아이템 추가/수정/삭제 ]");
        readMenuItems();
        cpl("1. 메뉴 아이템 추가");
        cpl("2. 메뉴 아이템 수정");
        cpl("3. 메뉴 아이템 삭제");
        cpl("4. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> createMenuItem();
            case "2" -> updateMenuItem();
            case "3" -> deleteMenuItem();
            case "4" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminMenuScreen;
    }
    private void readMenuItems() {
        menuHandler.readMenuItems();
    }
    private Screen createMenuItem() {
        menuHandler.createMenuItem();
        cpl("메뉴 아이템이 추가됐습니다.");
        return this;
    }
    private Screen updateMenuItem() {
        menuHandler.updateMenuItem();
        cpl("메뉴 아이템이 수정됐습니다.");
        return this;
    }
    private Screen deleteMenuItem() {
        menuHandler.deleteMenuItem();
        cpl("메뉴 아이템이 삭제됐습니다.");
        return this;
    }
}
