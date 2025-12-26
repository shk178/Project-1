package com.foodtruck.pos.foodtruck_pos_v2.menu;

public record MenuItem(
        Integer menuItemId,
        String menuItemName,
        Integer price,
        String menuItemState,
        Long menuId
) {}
