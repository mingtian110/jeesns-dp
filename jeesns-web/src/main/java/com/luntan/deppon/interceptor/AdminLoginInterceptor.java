package com.luntan.deppon.interceptor;

import com.luntan.deppon.common.utils.MemberUtil;
import com.luntan.deppon.core.utils.JeesnsConfig;
import com.luntan.deppon.core.utils.SpringContextHolder;
import com.luntan.deppon.model.member.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cubc-luntan 16/11/25.
 */
public class AdminLoginInterceptor implements JeesnsInterceptor {

    @Override
    public boolean interceptor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        Member loginAdmin = MemberUtil.getLoginMember(httpServletRequest);
        if (loginAdmin == null || loginAdmin.getIsAdmin() == 0) {
            JeesnsConfig jeesnsConfig = SpringContextHolder.getBean("jeesnsConfig");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + jeesnsConfig.getManagePath() + "/login");
            return false;
        }
        httpServletRequest.setAttribute("loginUser", loginAdmin);
        return true;
    }
}
