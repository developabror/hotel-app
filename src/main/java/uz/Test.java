package uz;

import uz.app.hotel.service.impl.AuthServiceImpl;

public class Test {
    public static void main(String[] args) {
        AuthServiceImpl authService = new AuthServiceImpl();
        while (true) {
            try {
                authService.service();
            } catch (Exception e) {}
        }
    }
}
