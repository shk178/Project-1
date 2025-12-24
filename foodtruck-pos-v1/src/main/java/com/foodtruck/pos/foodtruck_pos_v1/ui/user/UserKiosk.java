package com.foodtruck.pos.foodtruck_pos_v1.ui.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("user")
@RequiredArgsConstructor
@Component
public class UserKiosk implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("# 손님 모드 UI");
    }
}
