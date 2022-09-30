package cn.ewsd.mdata.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.common.Config;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.model.MCoreBase;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.DateUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.system.model.SysAuthdataApi;
import cn.ewsd.system.model.SysAuthdataFilter;
import cn.ewsd.system.service.SysAuthdataApiService;
import cn.ewsd.system.service.SysAuthdataFilterService;
import cn.ewsd.system.service.SysAuthdataGroupService;
import cn.ewsd.mdata.service.UserService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.*;

public class MdataBaseController extends BaseController {

    public Config systemConfig = new Config();

    public HashMap<String, Object> config = Config.systemConfig();

    @Resource
    private UserService userService;

    @Resource
    private SysAuthdataGroupService sysAuthdataGroupService;

    @Resource
    private SysAuthdataApiService sysAuthdataApiService;

    @Resource
    private SysAuthdataFilterService sysAuthdataFilterService;

    @Value("${spring.application.name}")
    public String applicationName;


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



    /**
     * 通用数据权限过滤
     *
     * @return
     */
    public String getAuthFilter() {
        //获得当前用户的所有数据授权组
        String ids = userService.getListByDataAuth(LoginInfo.getUserNameId());
        //根据数据授权组ids获得授权组uuids
        String uuids = sysAuthdataGroupService.getGroupUuidsByIds(ids);
        if (!("".equals(uuids) || "null".equals(uuids) || uuids == null)) {
            //获取与当前访问数据接口一致的数据接口（有多条使用优先级最高的一条，数字越大优先级越高）的SQL字符串
            String url = "/" + applicationName + request.getRequestURI();
            Map<String, Object> map = new HashMap<String, Object>();
            Set<String> keySet = map.keySet();
            List<SysAuthdataApi> sysAuthDataApis = sysAuthdataApiService.getListByGroupUuidsAndUrl(uuids.split(","), url);
            String apiUuid = sysAuthDataApis.size() > 0 ? sysAuthDataApis.get(0).getUuid().toString() : "";
            String sqlStr = sysAuthDataApis.size() > 0 ? sysAuthDataApis.get(0).getSqlStr().toString() : "";
            //查找对应SQL语句的所有变量值
            List<SysAuthdataFilter> sysAuthdataFilters = sysAuthdataFilterService.getListByApiUuid(apiUuid);
            //替换字符串中的变量
            for (SysAuthdataFilter sysAuthdataFilter : sysAuthdataFilters) {
                sqlStr = sqlStr.replace(sysAuthdataFilter.getField().toString(), sysAuthdataFilter.getValue().toString());
            }
            sqlStr = sqlStr.replace("${currentUserNameId}", LoginInfo.getUserNameId()).replace("${currentOrgId}", LoginInfo.getOrgId());
            //返回SQL字符串
            return "".equals(sqlStr) ? "" : sqlStr;
        }
        return "";
    }

    public boolean strNotNull(String str){
        if(str!=null&&!"".equals(str)&&!"null".equals(str)&&!"undefined".equals(str)&&!"".equals(str.trim())){
            return true;
        }else {
            return false;
        }
    }

}