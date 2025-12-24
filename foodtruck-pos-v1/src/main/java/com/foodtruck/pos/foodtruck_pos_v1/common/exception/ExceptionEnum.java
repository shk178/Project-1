package com.foodtruck.pos.foodtruck_pos_v1.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    INVALID_MENU_ITEM_NO("!! 유효한 메뉴 아이템 번호가 아닙니다"),
    INVALID_ORDER_AMOUNT("!! 유효한 주문 수량이 아닙니다"),
    INVALID_END_ORDER_NO("!! 유효한 주문 번호 끝자리가 아닙니다"),
    INVALID_ORDER_STATE("!! 유효한 주문 상태가 아닙니다"),
    BACK_TO_MAIN_SCREEN("!! 이전 화면으로 돌아갑니다"),
    NOT_FOUND_MENU("!! Not found Menu"),
    NOT_FOUND_CART_ITEM("!! Not found Cart Item"),
    NOT_FOUND_MENU_ITEM("!! Not found Menu Item"),
    NOT_FOUND_PAYMENT("!! Not found Payment"),
    NOT_FOUND_ORDER("!! Not found Order"),
    CAN_NOT_CHANGE_TO_SAME_ORDER_STATE("!! 동일한 주문 처리 상태로 변경할 수 없습니다"),
    INVALID_LOGIN_ID("!! 유효한 로그인 아이디가 아닙니다"),
    INVALID_PASSWORD("!! 유효한 패스워드가 아닙니다"),
    UNAUTHORIZED("!! 로그인 아이디 또는 패스워드가 올바르지 않습니다"),
    PAYMENT_AUTHENTICATION_FAILED("!! 결제 인증에 실패했습니다. 다시 주문해 주세요");
    private final String message;
    ExceptionEnum(String message) {
        this.message = message;
    }
}
