package com.foodtruck.pos.foodtruck_pos_v1.common.vo;

public record Money(int value) {
    public Money multiply(int multiplier) {
        return new Money(this.value * multiplier);
    }
}
