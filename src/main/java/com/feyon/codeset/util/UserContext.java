package com.feyon.codeset.util;

import com.feyon.codeset.dto.LoginUser;

/**
 * @author Feng Yong
 */
public class UserContext {

    private static final ThreadLocal<LoginUser> loginUser = new ThreadLocal<>();


    public static void setUser(LoginUser user) {
        loginUser.set(user);
    }

    public static LoginUser getUser() {
        return loginUser.get();
    }

    public static void removeUser() {
        loginUser.remove();
    }

    public static Integer getUserId() {
        return loginUser.get().getId();
    }
}
