package cn.ewsd.cost.controller;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.common.Config;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.model.MCoreBase;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.DateUtils;
import cn.ewsd.common.utils.RandomUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.logistics.model.SysSequence;
import cn.ewsd.logistics.service.SysSequenceService;
import cn.ewsd.mdata.service.UserService;
import cn.ewsd.system.model.SysAuthdataApi;
import cn.ewsd.system.model.SysAuthdataFilter;
import cn.ewsd.system.properties.DatasourceProperties;
import cn.ewsd.system.service.*;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class CostBaseController extends BaseController {

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdfd2 = new SimpleDateFormat("yyyy-MM-dd");

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

    @Autowired
    private SysSequenceService sysSequenceService;

    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @Autowired
    private RedissonService redissonService;

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

    public String generateRandomNo(){
        SimpleDateFormat sdfa=new SimpleDateFormat("yyyyMMddHHmm");
        return sdfa.format(new Date()).substring(2,12)+ RandomUtils.getRandNum(10000,99999)+"";
    }

    /**
     * ????????????
     * @param SystemTag ??????????????????????????????
     * @param ModuleTag ??????????????????????????????
     * @return
     */
    public String getSequence(String SystemTag,String ModuleTag){
        String resSequence = "";
        int nowSeq = 1;
        String thisDateStr = sdf.format(new Date());//"yyyy-MM-dd HH:mm:ss"
        int thisYear = Integer.parseInt(thisDateStr.substring(0,4));
        int thisMonth = Integer.parseInt(thisDateStr.substring(5,7));
        SysSequence findSequence = sysSequenceService.findSequence(SystemTag,ModuleTag,thisYear,thisMonth);
        if(findSequence!=null){
            nowSeq = findSequence.getSqSequence()+1;
            sysSequenceService.sequenceAddOne(findSequence.getUuid());
        }else {
            SysSequence Sequence = new SysSequence();
            Sequence.setSqSequence(nowSeq);
            Sequence.setSqYear(thisYear);
            Sequence.setSqMonth(thisMonth);
            Sequence.setSqDay(0);
            Sequence.setSqSystem(SystemTag);
            Sequence.setSqModule(ModuleTag);
            Sequence.setIsDel(0);
            sysSequenceService.insertSelective(getSaveData(Sequence));
        }
        String seq6_tmp = "0000000"+nowSeq;
        String seq6 = seq6_tmp.substring(seq6_tmp.length()-6,seq6_tmp.length());
        resSequence = thisDateStr.replace("-","").substring(2,6)+SystemTag+seq6+ModuleTag;
        return resSequence;
    }

    /**
     * ????????????????????????????????????
     * @param DataList  ????????????
     * @param args  ????????????#????????????
     * @return
     */
    public List<?> covDataListDic(List<?> DataList,String...args){
        String redissonName = "sysDicItem";
        RBucket<Map<String,List<Map<String,Object>>>> keyObj = redissonService.getRBucket(redissonName);
        if(!keyObj.isExists()){
            return DataList;
        }
        Map<String,List<Map<String,Object>>> keyObj2 = keyObj.get();
        Map<String,String> paramMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String [] paramArray = args[i].split("#");
            if (paramArray.length != 2){continue;}
            paramMap.put(paramArray[0],paramArray[1]);
        }
        if(paramMap.size()<1){
            return DataList;
        }
        for (int i = 0; i < DataList.size(); i++) {
            try{
                Class<? extends Object> tClass = DataList.get(i).getClass();
                //??????????????????
                Field[] field = tClass.getDeclaredFields();
                for (int j = 0; j < field.length; j++) {
                    //??????????????????????????????
                    field[j].setAccessible(true);
                    String natureName = field[j].getName();//????????????
                    if(paramMap.containsKey(natureName)){//????????????????????????
                        String dicName = paramMap.get(natureName);//??????????????????
                        String natureNameGet = "get"+natureName.replaceFirst(natureName.substring(0, 1), natureName.substring(0, 1).toUpperCase());
                        Method m = tClass.getMethod(natureNameGet);
                        String natureValue = (String) m.invoke(DataList.get(i));//?????????
                        //????????????????????????
                        if(keyObj2.containsKey(dicName)){
                            List<Map<String,Object>> Dicval = keyObj2.get(dicName);
                            for (int k = 0; k < Dicval.size(); k++) {
                                if(Dicval.get(k).get("value").equals(natureValue)){
                                    field[j].set(DataList.get(i),Dicval.get(k).get("text"));
                                    break;
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                return DataList;
            }
        }
        return DataList;
    }

    public Boolean checkHaveAccess(Integer id) throws Exception {
        Boolean flag = false;
        String [] userRole = LoginInfo.getRoleId().split(",");
        for (int i = 0; i < userRole.length; i++) {
            String sql = "select count(uuid) as NUM from sys_auth_access where role_id="+userRole[i]+" and id="+id+" and access_auth=1";
            PageData checkObj = DbUtil.executeQueryObject(sql);
            if(checkObj != null&&checkObj.get("NUM")!=null&&Integer.parseInt(checkObj.get("NUM").toString())>0){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * ?????????????????????????????????
     * @param userId
     * @return
     */
    public String getUserQryOrgStr(String userId){
        return "("+sysUserQryOrgService.getUserDeptId(userId)+")";
    }
    public String getUserQryOrgStr(){
        return "("+sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid())+")";
    }


}
