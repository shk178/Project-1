package com.foodtruck.pos.foodtruck_pos_v1.menu.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.Menu;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuWithItemsRowMapper implements RowMapper<Menu> {
    @Override
    public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<Integer, Menu> menuMap = new HashMap<>();
        Menu menu;
        do {
            int menuId = rs.getInt("menu_id");
            String menuName = rs.getString("menu_name");
            Menu.MenuType menuType = Menu.MenuType.valueOf(rs.getString("menu_type"));
            Menu.MenuState menuState = Menu.MenuState.valueOf(rs.getString("menu_state"));
            menu = menuMap.computeIfAbsent(menuId,
                    id ->
                            Menu.builder()
                                    .menuId(id)
                                    .menuName(menuName)
                                    .menuType(menuType)
                                    .menuState(menuState)
                                    .menuItems(new ArrayList<>())
                                    .build());
            int menuItemId = rs.getInt("menu_item_id");
            if (menuItemId != 0) {
                MenuItem menuItem =
                        MenuItem.builder()
                                .menuItemId(menuItemId)
                                .menuItemName(rs.getString("menu_item_name"))
                                .price(rs.getInt("price"))
                                .menuItemState(
                                        MenuItem.MenuItemState
                                                .valueOf(rs.getString("menu_item_state")))
                                .build();
                menu.getMenuItems().add(menuItem);
            }
        } while (rs.next());
        return menu;
    }
}
