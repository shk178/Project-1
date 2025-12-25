package com.foodtruck.pos.foodtruck_pos_v1.ui.user;

import com.foodtruck.pos.foodtruck_pos_v1.ui.common.exception.BackToPreviousScreenException;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.cart.screen.UserCartScreen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.main.screen.UserMainScreen;
import com.foodtruck.pos.foodtruck_pos_v1.ui.user.menu.screen.UserMenuScreen;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.iohandler.ConsoleInputHandler.inputValue;
import static com.foodtruck.pos.foodtruck_pos_v1.ui.common.helper.printer.ConsolePrinter.println;

@Profile("user")
@RequiredArgsConstructor
@Component
public class UserKiosk implements ApplicationRunner {
    private final UserMainScreen userMainScreen;
    private final UserMenuScreen userMenuScreen;
    private final UserCartScreen userCartScreen;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("# 손님 모드 UI");
        while (true) {
            processMainScreen();
            processMenuScreen();
            return;
        }
    }
    private void processMainScreen() {
        // while문을 벗어나는 조건: 공백일 경우 (공백 보다는 비어 있는 문자열)
        while (true) {
            try {
                /**
                 * TODO: 처리 순서
                 * 1. 메인 화면 컨텐츠 렌더링
                 * 2. 메뉴 화면으로 이동하기 위한 공백 값 입력
                 * 3. 공백 값 유효성 검증
                 *      - 공백이면, while문 종료
                 *      - 공백이 아니면 while문 반복
                 */
                userMainScreen.render();
                String value = inputValue();
                if (value.isEmpty()) {
                    return;
                }
            } catch (Exception ex) {
                // TODO: 예외 메시지 출력 및 while문 반복
                println(ex.getMessage());
            }
        }
    }
    private void processMenuScreen() {
        boolean completion = false;
        while (!completion) {
            try {
                /**
                 * TODO: 처리 순서
                 * 1. 메뉴 화면 컨텐츠 렌더링
                 * 2. 카트 화면 컨텐츠 렌더링
                 * 3. 주문할 메뉴 번호 입력
                 * 4. 주문 수량 입력
                 * 5. 선택한 음식을 카트에 출력
                 * 6. 처리 결과에 따른 while문 종료 여부 결정
                 *      - completion이 true: 종료
                 *      - completion이 false: 반복
                 */
                userMenuScreen.render();
                userCartScreen.render();
                completion = userMenuScreen.processOrder();
            } catch (BackToPreviousScreenException ex) {
                /**
                 * TODO: 이전 화면으로 가야 하는 예외 상황
                 * 1. 카트 클리어
                 * 2. 예외 메시지 출력
                 * 3. while문 종료
                 */
                userCartScreen.clearCart();
                println(ex.getMessage());
                println();
                completion = true;
            } catch (Exception ex) {
                // TODO: 예외 메시지 출력 및 while문 반복
                println(ex.getMessage());
                println();
            }
        }
    }
}
