package com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuItem {
    private int menuItemId;
    private String menuItemName;
    private int price;
    private MenuItemState menuItemState;

    @Getter
    public enum MenuItemState {
        AVAILABLE("주문 가능"),
        RUN_OUT_OF_INGREDIENTS("재료 소진"),
        NOT_AVAILABLE("주문 불가능");
        private final String state;
        MenuItemState(String state) {
            this.state = state;
        }
    }
}
