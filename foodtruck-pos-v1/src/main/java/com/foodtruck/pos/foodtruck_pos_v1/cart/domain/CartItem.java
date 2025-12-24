package com.foodtruck.pos.foodtruck_pos_v1.cart.domain;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;

public record CartItem(MenuItem menuItem, int quantity) {
}
