package com.foodtruck.pos.foodtruck_pos_v1.menuitem.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {
    private final JdbcClient jdbcClient;

    @Override
    public MenuItem findById(int menuItemId) {
        return null;
    }
}
