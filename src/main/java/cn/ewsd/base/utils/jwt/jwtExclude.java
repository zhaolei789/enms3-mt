package cn.ewsd.base.utils.jwt;

import java.util.ArrayList;

public class jwtExclude {

    public static final ArrayList JWT_EXCLUDE_STR() {
        ArrayList var6 = new ArrayList();
        var6.add("/actuator/info");
        var6.add("/actuator/health");
        var6.add("/login");
        var6.add("/genCodes");
        var6.add("/login/login");
        var6.add("/jwtLogin");
        var6.add("/codeItem/getListByCodeSetIdAndLevelId");
        var6.add("/codeItem/getListByPid");
        var6.add("/codeItem/getFatherIds");
        var6.add("/codeItem/getZoneFillbackData");
        var6.add("/organization/getListByLevelId");
        var6.add("/organization/getListByPid");
        var6.add("/user/getPageSet");
        var6.add("/po/poserver.zz");
        var6.add("/system/login");
        var6.add("/mdata/weixin/login");
        var6.add("/mdata/weixin/registrationAgency");
        var6.add("/mdata/weixin/weixinPayCallBack");
        var6.add("/plugins/bootstrap/css/bootstrap.min.css");
        var6.add("/plugins/jquery/jquery.cookie.js");
        var6.add("/public/images/loginBg.jpg");
        var6.add("/plugins/bootstrap/fonts/glyphicons-halflings-regular.woff2");
        var6.add("/plugins/bootstrap/fonts/glyphicons-halflings-regular.woff");
        var6.add("/plugins/bootstrap/fonts/glyphicons-halflings-regular.ttf");
        var6.add("/public/images/logo_100.png");
        var6.add("/public/images/logo3.png");
        var6.add("/plugins/font-awesome/css/font-awesome.min.css");
        var6.add("/plugins/bootstrap/js/bootstrap.min.js");
        var6.add("/plugins/jquery/jquery.min.js");
        var6.add("/plugins/font-awesome/fonts/fontawesome-webfont.woff2");
        var6.add("/system/jwtLogin");
        var6.add("/system/genCodes");
        var6.add("/");
        var6.add("/system/attachment/showPic");
        var6.add("/public/img/favicon-32.png");
        var6.add("/favicon.ico");
        var6.add("article");
        var6.add("product");
        var6.add("page");
        var6.add("/swagger-ui.html");
        var6.add("/uploads/image/2021/05/06/202105061617320257_54188.png");
        var6.add("/topjui/themes/default/images/load4.gif");
        var6.add("/public/images/login-bg-mobile.png");

        var6.add("/public/font_32ee7hlhta/iconfont.js");
        var6.add("/public/font_32ee7hlhta/iconfont.css");

        //小程序
//        var6.add("/logisticsApp/login/getUserByUserId");
//        var6.add("/logisticsApp/login/loginCheck");
//        var6.add("/logisticsApp/**");

        var6.add("/public/images/bg.jpg");
        var6.add("/bigdata/presentation/bigDataA");
        var6.add("/druid/login.html");

        var6.add("/system/portal/getAuditNotReadNumAllv2");
        var6.add("/system/portal/getAuditNotReadListv2");
        var6.add("/system/mtError");
        return var6;
    }

}
