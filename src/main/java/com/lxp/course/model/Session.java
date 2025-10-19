package com.lxp.course.model;

/**
 * This class is for test
 * Use this session simulator until actual login session system gets done
 * 
 * Thread-safe implementation using ThreadLocal to allow multiple threads/tests
 * to maintain separate session state.
 */
public class Session {

    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    private static final Session INSTANCE = new Session();

    private Session() {
        // Private constructor for singleton pattern
    }

    /**
     * Get the singleton instance of Session
     */
    public static Session getInstance() {
        return INSTANCE;
    }

    /**
     * Set the user ID for the current thread
     */
    public void setUserId(long userId) {
        userIdThreadLocal.set(userId);
    }

    /**
     * Get the user ID for the current thread
     * Returns 0 if no user ID has been set for this thread
     */
    public static long getUserId() {
        Long userId = userIdThreadLocal.get();
        return userId != null ? userId : 0L;
    }

    /**
     * Clear the user ID for the current thread
     * Useful for cleanup in tests or when user logs out
     */
    public void clearUserId() {
        userIdThreadLocal.remove();
    }

    /**
     * Legacy constructor for backward compatibility
     * Creates a session and sets the user ID for current thread
     */
    public Session(long userId) {
        setUserId(userId);
    }

}
