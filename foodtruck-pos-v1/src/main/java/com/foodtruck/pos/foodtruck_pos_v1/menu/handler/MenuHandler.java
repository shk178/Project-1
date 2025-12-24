package com.foodtruck.pos.foodtruck_pos_v1.menu.handler;

import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.Menu;
import com.foodtruck.pos.foodtruck_pos_v1.menu.service.MenuFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MenuHandler {
    private final MenuFindService menuFindService;

    // 콘솔 UI에 표시하는 메뉴 데이터 조회
    public Menu getMenuBy(Menu.MenuType menuType) {
        return menuFindService.findMenuBy(menuType);
    }
}
