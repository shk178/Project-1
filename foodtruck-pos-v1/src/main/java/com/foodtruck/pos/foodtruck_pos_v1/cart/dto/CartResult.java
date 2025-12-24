package com.foodtruck.pos.foodtruck_pos_v1.cart.dto;

import java.util.List;

public record CartResult(List<CartItemResult> cartItemResults, int paymentDueAmount) {
}
