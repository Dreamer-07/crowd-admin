package pers.prover07.crowd.config;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
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

    @ExceptionHandler(Exception.class)
    public ModelAndView resolverException(Exception exception,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
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
            response.getWriter().write(jsonStr);
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("error/index");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
