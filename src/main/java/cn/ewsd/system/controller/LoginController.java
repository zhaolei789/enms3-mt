package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.bean.Audience;
import cn.ewsd.common.common.Constants;
import cn.ewsd.base.utils.jwt.JwtUtil;
import cn.ewsd.common.utils.MD5Utils;
import cn.ewsd.common.utils.RequestData;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.UserService;
import cn.ewsd.system.model.AuthAccessView;
import cn.ewsd.system.properties.MyProperties;
import cn.ewsd.system.service.AuthAccessViewService;
import cn.ewsd.system.service.ConfigService;
import cn.ewsd.system.service.RedissonService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.*;

import static cn.ewsd.system.controller.SimulateRequest.username;

@Controller
    @RequestMapping("/system")
    public class LoginController extends SystemBaseController {

        @Autowired
        private MyProperties myProperties;

    @Resource
    private UserService userService;

    @Resource
    private ConfigService configService;

    @Resource
    private AuthAccessViewService authAccessViewService;

    @Autowired
    private RedissonService redissonService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) String portal) throws Exception {
        String title = "企业管理信息系统快速开发平台";
        request.setAttribute("title", title);

        Map<String, Map<String, String>> stringMapMap = configService.redisCacheConfig();
        Map<String, String> sysConfig = stringMapMap.get("sysConfig");
        request.setAttribute("sysConfig",sysConfig);
        //获取session
        HttpServletRequest httprequest = (HttpServletRequest)request;
        HttpSession session = httprequest.getSession();
        String code =genCodes(4,1);
        session.setAttribute("validateCode",code);
        request.setAttribute("validateCode",code);
        return "system/login/index_bootstrap";
    }

    @RequestMapping(value = "/login/index2", method = RequestMethod.GET)
    public ModelAndView index2(String redirectURL) {
        ModelAndView view = new ModelAndView();
        // 把拦截前路径存下来，以便登入成功可以直接请求到登录前的页面
        view.addObject("redirectURL", redirectURL);
        view.addObject("ctx", getContextPath());
        view.addObject("config", config);
        view.setViewName("app/System/login/login");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/login/casLogin")
    public void casLogin(String redirectURL, HttpServletResponse response) throws Exception {
        //String username = request.getParameter("username").trim();
        //String passwordEncode = DesUtils.encoderByMd5With32Bit(password);
        String hostName = request.getServerName();
        if (hostName.equals("localhost")) {
            hostName = "http://localhost:8080";
        } else {
            hostName = "http://" + hostName;
        }

        String remoteUser = request.getRemoteUser();

        //  String hql = "FROM User WHERE usernameid = '" + remoteUser + "'";
        Integer numberOfRows = userService.getCountByRemoteUser(remoteUser);

        HttpSession session = request.getSession();
        if (numberOfRows == 1) {
            User user = userService.getDetailByHql(remoteUser);
            if (user != null && StringUtils.isNotBlank(remoteUser)) {
                session.setAttribute(Constants.SESSION_MEMBER, user.getUserNameId());
                session.setAttribute(Constants.UID, user.getUuid());
                session.setAttribute(Constants.USER_NAME_ID, user.getUserNameId());
                session.setAttribute(Constants.USER_NAME, user.getUserName());
                response.sendRedirect(hostName + Constants.CONTEXT_PATH + "/Portal/Index/index");
            } else {
                response.sendRedirect("http://sso.ewsd.cn/cas/login?service=" + URLEncoder.encode(hostName + Constants.CONTEXT_PATH + "/system/login/casLogin", "UTF-8"));
            }
        } else {
            session.setAttribute(Constants.SESSION_MEMBER, null);
            response.sendRedirect("http://sso.ewsd.cn/cas/login?service=" + URLEncoder.encode(hostName + Constants.CONTEXT_PATH + "/system/login/casLogin", "UTF-8"));
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request) throws Exception {
        JSONObject jsonObject = RequestData.getRequestJsonObj(request);
        String userNameId = jsonObject.getString("username");
        String password = jsonObject.getString("password").trim();
        String referer = jsonObject.get("referer") == null ? "" : jsonObject.getString("referer");

        /*String username = request.getParameter("username").toString();
        String password = request.getParameter("password").trim();*/

        if (cn.ewsd.base.utils.StringUtils.isNullOrEmpty(referer)) {
            StringBuffer url = request.getRequestURL();
            String tempUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
            referer = tempUrl + "system/index/index?portal=system";
        }

        String passwordEncode = MD5Utils.encodeByMd5With32Bit(password);

        //String hql = String.format("FROM User WHERE userNameId = '%s' AND password = '%s' AND isDel <> 1 AND status !=0", userNameId, passwordEncode);
        Integer numberOfRows = userService.getCountBySql(userNameId, passwordEncode);

        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<>();
        if (numberOfRows == 1) {
            User user = userService.getDetailByLikeUserAndPass(userNameId, passwordEncode);
            if (user != null && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                session.setAttribute(Constants.SESSION_MEMBER, user.getUserNameId());
                session.setAttribute(Constants.UID, user.getUuid());
                session.setAttribute(Constants.USER_NAME_ID, user.getUserNameId());
                session.setAttribute(Constants.USER_NAME, user.getUserName());
                session.setAttribute("roleIds", user.getUserGroup());
                session.setAttribute("orgId", user.getOrgId());

                // 添加权限 url
                List<String> privilegeList = new ArrayList<>();
                List<AuthAccessView> privileges = authAccessViewService.getAuthorizedMenu(userService.getRoleIdByUserNameId(user.getUserNameId()));
                //privileges返回类型为List<Map<String, Object>>时使用下面循环
                /*if (privileges != null && !privileges.isEmpty()) {
                    for (Map<String, Object> entry : privileges) {
                        Object obj = entry.get("url");
                        if (obj != null && !"".equals(obj.toString())) {
                            privilegeList.add(entry.get("url").toString());
                        }
                    }
                }*/
                if (privileges != null && !privileges.isEmpty()) {
                    for (AuthAccessView authAccessView : privileges) {
                        Object obj = authAccessView.getUrl();
                        if (obj != null && !"".equals(obj.toString())) {
                            privilegeList.add(authAccessView.getUrl());
                        }
                    }
                }
                session.setAttribute(Constants.USER_URLS, privilegeList);

                map.put("userNameId", user.getUserNameId());
                map.put("statusCode", 200);
                map.put("referer", referer);
                //return base.jsonReturn(200, "登陆成功", false, contextPath + "/system/index/index");
            } else {
                map.put("statusCode", 300);
                map.put("referer", referer);
            }
        } else {
            session.setAttribute(Constants.SESSION_MEMBER, null);
            map.put("statusCode", 300);
            map.put("referer", referer);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/login/casLogout")
    public void casLogout(HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.SESSION_MEMBER, null);
        response.sendRedirect("http://sso.ewsd.cn/cas/login");
    }

    @RequestMapping(value = "/login/logout")
    public String logout(String portal) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.SESSION_MEMBER, null);
        session.setAttribute(Constants.USER_NAME_ID, null);
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("success", "1");
        //return JSON.toJSONString(jsonMap);
        String queryString = request.getQueryString();
        if (cn.ewsd.base.utils.StringUtils.isNullOrEmpty(queryString)) {
            queryString = "portal=system";
        }

        return "redirect:" + request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort() + "/system/login?" + queryString;
    }

    @Autowired
    private Audience audience;

    @PostMapping("/jwtLogin")
    @ResponseBody
    public Object jwtLogin(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = RequestData.getRequestJsonObj(request);
        String userNameId = jsonObject.getString("userNameId");
        String password = jsonObject.getString("password").trim();
//        String code =jsonObject.getString("code");

        //获取session
//        HttpServletRequest httprequest = (HttpServletRequest)request;
//        HttpSession session = httprequest.getSession();
//        String validateCode = (String) session.getAttribute("validateCode");
//        if (!code.toUpperCase().equals(validateCode.toUpperCase())){
//            return failure("验证码错误");
//        }
        User user = userService.getUserByUserNameId(userNameId);
        if (user == null) {
            return failure("用户名或邮箱错误");
        }
        if(user.getUserNameId() == null ){
            return failure("登录失败，请联系管理员！");
        }
        if(user.getStatus() != 1){
            return failure("当前用户已被禁用，请与管理员联系！");
        }
        //验证密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodePw = encoder.encode("ewsd0003");
        //String encodePw = "$2a$10$Ll7jzUGI9QREYuTKEame9OQHz2ZA0eY.ygQoHswlSjMrcI.qHJFgW";
        boolean is_password = encoder.matches(password, user.getPassword());
        if (!is_password) {
            //密码错误，返回提示
            return failure("您输入的密码不正确！");
        }

        // 添加权限 url
          List<String> privilegeList = new ArrayList<>();
        List<AuthAccessView> privileges = authAccessViewService.getAuthorizedMenu(userService.getRoleIdByUserNameId(userNameId));
        //privileges返回类型为List<AuthAccessView>时使用下面循环
        /*if (privileges != null && !privileges.isEmpty()) {
            for (Map<String, Object> entry : privileges) {
                Object obj = entry.get("url");
                if (obj != null && !"".equals(obj.toString())) {
                    privilegeList.add(entry.get("url").toString());
                }
            }
        }*/
        if (privileges != null && !privileges.isEmpty()) {
            for (AuthAccessView authAccessView : privileges) {
                Object obj = authAccessView.getUrl();
                if (obj != null && !"".equals(obj.toString())) {
                    privilegeList.add(authAccessView.getUrl());
                }
            }
        }
        RBucket<List<String>> keyObj = redissonService.getRBucket(userNameId);
        keyObj.set(privilegeList);

        String jwtToken = JwtUtil.createJWT(
                user.getUuid(),
                user.getUserNameId(),
                user.getUserName(),
                user.getOrgId(),
                user.getUserGroup(),
                user.getTenantId(),
                audience.getClientId(),
                audience.getName(),
                audience.getExpiresSecond() * 1000,
                audience.getBase64Secret()
        );
        String result_str = "bearer;" + jwtToken;

        String domain = request.getParameter("gatewayDomain");

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setMaxAge(3600 * 8);
        //IE下不能通过localhost设置cookie
        //cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);

        return success(result_str);
    }


    /**
     * @MethodName 生成验证码
     * @Description TODO
     * @Param null
     * @Return
     * @Author 朱永敬<zhuyongjing@zuoyour.com>
     * @Date 2019-09-23 9:04
     */

    public static String genCodes(int length,long num){

        String results="";

        for(int j=0;j<num;j++){
            String val = "";

            Random random = new Random();
            for(int i = 0; i < length; i++)
            {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

                if("char".equalsIgnoreCase(charOrNum)) // 字符串
                {
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                    val += (char) (choice + random.nextInt(26));
                }
                else if("num".equalsIgnoreCase(charOrNum)) // 数字
                {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            val=val.toLowerCase();
            if(results.contains(val)){
                continue;
            }else{
                return val;
            }
        }
        return results;

    }

    @ResponseBody
    @RequestMapping(value = "genCodes")
    public  String genCodes2(){
        String code = genCodes(4,1);
        //获取session
        HttpServletRequest httprequest = (HttpServletRequest)request;
        HttpSession session = httprequest.getSession();
        session.setAttribute("validateCode",code);
        return code;
    }


    @RequestMapping("/mtError")
    public String mtError(String msg) {
        request.setAttribute("msg",msg);
        return "error/mt_error";
    }
}
