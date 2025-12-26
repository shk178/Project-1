package com.foodtruck.pos.foodtruck_pos_v2.authentication;

public class AuthenticationHandler {
    public boolean authenticate(String loginId, String password) {
        return true;
    }

    public void logout() {
        
    }

    public boolean login(String loginId, String password) {
        return false;
    }

    public String getLoginId() {
        return null;
    }
}
