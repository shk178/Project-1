package com.foodtruck.pos.foodtruck_pos_v1.cart.service;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartClearService {
    private final CartRepository cartRepository;

    public void clearCartBy(int cartId) {
        cartRepository.clear(cartId);
    }
}
