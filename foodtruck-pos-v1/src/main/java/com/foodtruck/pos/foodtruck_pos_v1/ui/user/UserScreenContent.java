package com.foodtruck.pos.foodtruck_pos_v1.ui.user;

import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.content.UserCartBody;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.content.UserCartContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.content.UserCartFooter;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.content.UserCartHeader;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.main.content.UserMainBody;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.main.content.UserMainContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.main.content.UserMainHeader;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.content.*;
import lombok.Getter;

@Getter
public enum UserScreenContent {
    USER_MAIN(
            "user-main-screen",
            new UserMainContent(
                    new UserMainHeader("집 모양"),
                    new UserMainBody("[주문 시작하기] 엔터 키")
            )
    ),
    USER_MENU(
            "user-menu-screen",
            new UserMenuContent(
                    new UserMenuHeader(),
                    new UserMenuBody(
                            new UserMenuBodyTitle(),
                            new UserMenuBodyContent()
                    )
            )
    ),
    USER_CART(
            "user-cart-screen",
            new UserCartContent(
                    new UserCartHeader(),
                    new UserCartBody("기본 값 -> 동적 변경"),
                    new UserCartFooter("동적 표시")
            )
    );
    private final String screenName;
    private final Object content;
    UserScreenContent(String screenName, Object content) {
        this.screenName = screenName;
        this.content = content;
    }
}
