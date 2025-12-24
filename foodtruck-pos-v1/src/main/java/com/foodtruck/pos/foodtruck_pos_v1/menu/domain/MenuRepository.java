package com.foodtruck.pos.foodtruck_pos_v1.menu.domain;

public interface MenuRepository {
    Menu findByMenuType(Menu.MenuType menuType);
}
