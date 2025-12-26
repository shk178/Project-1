package com.foodtruck.pos.foodtruck_pos_v2.user;

import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.RestartSessionException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class UserMainScreen extends ScreenTemplate {
    private final UserMenuScreen userMenuScreen;
    @Override
    protected void printContent() {
        cpl("[ 푸드 트럭 키오스크 ]");
        cpl("1. 주문하기");
        cpl("2. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> userMenuScreen;
            case "2" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        throw new RestartSessionException();
    }
}
