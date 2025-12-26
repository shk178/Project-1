package com.foodtruck.pos.foodtruck_pos_v2.user;

import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.RestartSessionException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@Profile("user")
@RequiredArgsConstructor
@Component
public class UserKiosk implements ApplicationRunner {
    private final UserCartScreen userCartScreen;
    private final UserMenuScreen userMenuScreen;
    private final UserOrderScreen userOrderScreen;
    private final UserPaymentScreen userPaymentScreen;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        cpl("사용자 키오스크를 시작합니다.");
        while (true) {
            try {
                runUserSession();
            } catch (RestartSessionException e) {
                cpl(e.getMessage());
                cpl("사용자 키오스크를 다시 시작합니다.");
            } catch (ExitApplicationException e) {
                cpl(e.getMessage());
                cpl("사용자 키오스크를 종료합니다.");
                break;
            }
        }
    }
    private void runUserSession() {

    }
}
