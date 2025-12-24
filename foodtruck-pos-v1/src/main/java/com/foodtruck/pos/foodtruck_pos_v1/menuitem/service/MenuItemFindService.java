package com.foodtruck.pos.foodtruck_pos_v1.menuitem.service;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MenuItemFindService {
    private final MenuItemRepository menuItemRepository;

    @Transactional(readOnly = true)
    public MenuItem findMenuItemBy(int menuItemId) {
        return null;
    }
}
