package pers.prover07.crowd.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pers.prover.crowd.constant.CrowdAttrNameConstant;
import pers.prover.crowd.constant.HttpRespMsgConstant;
import pers.prover.crowd.exception.AccessForbiddenException;
import pers.prover07.crowd.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author by Prover07
 * @classname LoginInterceptor
 * @description TODO
 * @date 2022/2/12 11:22
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取 Session 对象
        HttpSession session = request.getSession();
        // 从 session 对象中获取 Admin 登录信息
        Admin admin = (Admin) session.getAttribute(CrowdAttrNameConstant.LOGIN_ADMIN);
        // 如果无法获取到就抛出异常
        if (admin == null) {
            throw new AccessForbiddenException(HttpRespMsgConstant.ACCESS_FORBIDDEN);
        }
        return true;
    }
}
