package cn.ewsd.system.interceptor;

import cn.ewsd.base.utils.LoggerUtils;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.SpringContextUtils;
import cn.ewsd.cost.model.SysUserLikeMenu;
import cn.ewsd.cost.service.SysUserLikeMenuService;
import cn.ewsd.system.model.LoggerInfo;
import cn.ewsd.system.model.SysLoggerInfo;
import cn.ewsd.system.service.SysLoggerInfoService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "_send_time";
    //请求日志实体标识
    private static final String LOGGER_ENTITY = "_logger_entity";

    @Resource
    private SysLoggerInfoService sysLoggerInfoService;
    @Autowired
    private SysUserLikeMenuService sysUserLikeMenuService;

    /**
     * 进入SpringMVC的Controller之前开始记录日志实体
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //创建日志实体
        LoggerInfo logger = new LoggerInfo();
        //获取请求sessionId
        String sessionId = request.getRequestedSessionId();
        //请求路径
        String url = request.getRequestURI();
        //获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue);
        //设置客户端ip
        logger.setClientIp(LoggerUtils.getCliectIp(request));
        //设置请求方法
        logger.setMethod(request.getMethod());
        //设置请求类型（json|普通请求）
        logger.setType(LoggerUtils.getRequestType(request));
        //设置请求参数内容json字符串
        logger.setParamData(paramData);
        //设置请求地址
        logger.setUri(url);
        //设置sessionId
        logger.setSessionId(sessionId);
        //设置请求开始时间
        request.setAttribute(LOGGER_SEND_TIME, System.currentTimeMillis());
        //设置请求实体到request内，方面afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY, logger);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        //获取请求错误码
        int status = response.getStatus();
        //当前时间
        long currentTime = System.currentTimeMillis();
        //请求开始时间
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        //获取本次请求日志实体
        LoggerInfo loggerInfo = (LoggerInfo) request.getAttribute(LOGGER_ENTITY);
        if(loggerInfo.getUri().endsWith("/index")||loggerInfo.getUri().endsWith("login")){
            UserInfo userInfo = SpringContextUtils.getBean(UserInfo.class);
            loggerInfo.setUuid(BaseUtils.UUIDGenerator());
            loggerInfo.setCreatorId(userInfo.getUserNameId());
            loggerInfo.setCreator(userInfo.getUserName());
            loggerInfo.setCreateTime(new Date());

            //设置请求时间差
            loggerInfo.setTimeConsuming(Integer.valueOf((currentTime - time) + ""));
            //设置返回时间
            loggerInfo.setReturnTime(currentTime + "");
            //设置返回错误码
            loggerInfo.setHttpStatusCode(status + "");
            //设置返回值
            loggerInfo.setReturnData(JSON.toJSONString(request.getAttribute(LoggerUtils.LOGGER_RETURN),
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteMapNullValue));
            //执行将日志写入数据库
            //LoggerRepository loggerRepository = getDAO(LoggerRepository.class, request);
            //loggerRepository.save(loggerInfo);
            SysLoggerInfo sysLoggerInfo = new SysLoggerInfo();
            sysLoggerInfo.setUuid(loggerInfo.getUuid());
            sysLoggerInfo.setCreateTime(loggerInfo.getCreateTime());
            sysLoggerInfo.setCreator(loggerInfo.getCreator());
            sysLoggerInfo.setCreateTime(loggerInfo.getCreateTime());
            sysLoggerInfo.setAliClientIp(loggerInfo.getClientIp());
            sysLoggerInfo.setAliHttpStatusCode(Integer.valueOf(loggerInfo.getHttpStatusCode()));
            sysLoggerInfo.setAliMethod(loggerInfo.getMethod());
            sysLoggerInfo.setAliParamData(loggerInfo.getParamData());
            sysLoggerInfo.setAliTime(loggerInfo.getTime());
            sysLoggerInfo.setAliReturnTime(loggerInfo.getReturnTime());
            sysLoggerInfo.setAliSessionId(loggerInfo.getSessionId());
            sysLoggerInfo.setAliUri(loggerInfo.getUri());
            sysLoggerInfo.setAliType(loggerInfo.getType());
            sysLoggerInfo.setAliTimeConsuming(loggerInfo.getTimeConsuming());
            Integer integer = sysLoggerInfoService.insertSelective(sysLoggerInfo);

            if(!loggerInfo.getUri().equals("/system/portal/index")&&!loggerInfo.getUri().equals("/system/index/index")&&!loggerInfo.getUri().equals("/system/menu/index")&&!loggerInfo.getUri().endsWith("login")) {
                //用户喜欢
                SysUserLikeMenu checkLike = sysUserLikeMenuService.queryByUserIdAndUri(LoginInfo.getUuid(), loggerInfo.getUri());
                if (checkLike != null) {
                    sysUserLikeMenuService.executeAddClicksNumber(LoginInfo.getUuid(), loggerInfo.getUri());
                } else {
                    SysUserLikeMenu sysUserLikeMenu = new SysUserLikeMenu();
                    sysUserLikeMenu.setUserId(userInfo.getUuid());
                    sysUserLikeMenu.setUserNameId(userInfo.getUserNameId());
                    sysUserLikeMenu.setMenuUri(loggerInfo.getUri());
                    sysUserLikeMenu.setClicksNumber(1);
                    sysUserLikeMenu.setUuid(BaseUtils.UUIDGenerator());
                    sysUserLikeMenuService.insertSelective(sysUserLikeMenu);
                }
            }
        }
    }

    /**
     * 根据传入的类型获取spring管理的对应dao
     *
     * @param clazz   类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getDAO(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
