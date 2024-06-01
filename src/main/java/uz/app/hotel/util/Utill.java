package uz.app.hotel.util;

import java.util.Scanner;

public interface Utill {
    Scanner scanner = new Scanner(System.in);
    Scanner strScanner = new Scanner(System.in);

    static int getInt(String msg) {

        try {
            System.out.println(msg);
            return scanner.nextInt();

        } catch (Exception e) {
            scanner.nextLine();
            return getInt(msg);
        }
    }

    static String getText(String msg) {
        System.out.println(msg);
        return strScanner.nextLine();
    }

}
