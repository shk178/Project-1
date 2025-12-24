package com.foodtruck.pos.foodtruck_pos_v1.menu.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.Menu;
import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.MenuRepository;
import com.foodtruck.pos.foodtruck_pos_v1.menu.exception.NotFoundMenuException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MenuRepositoryImpl implements MenuRepository {
    private final JdbcClient jdbcClient;

    @Override
    public Menu findByMenuType(Menu.MenuType menuType) {
        String query = """
                SELECT * FROM menu m
                INNER JOIN menu_item mi ON m.MENU_ID = mi.MENU_ID
                WHERE m.MENU_TYPE = :menuType
                """;
        return jdbcClient
                .sql(query)
                .param("menuType", menuType.name())
                .query(new MenuWithItemsRowMapper())
                .optional().orElseThrow(() -> new NotFoundMenuException());
    }
}
