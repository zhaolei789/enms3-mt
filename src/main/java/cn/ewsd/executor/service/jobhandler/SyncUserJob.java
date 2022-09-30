package cn.ewsd.executor.service.jobhandler;

import cn.ewsd.forest.client.MtUserClient;
import cn.ewsd.forest.model.getAlluser.GetAllUserResObj;
import cn.ewsd.forest.model.getAlluser.GetAllUserUserObj;
import cn.ewsd.forest.model.getUserDetail.UserDetailData;
import cn.ewsd.forest.model.getUserDetail.UserDetailObj;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.model.SysTenant;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.mdata.service.SysTenantService;
import cn.ewsd.mdata.service.UserService;
import com.dtflys.forest.http.ForestRequest;
import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
//import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JobHandler(value = "userSyncExecute")
@Component
public class SyncUserJob extends IJobHandler {

    @Autowired
    private MtUserClient mtUserClient;
    @Autowired
    private SysTenantService sysTenantService;
    @Resource
    private UserService userService;
    @Resource
    private OrganizationService organizationService;

    /**
     * 同步用户信息
     * @throws Exception
     */
//    @XxlJob("userSyncExecute")
//    public void userSyncExecute() throws Exception {
//        XxlJobHelper.log("userSyncExecute start...");
//
//        String access_token;
//        List<SysTenant> sysTenantList = sysTenantService.queryListByFS("is_del != 1 and status = 1");
//        for (int i = 0; i < sysTenantList.size(); i++) {
//            String cropid = sysTenantList.get(i).getUuid();
//            String tenantToken = userService.getNewAssessTokenByTenantId(cropid);
//            if(tenantToken!=null&&!"".equals(tenantToken)){
//                access_token = tenantToken;
//                GetAllUserResObj alluserObj = mtUserClient.getAlluser(cropid,access_token);
//                if(alluserObj.getMsg().equals("ok")){
//                    List<GetAllUserUserObj> allUserData = alluserObj.getData();
//                    List<String> newUserList = new ArrayList<>();
//                    List<String> allUserList = new ArrayList<>();
//                    for (int j = 0; j < allUserData.size(); j++) {
//                        if(userService.getUserByUuid(allUserData.get(j).getId().toString())==null){
//                            newUserList.add(allUserData.get(j).getId().toString());
//                        }
//                        allUserList.add(allUserData.get(j).getId().toString());
//                    }
//                    userService.executeDeleteUserByTenantId(allUserList.toArray(new String[allUserList.size()]),cropid);//同步删除数据
//                    userService.executeUnDeleteUserByTenantId(allUserList.toArray(new String[allUserList.size()]),cropid);//同步撤销删除
//                    int oneDisposeNum = 10;//一次最多处理10条数据
//                    int cycleNum = newUserList.size()%oneDisposeNum == 0?newUserList.size()/oneDisposeNum :newUserList.size()/oneDisposeNum +1;//循环数量
//                    for (int j = 0; j < cycleNum; j++) {
//                        String [] userIdsArray;
//                        if(j == cycleNum-1){//最后一次
//                           userIdsArray = new String[newUserList.size()-(oneDisposeNum*j)];
//                        }else {
//                            userIdsArray = new String[oneDisposeNum];
//                        }
//                        for (int k = 0; k < userIdsArray.length; k++) {
//                            userIdsArray[k] = newUserList.get((oneDisposeNum*j)+k);
//                        }
//                        if(userIdsArray.length>0){
//                            UserDetailObj userDetail = mtUserClient.getUserDetail(userIdsArray,cropid,access_token);
//                            if(userDetail.getMsg().equals("ok")&&userDetail.getData().size()>0){
//                                List<UserDetailData> userDataList = userDetail.getData();
//                                for (int k = 0; k < userDataList.size(); k++) {
//                                    String orgId = "";
//                                    String orgName = "";
//                                    //查询部门
//                                    if(userDataList.get(k).getDepts()!=null&&userDataList.get(k).getDepts().size()>0&&userDataList.get(k).getDepts().get(0)!=null){
//                                        Organization organization = organizationService.queryObject(userDataList.get(k).getDepts().get(0).getId());
//                                        if (organization!=null){
//                                            orgId = organization.getId().toString();
//                                            orgName = organization.getText();
//                                        }
//                                    }
//                                    PasswordEncoder encoder = new BCryptPasswordEncoder();
//                                    User user = new User();
//                                    user.setUuid(userDataList.get(k).getId().toString());
//                                    user.setIsDel(0);
//                                    user.setBirthday("");
//                                    user.setDegree("");
//                                    user.setEducation("");
//                                    user.setProvince("");
//                                    user.setCity("");
//                                    user.setTelephone(userDataList.get(k).getTel());
//                                    user.setCellphone(userDataList.get(k).getMobile());
//                                    user.setEmail(userDataList.get(k).getEmail());
//                                    user.setNation("");
//                                    user.setNativePlace("");
//                                    user.setNickName("");
//                                    user.setOrgId(orgId);
//                                    user.setOrgName(orgName);
//                                    user.setPost("");
//                                    user.setSchool("");
//                                    user.setUserGroup("85");
//                                    user.setDataAuth("4");
//                                    user.setUserName(userDataList.get(k).getName());
//                                    user.setUserNameId(userDataList.get(k).getId());
//                                    user.setPassword(encoder.encode(userDataList.get(k).getId()));
//                                    user.setIdCard("");
//                                    user.setAge(1);
//                                    user.setSex("");
//                                    user.setStatus(1);
//                                    user.setPostText("");
//                                    user.setOnlineStatus("");
//                                    user.setSign("");
//                                    user.setAvatar(userDataList.get(k).getAvatar());
//                                    user.setCountry("china");
//                                    user.setZone("");
//                                    user.setOpenid("");
//                                    user.setUnionid(userDataList.get(k).getAccountId());
//                                    user.setDistrict("");
//                                    user.setLinkEmployees("");
//                                    user.setLinkEmployeesName("");
//                                    user.setUserNameJp("");
//                                    user.setLbPost("");
//                                    user.setLbPostText("");
//                                    user.setGjPost("");
//                                    user.setGjPostText("");
//                                    user.setLeadFlag("");
//                                    user.setLeadFlagText("");
//                                    user.setIsDriver(0);
//                                    user.setDriverId("");
//                                    user.setIsTenantAdmin(0);
//                                    user.setTenantId(cropid);
//                                    user.setAssessToken("");
//                                    user.setRefreshToken("");
//                                    user.setTokenRefreshTime(null);
//                                    user.setCreateTime(new Date());
//                                    user.setEntryWay(1);
//                                    userService.insertSelective(user);
//                                }
//                            }
//                        }
//                    }
//
////                    for (int j = 0; j < allUserData.size(); j++) {
////                        if(userService.getUserByUuid(allUserData.get(j).getId().toString())==null){
////                            UserDetailObj userDetail = mtUserClient.getUserDetail(new String[]{allUserData.get(j).getId().toString()},cropid,access_token);
////                            if(userDetail.getMsg().equals("ok")){
////                                String orgId = "";
////                                String orgName = "";
////                                //查询部门
////                                if(userDetail.getData().get(0).getDepts()!=null&&userDetail.getData().get(0).getDepts().size()>0&&userDetail.getData().get(0).getDepts().get(0)!=null){
////                                    Organization organization = organizationService.queryObject(userDetail.getData().get(0).getDepts().get(0).getId());
////                                    if (organization!=null){
////                                        orgId = organization.getId().toString();
////                                        orgName = organization.getText();
////                                    }
////                                }
////                                PasswordEncoder encoder = new BCryptPasswordEncoder();
////                                User user = new User();
////                                user.setUuid(allUserData.get(j).getId().toString());
////                                user.setIsDel(0);
////                                user.setBirthday("");
////                                user.setDegree("");
////                                user.setEducation("");
////                                user.setProvince("");
////                                user.setCity("");
////                                user.setTelephone(userDetail.getData().get(0).getTel());
////                                user.setCellphone(userDetail.getData().get(0).getMobile());
////                                user.setEmail(userDetail.getData().get(0).getEmail());
////                                user.setNation("");
////                                user.setNativePlace("");
////                                user.setNickName("");
////                                user.setOrgId(orgId);
////                                user.setOrgName(orgName);
////                                user.setPost("");
////                                user.setSchool("");
////                                user.setUserGroup("85");
////                                user.setDataAuth("4");
////                                user.setUserName(allUserData.get(j).getName());
////                                user.setUserNameId(allUserData.get(j).getId().toString());
////                                user.setPassword(encoder.encode(allUserData.get(j).getId().toString()));
////                                user.setIdCard("");
////                                user.setAge(1);
////                                user.setSex("");
////                                user.setStatus(1);
////                                user.setPostText("");
////                                user.setOnlineStatus("");
////                                user.setSign("");
////                                user.setAvatar(allUserData.get(j).getAvatar());
////                                user.setCountry("china");
////                                user.setZone("");
////                                user.setOpenid("");
////                                user.setUnionid(userDetail.getData().get(0).getAccountId());
////                                user.setDistrict("");
////                                user.setLinkEmployees("");
////                                user.setLinkEmployeesName("");
////                                user.setUserNameJp("");
////                                user.setLbPost("");
////                                user.setLbPostText("");
////                                user.setGjPost("");
////                                user.setGjPostText("");
////                                user.setLeadFlag("");
////                                user.setLeadFlagText("");
////                                user.setIsDriver(0);
////                                user.setDriverId("");
////                                user.setIsTenantAdmin(0);
////                                user.setTenantId(cropid);
////                                user.setAssessToken("");
////                                user.setRefreshToken("");
////                                user.setTokenRefreshTime(null);
////                                user.setCreateTime(new Date());
////                                userService.insertSelective(user);
////                            }
////                        }
////                    }
//
//                }
//            }
//        }
//
//
//    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        String access_token;
        List<SysTenant> sysTenantList = sysTenantService.queryListByFS("is_del != 1 and status = 1");
        for (int i = 0; i < sysTenantList.size(); i++) {
            String cropid = sysTenantList.get(i).getUuid();
            String tenantToken = userService.getNewAssessTokenByTenantId(cropid);
            if(tenantToken!=null&&!"".equals(tenantToken)){
                access_token = tenantToken;
                GetAllUserResObj alluserObj = mtUserClient.getAlluser(cropid,access_token);
                if(alluserObj.getMsg().equals("ok")){
                    List<GetAllUserUserObj> allUserData = alluserObj.getData();
                    List<String> newUserList = new ArrayList<>();
                    List<String> allUserList = new ArrayList<>();
                    for (int j = 0; j < allUserData.size(); j++) {
                        if(userService.getUserByUuid(allUserData.get(j).getId().toString())==null){
                            newUserList.add(allUserData.get(j).getId().toString());
                        }
                        allUserList.add(allUserData.get(j).getId().toString());
                    }
                    userService.executeDeleteUserByTenantId(allUserList.toArray(new String[allUserList.size()]),cropid);//同步删除数据
                    userService.executeUnDeleteUserByTenantId(allUserList.toArray(new String[allUserList.size()]),cropid);//同步撤销删除
                    int oneDisposeNum = 10;//一次最多处理10条数据
                    int cycleNum = newUserList.size()%oneDisposeNum == 0?newUserList.size()/oneDisposeNum :newUserList.size()/oneDisposeNum +1;//循环数量
                    for (int j = 0; j < cycleNum; j++) {
                        String [] userIdsArray;
                        if(j == cycleNum-1){//最后一次
                            userIdsArray = new String[newUserList.size()-(oneDisposeNum*j)];
                        }else {
                            userIdsArray = new String[oneDisposeNum];
                        }
                        for (int k = 0; k < userIdsArray.length; k++) {
                            userIdsArray[k] = newUserList.get((oneDisposeNum*j)+k);
                        }
                        if(userIdsArray.length>0){
                            UserDetailObj userDetail = mtUserClient.getUserDetail(userIdsArray,cropid,access_token);
                            if(userDetail.getMsg().equals("ok")&&userDetail.getData().size()>0){
                                List<UserDetailData> userDataList = userDetail.getData();
                                for (int k = 0; k < userDataList.size(); k++) {
                                    String orgId = "";
                                    String orgName = "";
                                    //查询部门
                                    if(userDataList.get(k).getDepts()!=null&&userDataList.get(k).getDepts().size()>0&&userDataList.get(k).getDepts().get(0)!=null){
                                        Organization organization = organizationService.queryObject(userDataList.get(k).getDepts().get(0).getId());
                                        if (organization!=null){
                                            orgId = organization.getId().toString();
                                            orgName = organization.getText();
                                        }
                                    }
                                    PasswordEncoder encoder = new BCryptPasswordEncoder();
                                    User user = new User();
                                    user.setUuid(userDataList.get(k).getId().toString());
                                    user.setIsDel(0);
                                    user.setBirthday("");
                                    user.setDegree("");
                                    user.setEducation("");
                                    user.setProvince("");
                                    user.setCity("");
                                    user.setTelephone(userDataList.get(k).getTel());
                                    user.setCellphone(userDataList.get(k).getMobile());
                                    user.setEmail(userDataList.get(k).getEmail());
                                    user.setNation("");
                                    user.setNativePlace("");
                                    user.setNickName("");
                                    user.setOrgId(orgId);
                                    user.setOrgName(orgName);
                                    user.setPost("");
                                    user.setSchool("");
                                    user.setUserGroup("85");
                                    user.setDataAuth("4");
                                    user.setUserName(userDataList.get(k).getName());
                                    user.setUserNameId(userDataList.get(k).getId());
                                    user.setPassword(encoder.encode(userDataList.get(k).getId()));
                                    user.setIdCard("");
                                    user.setAge(1);
                                    user.setSex("");
                                    user.setStatus(1);
                                    user.setPostText("");
                                    user.setOnlineStatus("");
                                    user.setSign("");
                                    user.setAvatar(userDataList.get(k).getAvatar());
                                    user.setCountry("china");
                                    user.setZone("");
                                    user.setOpenid("");
                                    user.setUnionid(userDataList.get(k).getAccountId());
                                    user.setDistrict("");
                                    user.setLinkEmployees("");
                                    user.setLinkEmployeesName("");
                                    user.setUserNameJp("");
                                    user.setLbPost("");
                                    user.setLbPostText("");
                                    user.setGjPost("");
                                    user.setGjPostText("");
                                    user.setLeadFlag("");
                                    user.setLeadFlagText("");
                                    user.setIsDriver(0);
                                    user.setDriverId("");
                                    user.setIsTenantAdmin(0);
                                    user.setTenantId(cropid);
                                    user.setAssessToken("");
                                    user.setRefreshToken("");
                                    user.setTokenRefreshTime(null);
                                    user.setCreateTime(new Date());
                                    user.setEntryWay(1);
                                    userService.insertSelective(user);
                                }
                            }
                        }
                    }

//                    for (int j = 0; j < allUserData.size(); j++) {
//                        if(userService.getUserByUuid(allUserData.get(j).getId().toString())==null){
//                            UserDetailObj userDetail = mtUserClient.getUserDetail(new String[]{allUserData.get(j).getId().toString()},cropid,access_token);
//                            if(userDetail.getMsg().equals("ok")){
//                                String orgId = "";
//                                String orgName = "";
//                                //查询部门
//                                if(userDetail.getData().get(0).getDepts()!=null&&userDetail.getData().get(0).getDepts().size()>0&&userDetail.getData().get(0).getDepts().get(0)!=null){
//                                    Organization organization = organizationService.queryObject(userDetail.getData().get(0).getDepts().get(0).getId());
//                                    if (organization!=null){
//                                        orgId = organization.getId().toString();
//                                        orgName = organization.getText();
//                                    }
//                                }
//                                PasswordEncoder encoder = new BCryptPasswordEncoder();
//                                User user = new User();
//                                user.setUuid(allUserData.get(j).getId().toString());
//                                user.setIsDel(0);
//                                user.setBirthday("");
//                                user.setDegree("");
//                                user.setEducation("");
//                                user.setProvince("");
//                                user.setCity("");
//                                user.setTelephone(userDetail.getData().get(0).getTel());
//                                user.setCellphone(userDetail.getData().get(0).getMobile());
//                                user.setEmail(userDetail.getData().get(0).getEmail());
//                                user.setNation("");
//                                user.setNativePlace("");
//                                user.setNickName("");
//                                user.setOrgId(orgId);
//                                user.setOrgName(orgName);
//                                user.setPost("");
//                                user.setSchool("");
//                                user.setUserGroup("85");
//                                user.setDataAuth("4");
//                                user.setUserName(allUserData.get(j).getName());
//                                user.setUserNameId(allUserData.get(j).getId().toString());
//                                user.setPassword(encoder.encode(allUserData.get(j).getId().toString()));
//                                user.setIdCard("");
//                                user.setAge(1);
//                                user.setSex("");
//                                user.setStatus(1);
//                                user.setPostText("");
//                                user.setOnlineStatus("");
//                                user.setSign("");
//                                user.setAvatar(allUserData.get(j).getAvatar());
//                                user.setCountry("china");
//                                user.setZone("");
//                                user.setOpenid("");
//                                user.setUnionid(userDetail.getData().get(0).getAccountId());
//                                user.setDistrict("");
//                                user.setLinkEmployees("");
//                                user.setLinkEmployeesName("");
//                                user.setUserNameJp("");
//                                user.setLbPost("");
//                                user.setLbPostText("");
//                                user.setGjPost("");
//                                user.setGjPostText("");
//                                user.setLeadFlag("");
//                                user.setLeadFlagText("");
//                                user.setIsDriver(0);
//                                user.setDriverId("");
//                                user.setIsTenantAdmin(0);
//                                user.setTenantId(cropid);
//                                user.setAssessToken("");
//                                user.setRefreshToken("");
//                                user.setTokenRefreshTime(null);
//                                user.setCreateTime(new Date());
//                                userService.insertSelective(user);
//                            }
//                        }
//                    }

                }
            }
        }
        return ReturnT.SUCCESS;
    }
}
