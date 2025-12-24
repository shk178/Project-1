package com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.screen;

import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartResult;
import com.foodtruck.pos.foodtruck_pos_v1.ui.Screen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.UserScreenContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.content.UserCartContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer.ConsolePrinter.println;

@RequiredArgsConstructor
@Component
public class UserCartScreen extends Screen {
    private final UserCartContent userCartContent = (UserCartContent) UserScreenContent.USER_CART.getContent();

    @Override
    public void render() {
        println(userCartContent.header().title());
        println(userCartContent.body().content());
        println(userCartContent.footer().content());
        println();
    }

    public void addCartItem(int menuItemId, int amount) {
        // todo: cartHandler 호출
    }

    public CartResult getCart() {
        //
        return null;
    }

    public void clearCart() {
    }
}
