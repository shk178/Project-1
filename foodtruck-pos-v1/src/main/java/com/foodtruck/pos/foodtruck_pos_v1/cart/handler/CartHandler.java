package com.foodtruck.pos.foodtruck_pos_v1.cart.handler;

import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.Cart;
import com.foodtruck.pos.foodtruck_pos_v1.cart.domain.CartItem;
import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartItemResult;
import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartResult;
import com.foodtruck.pos.foodtruck_pos_v1.cart.service.CartClearService;
import com.foodtruck.pos.foodtruck_pos_v1.cart.service.CartFindService;
import com.foodtruck.pos.foodtruck_pos_v1.cart.service.CartItemAddService;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.service.MenuItemFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CartHandler {
    private final CartItemAddService cartItemAddService;
    private final MenuItemFindService menuItemFindService;
    private final CartClearService cartClearService;
    private final CartFindService cartFindService;

    public void addCartItemToCart(int cartId, int menuItemId, int quantity) {
        /**
         * Cart에 음식 추가
         * 1. menuItemId에 해당하는 메뉴아이템 정보 조회
         * 2. MenuItem을 CartItem으로 변환
         * 3. Cart에 CartItem 추가
         */
        MenuItem menuItem = menuItemFindService.findMenuItemBy(menuItemId);
        CartItem cartItem = new CartItem(menuItem, quantity);
        cartItemAddService.addCartItem(cartId, cartItem);
    }

    public CartResult getCartBy(int cartId) {
        Cart foundCart = cartFindService.findCartBy(cartId);
        List<CartItemResult> cartItemResults = CartItemResult.from(foundCart.getCartItemList());
        return new CartResult(cartItemResults,
                foundCart.calculatePaymentDueAmount());
    }

    public void clearCart(int cartId) {
        cartClearService.clearCartBy(cartId);
    }
}
