//package cn.ewsd.logistics.controller;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import cn.ewsd.cost.model.SysUserBarMaster;
//import cn.ewsd.cost.service.SysUserBarMasterService;
//import cn.ewsd.logistics.model.*;
//import cn.ewsd.logistics.service.*;
//import cn.ewsd.mdata.model.SysOrganizationMaster;
//import cn.ewsd.mdata.model.User;
//import cn.ewsd.mdata.service.SysOrganizationMasterService;
//import cn.ewsd.mdata.service.UserService;
//import cn.ewsd.system.model.SysAuditProcess;
//import cn.ewsd.system.model.SysUserStep;
//import cn.ewsd.system.service.SysAuditProcessService;
//import cn.ewsd.system.service.SysUserStepService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.stereotype.Controller;
//
//import cn.ewsd.common.annotation.ControllerLog;
//import cn.ewsd.common.controller.BaseController;
//import cn.ewsd.common.utils.BaseUtils;
//import cn.ewsd.common.utils.easyui.PageParam;
//import cn.ewsd.common.utils.easyui.PageSet;
//
//import javax.annotation.Resource;
//
///**
// * 审批日志
// *
// * @Author zxrmine
// * @Email zxrmine@163.cn
// * @Date 2021-03-25 14:10:22
// */
//@Controller
//@RequestMapping("/logistics/auditLog")
//public class AuditLogController extends LogisticsBaseController {
//
//    @Autowired
//    private AuditLogService auditLogService;
//    @Autowired
//    private SysAuditProcessService sysAuditProcessService;
//    @Autowired
//    private HqApartmentApplyService hqApartmentApplyService;
//    @Autowired
//    private HqMeetingService hqMeetingService;
//    @Autowired
//    private HqReserveCarService hqReserveCarService;
//    @Autowired
//    private SysOrganizationMasterService sysOrganizationMasterService;
//    @Autowired
//    private SysUserBarMasterService sysUserBarMasterService;
//    @Autowired
//    private SysUserStepService sysUserStepService;
//    @Resource
//    private UserService userService;
//    @Autowired
//    private HqTruckApplyService hqTruckApplyService;
//
//    //分页页面
//    @RequestMapping("/index")
//    @ControllerLog(description = "打开AuditLog模块管理页面")
//    public String index(@RequestParam Map<String, Object> params) {
//        return "logistics/auditLog/index";
//    }
//
//    @RequestMapping("/indexBanner")
//    @ControllerLog(description = "打开AuditLog模块管理页面")
//    public String indexBanner(@RequestParam Map<String, Object> params) {
//        return "logistics/banner/index";
//    }
//
//
//    //获取分页集
//    @ResponseBody
//    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
//    @ControllerLog(description = "获得AuditLog分页集数据")
//    public Object getPageSet(PageParam pageParam) {
//        String filterSort = "";
//        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
//        PageSet<AuditLog> pageSet = auditLogService.getPageSet(pageParam, filterSort);
//        return pageSet;
//    }
//
//    //获取详情
//    @ResponseBody
//    @RequestMapping(value = "/getDetailByUuid")
//    @ControllerLog(description = "获得AuditLog模块详细数据")
//    public Object getDetailByUuid(String uuid) {
//        AuditLog auditLog = auditLogService.selectByPrimaryKey(uuid);
//        return auditLog;
//    }
//
//    //新增页面
//    @RequestMapping("/add")
//    @ControllerLog(description = "打开AuditLog模块新增页面")
//    public String add() {
//        return "logistics/auditLog/edit";
//    }
//
//    //保存数据
//    @ResponseBody
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ControllerLog(description = "保存AuditLog模块数据")
//    public Object save(@ModelAttribute AuditLog auditLog) {
//        int result = auditLogService.insertSelective(getSaveData(auditLog));
//        return result > 0 ? success("保存成功！") : failure("保存失败！");
//    }
//
//    //编辑页面
//    @RequestMapping("/edit")
//    @ControllerLog(description = "打开AuditLog模块编辑页面")
//    public String edit() {
//        return "logistics/auditLog/edit";
//    }
//
//
//
//    //更新数据
//    @ResponseBody
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @ControllerLog(description = "更新AuditLog模块数据")
//    public Object update(@ModelAttribute AuditLog auditLog) {
//        int result = auditLogService.updateByPrimaryKeySelective(getUpdateData(auditLog));
//        return result > 0 ? success("更新成功！") : failure("更新失败！");
//    }
//
//    //删除数据
//    @ResponseBody
//    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
//    @ControllerLog(description = "删除AuditLog模块数据")
//    public Object deleteBatch(@RequestParam String[] uuid) {
//        int result = auditLogService.executeDeleteBatch(uuid);
//        return result > 0 ? success("删除成功！") : failure("删除失败！");
//    }
//
//    //审批信息列表
//    @RequestMapping("/reviewLog")
//    public String reviewLog(String businessType,String businessId) {
//        List<AuditLog> processLogList = auditLogService.queryListByBusiness(businessType,businessId);
//        request.setAttribute("processLogList", processLogList);
//        return "logistics/auditLog/review_log";
//    }
//
//
//    /**
//     * 审批时间线
//     * @param businessType 业务类型 1
//     * @param businessId 业务ID
//     * @param processNo 流程编号
//     * @return
//     */
//    @RequestMapping("/auditLine")
//    @ControllerLog(description = "打开审批进度")
//    public String auditLine(String businessType,String businessId,String processNo) {
//        List<AuditLine> auditLineList = new ArrayList<>();
//        SysAuditProcess processObj = sysAuditProcessService.queryObjectByProcessNo(processNo);
//        List<SysAuditProcess> processList = sysAuditProcessService.queryListByFuuid(processObj.getUuid());//审批步骤
//        //预定义对象====================================================================
//        HqApartmentApply hqApartmentApply = new HqApartmentApply();
//        HqMeeting hqMeeting = new HqMeeting();
//        HqReserveCar hqReserveCar = new HqReserveCar();
//        HqTruckApply hqTruckApply = new HqTruckApply();
//
//        int allStepNum = processList.size();//步骤数量
//        int nowStepNum = processList.size();//当前在第几个步骤
//        AuditLine auditLine;
//        for (int i = 0; i < processList.size(); i++) {
//            auditLine = new AuditLine();
//            auditLine.setLINE_NAME(processList.get(i).getProcessName()+"["+processList.get(i).getProcessNo()+"]");
//            auditLine.setLINE_SORT(processList.get(i).getProcessSequence());
//            List<AuditLog> auditLogsList = auditLogService.queryListByFS(" audit_process_step = '"+processList.get(i).getUuid()+"' and business_id = '"+businessId+"' order by create_time desc limit 1 ");
//            if(auditLogsList!=null&&auditLogsList.size()>=1){
//                //有数据
//                auditLine.setLINE_STATUS(2);
//                auditLine.setAUDIT_USER_NAME(auditLogsList.get(0).getCreator());
//                auditLine.setAUDIT_USER_NAME_ID(auditLogsList.get(0).getCreatorId());
//                auditLine.setAUDIT_OPINION(auditLogsList.get(0).getOpinion());
//                auditLine.setAUDIT_DATE(sdf.format(auditLogsList.get(0).getCreateTime()));
//                auditLine.setIS_RUNNING(0);
//            }else {
//                auditLine.setLINE_STATUS(1);
//                auditLine.setAUDIT_USER_NAME("");
//                auditLine.setAUDIT_USER_NAME_ID("");
//                auditLine.setAUDIT_OPINION("");
//                auditLine.setAUDIT_DATE("");
//                if(i==0){
//                    auditLine.setIS_RUNNING(1);
//                    nowStepNum = i;
//                }else if(auditLineList.get(i-1).getLINE_STATUS()==2){
//                    auditLine.setIS_RUNNING(1);
//                    nowStepNum = i;
//                }else {
//                    auditLine.setIS_RUNNING(0);
//                }
//                //特殊流程处理==========================================================================
//                String auditPersonStr = "";
//                //公寓申请流程
//                if(processNo.equals("002")){
//                    String FGLD_STEP_ID  = "E1F13F6BE7C24F62B6377D81684E86D3";//分管领导审批步骤
//                    String BMFZR_STEP_ID = "636A370DF0C144499F96469968803353";//部门负责人审批步骤
//                    if(hqApartmentApply == null ||hqApartmentApply.getUuid()==null||hqApartmentApply.getUuid().equals("")){
//                        hqApartmentApply = hqApartmentApplyService.queryObject(businessId);
//                    }
//                    //查询该步骤下的审批人员
//                    if(processList.get(i).getUuid().equals(FGLD_STEP_ID)){
//                        //分管领导步骤;
//                        //1.查询对应部门的分管领导列表
//                        String orgMasterStr = "";
//                        List<SysOrganizationMaster> sysOrganizationMasterList = sysOrganizationMasterService.queryListByFS(" org_id = "+hqApartmentApply.getCreatorOrgId());
//                        for (int j = 0; j < sysOrganizationMasterList.size(); j++) {
//                            if(j == 0){
//                                orgMasterStr = "'"+sysOrganizationMasterList.get(j).getMasterUuid()+"'";
//                            }else {
//                                orgMasterStr += ",'"+sysOrganizationMasterList.get(j).getMasterUuid()+"'";
//                            }
//                        }
//                        //2.查询哪些人员拥有该步骤
//                        String stepUStr = "";
//                        List<SysUserStep> userStepList = sysUserStepService.queryListByStepGroupUser(FGLD_STEP_ID);
//                        for (int j = 0; j < userStepList.size(); j++) {
//                            if(j == 0){
//                                stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                            }else {
//                                stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                            }
//                        }
//                        //3.查询这些人员那个有1中的分管领导权限
//                        List<String> userIdList = new ArrayList<>();
//                        if(!stepUStr.equals("")&&!orgMasterStr.equals("")){
//                            List<SysUserBarMaster> sysUserBarMasterList = sysUserBarMasterService
//                                    .queryListByFS(" user_id in ("+stepUStr+") and master_id in ("+orgMasterStr+") group by user_id ");
//                            for (int j = 0; j < sysUserBarMasterList.size(); j++) {
//                                userIdList.add(sysUserBarMasterList.get(j).getUserId());
//                            }
//                        }
//                        //4.得出人员列表
//                        if(userIdList.size()>0){
//                            List<User> userList = userService.getListByIds(userIdList.toArray(new String[0]));
//                            for (int j = 0; j < userList.size(); j++) {
//                                if(j==0){
//                                    auditPersonStr = userList.get(j).getUserName();
//                                }else {
//                                    auditPersonStr += ","+userList.get(j).getUserName();
//                                }
//                            }
//                        }else {
//                            auditPersonStr = "暂无人员";
//                        }
//                    }else if(processList.get(i).getUuid().equals(BMFZR_STEP_ID)){
//                        //部门负责人步骤
//                        //1.查询有该步骤的用户,且用户组织机构ID = 申请数据ID
//                        String stepUStr = "";
//                        List<SysUserStep> userStepList = sysUserStepService.queryListByStepGroupUser(BMFZR_STEP_ID);
//                        for (int j = 0; j < userStepList.size(); j++) {
//                            if(j == 0){
//                                stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                            }else {
//                                stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                            }
//                        }
//                        if(!stepUStr.equals("")){
//                            List<User> userList = userService
//                                    .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1 and org_id = "+hqApartmentApply.getCreatorOrgId());
//                            for (int j = 0; j < userList.size(); j++) {
//                                if(j==0){
//                                    auditPersonStr = userList.get(j).getUserName();
//                                }else {
//                                    auditPersonStr += ","+userList.get(j).getUserName();
//                                }
//                            }
//                        }else {
//                            auditPersonStr = "暂无人员";
//                        }
//                    }else {
//                        //普通步骤
//                        //1.查询有该步骤的用户,且步骤部门ID=数据部门ID,或步骤部门为空
//                        String stepUStr = "";
//                        List<SysUserStep> userStepList = sysUserStepService.queryListByStepAndOrgGroupUser(processList.get(i).getUuid(),hqApartmentApply.getCreatorOrgId());
//                        for (int j = 0; j < userStepList.size(); j++) {
//                            if(j == 0){
//                                stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                            }else {
//                                stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                            }
//                        }
//                        if(!stepUStr.equals("")){
//                            List<User> userList = userService
//                                    .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1");
//                            for (int j = 0; j < userList.size(); j++) {
//                                if(j==0){
//                                    auditPersonStr = userList.get(j).getUserName();
//                                }else {
//                                    auditPersonStr += ","+userList.get(j).getUserName();
//                                }
//                            }
//                        }else {
//                            auditPersonStr = "暂无人员";
//                        }
//                    }
//                }
//                //会议申请流程
//                else if(processNo.equals("007")){//会议申请流程
//                    String FGLD_STEP_ID = "F347061390964534B684144A8B07DC68";//分管领导审批步骤
//                    String BMFZR_STEP_ID = "9BE926B40A144301BE8F6C521E4080FC";//部门负责人审批步骤
//                    if(hqMeeting == null ||hqMeeting.getUuid()==null||hqMeeting.getUuid().equals("")){
//                        hqMeeting = hqMeetingService.queryObject(businessId);
//                    }
//                    //分管领导步骤
//                    if(processList.get(i).getUuid().equals(FGLD_STEP_ID)){
//                    String orgMasterStr = "";
//                    List<SysOrganizationMaster> sysOrganizationMasterList = sysOrganizationMasterService.queryListByFS(" org_id = "+hqMeeting.getCreatorOrgId());
//                    for (int j = 0; j < sysOrganizationMasterList.size(); j++) {
//                        if(j == 0){
//                            orgMasterStr = "'"+sysOrganizationMasterList.get(j).getMasterUuid()+"'";
//                        }else {
//                            orgMasterStr += ",'"+sysOrganizationMasterList.get(j).getMasterUuid()+"'";
//                        }
//                    }
//                    //2.查询哪些人员拥有该步骤
//                    String stepUStr = "";
//                    List<SysUserStep> userStepList = sysUserStepService.queryListByStepGroupUser(FGLD_STEP_ID);
//                    for (int j = 0; j < userStepList.size(); j++) {
//                        if(j == 0){
//                            stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                        }else {
//                            stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                        }
//                    }
//                    //3.查询这些人员那个有1中的分管领导权限
//                    List<String> userIdList = new ArrayList<>();
//                    if(!stepUStr.equals("")&&!orgMasterStr.equals("")){
//                        List<SysUserBarMaster> sysUserBarMasterList = sysUserBarMasterService
//                                .queryListByFS(" user_id in ("+stepUStr+") and master_id in ("+orgMasterStr+") group by user_id ");
//                        for (int j = 0; j < sysUserBarMasterList.size(); j++) {
//                            userIdList.add(sysUserBarMasterList.get(j).getUserId());
//                        }
//                    }
//                    //4.得出人员列表
//                    if(userIdList.size()>0){
//                        List<User> userList = userService.getListByIds(userIdList.toArray(new String[0]));
//                        for (int j = 0; j < userList.size(); j++) {
//                            if(j==0){
//                                auditPersonStr = userList.get(j).getUserName();
//                            }else {
//                                auditPersonStr += ","+userList.get(j).getUserName();
//                            }
//                        }
//                    }else {
//                        auditPersonStr = "暂无人员";
//                    }
//                }else if(processList.get(i).getUuid().equals(BMFZR_STEP_ID)){
//                    //部门负责人步骤
//                    //1.查询有该步骤的用户,且用户组织机构ID = 申请数据ID
//                    String stepUStr = "";
//                    List<SysUserStep> userStepList = sysUserStepService.queryListByStepGroupUser(BMFZR_STEP_ID);
//                    for (int j = 0; j < userStepList.size(); j++) {
//                        if(j == 0){
//                            stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                        }else {
//                            stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                        }
//                    }
//                    if(!stepUStr.equals("")){
//                        List<User> userList = userService
//                                .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1 and org_id = "+hqMeeting.getCreatorOrgId());
//                        for (int j = 0; j < userList.size(); j++) {
//                            if(j==0){
//                                auditPersonStr = userList.get(j).getUserName();
//                            }else {
//                                auditPersonStr += ","+userList.get(j).getUserName();
//                            }
//                        }
//                    }else {
//                        auditPersonStr = "暂无人员";
//                    }
//                }else {
//                    //普通步骤
//                    //1.查询有该步骤的用户,且步骤部门ID=数据部门ID,或步骤部门为空
//                    String stepUStr = "";
//                    List<SysUserStep> userStepList = sysUserStepService.queryListByStepAndOrgGroupUser(processList.get(i).getUuid(),hqMeeting.getCreatorOrgId());
//                    for (int j = 0; j < userStepList.size(); j++) {
//                        if(j == 0){
//                            stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                        }else {
//                            stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                        }
//                    }
//                    if(!stepUStr.equals("")){
//                        List<User> userList = userService
//                                .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1");
//                        for (int j = 0; j < userList.size(); j++) {
//                            if(j==0){
//                                auditPersonStr = userList.get(j).getUserName();
//                            }else {
//                                auditPersonStr += ","+userList.get(j).getUserName();
//                            }
//                        }
//                    }else {
//                        auditPersonStr = "暂无人员";
//                    }
//                }
//                }
//                //接送申请流程;全普通步骤
//                else if(processNo.equals("004")){
//                    if(hqReserveCar == null ||hqReserveCar.getUuid()==null||hqReserveCar.getUuid().equals("")) {
//                        hqReserveCar = hqReserveCarService.queryObject(businessId);
//                    }
//                    String stepUStr = "";
//                    List<SysUserStep> userStepList = sysUserStepService.queryListByStepAndOrgGroupUser(processList.get(i).getUuid(),hqReserveCar.getCreatorOrgId());
//                    for (int j = 0; j < userStepList.size(); j++) {
//                        if(j == 0){
//                            stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                        }else {
//                            stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                        }
//                    }
//                    if(!stepUStr.equals("")){
//                        List<User> userList = userService
//                                .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1");
//                        for (int j = 0; j < userList.size(); j++) {
//                            if(j==0){
//                                auditPersonStr = userList.get(j).getUserName();
//                            }else {
//                                auditPersonStr += ","+userList.get(j).getUserName();
//                            }
//                        }
//                    }else {
//                        auditPersonStr = "暂无人员";
//                    }
//                }
//                //工程车租赁流程
//                else if(processNo.equals("005")){
//                    String FGLD_STEP_ID  = "11813FEE47F64D0A9073F36E5D2D14D8";//分管领导审批步骤
//                    String BMFZR_STEP_ID = "CB6207048A964A20B15FA6E5869ECB0B";//部门负责人审批步骤
//                    if(hqTruckApply == null ||hqTruckApply.getUuid()==null||hqTruckApply.getUuid().equals("")) {
//                        hqTruckApply = hqTruckApplyService.queryObject(businessId);
//                    }
//                    if(processList.get(i).getUuid().equals(FGLD_STEP_ID)){
//                        //分管领导步骤;
//                        //1.查询对应部门的分管领导列表
//                        String orgMasterStr = "";
//                        List<SysOrganizationMaster> sysOrganizationMasterList = sysOrganizationMasterService.queryListByFS(" org_id = "+hqTruckApply.getCreatorOrgId());
//                        for (int j = 0; j < sysOrganizationMasterList.size(); j++) {
//                            if(j == 0){
//                                orgMasterStr = "'"+sysOrganizationMasterList.get(j).getMasterUuid()+"'";
//                            }else {
//                                orgMasterStr += ",'"+sysOrganizationMasterList.get(j).getMasterUuid()+"'";
//                            }
//                        }
//                        //2.查询哪些人员拥有该步骤
//                        String stepUStr = "";
//                        List<SysUserStep> userStepList = sysUserStepService.queryListByStepGroupUser(FGLD_STEP_ID);
//                        for (int j = 0; j < userStepList.size(); j++) {
//                            if(j == 0){
//                                stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                            }else {
//                                stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                            }
//                        }
//                        //3.查询这些人员那个有1中的分管领导权限
//                        List<String> userIdList = new ArrayList<>();
//                        if(!stepUStr.equals("")&&!orgMasterStr.equals("")){
//                            List<SysUserBarMaster> sysUserBarMasterList = sysUserBarMasterService
//                                    .queryListByFS(" user_id in ("+stepUStr+") and master_id in ("+orgMasterStr+") group by user_id ");
//                            for (int j = 0; j < sysUserBarMasterList.size(); j++) {
//                                userIdList.add(sysUserBarMasterList.get(j).getUserId());
//                            }
//                        }
//                        //4.得出人员列表
//                        if(userIdList.size()>0){
//                            List<User> userList = userService.getListByIds(userIdList.toArray(new String[0]));
//                            for (int j = 0; j < userList.size(); j++) {
//                                if(j==0){
//                                    auditPersonStr = userList.get(j).getUserName();
//                                }else {
//                                    auditPersonStr += ","+userList.get(j).getUserName();
//                                }
//                            }
//                        }else {
//                            auditPersonStr = "暂无人员";
//                        }
//                    }else if(processList.get(i).getUuid().equals(BMFZR_STEP_ID)){
//                        //部门负责人步骤
//                        //1.查询有该步骤的用户,且用户组织机构ID = 申请数据ID
//                        String stepUStr = "";
//                        List<SysUserStep> userStepList = sysUserStepService.queryListByStepGroupUser(BMFZR_STEP_ID);
//                        for (int j = 0; j < userStepList.size(); j++) {
//                            if(j == 0){
//                                stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                            }else {
//                                stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                            }
//                        }
//                        if(!stepUStr.equals("")){
//                            List<User> userList = userService
//                                    .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1 and org_id = "+hqTruckApply.getCreatorOrgId());
//                            for (int j = 0; j < userList.size(); j++) {
//                                if(j==0){
//                                    auditPersonStr = userList.get(j).getUserName();
//                                }else {
//                                    auditPersonStr += ","+userList.get(j).getUserName();
//                                }
//                            }
//                        }else {
//                            auditPersonStr = "暂无人员";
//                        }
//                    }else {
//                        //普通步骤
//                        //1.查询有该步骤的用户,且步骤部门ID=数据部门ID,或步骤部门为空
//                        String stepUStr = "";
//                        List<SysUserStep> userStepList = sysUserStepService.queryListByStepAndOrgGroupUser(processList.get(i).getUuid(),hqTruckApply.getCreatorOrgId());
//                        for (int j = 0; j < userStepList.size(); j++) {
//                            if(j == 0){
//                                stepUStr = "'"+userStepList.get(j).getUserId()+"'";
//                            }else {
//                                stepUStr += ",'"+userStepList.get(j).getUserId()+"'";
//                            }
//                        }
//                        if(!stepUStr.equals("")){
//                            List<User> userList = userService
//                                    .queryListByFS(" uuid in ("+stepUStr+") and is_del != 1");
//                            for (int j = 0; j < userList.size(); j++) {
//                                if(j==0){
//                                    auditPersonStr = userList.get(j).getUserName();
//                                }else {
//                                    auditPersonStr += ","+userList.get(j).getUserName();
//                                }
//                            }
//                        }else {
//                            auditPersonStr = "暂无人员";
//                        }
//                    }
//                }
//                //工程车验收流程
//
//
//
//                auditLine.setAUDIT_USER_NAME(auditPersonStr);
//            }
//            auditLineList.add(auditLine);
//        }
//
//        double wcl = ((double) nowStepNum/allStepNum)*100;
//        String wclStr = String.valueOf(wcl).split("\\.")[0];
//        request.setAttribute("wcl",wclStr+"%");
//        request.setAttribute("auditLineList",auditLineList);
//        return "logistics/auditLog/audit_line";
//    }
//
//
//
//
//
//}
