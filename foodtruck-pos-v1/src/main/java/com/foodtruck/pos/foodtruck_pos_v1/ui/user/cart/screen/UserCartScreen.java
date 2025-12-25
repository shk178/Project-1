package com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.screen;

import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartItemResult;
import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartResult;
import com.foodtruck.pos.foodtruck_pos_v1.cart.handler.CartHandler;
import com.foodtruck.pos.foodtruck_pos_v1.ui.Screen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.UserScreenContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.content.UserCartContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer.ConsolePrinter.println;

@RequiredArgsConstructor
@Component
public class UserCartScreen extends Screen {
    private static final int SINGLE_CART_ID = 1;
    private final UserCartContent userCartContent = (UserCartContent) UserScreenContent.USER_CART.getContent();
    private final CartHandler cartHandler;

    @Override
    public void render() {
        CartResult cartResult = getCart();
        int paymentDueAmound = cartResult.paymentDueAmount();
        println(userCartContent.header().title());
        println(renderBodyContentWithCartItems(cartResult.cartItemResults()));
        println(renderFooterContentWithPrice(paymentDueAmound));
        println();
    }

    public void addCartItem(int menuItemId, int amount) {
        // todo: cartHandler 호출
        cartHandler.addCartItemToCart(SINGLE_CART_ID, menuItemId, amount);
    }

    public CartResult getCart() {
        //
        return cartHandler.getCartBy(SINGLE_CART_ID);
    }

    public void clearCart() {
        cartHandler.clearCart(SINGLE_CART_ID);
    }

    private String renderBodyContentWithCartItems(List<CartItemResult> cartItemResults) {
        return !cartItemResults.isEmpty() ? formattedBodyContentFromCartItems(cartItemResults) : userCartContent.body().content();
    }
    private String formattedBodyContentFromCartItems(List<CartItemResult> cartItemResults) {
        return cartItemResults.stream()
                .map(cartItemResult ->
                        String.format(
                                "%15s | %2d개 | %6d원",
                                cartItemResult.menuItemName(),
                                cartItemResult.quantity(),
                                cartItemResult.totalPrice()))
                .collect(Collectors.joining("\n"));
    }
    private String renderFooterContentWithPrice(int paymentDueAmount) {
        return userCartContent
                .footer()
                .content()
                .replace("${price}",
                        Integer.toString(paymentDueAmount));
    }
}
