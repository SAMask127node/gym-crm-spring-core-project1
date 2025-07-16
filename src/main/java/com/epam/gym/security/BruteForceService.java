// src/main/java/com/epam/gym/security/BruteForceService.java
package com.epam.gym.security;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BruteForceService {
    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();
    private final Map<String, Long> lockTime = new ConcurrentHashMap<>();

    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MS = 5 * 60 * 1000L; // 5 minutes

    public void loginFailed(String username) {
        int cnt = attempts.getOrDefault(username, 0) + 1;
        attempts.put(username, cnt);
        if (cnt >= MAX_ATTEMPTS) {
            lockTime.put(username, System.currentTimeMillis());
        }
    }

    public boolean isBlocked(String username) {
        Long lockedAt = lockTime.get(username);
        if (lockedAt == null) {
            return false;
        }
        if (System.currentTimeMillis() - lockedAt > LOCK_DURATION_MS) {
            // release lock
            lockTime.remove(username);
            attempts.remove(username);
            return false;
        }
        return true;
    }

    public void loginSucceeded(String username) {
        attempts.remove(username);
        lockTime.remove(username);
    }
}
