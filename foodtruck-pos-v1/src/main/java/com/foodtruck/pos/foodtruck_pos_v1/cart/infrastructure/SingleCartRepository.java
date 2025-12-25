package com.foodtruck.pos.foodtruck_pos_v1.cart.infrastructure;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.Cart;
import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartRepository;
import com.foodtruck.pos.foodtruck_pos_v1.cart.exception.NotFoundCartException;
import org.springframework.stereotype.Repository;

@Repository
public class SingleCartRepository implements CartRepository {
    private final Cart singleCart = SingletonCartFactory.getInstance();

    @Override
    public void save(Cart cart) {
        // 싱글톤이어서 저장 안 해도 된다.
    }

    @Override
    public Cart findById(int cartId) {
        if (singleCart.getCartId() != cartId) {
            throw new NotFoundCartException();
        }
        return singleCart;
    }

    @Override
    public void clear(int cartId) {
        if (singleCart.getCartId() != cartId) {
            throw new NotFoundCartException();
        }
        singleCart.clear();
    }
}
