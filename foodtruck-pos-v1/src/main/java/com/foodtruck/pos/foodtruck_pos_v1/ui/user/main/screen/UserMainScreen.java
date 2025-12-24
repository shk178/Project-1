package com.foodtruck.pos.foodtruck_pos_v1.ui.user.main.screen;

import com.foodtruck.pos.foodtruck_pos_v1.ui.Screen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.UserScreenContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.main.content.UserMainContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer.ConsolePrinter.println;

@RequiredArgsConstructor
@Component
public class UserMainScreen extends Screen {
    private final UserMainContent userMainContent = (UserMainContent) UserScreenContent.USER_MAIN.getContent();
    @Override
    public void render() {
        println(userMainContent.header().breadcrumb());
        println(userMainContent.body().content());
    }
}
