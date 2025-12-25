package com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.screen;

import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartItemResult;
import com.foodtruck.pos.foodtruck_pos_v1.cart.dto.CartResult;
import com.foodtruck.pos.foodtruck_pos_v1.menu.domain.Menu;
import com.foodtruck.pos.foodtruck_pos_v1.menu.handler.MenuHandler;
import com.foodtruck.pos.foodtruck_pos_v1.menuitem.domain.MenuItem;
import com.foodtruck.pos.foodtruck_pos_v1.order.domain.OrderItem;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderFood;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderRequest;
import com.foodtruck.pos.foodtruck_pos_v1.order.dto.OrderResult;
import com.foodtruck.pos.foodtruck_pos_v1.order.handler.OrderHandler;
import com.foodtruck.pos.foodtruck_pos_v1.ui.Screen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.common.exception.BackToPreviousScreenException;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.UserScreenContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.screen.UserCartScreen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.content.UserMenuBody;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.content.UserMenuBodyContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.content.UserMenuContent;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.validator.MenuValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.iohandler.ConsoleInputHandler.inputValue;
import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer.ConsolePrinter.printf;
import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer.ConsolePrinter.println;

@RequiredArgsConstructor
@Component
public class UserMenuScreen extends Screen {
    private UserMenuContent userMenuContent = (UserMenuContent) UserScreenContent.USER_MENU.getContent();
    private final UserCartScreen userCartScreen;
    private final MenuHandler menuHandler;
    private int menuItemCount;
    private final OrderHandler orderHandler;

    @PostConstruct
    public void init() {
        /**
         * 1. 메뉴 타입에 해당되는 메뉴 정보 조회 (메뉴 아이템이 포함되어 있음)
         * 2. UserMenuContent의 bodyContent 업데이트
         */
        Menu menu = menuHandler.getMenuBy(Menu.MenuType.DEFAULT);
        menuItemCount = menu.getMenuItems().size();
        String updatedBodyContent = getUpdatedBodyContent(menu.getMenuItems());
        if (!updatedBodyContent.isEmpty()) {
            UserMenuBody userMenuBody = new UserMenuBody(userMenuContent.body().bodyTitle(), new UserMenuBodyContent(updatedBodyContent));
            userMenuContent = new UserMenuContent(userMenuContent.header(), userMenuBody);
        }
    }
    @Override
    public void render() {
        println(userMenuContent.header().breadcrumb());
        println(userMenuContent.header().content());
        println();
        println(userMenuContent.body().bodyTitle().title());
        println(userMenuContent.body().bodyContent().content());
        println();
    }

    public boolean processOrder() {
        /**
         * TODO
         * 1. 메뉴 아이템 선택 로직
         * - 메뉴 아이템 번호 입력
         * - 메뉴 아이템 수량 입력
         * 2. 카트 아이템 추가 로직
         * - 카트에 메뉴 아이템 추가
         * - 카트에 추가된 메뉴 아이템 출력
         * 3. 주문 옵션 선택 로직
         * - 주문 옵션 번호 입력
         * - 선택한 주문 옵션 번호에 따른 처리
         */
        Pair<Integer, Integer> selectedMenuItem = selectMenuItem();
        userCartScreen.addCartItem(selectedMenuItem.getFirst(), selectedMenuItem.getSecond());
        userCartScreen.render();
        String selectedOrderOptionNo = selectOrderOptions();
        return processEachOption(selectedOrderOptionNo);
    }

    private Pair<Integer, Integer> selectMenuItem() {
        int menuItemNo = processInputMenuItemNo();
        int amount = processInputMenuItemAmount();
        return Pair.of(menuItemNo, amount);
    }

    private int processInputMenuItemNo() {
        while (true) {
            try {
                println("[메뉴 선택] 주문하실 메뉴 번호를 입력하세요. ex) 1");
                String menuItemNo = inputValue();
                MenuValidator.validateMenuItemNo(menuItemNo, menuItemCount);
                return Integer.parseInt(menuItemNo);
            } catch (BackToPreviousScreenException ex) {

            } catch (Exception ex) {
                // 메뉴 아이템 번호가 유효하지 않아 throw된 예외 처리
                println(ex.getMessage());
                println();
            }
        }
    }

