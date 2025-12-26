package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.RestartSessionException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsoleInputHelper.ci;
import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cp;
import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminAuthenticationScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminMainScreen adminMenuScreen;
    @Override
    protected void printContent() {
        cpl("[ 관리자 로그인 ]");
        cpl("1. 로그인");
        cpl("2. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> login();
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
    private Screen login() {
        authenticationHandler.logout();
        cp("ID: ");
        String loginId = ci();
        cp("Password: ");
        String password = ci();
        if (authenticationHandler.login(loginId, password)) {
            cpl("로그인 성공");
            return adminMenuScreen;
        } else {
            cpl("로그인 실패");
            return this;
        }
    }
}
