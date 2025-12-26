package com.foodtruck.pos.foodtruck_pos_v2.cart;

import com.foodtruck.pos.foodtruck_pos_v2.menu.MenuItem;

public record CartItem(
        MenuItem menuItem,
        int quantity
) {}
