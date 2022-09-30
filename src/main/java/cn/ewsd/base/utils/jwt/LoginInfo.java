package cn.ewsd.base.utils.jwt;

import cn.ewsd.base.utils.jwt.UserInfo;

public class LoginInfo {
    public static final ThreadLocal<cn.ewsd.base.utils.jwt.UserInfo> userInfoTL = new ThreadLocal();

    public LoginInfo() {
    }

    public static void add(cn.ewsd.base.utils.jwt.UserInfo userInfo) {
        userInfoTL.set(userInfo);
    }

    public static cn.ewsd.base.utils.jwt.UserInfo get() {
        return (cn.ewsd.base.utils.jwt.UserInfo)userInfoTL.get();
    }

    public static String getUuid() {
        return ((cn.ewsd.base.utils.jwt.UserInfo)userInfoTL.get()).getUuid();
    }

    public static String getUserNameId() {
        return ((cn.ewsd.base.utils.jwt.UserInfo)userInfoTL.get()).getUserNameId();
    }

    public static String getUserName() {
        return ((cn.ewsd.base.utils.jwt.UserInfo)userInfoTL.get()).getUserName();
    }

    public static String getOrgId() {
        return ((cn.ewsd.base.utils.jwt.UserInfo)userInfoTL.get()).getOrgId();
    }

    public static String getRoleId() {
        return ((UserInfo)userInfoTL.get()).getRoleId();
    }

    public static String getTenantId() {
        return ((cn.ewsd.base.utils.jwt.UserInfo)userInfoTL.get()).getTenantId();
    }

    public static void remove() {
        userInfoTL.remove();
    }
}