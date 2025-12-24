package com.foodtruck.pos.foodtruck_pos_v1.ui.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("admin")
@RequiredArgsConstructor
@Component
public class AdminKiosk implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("# 관리자 모드 UI");
    }
}
