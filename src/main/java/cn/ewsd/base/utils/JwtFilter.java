package cn.ewsd.base.utils;

import cn.ewsd.base.utils.jwt.jwtExclude;
import cn.ewsd.common.bean.Audience;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.CookieUtil;
import cn.ewsd.base.utils.jwt.JwtUtil;
import cn.ewsd.forest.client.MtBaseClient;
import cn.ewsd.forest.client.MtUserClient;
import cn.ewsd.forest.model.getToken.getTokenData;
import cn.ewsd.forest.model.getToken.getTokenObj;
import cn.ewsd.forest.model.tokenGetUserInfo.tokenUserObj;
import cn.ewsd.forest.model.tokenGetUserInfo2.tokenUserObj2;
import cn.ewsd.mdata.model.SysTenant;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.SysTenantService;
import cn.ewsd.mdata.service.UserService;
import cn.ewsd.system.model.AuthAccessView;
import cn.ewsd.system.service.AuthAccessViewService;
import cn.ewsd.system.service.RedissonService;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.dtflys.forest.Forest;
import io.jsonwebtoken.Claims;
import org.redisson.api.RBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JwtFilter extends cn.ewsd.common.filter.JwtFilter {

    static Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private Audience audience;


    public JwtFilter() {
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String var4 = request.getRequestURI();
        //logger.info("请求地址:"+var4);
        logger.info("userCode="+request.getParameter("userCode")+";corpId="+request.getParameter("corpId")+";clientType="+request.getParameter("clientType"));//userCode
        String var5 = request.getHeader("requestType");
        var5 = var5 == null ? "" : var5;
        ArrayList var6 = jwtExclude.JWT_EXCLUDE_STR();

        if (var4.contains("article")){
            var4="article";
        }
        if (var4.contains("product")){
            var4="product";
        }
        if (var4.contains("page")){
            var4="page";
        }
        if (!var6.contains(var4)) {
            if (var5.equals("feign")) {
                filterChain.doFilter(request, response);
            } else {
                String var7 = getToken(request);

                String var8 = "bearer;" + var7;
                if ("OPTIONS".equals(request.getMethod())) {
                    response.setStatus(200);
                    filterChain.doFilter(request, response);
                } else {
                    if(request.getParameter("userCode") != null){
                        //MT单点登录方式进入
                        String userCode = request.getParameter("userCode");
                        String corpId = request.getParameter("corpId");
                        UserService userService = (UserService)ApplicationContextProvider.getBean("userServiceImpl");
                        AuthAccessViewService authAccessViewService = (AuthAccessViewService)ApplicationContextProvider.getBean("authAccessViewServiceImpl");
                        RedissonService redissonService = (RedissonService)ApplicationContextProvider.getBean("redissonService");
                        SysTenantService sysTenantService = (SysTenantService)ApplicationContextProvider.getBean("sysTenantServiceImpl");
                        MtBaseClient mtBaseClient = Forest.client(MtBaseClient.class);
                        MtUserClient mtUserClient = Forest.client(MtUserClient.class);

                        String clientId = "";
                        String base64Secret = "";
                        String name = "";
                        int expiresSecond = 1;
                        if(audience!=null){
                            clientId = audience.getClientId();
                            base64Secret = audience.getBase64Secret();
                            name = audience.getName();
                            expiresSecond = audience.getExpiresSecond();
                        }else {
                            clientId = "098f6bcd4621d373cade4e832627b4f9";
                            base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
                            name = "systemJwt";
                            expiresSecond = 172800;
                        }

                        //通过第三方userCode获取用户信息
                        //1.通过userCode换取access_token
                        getTokenObj tokenObj = mtBaseClient.getTokenByCode(userCode);
                        if(tokenObj!=null&&"ok".equals(tokenObj.getMsg())){
                            //2.通过token获取用户信息
                            tokenUserObj2 tokenUserObj = mtUserClient.getTokenUserInfo2(corpId,tokenObj.getData().getAccess_token());
                            if(tokenUserObj!=null&&"ok".equals(tokenUserObj.getMsg())){
                                //3.通过用户信息比对用户表,提取本系统用户
                                //查询是否有租户，无则增加
                                boolean tenant_status_del = false;
                                boolean tenant_status_jy = false;
                                List<SysTenant> tenantList = sysTenantService.queryListByFS("uuid = '"+corpId+"'");
                                if(tenantList!=null &&tenantList.size()>0){
                                    for (int i = 0; i < tenantList.size(); i++) {
                                        if(tenantList.get(i).getIsDel()==1){
                                            tenant_status_del = true;
                                            break;
                                        }
                                        if(tenantList.get(i).getStatus()==2){
                                            tenant_status_jy = true;
                                            break;
                                        }
                                    }
                                    if(tenant_status_del){
                                        request.getRequestDispatcher("/system/mtError?msg=租户已被删除！").forward(request,response);
                                    }
                                    if(tenant_status_jy){
                                        request.getRequestDispatcher("/system/mtError?msg=租户已被禁用！").forward(request,response);
                                    }
                                }
                                if(tenantList == null || tenantList.size()<1){
                                    SysTenant sysTenant = new SysTenant();
                                    sysTenant.setUuid(corpId);
                                    sysTenant.setCreateTime(new Date());
                                    sysTenant.setTenantName("新建租户");
                                    sysTenant.setLogo("");
                                    sysTenant.setEstablishDate("");
                                    sysTenant.setBusinessEmail("");
                                    sysTenant.setBusinessPhone("");
                                    sysTenant.setBusinessName(corpId);
                                    sysTenant.setLegalPerson("");
                                    sysTenant.setCorpAddress("");
                                    sysTenant.setBusinessScope("");
                                    sysTenant.setAdminName("");
                                    sysTenant.setAdminPhone("");
                                    sysTenant.setEnableTime(new Date());
                                    sysTenant.setOverdueTime(new Date());
                                    sysTenant.setNote("");
                                    sysTenant.setType(1);
                                    sysTenant.setStatus(1);
                                    sysTenant.setIsDel(0);
                                    sysTenantService.insertSelective(sysTenant);
                                }

                                User sysUser = userService.getUserByUnionId(tokenUserObj.getData().getId());
                                if(sysUser != null){
                                    //4.同步用户assessToken
                                    userService.updateTokenByUserId(tokenObj.getData().getAccess_token(),tokenObj.getData().getRefresh_token(),sysUser.getUuid());
                                    //5.校验用户
                                    if(sysUser.getIsDel()!=1&&sysUser.getStatus()==1){
                                        //6.执行授权
                                        List<String> privilegeList = new ArrayList<>();
                                        List<AuthAccessView> privileges = authAccessViewService.getAuthorizedMenu(userService.getRoleIdByUserNameId(sysUser.getUserNameId()));
                                        if (privileges != null && !privileges.isEmpty()) {
                                            for (AuthAccessView authAccessView : privileges) {
                                                Object obj = authAccessView.getUrl();
                                                if (obj != null && !"".equals(obj.toString())) {
                                                    privilegeList.add(authAccessView.getUrl());
                                                }
                                            }
                                        }
                                        RBucket<List<String>> keyObj = redissonService.getRBucket(sysUser.getUserNameId());
                                        keyObj.set(privilegeList);
                                        String jwtToken = JwtUtil.createJWT(
                                                sysUser.getUuid(),
                                                sysUser.getUserNameId(),
                                                sysUser.getUserName(),
                                                sysUser.getOrgId(),
                                                sysUser.getUserGroup(),
                                                sysUser.getTenantId(),
                                                clientId,
                                                name,
                                                expiresSecond * 1000,
                                                base64Secret
                                        );
                                        String result_str = "bearer;" + jwtToken;
                                        Cookie cookie = new Cookie("token", jwtToken);
                                        cookie.setMaxAge(3600 * 8);
                                        cookie.setPath("/");
                                        response.addCookie(cookie);

                                        UserInfo var10 = new UserInfo();
                                        var10.setUuid(sysUser.getUuid());
                                        var10.setUserNameId(sysUser.getUserNameId());
                                        var10.setUserName(sysUser.getUserName());
                                        var10.setRoleId(sysUser.getUserGroup());
                                        var10.setOrgId(sysUser.getOrgId());
                                        var10.setTenantId(sysUser.getTenantId());
                                        LoginInfo.add(var10);
                                        filterChain.doFilter(request, response);
                                    }else {
                                        request.getRequestDispatcher("/system/mtError?msg=用户状态不能进入系统").forward(request,response);
                                    }
                                }else {
                                    //将该用户token赋予系统默认账号
                                    String DEFAULT_USER_ID = "d24d95b258fa4d82b49b03856eb29fd4";
                                    userService.updateTokenAndTenantIdByUserId(tokenObj.getData().getAccess_token(),tokenObj.getData().getRefresh_token(),corpId,DEFAULT_USER_ID);
                                    request.getRequestDispatcher("/system/mtError?msg=未获取到用户数据").forward(request,response);
                                }
                            }else {
                                request.getRequestDispatcher("/system/mtError?msg=未能通过Token获取用户信息").forward(request,response);
                            }
                        }else {
                            request.getRequestDispatcher("/system/mtError?msg=用户接口信息错误").forward(request,response);
                        }
                    }else {
                        if (!var8.startsWith("bearer;")) {
                            System.out.println("token does not start with bearer;！");
                        }
                        try {
                            WebApplicationContext var9;
                            if (this.audience == null) {
                                var9 = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                                this.audience = (Audience)var9.getBean("audience");
                            }

                            Claims var11 = JwtUtil.parseJWT(var7, this.audience.getBase64Secret());
                            if (var11 == null) {
                                System.out.println("claims is null！");
                            }

                            request.setAttribute("claims", var11);

                            //解析token中用户数据进行存储
                            UserInfo var10 = new UserInfo();
                            var10.setUuid(Convert.toStr(var11.get("uuid")));
                            var10.setUserNameId(Convert.toStr(var11.get("userNameId")));
                            var10.setUserName(Convert.toStr(var11.get("userName")));
                            var10.setRoleId(Convert.toStr(var11.get("roleId")));
                            var10.setOrgId(Convert.toStr(var11.get("orgId")));
                            var10.setTenantId(Convert.toStr(var11.get("tenantId")));
                            LoginInfo.add(var10);

                            filterChain.doFilter(request, response);
                        } catch (Exception var10) {
                            System.out.println(var10);
                            System.out.println(var10.getMessage());
                            request.getRequestDispatcher("/system/login").forward(request,response);
                        }
                    }
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }


    //获取Token
    private String getToken(HttpServletRequest request) {
        String var7 = CookieUtil.getCookieByName(request, "token");
        String paramToken = request.getParameter("token");
        if (!StringUtils.isNullOrEmpty(paramToken)) {
            var7 = paramToken;
        }
        if (StringUtils.isNullOrEmpty(var7)) {
            var7 = request.getHeader("token");
        }
        return var7;
    }
}
