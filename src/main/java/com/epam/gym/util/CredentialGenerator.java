package com.epam.gym.util;

import java.security.SecureRandom;

/**
 * Utility to generate random passwords / enforce unique usernames.
 */
public final class CredentialGenerator {
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789";
    private static final SecureRandom random = new SecureRandom();

    private CredentialGenerator() { }

    public static String randomPassword() {
        var sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(idx));
        }
        return sb.toString();
    }
}