package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.RestartSessionException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@Profile("admin")
@RequiredArgsConstructor
@Component
public class AdminKiosk implements ApplicationRunner {
    private final AuthenticationHandler authenticationHandler;
    private final AdminAuthenticationScreen adminAuthenticationScreen;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        cpl("관리자 키오스크를 시작합니다.");
        while (true) {
            try {
                runAdminSession();
            } catch (RestartSessionException e) {
                cpl(e.getMessage());
                cpl("관리자 키오스크를 다시 시작합니다.");
            } catch (ExitApplicationException e) {
                cpl(e.getMessage());
                cpl("관리자 키오스크를 종료합니다.");
                break;
            } finally {
                authenticationHandler.logout();
            }
        }
    }
    private void runAdminSession() {
        Screen currentScreen = adminAuthenticationScreen;
        while (true) {
            currentScreen.render();
            currentScreen = currentScreen.handleInput();
        }
    }
}
