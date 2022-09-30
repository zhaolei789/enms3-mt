package cn.ewsd.forest.model.getToken;

import java.util.List;

public class refreshTokenData {
    private String access_token;
    private String refresh_token;
    private String license;
    private String accountId;
    private String scope;
    private List<refreshTokenDataOrgInfo> orgInfo;
    private String token_type;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<refreshTokenDataOrgInfo> getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(List<refreshTokenDataOrgInfo> orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
