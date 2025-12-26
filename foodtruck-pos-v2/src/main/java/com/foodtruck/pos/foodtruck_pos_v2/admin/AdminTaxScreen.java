package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminTaxScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminMainScreen adminMainScreen;
    private final AdminManageTaxRateScreen adminManageTaxRateScreen;
    private final AdminGenerateTaxReportScreen adminGenerateTaxReportScreen;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 세무 관리 ]");
        cpl("1. 세율 추가/수정/삭제");
        cpl("2. 세무 보고서 생성");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> adminManageTaxRateScreen;
            case "2" -> adminGenerateTaxReportScreen;
            case "3" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminMainScreen;
    }
}
