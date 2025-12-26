package com.foodtruck.pos.foodtruck_pos_v2.common;

import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsoleInputHelper.ciq;
import static com.foodtruck.pos.foodtruck_pos_v2.common.ConsolePrintHelper.cpl;

public abstract class ScreenTemplate implements Screen {
    @Override
    public final void render() {
        cpl("=".repeat(16));
        printContent();
    }
    @Override
    public final Screen handleInput() {
        try {
            String input = ciq();
            return processInput(input);
        } catch (BackScreenException e) {
            return getPreviousScreen();
        }
    }
    protected abstract void printContent();
    protected abstract Screen processInput(String input);
    protected abstract Screen getPreviousScreen();
}
