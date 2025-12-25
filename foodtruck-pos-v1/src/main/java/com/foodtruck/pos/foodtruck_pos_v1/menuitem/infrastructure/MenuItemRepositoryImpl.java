package com.foodtruck.pos.foodtruck_pos_v1.menuitem.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItemRepository;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.exception.NotFoundMenuItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {
    private final JdbcClient jdbcClient;

    @Override
    public MenuItem findById(int menuItemId) {
        return jdbcClient
                .sql("SELECT FROM menu_item WHERE menu_item_id=:menuItemId")
                .param("menuItemId", menuItemId)
                .query(MenuItem.class)
                .optional()
                .orElseThrow(NotFoundMenuItemException::new);
    }
}
