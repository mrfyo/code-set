package com.feyon.codeset.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Feng Yong
 */
public class TimeCastApiInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(TimeCastApiInterceptor.class);

    private final ThreadLocal<Long> startNanoSeconds = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startNanoSeconds.set(System.nanoTime());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long cast = System.nanoTime() - startNanoSeconds.get();
        String url = request.getRequestURI();
        Integer status = response.getStatus();
        String method = request.getMethod();
        if(cast < 1000) {
            log.info(String.format(" %d | %10dns | %s | %s", status, cast, method, url));
        }else if (cast < 1000_000){
            log.info(String.format(" %d | %10dus | %s | %s", status, cast / 1000, method, url));
        }else {
            log.info(String.format(" %d | %10dms | %s | %s", status, cast / 1000_000, method, url));
        }
        startNanoSeconds.remove();;
    }
}
