package com.foodtruck.pos.foodtruck_pos_v1.menu.service;

import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.Menu;
import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MenuFindService {
    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public Menu findMenuBy(Menu.MenuType menuType) {
        return menuRepository.findByMenuType(menuType);
    }
}