    private int processInputMenuItemAmount() {
        while (true) {
            try {
                println("[수량 선택] 주문 수량을 입력하세요 ex 1");
                String amount = inputValue();
                MenuValidator.validateMenuItemAmount(amount);
                return Integer.parseInt(amount);
            } catch (BackToPreviousScreenException ex) {
                throw new BackToPreviousScreenException();
            } catch (Exception ex) {
                // 메뉴 아이템 수량이 유효하지 않은 때 처리
                println(ex.getMessage());
                println();
            }
        }
    }

    private String selectOrderOptions() {
        println("음식을 계속 선택하시려면 [1], 주문하시려면 [2], 주문 취소하시려면 [0]");
        return null;
    }

    private boolean processEachOption(String selectedOptionNo) {
        boolean completion = false;
        switch (selectedOptionNo) {
            case "1" -> {
                completion = false; // 음식 선택을 다시 하도록 해준다
            }
            case "2" -> {
                performOrder();
                completion = true; // 주문 처리
            }
            case "0" -> {
                abortOrderProcess();
                completion = true; // 주문 취소 처리
            }
            default -> throw new IllegalStateException("!! 유효한 버튼 번호가 아닙니다: " + selectedOptionNo);
        }
        return completion;
    }

    private void performOrder() {
        /**
         * 주문 처리 과정
         * 1. 주문 수행
         * 2. 카트 클리어
         * 3. 주문 처리 결과 출력
         */
        OrderResult orderResult = orderWithCartItem();
        if (Objects.nonNull(orderResult)) {
            userCartScreen.clearCart();
            println("주문 완료");
            printOrderDetails(orderResult);
        }
    }

    private void abortOrderProcess() {
        /**
         * 카트 클리어
         */
        userCartScreen.clearCart();
        println("# 주문 취소");
        println();
    }

    private String getUpdatedBodyContent(List<MenuItem> menuItems) {
        AtomicInteger orderingNo = new AtomicInteger();
        return menuItems.stream()
                .map(menuItem -> String.format(
                        "%d. %s(%d 원)",
                        orderingNo.getAndIncrement()+1,
                        menuItem.getMenuItemName(),
                        menuItem.getPrice()
                ))
                .collect(Collectors.joining("\n"));
    }

    private OrderResult orderWithCartItem() {
        /**
         * 1. 카드 조회
         * 2. CartItem을 OrderFood로 변환
         * 3. OrderRequest 객체 생성
         * 4. OrderHandler에 주문 요청
         */
        try {
            CartResult cartResult = userCartScreen.getCart();
            List<OrderFood> orderFoods = convertCartItemsToOrderFoods(cartResult.cartItemResults());
            OrderRequest orderRequest = new OrderRequest(orderFoods);
            return orderHandler.requestOrder(orderRequest);
        } catch (Exception ex) {
            /**
             * 1. 카트 클리어
             * 2. 결제 요청 실패 등 주문 예외 rethrow
             */
            userCartScreen.clearCart();
            throw new OrderFailedException();
        }
        return null;
    }

    private List<OrderFood> convertCartItemsToOrderFoods(List<CartItemResult> cartItemResults) {
        return cartItemResults.stream()
                .map(cartItemResult ->
                        new OrderFood(cartItemResult.menuItemId(), cartItemResult.quantity()))
                .toList();
    }

    private void printOrderDetails(OrderResult orderResult) {
        String orderItemContent = renderOrderItemContent(orderResult.orderItems());
        println("***");
        println("주문 내역");
        printf("주문 번호: %s\n", orderResult.orderNo().number());
        printf("주문 일시: %s\n", orderResult.orderDate().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
        ));
        println(orderItemContent);
        printf("결제 금액: %d원\n", orderResult.totalAmount().value());
        printf("주문 대기 번호: %s\n", orderResult.waitingNo());
        println("***");
    }

    private String renderOrderItemContent(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        String.format("%15s | %2d개 | %6d원",
                                orderItem.getOrderItemName(),
                                orderItem.getQuantity(),
                                orderItem.getAmount().value()
                        )
                )
                .collect(Collectors.joining("\n"));
    }
}
