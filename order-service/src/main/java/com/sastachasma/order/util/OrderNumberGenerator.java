package com.sastachasma.order.util;

import java.security.SecureRandom;

public class OrderNumberGenerator {
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ORDER_NUMBER_LENGTH = 8; // Length of the random part
    
    public static String generateOrderNumber() {
        StringBuilder sb = new StringBuilder("ORD-");
        for (int i = 0; i < ORDER_NUMBER_LENGTH; i++) {
            sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }
}
