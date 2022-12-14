package cn.ewsd.system.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.common.Config;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.model.MCoreBase;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.DateUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.mdata.service.UserService;
import cn.ewsd.system.model.SysAuthdataApi;
import cn.ewsd.system.model.SysAuthdataFilter;
import cn.ewsd.system.properties.DatasourceProperties;
import cn.ewsd.system.service.*;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SystemBaseController extends BaseController {

    @Resource
    public ConfigService configService;

    @Resource
    private UserService userService;

    @Resource
    private SysAuthDataService sysAuthDataService;

    @Resource
    private SysAuthdataGroupService sysAuthdataGroupService;

    @Resource
    private SysAuthdataApiService sysAuthdataApiService;

    @Resource
    private SysAuthdataFilterService sysAuthdataFilterService;

    @Autowired
    public DatasourceProperties datasourceProperties;

    @Value("${spring.datasource.driver-class-name}")
    public String driverClassName;

    @Value("${spring.application.name}")
    public String applicationName;

    public HashMap<String, Object> config = Config.systemConfig();

    public Configuration getConfig(String fileName) {
        try {
            return new PropertiesConfiguration(fileName);
        } catch (ConfigurationException e) {
            throw new RuntimeException("???????????????????????????", e);
        }
    }

    public String getCurrentUuid() {
        return LoginInfo.getUuid();
    }

    public String getCurrentUserNameId() {
        return LoginInfo.getUserNameId();
    }

    public String getCurrentUserName() {
        return LoginInfo.getUserName();
    }

    public String getCurrentRoleId() {
        return LoginInfo.getRoleId();
    }

    public String getCurrentOrgId() {
        return LoginInfo.getOrgId();
    }

    public <T> T getSaveData(T entity) {
        if (StringUtils.isNullOrEmpty(((MCoreBase)entity).getUuid())) {
            ((MCoreBase)entity).setUuid(BaseUtils.UUIDGenerator());
        }

        ((MCoreBase)entity).setCreatorId(this.getCurrentUserNameId());
        ((MCoreBase)entity).setCreator(this.getCurrentUserName());
        ((MCoreBase)entity).setCreateTime(new Date());
        ((MCoreBase)entity).setCreatorOrgId(Integer.parseInt(LoginInfo.getOrgId()));
        return entity;
    }

    public <T> T getUpdateData(T entity) {
        ((MCoreBase)entity).setModifierId(this.getCurrentUserNameId());
        ((MCoreBase)entity).setModifier(this.getCurrentUserName());
        ((MCoreBase)entity).setModifyTime(DateUtils.getSystemDate());
        return entity;
    }


    public Boolean executeSql(String sql) {
        Connection conn = null;
        Statement st = null;
        try {
            Configuration config = getConfig("config.properties");
            String dbDriver = config.getString("mysql.jdbc.driverClassName");
            String dbUrl = config.getString("system.jdbc.url");
            String dbUsername = config.getString("system.jdbc.username");
            String dbPassword = config.getString("system.jdbc.password");

            //??????jdbc??????
            Class.forName(dbDriver);
            //????????????
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            st = conn.createStatement();
            st.addBatch(sql);
            st.executeBatch();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            try {
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.out.println("?????????????????????");
            }
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    /*public String getFilterByOrgId() {
        String filter = userService.getListByDataAuth(userInfo.getUserNameId());//?????????User??????dataAuth
        List<SysAuthData> sysAuthData = sysAuthDataService.getLisById(filter);//?????????SysAuthData???orgid
        String orgId = "";
        for (int i = 0; i < sysAuthData.size(); i++) {
            orgId += sysAuthData.get(i).getOrgId() + ",";
        }
        orgId = orgId.substring(0, orgId.length() - 1);
        return orgId;
    }*/

    /**
     * ????????????????????????
     *
     * @return
     */
    public String getAuthFilter() {
        //??????????????????????????????????????????
        String dataAuthGroups = userService.getListByDataAuth(LoginInfo.getUserNameId());
        //?????????????????????ids???????????????uuids
        String groupUuids= sysAuthdataGroupService.getGroupUuidsByIds(dataAuthGroups);
        if (!("".equals(groupUuids) || "null".equals(groupUuids) || groupUuids == null)) {
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????SQL?????????
            String url = "/" + applicationName + request.getRequestURI();
            List<SysAuthdataApi> sysAuthDataApis = sysAuthdataApiService.getListByGroupUuidsAndUrl(groupUuids.split(","), url);
            String apiUuid = sysAuthDataApis.size() > 0 ? sysAuthDataApis.get(0).getUuid() : "";
            String sqlStr = sysAuthDataApis.size() > 0 ? sysAuthDataApis.get(0).getSqlStr() : "";
            //????????????SQL????????????????????????
            List<SysAuthdataFilter> sysAuthdataFilters = sysAuthdataFilterService.getListByApiUuid(apiUuid);
            //???????????????????????????
            for (SysAuthdataFilter sysAuthdataFilter : sysAuthdataFilters) {
                sqlStr = sqlStr.replace(sysAuthdataFilter.getField(), sysAuthdataFilter.getValue());
            }
            sqlStr = sqlStr.replace("${currentUserNameId}", LoginInfo.getUserNameId()).replace("${currentOrgId}", LoginInfo.getOrgId());
            //??????SQL?????????
            return "".equals(sqlStr) ? "" : sqlStr;
        }
        return "";
    }

    public HashMap<String, Object> getAppSuccessRes(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("code",200);
        stringObjectHashMap.put("msg","");
        stringObjectHashMap.put("data","");
        return stringObjectHashMap;
    }

    public HashMap<String, Object> getAppFailRes(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("code",300);
        stringObjectHashMap.put("msg","");
        stringObjectHashMap.put("data","");
        return stringObjectHashMap;
    }

}