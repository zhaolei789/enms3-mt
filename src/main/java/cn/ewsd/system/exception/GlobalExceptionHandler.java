package cn.ewsd.system.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice(annotations=RestController.class)
//@ControllerAdvice(basePackages={"com.xxx","com.ooo"})
@ControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler(RuntimeException.class)
    //@ExceptionHandler(ExpiredJwtException.class)
    //@ExceptionHandler(value={RuntimeException.class,MyRuntimeException.class})
    @ExceptionHandler //处理所有异常
    @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public ModelAndView exceptionHandler(Exception ex, HttpServletResponse response) throws Exception {

        //如果该 异常类型是系统 自定义的异常，直接取出异常信息，在错误页面展示。
        CustomException customException = null;
        if (ex instanceof CustomException) {
            customException = (CustomException) ex;
        } else {
            //如果该 异常类型不是系统 自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）。
            //customException = new CustomException("操作失败，未知错误！");
            if(ex.getMessage() != null)
                customException = new CustomException(ex.getMessage());
            else
                customException = new CustomException(ex.toString());
        }

        //错误信息
        String message = customException.getMessage();

        /*  方式一：使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("statusCode", "300");
        attributes.put("title", "操作提示");
        //具体的错误信息
        //attributes.put("message", ex.getMessage());
        //自定义错误信息
        attributes.put("message", message);
        view.setAttributesMap(attributes);

        ModelAndView mv = new ModelAndView();
        mv.setView(view);
        return mv;
    }

}