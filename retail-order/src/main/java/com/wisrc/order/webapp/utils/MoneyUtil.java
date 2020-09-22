package com.wisrc.order.webapp.utils;

public class MoneyUtil {

    public static double changeMonry(String type, double money) {
        if (type.equals("CNY")) {
            return money;
        }
        if (type.equals("EUR")) {
            return 7.5409 * money;
        }
        if (type.equals("GBP")) {
            return 8.5525 * money;
        }
        if (type.equals("HKD")) {
            return 0.8149 * money;
        }
        if (type.equals("JPY")) {
            return 0.05797 * money;
        }
        if (type.equals("USD")) {
            return 6.3944 * money;
        } else {
            return money;
        }
    }
}
