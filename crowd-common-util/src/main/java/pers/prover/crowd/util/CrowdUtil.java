package pers.prover.crowd.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 存放项目一些常用方法的工具类
 * @author by Prover07
 * @classname CrowdUtil
 * @description TODO
 * @date 2022/2/11 10:52
 */
public class CrowdUtil {

    /**
     * 判断请求是否为 Ajax 请求(true - 是; false - 不是)
     * @param request
     * @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWithHeader = request.getHeader("X-Requested-With");
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                ("XMLHttpRequest".equals(xRequestedWithHeader));
    }

}
