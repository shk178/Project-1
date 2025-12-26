package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import com.foodtruck.pos.foodtruck_pos_v2.tax.TaxHandler;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminManageTaxRateScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminTaxScreen adminTaxScreen;
    private final TaxHandler taxHandler;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 세율 추가/수정/삭제 ]");
        readTaxRates();
        cpl("1. 세율 추가");
        cpl("2. 세율 수정");
        cpl("3. 세율 삭제");
        cpl("4. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> createTaxRate();
            case "2" -> updateTaxRate();
            case "3" -> deleteTaxRate();
            case "4" -> throw new ExitApplicationException();
            default -> {
                cpl("잘못된 입력입니다.");
                yield this;
            }
        };
    }
    @Override
    protected Screen getPreviousScreen() {
        return adminTaxScreen;
    }
    private void readTaxRates() {
        taxHandler.readTaxRates();
    }
    private Screen createTaxRate() {
        taxHandler.createTaxRate();
        cpl("세율이 추가됐습니다.");
        return this;
    }
    private Screen updateTaxRate() {
        taxHandler.updateTaxRate();
        cpl("세율이 수정됐습니다.");
        return this;
    }
    private Screen deleteTaxRate() {
        taxHandler.deleteTaxRate();
        cpl("세율이 삭제됐습니다.");
        return this;
    }
}
