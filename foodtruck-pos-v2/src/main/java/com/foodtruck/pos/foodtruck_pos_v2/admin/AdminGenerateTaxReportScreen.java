package com.foodtruck.pos.foodtruck_pos_v2.admin;

import com.foodtruck.pos.foodtruck_pos_v2.authentication.AuthenticationHandler;
import com.foodtruck.pos.foodtruck_pos_v2.common.ExitApplicationException;
import com.foodtruck.pos.foodtruck_pos_v2.common.Screen;
import com.foodtruck.pos.foodtruck_pos_v2.common.ScreenTemplate;
import com.foodtruck.pos.foodtruck_pos_v2.tax.TaxHandler;
import lombok.RequiredArgsConstructor;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

@RequiredArgsConstructor
public class AdminGenerateTaxReportScreen extends ScreenTemplate {
    private final AuthenticationHandler authenticationHandler;
    private final AdminTaxScreen adminTaxScreen;
    private final TaxHandler taxHandler;
    @Override
    protected void printContent() {
        cpl("[ 관리자: " + authenticationHandler.getLoginId() + " ]");
        cpl("[ 세무 보고서 생성 ]");
        cpl("1. 매출 집계 보고서");
        cpl("2. 결제 집계 보고서");
        cpl("3. 종료");
        cpl("입력하세요 (뒤로가기: :q)");
    }
    @Override
    protected Screen processInput(String input) {
        return switch (input) {
            case "1" -> generateSalesTaxReport();
            case "2" -> generatePaymentTaxReport();
            case "3" -> throw new ExitApplicationException();
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
    private Screen generateSalesTaxReport() {
        taxHandler.generateSalesTaxReport();
        cpl("매출 집계 보고서가 생성됐습니다.");
        return this;
    }
    private Screen generatePaymentTaxReport() {
        taxHandler.generatePaymentTaxReport();
        cpl("결제 집계 보고서가 생성됐습니다.");
        return this;
    }
}
