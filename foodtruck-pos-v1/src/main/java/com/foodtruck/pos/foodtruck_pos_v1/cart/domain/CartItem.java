package com.foodtruck.pos.foodtruck_pos_v1.cart.domain;

import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;

import java.util.Objects;

public record CartItem(MenuItem menuItem, int quantity) {
    /* 자동 생성
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CartItem cartItem)) return false;
        return Objects.equals(menuItem, cartItem.menuItem);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuItem);
    }
     */
    @Override
    public boolean equals(Object o) {
        // 중복 아이템 방지용: 성능 최적화 (동일 객체)
        if (this == o) {
            return true;
        }
        // o가 CartItem이 맞을 경우에만 두 객체를 비교
        if (!(o instanceof  CartItem newItem)) {
            return false;
        }
        // menuItemId 같은지
        return Objects.equals(this.menuItem().getMenuItemId(), newItem.menuItem().getMenuItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.menuItem().getMenuItemId());
    }

    public int totalPrice() {
        return this.menuItem.getPrice() * this.quantity;
    }
}
