package com.luntan.deppon.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cubc-luntan 2016/11/25.
 */
public interface JeesnsInterceptor {
    boolean interceptor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception;
}
