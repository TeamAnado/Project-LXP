package com.lxp.course.model;

/**
 * This class is for test
 * Use this session simulator until actual login session system gets done
 */
public class Session {

    private static long userId;

    Session(long userId) {
        Session.userId = userId;
    }

    public static long getUserId() {
        return userId;
    }

}
