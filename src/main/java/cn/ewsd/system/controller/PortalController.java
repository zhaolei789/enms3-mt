package cn.ewsd.system.controller;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.cost.model.SysUserLikeMenu;
import cn.ewsd.cost.service.FChargeFeeService;
import cn.ewsd.cost.service.SysUserLikeMenuService;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.material.service.MYearBudgetService;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.UserService;
import cn.ewsd.system.model.Archive;
import cn.ewsd.system.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/system/portal")
public class PortalController extends SystemBaseController {

    @Autowired
    private ArchiveService archivesService;
    @Autowired
    private SysUserLikeMenuService sysUserLikeMenuService;
    @Resource
    private UserService userService;
    @Autowired
    private MYearBudgetService mYearBudgetService;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/index")
    @ControllerLog(description = "打开Portal模块管理页面")
    public String index(@RequestParam Map<String, Object> params) throws Exception {
        request.setAttribute("userInfo", userService.getUserByUuid(LoginInfo.getUuid()));
        System.out.println(LoginInfo.getOrgId()+"=========当前登录用户组织机构=========");
        System.out.println(LoginInfo.getTenantId()+"==============tenantId==============");
        String filterSort = "";
        Map<String,String> portalRes = new HashMap<>();

        request.setAttribute("getDate",sdf.format(new Date()));

        //通知消息10条
        filterSort = " category_id =38 order by create_time desc limit 10;";
        List<Archive> Tzxx10List = archivesService.getListByFilterSort(filterSort);
        request.setAttribute("Tzxx10List",Tzxx10List);
        //操作帮助5
//        filterSort = " category_id =39 order by create_time desc limit 5;";
//        List<Archive> Czbz5List = archivesService.getListByFilterSort(filterSort);
//        request.setAttribute("Czbz5List",Czbz5List);
        //用户喜欢功能
        List<SysUserLikeMenu> userLikeMenuList = sysUserLikeMenuService.queryListByUser(LoginInfo.getUuid());
        request.setAttribute("userLikeMenuList",userLikeMenuList);

        //消耗概况
        PageParam defaultPageParam = new PageParam();
        defaultPageParam.setPage(1);
        defaultPageParam.setRows(100);
        String yearQry = XDate.getYear();
        String month1 = XDate.getMonth();
        String month2 = XDate.getMonth();
        String teamNo = LoginInfo.getOrgId();//"13";
//        List<FChargeFee> fChargeFeeList = fChargeFeeService.getCostAnalysisDetailList(yearQry, month1, month2, teamNo);
//        request.setAttribute("fChargeFeeList",fChargeFeeList);
        //材料费用分析
        Double yearBud = mYearBudgetService.getDeptYearBudget(yearQry, teamNo);
        yearBud = yearBud==null ? 0 : yearBud;
        Double yearOcc = mYearBudgetService.getDeptYearOccBudget(yearQry, month2, teamNo);
        yearOcc = yearOcc==null ? 0 : yearOcc;
        Double yearOut = mYearBudgetService.getDeptYearOutBudget(yearQry, month2, teamNo);
        yearOut = yearOut==null ? 0 : yearOut;
        Double yearAnalysis = yearBud==0 ? 0 : yearOut / yearBud * 100;
        Double budBala = mYearBudgetService.getDeptMonthBudget(yearQry, month1, month2, teamNo);
        budBala = budBala==null ? 0 : budBala;
        Double occBala = mYearBudgetService.getDeptMonthOccBudget(month1, month2, teamNo);
        occBala = occBala==null ? 0 : occBala;
        Double economize = occBala - budBala;
        Double monthAnalysis = budBala==0 ? 0 : occBala / budBala * 100;
        Double planSum = mYearBudgetService.getDeptPlanSum(month1, month2, teamNo);
        planSum = planSum==null ? 0 : planSum;
        Double addPlan = mYearBudgetService.getDeptAddPlan(month1, month2, teamNo);
        addPlan = addPlan==null ? 0 : addPlan;
        Double noOk = mYearBudgetService.getDeptNoOk(month1, month2, teamNo);
        noOk = noOk==null ? 0 : noOk;
        HashMap<String, Object> clfyfx_map = new HashMap();
        clfyfx_map.put("yearBud", Data.normalToFinal(Data.trimDoubleNo0(yearBud / 10000, 2)));
        clfyfx_map.put("yearOcc", Data.normalToFinal(Data.trimDoubleNo0(yearOcc / 10000, 2)));
        clfyfx_map.put("yearOut", Data.normalToFinal(Data.trimDoubleNo0(yearOut / 10000, 2)));
        clfyfx_map.put("yearAnalysis", Data.trimDoubleNo0(yearAnalysis, 2));
        clfyfx_map.put("budBala", Data.normalToFinal(Data.trimDoubleNo0(budBala / 10000, 2)));
        clfyfx_map.put("occBala", Data.normalToFinal(Data.trimDoubleNo0(occBala / 10000, 2)));
        clfyfx_map.put("economize", Data.normalToFinal(Data.trimDoubleNo0(economize, 2)));
        clfyfx_map.put("monthAnalysis", Data.trimDoubleNo0(monthAnalysis, 2));
        clfyfx_map.put("planSum", Data.normalToFinal(Data.trimDoubleNo0(planSum, 2)));
        clfyfx_map.put("addPlan", Data.normalToFinal(Data.trimDoubleNo0(addPlan, 2)));
        clfyfx_map.put("noOk", Data.normalToFinal(Data.trimDoubleNo0(noOk, 2)));
        request.setAttribute("clfyfxMap",clfyfx_map);

        return "system/portal/index";
    }

