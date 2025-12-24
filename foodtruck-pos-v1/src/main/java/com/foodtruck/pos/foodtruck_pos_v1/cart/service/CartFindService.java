package com.foodtruck.pos.foodtruck_pos_v1.cart.service;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.Cart;
import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartFindService {
    private final CartRepository cartRepository;

    public Cart findCartBy(int cartId) {
        return null;
    }
}
