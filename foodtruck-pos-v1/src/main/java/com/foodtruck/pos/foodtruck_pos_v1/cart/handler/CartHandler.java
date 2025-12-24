package com.foodtruck.pos.foodtruck_pos_v1.cart.handler;

import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartResult;
import com.foodtruck.pos.foodtruck_pos_v1.cart.service.CartClearService;
import com.foodtruck.pos.foodtruck_pos_v1.cart.service.CartFindService;
import com.foodtruck.pos.foodtruck_pos_v1.cart.service.CartItemAddService;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.service.MenuItemFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartHandler {
    private final CartItemAddService cartItemAddService;
    private final MenuItemFindService menuItemFindService;
    private final CartClearService cartClearService;
    private final CartFindService cartFindService;

    public void addCartItemToCart(int cartId, int menuItemId, int amount) {

    }

    public CartResult getCartBy(int cartId) {
        return null;
    }

    public void clearCart(int cartId) {

    }
}