    @RequestMapping("/tag")
    @ControllerLog(description = "打开Portal模块管理页面")
    public String tag(@RequestParam Map<String, Object> params) {
        List<User> users = new ArrayList<User>();
        User user1 = new User();
        user1.setStatus(1);
        users.add(user1);
        User user2 = new User();
        user2.setStatus(0);
        users.add(user2);
        request.setAttribute("users", users);

        return "system/portal/tag";
    }

    //  @ResponseBody
    //  @RequestMapping("/getAuditNotReadNumAllv2")
    //  @ControllerLog(description = "打开Portal模块管理页面")
    //  public Object getAuditNotReadNumAllv2(String userUuid) throws Exception {
//        User user = userService.getUserByUuid(userUuid);
//        UserInfo userInfo = new UserInfo();
//        userInfo.setOrgId(user.getOrgId());
//        userInfo.setUserName(user.getUserName());
//        userInfo.setUuid(user.getUuid());
//        userInfo.setRoleId("");
//        int notReadNum = 0;
//        List<AuditMessage> auditMessageList = mPlanService.getPendingRecords(userInfo);//物资模块
//        auditMessageList.add(hqMeetingService.getAuditMsg(userInfo));//会议审批
//        auditMessageList.add(hqApartmentApplyService.getAuditMsg(userInfo,"2"));//招待所审批
//        auditMessageList.add(hqApartmentApplyService.getAuditMsg(userInfo,"3"));//其它房间审批
//        auditMessageList.add(hqReserveCarService.getAuditMsg(userInfo));//接送审批
//        for (int i = 0; i < auditMessageList.size(); i++) {
//            notReadNum += auditMessageList.get(i).getMessageNum();
//        }
//        HashMap<String, Object> stringObjectHashMap = getAppSuccessRes();
//        stringObjectHashMap.put("data",notReadNum);
//        return stringObjectHashMap;
        //  }

    //   @ResponseBody
    //   @RequestMapping("/getAuditNotReadListv2")
    //   @ControllerLog(description = "打开Portal模块管理页面")
//    public Object getAuditNotReadListv2(String userUuid) throws Exception {
//        User user = userService.getUserByUuid(userUuid);
//        UserInfo userInfo = new UserInfo();
//        userInfo.setOrgId(user.getOrgId());
//        userInfo.setUserName(user.getUserName());
//        userInfo.setUuid(user.getUuid());
//        userInfo.setRoleId("");
//        List<AuditMessageApp> appAuditMessageList = new ArrayList<>();
//        AuditMessageApp auditMessageApp;
//        List<AuditMessage> auditMessageList = mPlanService.getPendingRecords(userInfo);//物资模块
//        auditMessageList.add(hqMeetingService.getAuditMsg(userInfo));//会议审批
//        auditMessageList.add(hqApartmentApplyService.getAuditMsg(userInfo,"2"));//招待所审批
//        auditMessageList.add(hqApartmentApplyService.getAuditMsg(userInfo,"3"));//其它房间审批
//        auditMessageList.add(hqReserveCarService.getAuditMsg(userInfo));//接送审批
//        Collections.sort(auditMessageList);
//        HashMap<String, Object> stringObjectHashMap = getAppSuccessRes();
//        for (int i = 0; i < auditMessageList.size(); i++) {
//            auditMessageApp = new AuditMessageApp();
//            auditMessageApp.setMessageId(auditMessageList.get(i).getMessageId());
//            auditMessageApp.setMessageName(auditMessageList.get(i).getMessageName());
//            auditMessageApp.setMessageType(auditMessageList.get(i).getMessageType());
//            auditMessageApp.setMessageNum(auditMessageList.get(i).getMessageNum());
//            auditMessageApp.setMessageSort(auditMessageList.get(i).getMessageSort());
//            auditMessageApp.setJumpPageUrl(auditMessageList.get(i).getJumpPageUrl());
//            auditMessageApp.setJumpPageName(auditMessageList.get(i).getJumpPageName());
//            auditMessageApp.setBusinessId(auditMessageList.get(i).getBusinessId());
//            auditMessageApp.setParam1("");
//            auditMessageApp.setParam2("");
//            if(auditMessageList.get(i).getJumpPageUrl()!=null&&!"".equals(auditMessageList.get(i).getJumpPageUrl())){
//                List<String> urlParam = geturlvalue(auditMessageList.get(i).getJumpPageUrl());
//                if(urlParam!=null&&urlParam.size()>0){
//                    auditMessageApp.setParam1(urlParam.get(0));
//                }
//                if(urlParam!=null&&urlParam.size()>1){
//                    auditMessageApp.setParam2(urlParam.get(1));
//                }
//            }
//            appAuditMessageList.add(auditMessageApp);
//        }
//        stringObjectHashMap.put("data",appAuditMessageList);
//        return stringObjectHashMap;
//    }

    private static List<String> geturlvalue(String url){
        String regEx="(\\?|&+)(.+?)=([^&]*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        List<String> resList = new ArrayList<String>();
        while(m.find()) {
            resList.add(m.group(3));
        }
        return resList;
    }

}
