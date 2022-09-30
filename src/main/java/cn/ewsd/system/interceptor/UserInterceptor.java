/*
package cn.ewsd.system.interceptor;

import cn.ewsd.common.utils.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域请求
        String[] allowDomain = {"http://localhost:8000", "http://oa.ewsd.cn"};
        Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader = request.getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Cookie");
            //response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        String serverName = request.getServerName();
        Integer serverPort = request.getServerPort();
        String hostName = serverPort == 80 ? "http://" + serverName : "http://" + serverName + ":" + serverPort;
        // 请求的路径
        //String contextPath = request.getContextPath();
        //String url = hostName + Constants.CONTEXT_PATH + "/" + request.getServletPath().toString();
        String url = hostName + "/system/login/casLogin";
        HttpSession session = request.getSession();
        String userNameId = (String) session.getAttribute("session_member");
        // 这里可以根据session的用户来判断角色的权限，根据权限来重定向不同的页面，简单起见，这里只是做了一个重定向
        if (userNameId == null || userNameId == "") {
            // 被拦截，重定向到login界面
            //response.sendRedirect("http://sso.ewsd.cn/cas/login?service=" + URLEncoder.encode(url, "UTF-8"));
            String lastAccessUrl;
            String requestUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (StringUtils.isNullOrEmpty(queryString)) {
                lastAccessUrl = requestUrl + "/system/login?portal=system";
            } else {
                lastAccessUrl = requestUrl + "/system/login?" + queryString;
            }
            response.sendRedirect(hostName + "/system/login?referer=" + URLEncoder.encode(lastAccessUrl, "UTF-8"));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
*/
