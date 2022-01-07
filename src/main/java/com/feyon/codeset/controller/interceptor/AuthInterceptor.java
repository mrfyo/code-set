package com.feyon.codeset.controller.interceptor;

import com.feyon.codeset.constants.SessionKey;
import com.feyon.codeset.dto.LoginUser;
import com.feyon.codeset.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if(session == null) {
            return false;
        }

        Object attr = session.getAttribute(SessionKey.LOGIN_USER);
        if(!(attr instanceof LoginUser)) {
            return false;
        }
        LoginUser user = (LoginUser) attr;
        UserContext.setUser(user);

        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
