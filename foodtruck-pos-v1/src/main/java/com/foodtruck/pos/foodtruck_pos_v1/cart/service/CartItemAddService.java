package com.foodtruck.pos.foodtruck_pos_v1.cart.service;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.Cart;
import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartItem;
import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemAddService {
    private final CartRepository cartRepository;

    public void addCartItem(int cartId, CartItem cartItem) {
        /**
         * 1. 카트 아이디에 해당하는 카트를 조회
         * 2. 카트 객체에 카트 아이템을 추가 (도메인 로직)
         * 3. 카트를 카트 repo에 저장
         */
        Cart foundCart = cartRepository.findById(cartId);
        foundCart.addCartItem(cartItem);
        cartRepository.save(foundCart);
    }
}
