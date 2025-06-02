package com.epam.gym.util;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

public final class CredentialGenerator {
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random rnd = new SecureRandom();

    public static String randomPassword() {
        var sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(ALPHANUM.charAt(rnd.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    public static String uniqueUsername(String firstName, String lastName, Map<String, ?> existing) {
        String base = firstName + "." + lastName;
        if (!existing.containsKey(base)) {
            return base;
        }
        int suffix = 1;
        String candidate;
        do {
            candidate = base + suffix++;
        } while (existing.containsKey(candidate));
        return candidate;
    }
}