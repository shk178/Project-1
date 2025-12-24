package com.foodtruck.pos.foodtruck_pos_v1.menu.domain;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Menu {
    private int menuId;
    private String menuName;
    private MenuType menuType;
    private MenuState menuState;
    private List<MenuItem> menuItems;

    @Getter
    public enum MenuType {
        DEFAULT("기본"),
        SPECIAL("스페셜");
        private final String type;
        MenuType(String type) {
            this.type = type;
        }
    }

    @Getter
    public enum MenuState {
        AVAILABLE("사용 가능"),
        NOT_AVAILABLE("사용 불가능");
        private final String state;
        MenuState(String state) {
            this.state = state;
        }
    }
}
