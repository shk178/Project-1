package com.foodtruck.pos.foodtruck_pos_v1.order.dto;

import java.util.List;

public record OrderRequest(List<OrderFood> orderFoods) {
}
