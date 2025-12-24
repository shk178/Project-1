package com.foodtruck.pos.foodtruck_pos_v1.cart.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.Cart;
import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SingleCartRepository implements CartRepository {
    @Override
    public void save(Cart cart) {

    }

    @Override
    public Cart findById(int cartId) {
        return null;
    }

    @Override
    public void clear(int cartId) {

    }
}
