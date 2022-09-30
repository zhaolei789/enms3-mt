package cn.ewsd.repository.controller;

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

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className GirlController
 * @description
 * @date 2018-03-28 22:24
 */
@Controller
public class RepositoryBaseController extends BaseController {

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
            throw new RuntimeException("获取配置文件失败，", e);
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

            //注册jdbc驱动
            Class.forName(dbDriver);
            //打开连接
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
                System.out.println("连接关闭异常！");
            }
        }
    }

    /**
     * 通用组织机构数据权限过滤
     *
     * @return
     */
    /*public String getFilterByOrgId() {
        String filter = userService.getListByDataAuth(userInfo.getUserNameId());//查询到User表中dataAuth
        List<SysAuthData> sysAuthData = sysAuthDataService.getLisById(filter);//查询到SysAuthData中orgid
        String orgId = "";
        for (int i = 0; i < sysAuthData.size(); i++) {
            orgId += sysAuthData.get(i).getOrgId() + ",";
        }
        orgId = orgId.substring(0, orgId.length() - 1);
        return orgId;
    }*/

    /**
     * 通用数据权限过滤
     *
     * @return
     */
    public String getAuthFilter() {
        //获得当前用户的所有数据授权组
        String dataAuthGroups = userService.getListByDataAuth(LoginInfo.getUserNameId());
        //根据数据授权组ids获得授权组uuids
        String groupUuids= sysAuthdataGroupService.getGroupUuidsByIds(dataAuthGroups);
        if (!("".equals(groupUuids) || "null".equals(groupUuids) || groupUuids == null)) {
            //获取与当前访问数据接口一致的数据接口（有多条使用优先级最高的一条，数字越大优先级越高）的SQL字符串
            String url = "/" + applicationName + request.getRequestURI();
            List<SysAuthdataApi> sysAuthDataApis = sysAuthdataApiService.getListByGroupUuidsAndUrl(groupUuids.split(","), url);
            String apiUuid = sysAuthDataApis.size() > 0 ? sysAuthDataApis.get(0).getUuid() : "";
            String sqlStr = sysAuthDataApis.size() > 0 ? sysAuthDataApis.get(0).getSqlStr() : "";
            //查找对应SQL语句的所有变量值
            List<SysAuthdataFilter> sysAuthdataFilters = sysAuthdataFilterService.getListByApiUuid(apiUuid);
            //替换字符串中的变量
            for (SysAuthdataFilter sysAuthdataFilter : sysAuthdataFilters) {
                sqlStr = sqlStr.replace(sysAuthdataFilter.getField(), sysAuthdataFilter.getValue());
            }
            sqlStr = sqlStr.replace("${currentUserNameId}", LoginInfo.getUserNameId()).replace("${currentOrgId}", LoginInfo.getOrgId());
            //返回SQL字符串
            return "".equals(sqlStr) ? "" : sqlStr;
        }
        return "";
    }

}