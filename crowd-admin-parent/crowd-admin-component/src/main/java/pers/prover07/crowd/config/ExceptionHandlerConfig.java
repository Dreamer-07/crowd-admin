package pers.prover07.crowd.config;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import pers.prover.crowd.exception.AccessForbiddenException;
import pers.prover.crowd.exception.LoginFailedException;
import pers.prover.crowd.util.CrowdUtil;
import pers.prover.crowd.util.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by Prover07
 * @classname ExceptionHandlerConfig
 * @description TODO
 * @date 2022/2/11 11:07
 */
@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler({LoginFailedException.class, AccessForbiddenException.class})
    public ModelAndView resolverLoginFailedException(Exception ex,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return commonResolver("admin/login", ex, request, response);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView resolverException(Exception exception,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        return commonResolver("system/error", exception, request, response);
    }

    /**
     * 异常解析通用处理逻辑
     * @param viewName
     * @param exception
     * @param request
     * @param response
     * @return
     */
    public ModelAndView commonResolver(
            String viewName, Exception exception,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 获取异常信息
        String message = exception.getMessage();
        // 判断请求类型
        boolean ajaxRequest = CrowdUtil.judgeRequestType(request);
        if (ajaxRequest) {
            // 构建 JSON 响应数据
            ResultEntity resultEntity = ResultEntity.fail(message);
            // 使用 GSON 进行转换
            Gson gson = new Gson();
            String jsonStr = gson.toJson(resultEntity);
            // 返回 json 字符串数据
            try {
                response.getWriter().write(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
