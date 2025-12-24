package com.foodtruck.pos.foodtruck_pos_v1.menuitem.handler;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.service.MenuItemFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MenuItemHandler {
    private final MenuItemFindService menuItemFindService;

    // 한 건의 음식 정보 조회
    public MenuItem getMenuItemBy(int menuItemId) {
        return null;
    }
}
