//package cn.ewsd.cost.controller;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.util.*;
//
//import cn.ewsd.base.bean.PageData;
//import cn.ewsd.base.utils.*;
//import cn.ewsd.base.utils.jwt.LoginInfo;
//import cn.ewsd.cost.model.BarMaster;
//import cn.ewsd.cost.model.FAwardAssign;
//import cn.ewsd.cost.model.TSign;
//import cn.ewsd.cost.service.BarMasterService;
//import cn.ewsd.cost.service.FAwardAssignService;
//import cn.ewsd.cost.service.TSignService;
//import cn.ewsd.cost.util.ExcelStyle;
//import cn.ewsd.logistics.model.HqLinkPerson;
//import cn.ewsd.logistics.service.AuditLogService;
//import cn.ewsd.mdata.model.User;
//import cn.ewsd.mdata.service.UserService;
//import cn.ewsd.system.model.SysAuditProcess;
//import cn.ewsd.system.service.RedissonService;
//import cn.ewsd.system.service.SysAuditProcessService;
//import cn.ewsd.system.service.SysUserStepService;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
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
//import cn.ewsd.cost.model.FAward;
//import cn.ewsd.cost.service.FAwardService;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * ????????????
// *
// * @Author zxrmine
// * @Email zxrmine@163.cn
// * @Date 2021-08-26 18:03:27
// */
//@Controller
//@RequestMapping("/cost/fAward")
//public class FAwardController extends CostBaseController {
//    String processNo1 = "3013";//??????????????????
//    Integer Khy = 2641;//?????????
//    Integer Bsy = 2642;//?????????
//
//    @Autowired
//    private FAwardService fAwardService;
//    @Autowired
//    private FAwardAssignService fAwardAssignService;
//    @Autowired
//    private TSignService tSignService;
//    @Autowired
//    private BarMasterService barMasterService;
//    @Resource
//    private UserService userService;
//
//    //????????????
//    @Autowired
//    private SysAuditProcessService sysAuditProcessService;
//    @Autowired
//    private SysUserStepService sysUserStepService;
//    @Autowired
//    private AuditLogService auditLogService;
//
//    //????????????
//    @RequestMapping("/index")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String index(@RequestParam Map<String, Object> params) {
//        return "cost/fAward/index";
//    }
//
//    //??????
//    @RequestMapping("/indexSelect")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String indexSelect(@RequestParam Map<String, Object> params) {
//        return "cost/fAward/index_select";
//    }
//
//    //??????
//    @RequestMapping("/indexFp")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String indexFp(@RequestParam Map<String, Object> params) {
//        return "cost/fAward/index_fp";
//    }
//
//    //????????????
//    @RequestMapping("/indexCS")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String indexCS(@RequestParam Map<String, Object> params) {
//        return "cost/fAward/index_cs";
//    }
//
//    //????????????
//    @RequestMapping("/indexTz")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String indexTz(@RequestParam Map<String, Object> params) {
//        return "cost/fAward/index_tz";
//    }
//
//    //????????????
//    @RequestMapping("/indexHqAudit")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String indexHqAudit(@RequestParam Map<String, Object> params) {
//        return "cost/fAward/index_hq_audit";
//    }
//
//    //???????????????
//    @ResponseBody
//    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward???????????????")
//    public Object getPageSet(PageParam pageParam) throws Exception {
//        String filterSort = " and f.is_del !=1 and f.upd_status = '0'";
//        //????????????
//        String query_teamNo = request.getParameter("query_teamNo");
//        if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
//            filterSort += " and f.team_no = "+query_teamNo;
//        }
//        String query_assessCate = request.getParameter("query_assessCate");
//        if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
//            filterSort += " and f.assess_cate = '"+query_assessCate+"'";
//        }
//        String query_modiStart = request.getParameter("query_modiStart");
//        if(query_modiStart!=null&&!"".equals(query_modiStart)&&!"undefined".equals(query_modiStart)){
//            filterSort += " and f.modi_date >= '"+query_modiStart+"'";
//        }
//        String query_modiEnd = request.getParameter("query_modiEnd");
//        if(query_modiEnd!=null&&!"".equals(query_modiEnd)&&!"undefined".equals(query_modiEnd)){
//            filterSort += " and f.modi_date <= '"+query_modiEnd+"'";
//        }
//        String query_assessTeam = request.getParameter("query_assessTeam");
//        if(query_assessTeam!=null&&!"".equals(query_assessTeam)&&!"undefined".equals(query_assessTeam)){
//            filterSort += " and f.assess_team = "+query_assessTeam;
//        }
//        String query_modiTeam = request.getParameter("query_modiTeam");
//        if(query_modiTeam!=null&&!"".equals(query_modiTeam)&&!"undefined".equals(query_modiTeam)){
//            filterSort += " and f.modi_team = "+query_modiTeam;
//        }
//        String query_status = request.getParameter("query_status");
//        if(query_status!=null&&!"".equals(query_status)&&!"undefined".equals(query_status)){
//            filterSort += " and f.status = '"+query_status+"'";
//        }
//        String query_noticeNo = request.getParameter("query_noticeNo");
//        if(query_noticeNo!=null&&!"".equals(query_noticeNo)&&!"undefined".equals(query_noticeNo)){
//            filterSort += " and f.notice_no like '%"+query_noticeNo+"%'";
//        }
//        String query_signEmp = request.getParameter("query_signEmp");
//        if(query_signEmp!=null&&!"".equals(query_signEmp)&&!"undefined".equals(query_signEmp)){
//            filterSort += " and f.sign_emp like '%"+query_signEmp+"%'";
//        }
//        String query_reason = request.getParameter("query_reason");
//        if(query_reason!=null&&!"".equals(query_reason)&&!"undefined".equals(query_reason)){
//            filterSort += " and f.reason like '%"+query_reason+"%'";
//        }
//        String query_person = request.getParameter("query_person");
//
//        //????????????
//        String Type = request.getParameter("Type");
//        if(Type!=null&&Type.equals("SELECT")){
//            String QryOrgStr = this.getUserQryOrgStr();
//            Boolean ifKhy = checkHaveAccess(Khy);//????????????
//            Boolean ifBsy = checkHaveAccess(Bsy);//????????????
//            if(ifKhy){
//                filterSort += " and (f.modi_team="+ LoginInfo.getOrgId()+" OR f.team_no="+LoginInfo.getOrgId()+" OR f.assess_team="+LoginInfo.getOrgId()+")";
//                //filterSort += " and (f.modi_team="+LoginInfo.getOrgId()+" OR f.team_no in "+QryOrgStr+")";;
//            }else if(ifBsy){
//                filterSort += " AND f.team_no="+LoginInfo.getOrgId();
//                //filterSort += " AND f.team_no in "+QryOrgStr;
//            }else{
//                //filterSort += " and f.modi_team = " + LoginInfo.getOrgId();
//                filterSort += " AND f.team_no in "+QryOrgStr;
//            }
//            filterSort += " and f.status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
//            if(query_person!=null&&!"".equals(query_person)&&!"undefined".equals(query_person)){
//                filterSort += " and a.emp_name like '%"+query_person+"%'";
//                filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
//                PageSet<FAward> pageSet = fAwardService.getPageSetJoinAssign(pageParam, filterSort.replace("ORDER BY ","ORDER BY f."));
//                pageSet.setRows((List<FAward>)covDataListDic(pageSet.getRows(),"assessCate#f.awardType"));
//                return pageSet;
//            }
//        }else {
//            //?????????
//            //?????????????????????????????????
//            filterSort += " and f.modi_team = " + LoginInfo.getOrgId();
//        }
//        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
//        PageSet<FAward> pageSet = fAwardService.getPageSet(pageParam, filterSort.replace("ORDER BY ","ORDER BY f."));
//        pageSet.setRows((List<FAward>)covDataListDic(pageSet.getRows(),"assessCate#f.awardType"));
//        return pageSet;
//    }
//
//    //??????????????????
//    @ResponseBody
//    @RequestMapping(value = "/getPageSetFp", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward???????????????")
//    public Object getPageSetFp(PageParam pageParam) throws Exception {
//        String filterSort = " and f.is_del !=1 and f.upd_status = '0'";
//        String Type = request.getParameter("Type");
//        //????????????
//        if(Type!=null&&Type.equals("TIMEOUT")){
//            filterSort += " and f.assign_date < '"+sdfd2.format(new Date())+"'";
//            filterSort += " and f.status = 'DCCC14FCAEFC45F9A0CC1F5E56EE19F1'";
//        }else{
//            //????????????(??????)
//            /*filterSort += " and(";
//            String userStepStr = sysUserStepService.selectUserStepSqlByProcessNo(processNo1, LoginInfo.getUuid());//?????????????????????????????????
//            if(userStepStr!=null&&!"".equals(userStepStr)){
//                String [] userStepArray = userStepStr.split(",");
//                for (int i = 0; i < userStepArray.length; i++) {
//                    String uStep = userStepArray[i].substring(2,userStepArray[i].length()-2);
//                    String orgPmsStr = sysUserStepService.selectUserPmsOrg(uStep,LoginInfo.getUuid());//?????????????????????????????????
//                    if(orgPmsStr == null ||"".equals(orgPmsStr)){continue;}
//                    filterSort += " (f.status = '"+uStep+"'"+" and f.team_no in ("+orgPmsStr+")) or";
//                }
//            }
//            filterSort += " (1 = 2))";*/
//            //?????????????????????????????????????????????
//            filterSort += " and f.team_no = "+LoginInfo.getOrgId();
//            filterSort += " and f.status = 'DCCC14FCAEFC45F9A0CC1F5E56EE19F1'";
//            //????????????(??????)
//            filterSort += " and f.assign_date >= '"+sdfd2.format(new Date())+"'";
//        }
//
//        //????????????
//        String query_teamNo = request.getParameter("query_teamNo");
//        if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
//            filterSort += " and f.team_no = "+query_teamNo;
//        }
//        String query_assessCate = request.getParameter("query_assessCate");
//        if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
//            filterSort += " and f.assess_cate = '"+query_assessCate+"'";
//        }
//
//        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
//        PageSet<FAward> pageSet = fAwardService.getPageSet(pageParam, filterSort);
//        pageSet.setRows((List<FAward>)covDataListDic(pageSet.getRows(),"assessCate#f.awardType"));
//        return pageSet;
//    }
//
//    //??????????????????
//    @ResponseBody
//    @RequestMapping(value = "/getPageSetTz", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward???????????????")
//    public Object getPageSetTz(PageParam pageParam) throws Exception {
//        String filterSort = " and is_del !=1 and (upd_status != '0' or del_status != '0')";
//        //????????????
//        String query_teamNo = request.getParameter("query_teamNo");
//        if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
//            filterSort += " and team_no = "+query_teamNo;
//        }
//        String query_assessCate = request.getParameter("query_assessCate");
//        if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
//            filterSort += " and assess_cate = '"+query_assessCate+"'";
//        }
//        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
//        PageSet<FAward> pageSet = fAwardService.getPageSet(pageParam, filterSort);
//        pageSet.setRows((List<FAward>)covDataListDic(pageSet.getRows(),"assessCate#f.awardType"));
//        return pageSet;
//    }
//
//    /**
//     * ????????????
//     * @param pageParam
//     * @return
//     * @throws Exception
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getPageSetHqAudit", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward???????????????")
//    public Object getPageSetHqAudit(PageParam pageParam) throws Exception {
//        String filterSort = " and f.is_del !=1 and s.result = 0";
//
//        //????????????
//        String pmsMaster="(";
//        String checkSql = " select * from sys_user_bar_master where user_id = '"+LoginInfo.getUuid()+"'";
//        List<PageData> checkObjList = DbUtil.executeQueryList(checkSql);
//        if(checkObjList!=null){
//            for (int i = 0; i < checkObjList.size(); i++) {
//                if(i==0){
//                    pmsMaster += "'"+checkObjList.get(i).getString("master_id")+"'";
//                }else{
//                    pmsMaster += ",'"+checkObjList.get(i).getString("master_id")+"'";
//                }
//            }
//        }
//        pmsMaster += ")";
//        filterSort += " and s.user_id in"+pmsMaster;
//
//
//
//        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
//        PageSet<TSign> pageSet = tSignService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY s.create_time"));
//        pageSet.setRows((List<TSign>)covDataListDic(pageSet.getRows(),"assessCate#f.awardType"));
//        return pageSet;
//    }
//
//    //????????????
//    @ResponseBody
//    @RequestMapping(value = "/getDetailByUuid")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public Object getDetailByUuid(String uuid) throws ParseException {
//        FAward fAward = fAwardService.queryObject(uuid);
//        String occDate = sdfd2.format(sdfd.parse(fAward.getOccDate()));
//        fAward.setOccDate(occDate);
//        return fAward;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/getDetailByUuidE")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public Object getDetailByUuidE(String uuid) throws ParseException {
//        FAward fAward = fAwardService.queryObject(uuid);
//        return fAward;
//    }
//
//    //????????????
//    @RequestMapping("/add")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String add() {
//        return "cost/fAward/edit";
//    }
//
//    //????????????
//    @ResponseBody
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object save(@ModelAttribute FAward fAward) throws Exception {
//        String sql = " SELECT sub_key FROM t_dept_type WHERE dept_id=" + fAward.getTeamNo()+ " AND dict_key= 'deptAttr.assType'";
//        PageData deptTypeObj = new PageData();
//        try {
//            deptTypeObj = DbUtil.executeQueryObject(sql);
//        }catch (Exception e){
//            return failure("??????????????????????????????????????????????????????????????????");
//        }
//        if(deptTypeObj==null||deptTypeObj.get("sub_key")==null){
//            return failure("??????????????????????????????????????????????????????????????????");
//        }
//        String deptType = deptTypeObj.getString("sub_key");
//        long awardId = Snow.getUUID();
//        String uuid = BaseUtils.UUIDGenerator();
//        fAward.setUuid(uuid);
//        fAward.setAwardId(awardId);
//
//        //??????????????????????????????
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.add(Calendar.DATE, +1);
//        if(sdfd2.parse(fAward.getOccDate()).getTime()>=calendar.getTime().getTime()){
//            return failure("??????????????????????????????");
//        }
//
//        fAward.setOccDate(fAward.getOccDate());
//        fAward.setAssMonth(fAward.getOccDate().replace("-","").substring(0,6));
//        fAward.setModiDate(sdfd2.format(new Date()));
//        fAward.setModiUser(LoginInfo.getUserNameId());
//        fAward.setModiUserName(LoginInfo.getUserName());
//
//        Calendar calendar_2 = Calendar.getInstance();
//        Date date = new Date(System.currentTimeMillis());
//        calendar_2.setTime(date);
//        calendar_2.add(Calendar.DATE, +3);
//
//        fAward.setAssignDate(sdfd2.format(calendar_2.getTime()));
//        fAward.setAssignUser("");
//        fAward.setAssignUserName("");
//        fAward.setStatus(sysAuditProcessService.getFirstStepNoByProcessNo(processNo1));
//        fAward.setCheckNo(null);
//        fAward.setIsDel(0);
//        fAward.setIsExecute(1);
//        fAward.setDeptType(deptType);
//        fAward.setModiTeam(LoginInfo.getOrgId());
//        fAward.setUpdStatus("0");
//        fAward.setDelStatus("0");
//        fAward.setNoticeNo(fAward.getNoticeNo().trim());
//
//        //????????????
//        String userIds = request.getParameter("userIds");
//        if(userIds!=null&&!"".equals(userIds)&&!"undefined".equals(userIds)){
//         String [] userAddIdArray = userIds.split(",");
//         if(userAddIdArray.length>0){
//             for (int i = 0; i < userAddIdArray.length; i++) {
//
//                 String empId = request.getParameter("empId_"+userAddIdArray[i]);
//                 String empName = request.getParameter("empName_"+userAddIdArray[i]);
//                 String awardBala = request.getParameter("awardBala_"+userAddIdArray[i]);
//                 if(empId==null||empId.equals("undefined")||empId.equals("")){continue;}
//                 if(empName==null||empName.equals("undefined")||empName.equals("")){continue;}
//                 if(awardBala==null||awardBala.equals("undefined")||awardBala.equals("")){continue;}
//                 User user = userService.getUserByUserNameId(empId);
//                 if(user == null){continue;}
//                 FAwardAssign fAwardAssign = new FAwardAssign();
//                 fAwardAssign.setAwardId(uuid);
//                 fAwardAssign.setEmpName(empName);
//                 fAwardAssign.setUserName(empName);
//                 fAwardAssign.setEmpId(empId);
//                 fAwardAssign.setAwardBala(new BigDecimal(awardBala));
//                 fAwardAssign.setRemark("");
//                 fAwardAssign.setAssignId(Snow.getUUID());
//                 fAwardAssign.setAssignType("f.assignType.3");
//                 fAwardAssign.setModiDate(sdfd2.format(new Date()));
//                 fAwardAssign.setModiUser(LoginInfo.getUserNameId());
//                 fAwardAssign.setUserName(LoginInfo.getUserName());
//                 fAwardAssign.setIsDel(0);
//                 fAwardAssign.setLeadFlag(user.getLeadFlag());
//                 fAwardAssignService.insertSelective(getSaveData(fAwardAssign));
//             }
//         }
//        }
//        int result = fAwardService.insertSelective(getSaveData(fAward));
//        return result > 0 ? success("???????????????") : failure("???????????????");
//    }
//
//    //????????????
//    @RequestMapping("/edit")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String edit() {
//        return "cost/fAward/edit";
//    }
//
//    @RequestMapping("/editEdit")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String editEdit() {
//        return "cost/fAward/edit_edit";
//    }
//
//    @RequestMapping("/editTz")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String editTz() {
//        return "cost/fAward/edit_tz";
//    }
//
//    //????????????????????????1
//    @RequestMapping("/editAudit1")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String editAudit1() throws ParseException {
//        String uuid = request.getParameter("uuid");
//        //????????????
//        FAward fAward = fAwardService.queryObject2(uuid);
//        String occDate = sdfd2.format(sdfd.parse(fAward.getOccDate()));
//        fAward.setOccDate(occDate);
//        request.setAttribute("fAward",fAward);
//        //????????????????????????
//        List<FAwardAssign> assignList = fAwardAssignService.queryListByFilterSort(" award_id='"+fAward.getUuid()+"'");
//        request.setAttribute("assignList",assignList);
//
//        if(fAward.getDelStatus().equals("0")) {
//            //????????????
//            FAward fAward2 = fAwardService.queryObject2(fAward.getUpdReason());
//            fAward2.setOccDate(sdfd2.format(sdfd.parse(fAward2.getOccDate())));
//            request.setAttribute("fAward2", fAward2);
//            //????????????????????????
//            List<FAwardAssign> assignList2 = fAwardAssignService.queryListByFilterSort(" award_id='" + fAward2.getUuid() + "'");
//            request.setAttribute("assignList2", assignList2);
//        }
//        return "cost/fAward/edit_audit1";
//    }
//
//    @RequestMapping("/editAudit2")
//    @ControllerLog(description = "??????FAward??????????????????")
//    public String editAudit2() throws ParseException {
//        String uuid = request.getParameter("uuid");
//        TSign tsign = tSignService.queryObject(uuid);
//
//        //????????????
//        FAward fAward = fAwardService.queryObject2(tsign.getBillId());
//        String occDate = sdfd2.format(sdfd.parse(fAward.getOccDate()));
//        fAward.setOccDate(occDate);
//        request.setAttribute("fAward",fAward);
//        //????????????????????????
//        List<FAwardAssign> assignList = fAwardAssignService.queryListByFilterSort(" award_id='"+fAward.getUuid()+"'");
//        request.setAttribute("assignList",assignList);
//        if(fAward.getDelStatus().equals("0")) {
//            //????????????
//            FAward fAward2 = fAwardService.queryObject2(fAward.getUpdReason());
//            fAward2.setOccDate(sdfd2.format(sdfd.parse(fAward2.getOccDate())));
//            request.setAttribute("fAward2", fAward2);
//            //????????????????????????
//            List<FAwardAssign> assignList2 = fAwardAssignService.queryListByFilterSort(" award_id='" + fAward2.getUuid() + "'");
//            request.setAttribute("assignList2", assignList2);
//        }
//        return "cost/fAward/edit_audit2";
//    }
//
//
//    //????????????
//    @ResponseBody
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object update(@ModelAttribute FAward fAward) throws ParseException {
//        fAward.setAssMonth(fAward.getOccDate().replace("-","").substring(0,6));
//        //
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.add(Calendar.DATE, +1);
//        if(sdfd2.parse(fAward.getOccDate()).getTime()>=calendar.getTime().getTime()){
//            return failure("??????????????????????????????");
//        }
//        int result = fAwardService.updateByPrimaryKeySelective(getUpdateData(fAward));
//        return result > 0 ? success("???????????????") : failure("???????????????");
//    }
//
//    //????????????????????????
//    @ResponseBody
//    @RequestMapping(value = "/auditSave", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object auditSave() {
//        int result = 0;
//        String uuid = request.getParameter("uuid");
//        String [] HqLeader = request.getParameterValues("HqLeader");
//        String Type = request.getParameter("Type");//1?????? 2??????
//
//        FAward fAward = fAwardService.queryObject(uuid);//????????????
//        //????????????
//        if(fAward.getUpdStatus().equals("0")){
//            if(Type.equals("2")){
//                //????????????
//                FAward fAward_tmp2 = new FAward();
//                fAward_tmp2.setUuid(uuid);
//                fAward_tmp2.setDelStatus("0");
//                fAward_tmp2.setDelReason(XDate.getDate()+"????????????????????????");
//                result += fAwardService.executeUpdate2(fAward_tmp2);
//            }else if(Type.equals("1")){
//                FAward fAward_tmp2 = new FAward();
//                fAward_tmp2.setUuid(uuid);
//                fAward_tmp2.setIsDel(1);
//                fAward_tmp2.setDelReason(XDate.getDate()+"???????????????");
//                result += fAwardService.executeUpdate2(fAward_tmp2);
//            }
//        }
//        else {//????????????
//            if(!fAward.getUpdStatus().equals("1")){
//                return failure("?????????????????????");
//            }
//            //????????????
//            String oldUuid = fAward.getUpdReason();
//            if(Type.equals("2")){
//                FAward fAward_tmp2 = new FAward();
//                fAward_tmp2.setUuid(oldUuid);
//                fAward_tmp2.setDelReason(XDate.getDate()+"??????????????????");
//                result += fAwardService.executeUpdate2(fAward_tmp2);
//                result += fAwardService.executeDeleteBatch(new String[]{uuid});//??????????????????
//            }else if(Type.equals("1")){//????????????
//                if (HqLeader == null || HqLeader.length < 1) {
//                    //??????????????????
//                    //????????????????????????????????????????????????
//                    fAwardService.executeDeleteBatch(new String[]{oldUuid});
//                    FAward fAward_tmp2 = new FAward();
//                    fAward_tmp2.setUuid(uuid);
//                    fAward_tmp2.setUpdStatus("0");
//                    fAward_tmp2.setDelReason(XDate.getDate()+"???????????????");
//                    result += fAwardService.executeUpdate2(fAward_tmp2);
//                } else {
//                    //??????????????????
//                    FAward fAward_tmp2 = new FAward();
//                    fAward_tmp2.setUuid(uuid);
//                    fAward_tmp2.setUpdStatus("2");
//                    fAward_tmp2.setDelReason(XDate.getDate()+"??????????????????");
//                    result += fAwardService.executeUpdate2(fAward_tmp2);
//                    for (int i = 0; i < HqLeader.length; i++) {
//                        BarMaster barMaster = barMasterService.queryObject(HqLeader[i]);
//                        if(barMaster != null) {
//                            TSign tSign = new TSign();
//                            tSign.setBillId(uuid);
//                            tSign.setUserId(HqLeader[i]);
//                            tSign.setUserName(barMaster.getUserName());
//                            tSign.setCheckId(uuid);
//                            tSign.setCheckDate("");
//                            tSign.setCheckTime("");
//                            tSign.setResult("0");
//                            tSign.setRemark("");
//                            tSign.setStepKey("1");
//                            tSign.setStepCode("1");
//                            result += tSignService.insertSelective(getSaveData(tSign));
//                        }
//                    }
//                }
//            }
//        }
//        return result > 0 ? success("???????????????") : failure("???????????????");
//    }
//
//    //??????????????????
//    @ResponseBody
//    @RequestMapping(value = "/auditHqSave", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object auditHqSave() throws Exception {
//        int result = 0;
//        String uuid = request.getParameter("uuid");//?????????ID
//        String Type = request.getParameter("Type");//1?????? 2??????
//
//        TSign tsign = tSignService.queryObject(uuid);
//        FAward fAward = fAwardService.queryObject(tsign.getBillId());//????????????
//        if(Type.equals("1")){
//            String checkSql = "select * from t_sign where bill_id = '"+tsign.getBillId()+"' and result = 0";
//            List<PageData> noAuditList = DbUtil.executeQueryList(checkSql);
//            //????????????
//            String sql = " update t_sign set result = 1 where uuid = '"+uuid+"'";
//            result += DbUtil.executeUpdateKR2(sql);
//            //????????????????????????????????????
//            if(noAuditList!=null&&noAuditList.size()<=1){
//                //????????????????????????????????????????????????
//                fAwardService.executeDeleteBatch(new String[]{fAward.getUpdReason()});
//                FAward fAward_tmp2 = new FAward();
//                fAward_tmp2.setUuid(fAward.getUuid());
//                fAward_tmp2.setUpdStatus("0");
//                fAward_tmp2.setDelReason(XDate.getDate()+"???????????????");
//                result += fAwardService.executeUpdate2(fAward_tmp2);
//            }
//        }else if(Type.equals("2")){
//            //??????
//            String sql = " update t_sign set result = 2 where uuid = '"+uuid+"'";
//            DbUtil.executeUpdateKR2(sql);
//
//            FAward fAward_tmp2 = new FAward();
//            fAward_tmp2.setUuid(fAward.getUpdReason());
//            fAward_tmp2.setDelReason(XDate.getDate()+"??????????????????,???????????????"+tsign.getUserName());
//            result += fAwardService.executeUpdate2(fAward_tmp2);
//            result += fAwardService.executeDeleteBatch(new String[]{fAward.getUuid()});//??????????????????
//        }
//        return result > 0 ? success("???????????????") : failure("???????????????");
//    }
//
//    //??????
//    //??????????????????????????????????????????????????????????????????
//    @ResponseBody
//    @RequestMapping(value = "/adjust", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object adjust(@ModelAttribute FAward fAward) throws Exception {
//        int result = 0 ;
//        String checkSql = " select count(uuid) as NUM from f_award where upd_reason = '"+fAward.getUuid()+"'";
//        PageData checkObj = DbUtil.executeQueryObject(checkSql);
//        if(checkObj!=null&&checkObj.get("NUM")!=null&&Integer.parseInt(checkObj.get("NUM").toString())>0){
//            return failure("????????????,?????????????????????");
//        }
//        String uuid = BaseUtils.UUIDGenerator();
//        String oldUuid = fAward.getUuid();
//        FAward fAward_tmp = fAwardService.queryObject(oldUuid);
//        if(!fAward_tmp.getDelStatus().equals("0")){
//            return failure("????????????,?????????????????????????????????");
//        }
//        fAward_tmp.setOccDate(fAward.getOccDate());
//        fAward_tmp.setTeamNo(fAward.getTeamNo());
//        //fAward_tmp.setNoticeNo(fAward.getNoticeNo());
//        //fAward_tmp.setAssessType(fAward.getAssessType());
//        //fAward_tmp.setAssessCate(fAward.getAssessCate());
//        fAward_tmp.setAwardBala(fAward.getAwardBala());
//        //fAward_tmp.setAssessTeam(fAward.getAssessTeam());
//        //fAward_tmp.setSignEmp(fAward.getSignEmp());
//        fAward_tmp.setReason(fAward.getReason());
//        fAward_tmp.setUuid(uuid);
//        fAward_tmp.setUpdStatus("1");
//        fAward_tmp.setUpdReason(oldUuid);
//        result += fAwardService.insertSelective(getSaveData(fAward_tmp));
//
//        String userIds = request.getParameter("userIds");
//        boolean haveU = false;
//        if(userIds!=null&&!"".equals(userIds)&&!"undefined".equals(userIds)){
//            String [] userAddIdArray = userIds.split(",");
//            if(userAddIdArray.length>0){
//                haveU = true;
//                for (int i = 0; i < userAddIdArray.length; i++) {
//                    String empId = request.getParameter("empId_"+userAddIdArray[i]);
//                    String empName = request.getParameter("empName_"+userAddIdArray[i]);
//                    String awardBala = request.getParameter("awardBala_"+userAddIdArray[i]);
//                    if(empId==null||empId.equals("undefined")||empId.equals("")){continue;}
//                    if(empName==null||empName.equals("undefined")||empName.equals("")){continue;}
//                    if(awardBala==null||awardBala.equals("undefined")||awardBala.equals("")){continue;}
//                    User user = userService.getUserByUserNameId(empId);
//                    if(user == null){continue;}
//                    FAwardAssign fAwardAssign = new FAwardAssign();
//                    fAwardAssign.setAwardId(uuid);
//                    fAwardAssign.setEmpName(empName);
//                    fAwardAssign.setUserName(empName);
//                    fAwardAssign.setEmpId(empId);
//                    fAwardAssign.setAwardBala(new BigDecimal(awardBala));
//                    fAwardAssign.setRemark("");
//                    fAwardAssign.setAssignId(Snow.getUUID());
//                    fAwardAssign.setAssignType("f.assignType.3");
//                    fAwardAssign.setModiDate(sdfd2.format(new Date()));
//                    fAwardAssign.setModiUser(LoginInfo.getUserNameId());
//                    fAwardAssign.setUserName(LoginInfo.getUserName());
//                    fAwardAssign.setIsDel(0);
//                    fAwardAssign.setLeadFlag(user.getLeadFlag());
//                    fAwardAssignService.insertSelective(getSaveData(fAwardAssign));
//                }
//            }
//        }
//        if(!haveU){
//            //????????????????????????????????????????????????
//            List<FAwardAssign> assignList = fAwardAssignService.queryListByFilterSort(" award_id='"+oldUuid+"'");
//            for (int i = 0; i < assignList.size(); i++) {
//                FAwardAssign assign_tmp = assignList.get(i);
//                assign_tmp.setAwardId(uuid);
//                assign_tmp.setUuid(BaseUtils.UUIDGenerator());
//                fAwardAssignService.insertSelective(assign_tmp);
//            }
//        }
//        return result > 0 ? success("???????????????") : failure("???????????????");
//    }
//
//    /**
//     * ????????????
//     * @param uuid
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/delSubmitBatch", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object delSubmitBatch(@RequestParam String[] uuid) throws Exception {
//        int result = 0;
//        int result_error = 0;
//        String error_info = "";
//        for (int i = 0; i < uuid.length; i++) {
//            FAward fAward_tmp = fAwardService.queryObject(uuid[i]);
//            if(!fAward_tmp.getDelStatus().equals("0")){
//                result_error ++;
//                error_info += "????????????????????????;";
//                continue;
//            }
//            String checkSql = " select count(uuid) as NUM from f_award where upd_reason = '"+fAward_tmp.getUuid()+"'";
//            PageData checkObj = DbUtil.executeQueryObject(checkSql);
//            if(checkObj!=null&&checkObj.get("NUM")!=null&&Integer.parseInt(checkObj.get("NUM").toString())>0){
//                result_error ++;
//                error_info += "?????????????????????;";
//                continue;
//            }
//            FAward fAward_tmp2 = new FAward();
//            fAward_tmp2.setUuid(uuid[i]);
//            fAward_tmp2.setDelStatus("1");
//            result += fAwardService.executeUpdate2(fAward_tmp2);
//        }
//        String successMsg = result_error > 0?"????????????"+result+"????????????"+result_error+"????????????:"+error_info:"????????????!";
//        return result > 0 ? success(successMsg) : failure("?????????????????????:"+error_info);
//    }
//
//    //????????????
//    @ResponseBody
//    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
//    @ControllerLog(description = "??????FAward????????????")
//    public Object deleteBatch(@RequestParam String[] uuid) {
//        String start = sysAuditProcessService.getFirstStepNoByProcessNo(processNo1);
//        for (int i = 0; i < uuid.length; i++) {
//            FAward fAward_tmp2 =fAwardService.queryObject(uuid[i]);
//            if(!fAward_tmp2.getStatus().equals(start)){
//                return failure("??????????????????????????????");
//            }
//        }
//        int result = fAwardService.executeDeleteBatch(uuid);
//        return result > 0 ? success("???????????????") : failure("???????????????");
//    }
//
//    //????????????
//    //Type 1????????????
//    @ResponseBody
//    @RequestMapping(value = "/submitBatch", method = RequestMethod.POST)
//    @ControllerLog(description = "submitBatchFAward????????????")
//    public Object submitBatch(@RequestParam String[] uuid) {
//        String Type = request.getParameter("Type");
//        int result = 0;
//        int result_error = 0;
//        String error_info = "";
//        //????????????
//        if(Type.equals("1")){
//            for (int i = 0; i < uuid.length; i++) {
//                FAward fAward_tmp = fAwardService.queryObjectSum(uuid[i]);
//                BigDecimal assignSum = fAward_tmp.getAssignSum();//??????????????????
//                int assignCount = fAward_tmp.getAssignCount();//?????????????????????
//                //????????????????????????????????????????????????????????????
//                if(assignCount>0){
//                    if(assignSum!=null&&fAward_tmp.getAwardBala()!=null){
//                        if(assignSum.compareTo(fAward_tmp.getAwardBala()) != 0){
//                            result_error ++;
//                            error_info += "?????????????????????????????????;";
//                            continue;
//                        }
////                        if(assignSum.compareTo(fAward_tmp.getAwardBala()) == 1){
////                            result_error ++;
////                            error_info += "??????????????????;";
////                            continue;
////                        }
//                    }
//                }
//                //????????????
//                if(!fAward_tmp.getStatus().equals(sysAuditProcessService.getFirstStepNoByProcessNo(processNo1))){
//                    result_error ++;
//                    error_info += "?????????????????????;";
//                    continue;
//                }
//                //????????????
//                SysAuditProcess auditProcess = sysAuditProcessService.queryObject(fAward_tmp.getStatus());//????????????
//                //????????? auditProcess.getNextStep()
//                //????????? auditProcess.getLastStep()
//                String status = auditProcess.getNextStep();
//                FAward fAward_tmp_e = new FAward();
//                fAward_tmp_e.setUuid(fAward_tmp.getUuid());
//                fAward_tmp_e.setStatus(auditProcess.getNextStep());
//                if(assignCount>0){
//                    fAward_tmp_e.setStatus("484AE79DE2CE4A9CA527C9C279CCE055");//??????????????????
//                }
//                result += fAwardService.executeUpdate2(fAward_tmp_e);
//            }
//        }
//        //????????????
//        else
//        if(Type.equals("2")){
//            for (int i = 0; i < uuid.length; i++) {
//                FAward fAward_tmp = fAwardService.queryObjectSum(uuid[i]);
//                BigDecimal assignSum = fAward_tmp.getAssignSum();//??????????????????
//                int assignCount = fAward_tmp.getAssignCount();//?????????????????????
//                //????????????????????????????????????????????????????????????
//                if(assignCount>0){
//                    if(assignSum!=null&&fAward_tmp.getAwardBala()!=null){
//                        if(assignSum.compareTo(fAward_tmp.getAwardBala()) != 0){
//                            result_error ++;
//                            error_info += "?????????????????????????????????;";
//                            continue;
//                        }
////                        if(assignSum.compareTo(fAward_tmp.getAwardBala()) == 1){
////                            result_error ++;
////                            error_info += "??????????????????;";
////                            continue;
////                        }
//                    }
//                }else{
//                    result_error ++;
//                    error_info += "???????????????;";
//                    continue;
//                }
//                //????????????
//                SysAuditProcess auditProcess = sysAuditProcessService.queryObject(fAward_tmp.getStatus());//????????????
//                //????????? auditProcess.getNextStep()
//                //????????? auditProcess.getLastStep()
//                String status = auditProcess.getNextStep();
//                FAward fAward_tmp_e = new FAward();
//                fAward_tmp_e.setUuid(fAward_tmp.getUuid());
//                fAward_tmp_e.setStatus(auditProcess.getNextStep());
//                result += fAwardService.executeUpdate2(fAward_tmp_e);
//            }
//        }
//
//        String successMsg = result_error > 0?"????????????"+result+"????????????"+result_error+"???????????????"+error_info:"????????????!";
//        return result > 0 ? success(successMsg) : failure("?????????????????????"+error_info);
//    }
//
//    //??????
//    @ResponseBody
//    @RequestMapping(value = "/backBatch", method = RequestMethod.POST)
//    @ControllerLog(description = "submitBatchFAward????????????")
//    public Object backBatch(@RequestParam String[] uuid) {
//        int result = 0;
//        int result_error = 0;
//        String error_info = "";
//
//        //????????????
//        for (int i = 0; i < uuid.length; i++) {
//            FAward fAward_tmp = fAwardService.queryObject(uuid[i]);
//            if(!fAward_tmp.getDelStatus().equals("0")||!fAward_tmp.getUpdStatus().equals("0")){
//                result_error++;
//                error_info += "??????????????????????????????";
//                continue;
//            }
//            //????????????
//            //SysAuditProcess auditProcess = sysAuditProcessService.queryObject(fAward_tmp.getStatus());//????????????
//            //????????? auditProcess.getNextStep()
//            //????????? auditProcess.getLastStep()
//            String status = sysAuditProcessService.getFirstStepNoByProcessNo(processNo1);
//            FAward fAward_tmp_e = new FAward();
//            fAward_tmp_e.setUuid(uuid[i]);
//            fAward_tmp_e.setStatus(status);
//            result += fAwardService.executeUpdate2(fAward_tmp_e);
//        }
//
//        String successMsg = result_error > 0?"????????????"+result+"????????????"+result_error+"???????????????"+error_info:"????????????!";
//        return result > 0 ? success(successMsg) : failure("????????????????????????"+error_info);
//    }
//
//    //????????????????????????
//    @ResponseBody
//    @RequestMapping(value = "/getSysUserListByKeywords")
//    public Object getSysUserListByKeywords(String q,String teamNo) throws Exception {
//        String sql = "select uuid,user_name,user_name_id,org_name from sys_user where is_del != 1 ";
//        if (null != teamNo && !teamNo.equals("")) {
//            //sql += " and org_id ="+teamNo;
//            sql += " and (org_id ="+teamNo+" or lead_flag = 'leadFlag.1') ";
//        }
//        if (null == q || q.equals("")) {
//            sql += " order by user_name_id asc limit 10";
//        } else {
//            sql += " and (user_name LIKE '%" + q + "%' OR user_name_id LIKE '%" + q + "%' OR user_name_jp LIKE '%" + q + "%')";
//        }
//        List<PageData> sysUserList = DbUtil.executeQueryList(sql);
//        return sysUserList;
//    }
//
//    /**
//     * ??????????????????
//     * @param userNo
//     * @param upDown
//     * @param mngTeam
//     * @param ifSafe
//     * @param ifStock
//     * @param bigType
//     * @param storeType
//     * @param storeLevel
//     * @return
//     * @throws Exception
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getSysStoreList")
//    public Object getSysStoreList(	String userNo,
//                                   String upDown,
//                                   String mngTeam,
//                                   String ifSafe,
//                                   String ifStock,
//                                   String bigType,
//                                   String storeType,
//                                   String storeLevel)  throws Exception {
//        String sql = " SELECT uuid,store_no, store_name FROM m_store" +
//                " WHERE 1=1" +
//                (userNo==null||"".equals(userNo)?"":" AND store_no IN (SELECT store_no FROM t_user_store WHERE user_id='"+userNo+"')") +
//                (upDown==null||"".equals(upDown)?"":" AND up_down IN ('"+upDown+"')") +
//                (mngTeam==null||"".equals(mngTeam)?"":" AND mng_team IN ('"+mngTeam+"')") +
//                (ifSafe==null||"".equals(ifSafe)?"":" AND if_safe = '"+ifSafe+"'") +
//                (ifStock==null||"".equals(ifStock)?"":" AND if_stock = '"+ifStock+"'") +
//                (bigType==null||"".equals(bigType)?"":" AND big_type IN ('"+bigType+"')") +
//                (storeType==null||"".equals(storeType)?"":" AND store_type IN ('"+storeType+"')") +
//                (storeLevel==null||"".equals(storeLevel)?"":" AND store_level IN ('"+storeLevel+"')") +
//                " ORDER BY store_name";
//        List<PageData> resList = DbUtil.executeQueryList(sql);
//        return resList;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "getOrgByFaward", method = RequestMethod.POST)
//    public Object getOrgByFaward() throws Exception {
//        String sql = "SELECT o.* FROM sys_organization o " +
//                "LEFT JOIN t_dept_type t ON o.id = t.dept_id " +
//                "WHERE o.level_id=3 AND t.dict_key = 'deptAttr.assType' order by o.sort asc";
//        List<PageData> resList = DbUtil.executeQueryList(sql);
//        return resList;
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "getStepList", method = RequestMethod.POST)
//    public Object getStepList() throws Exception {
//        String sql = "SELECT * FROM `sys_audit_process` WHERE process_no LIKE '3013%' AND LEVEL = 2";
//        List<PageData> resList = DbUtil.executeQueryList(sql);
//        return resList;
//    }
//
//    /**
//     * excel??????
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/exportExcel")
//    @ControllerLog(description = "??????????????????")
//    public void exportExcel(HttpServletResponse response) {
//
//        String fileName = "?????????????????????????????????????????????????????????";
//        String textName = "??????,????????????,????????????,????????????,????????????,????????????,????????????,????????????,????????????,????????????,????????????,????????????,????????????";
//        String fieldName = "auditStepName,occDate,modiDate,assignDate,noticeNo,teamNoName,assessType,awardBala,assessTeamName,modiTeamName,signEmp,assessCate,standard";
//
//        String filterSort = "";
//        filterSort += " and is_del !=1 and upd_status = '0'";
//        filterSort += " and status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
//        filterSort += " and modi_team = " + LoginInfo.getOrgId();
//        filterSort += " order by create_time desc";
//
//        List<FAward> awardList = fAwardService.queryList(filterSort);
//        List<FAward> awardList1 = (List<FAward>)covDataListDic(awardList,"assessCate#f.awardType");
//        for (int i = 0; i < awardList1.size(); i++) {
//            if(awardList1.get(i).getAssessType().equals("1")){
//                awardList1.get(i).setAssessType("??????");
//            }
//        }
//        PoiUtils.exportExcel2(response, fileName, textName, fieldName, awardList1,LoginInfo.getUserName());
//        //return success("???????????????");
//    }
//
//    /**
//     * excel??????(????????????)
//     * ??????????????????
//     * @param response
//     */
//    @RequestMapping(value = "/exportExcel2")
//    @ControllerLog(description = "??????????????????")
//    public void exportExcel2(HttpServletResponse response) {
//        String fileName = "?????????????????????????????????????????????????????????";
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet(fileName);
//        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
//        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
//        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
//        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
//        HSSFPrintSetup printSetup = sheet.getPrintSetup();
//        printSetup.setLandscape(true);
//        printSetup.setPaperSize((short) 9);
//        //????????????
//        sheet.setColumnWidth(0, 2500);
//        sheet.setColumnWidth(1, 2500);
//        sheet.setColumnWidth(2, 4000);
//        sheet.setColumnWidth(3, 4000);
//        sheet.setColumnWidth(4, 4000);
//        sheet.setColumnWidth(5, 4000);
//        sheet.setColumnWidth(6, 5000);
//        sheet.setColumnWidth(7, 3000);
//        sheet.setColumnWidth(8, 3000);
//        sheet.setColumnWidth(9, 4000);
//        sheet.setColumnWidth(10, 3000);
//        sheet.setColumnWidth(11, 5000);
//        sheet.setColumnWidth(12, 3000);
//        sheet.setColumnWidth(13, 3000);
//        sheet.setColumnWidth(14, 10000);
//
//        //?????????(??????)
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
//        HSSFRow headerRow = sheet.createRow(0);
//        headerRow.setHeightInPoints(50.0F);
//        HSSFCell cell = headerRow.createCell(0);
//        cell.setCellStyle(ExcelStyle.getHeaderStyle(workbook));
//        cell.setCellValue(fileName);
//        //?????????(?????????)
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));
//        HSSFRow dateRow = sheet.createRow(1);
//        dateRow.setHeightInPoints(30.0F);
//        HSSFCell cell1 = dateRow.createCell(0);
//        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
//        cell1.setCellValue("?????????: " + LoginInfo.getUserName() + "   ???????????????" + DateUtils.format(new Date(),"yyyy-MM-dd"));
//        //?????????(??????)
//        HSSFRow tableHeaderRow = sheet.createRow(2);
//        tableHeaderRow.setHeightInPoints(30.0F);
//        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(1);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(2);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(3);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(4);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(5);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(6);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(7);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("?????????");
//        tableHederCell = tableHeaderRow.createCell(8);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(9);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(10);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(11);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(12);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(13);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(14);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//
//        HSSFRow tableContentRow = null;
//        HSSFCell tableContentCell = null;
//        try{
//            int startRow = 3;//??????N?????????????????????
//            //????????????
//            HSSFFont hssfFont = workbook.createFont();
//            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
//
//            String filterSort = "";
//            filterSort = " and f.is_del !=1 and f.upd_status = '0'";
//            //????????????
//            String query_teamNo = request.getParameter("query_teamNo");
//            if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
//                filterSort += " and f.team_no = "+query_teamNo;
//            }
//            String query_assessCate = request.getParameter("query_assessCate");
//            if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
//                filterSort += " and f.assess_cate = '"+query_assessCate+"'";
//            }
//            String query_modiStart = request.getParameter("query_modiStart");
//            if(query_modiStart!=null&&!"".equals(query_modiStart)&&!"undefined".equals(query_modiStart)){
//                filterSort += " and f.modi_date >= '"+query_modiStart+"'";
//            }
//            String query_modiEnd = request.getParameter("query_modiEnd");
//            if(query_modiEnd!=null&&!"".equals(query_modiEnd)&&!"undefined".equals(query_modiEnd)){
//                filterSort += " and f.modi_date <= '"+query_modiEnd+"'";
//            }
//            String query_assessTeam = request.getParameter("query_assessTeam");
//            if(query_assessTeam!=null&&!"".equals(query_assessTeam)&&!"undefined".equals(query_assessTeam)){
//                filterSort += " and f.assess_team = "+query_assessTeam;
//            }
//            String query_modiTeam = request.getParameter("query_modiTeam");
//            if(query_modiTeam!=null&&!"".equals(query_modiTeam)&&!"undefined".equals(query_modiTeam)){
//                filterSort += " and f.modi_team = "+query_modiTeam;
//            }
//            String query_status = request.getParameter("query_status");
//            if(query_status!=null&&!"".equals(query_status)&&!"undefined".equals(query_status)){
//                filterSort += " and f.status = '"+query_status+"'";
//            }
//            String query_noticeNo = request.getParameter("query_noticeNo");
//            if(query_noticeNo!=null&&!"".equals(query_noticeNo)&&!"undefined".equals(query_noticeNo)){
//                filterSort += " and f.notice_no like '%"+query_noticeNo+"%'";
//            }
//            String query_signEmp = request.getParameter("query_signEmp");
//            if(query_signEmp!=null&&!"".equals(query_signEmp)&&!"undefined".equals(query_signEmp)){
//                filterSort += " and f.sign_emp like '%"+query_signEmp+"%'";
//            }
//            String query_reason = request.getParameter("query_reason");
//            if(query_reason!=null&&!"".equals(query_reason)&&!"undefined".equals(query_reason)){
//                filterSort += " and f.reason like '%"+query_reason+"%'";
//            }
//            String query_person = request.getParameter("query_person");
//            if(query_person!=null&&!"".equals(query_person)&&!"undefined".equals(query_person)) {
//                filterSort += " and a.emp_name like '%" + query_person + "%'";
//            }
//
//
//            String QryOrgStr = this.getUserQryOrgStr();
//            Boolean ifKhy = checkHaveAccess(Khy);//????????????
//            Boolean ifBsy = checkHaveAccess(Bsy);//????????????
//            if(ifKhy){
//                filterSort += " and (f.modi_team="+LoginInfo.getOrgId()+" OR f.team_no="+LoginInfo.getOrgId()+")";
//            }else if(ifBsy){
//                filterSort += " AND f.team_no="+LoginInfo.getOrgId();
//            }else{
//                filterSort += " AND f.team_no in "+QryOrgStr;
//            }
//            filterSort += " and f.status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
//            filterSort += " order by f.create_time desc";
//
////            filterSort += " and is_del !=1 and upd_status = '0'";
////            filterSort += " and status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
////            filterSort += " and modi_team = " + LoginInfo.getOrgId();
////            filterSort += " order by create_time desc";
//            List<FAward> awardList_tmp = new ArrayList<>();
//            if(query_person!=null&&!"".equals(query_person)&&!"undefined".equals(query_person)) {
//                awardList_tmp = fAwardService.queryListJoinAssign(filterSort);
//            }else{
//                awardList_tmp = fAwardService.queryList(filterSort);
//            }
//
//            List<FAward> awardList = (List<FAward>)covDataListDic(awardList_tmp,"assessCate#f.awardType");
//            for (int i = 0; i < awardList.size(); i++) {
//                int takeUpRowNum = 1;//????????????
//                int assignNum = 0;//????????????
//                List<FAwardAssign> awardAssignList = fAwardAssignService.queryListByFilterSort(" and award_id = '"+awardList.get(i).getUuid()+"'");
//                if(awardAssignList!=null&&awardAssignList.size()>0){
//                    takeUpRowNum = awardAssignList.size();
//                    assignNum = awardAssignList.size();
//                }
//
//                if(takeUpRowNum>1){
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 0, 0));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 1, 1));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 2, 2));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 3, 3));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 4, 4));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 5, 5));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 6, 6));
//                }
//                tableContentRow = sheet.createRow(startRow);
//                //tableContentRow.setHeightInPoints(20.0F);
//
//                //??????
//                tableContentCell = tableContentRow.createCell(0);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(i+1);
//                //??????
//                tableContentCell = tableContentRow.createCell(1);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getAuditStepName());
//                //????????????
//                tableContentCell = tableContentRow.createCell(2);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getOccDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(3);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getModiDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(4);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getAssignDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(5);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getNoticeNo());
//                //????????????
//                tableContentCell = tableContentRow.createCell(6);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getTeamNoName());
//
//                if(assignNum>0) {
//                    for (int j = 0; j < awardAssignList.size(); j++) {
//                        if(j>0) {
//                            tableContentRow = sheet.createRow(startRow + j);
//                            tableContentCell = tableContentRow.createCell(0);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(1);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(2);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(3);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(4);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(5);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(6);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            //tableContentRow.setHeightInPoints(20.0F);
//                        }
//                        //?????????
//                        tableContentCell = tableContentRow.createCell(7);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getEmpName());
//                        //??????
//                        tableContentCell = tableContentRow.createCell(8);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getEmpId());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(9);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getAwardBala().stripTrailingZeros().toPlainString());
//                        //????????????
//                        if(awardList.get(i).getAssessType().equals("1")){
//                            tableContentCell = tableContentRow.createCell(10);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell.setCellValue("??????");
//                        }
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(11);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getAssessTeamName());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(12);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getSignEmp());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(13);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getAssessCate());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(14);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getReason());
//                    }
//                }
//                startRow += takeUpRowNum;
//            }
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        ServletOutputStream os = null;
//        try {
//            response.reset();
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            os = response.getOutputStream();
//            workbook.write(os);
//        } catch (Exception var39) {
//            var39.printStackTrace();
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException var38) {
//                    var38.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    @RequestMapping(value = "/exportExcel3")
//    @ControllerLog(description = "??????????????????")
//    public void exportExcel3(HttpServletResponse response) {
//        String fileName = "?????????????????????????????????????????????????????????";
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet(fileName);
//        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
//        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
//        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
//        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
//        HSSFPrintSetup printSetup = sheet.getPrintSetup();
//        printSetup.setLandscape(true);
//        printSetup.setPaperSize((short) 9);
//        //????????????
//        sheet.setColumnWidth(0, 2500);
//        sheet.setColumnWidth(1, 2500);
//        sheet.setColumnWidth(2, 4000);
//        sheet.setColumnWidth(3, 4000);
//        sheet.setColumnWidth(4, 4000);
//        sheet.setColumnWidth(5, 4000);
//        sheet.setColumnWidth(6, 5000);
//        sheet.setColumnWidth(7, 3000);
//        sheet.setColumnWidth(8, 3000);
//        sheet.setColumnWidth(9, 4000);
//        sheet.setColumnWidth(10, 3000);
//        sheet.setColumnWidth(11, 5000);
//        sheet.setColumnWidth(12, 3000);
//        sheet.setColumnWidth(13, 3000);
//        sheet.setColumnWidth(14, 10000);
//
//        //?????????(??????)
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
//        HSSFRow headerRow = sheet.createRow(0);
//        headerRow.setHeightInPoints(50.0F);
//        HSSFCell cell = headerRow.createCell(0);
//        cell.setCellStyle(ExcelStyle.getHeaderStyle(workbook));
//        cell.setCellValue(fileName);
//        //?????????(?????????)
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));
//        HSSFRow dateRow = sheet.createRow(1);
//        dateRow.setHeightInPoints(30.0F);
//        HSSFCell cell1 = dateRow.createCell(0);
//        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
//        cell1.setCellValue("?????????: " + LoginInfo.getUserName() + "   ???????????????" + DateUtils.format(new Date(),"yyyy-MM-dd"));
//        //?????????(??????)
//        HSSFRow tableHeaderRow = sheet.createRow(2);
//        tableHeaderRow.setHeightInPoints(30.0F);
//        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(1);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(2);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(3);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(4);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(5);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(6);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(7);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("?????????");
//        tableHederCell = tableHeaderRow.createCell(8);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(9);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(10);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(11);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(12);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(13);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(14);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//
//        HSSFRow tableContentRow = null;
//        HSSFCell tableContentCell = null;
//        try{
//            int startRow = 3;//??????N?????????????????????
//            //????????????
//            HSSFFont hssfFont = workbook.createFont();
//            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
//
//            String filterSort = "";
//            String query_teamNo = request.getParameter("query_teamNo");
//            if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
//                filterSort += " and f.team_no = "+query_teamNo;
//            }
//            String query_assessCate = request.getParameter("query_assessCate");
//            if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
//                filterSort += " and f.assess_cate = '"+query_assessCate+"'";
//            }
//            filterSort += " and is_del !=1 and upd_status = '0'";
//            filterSort += " and status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
//            filterSort += " and modi_team = " + LoginInfo.getOrgId();
//            filterSort += " order by create_time desc";
//
//
//            List<FAward> awardList = (List<FAward>)covDataListDic(fAwardService.queryList(filterSort),"assessCate#f.awardType");
//            for (int i = 0; i < awardList.size(); i++) {
//                int takeUpRowNum = 1;//????????????
//                int assignNum = 0;//????????????
//                List<FAwardAssign> awardAssignList = fAwardAssignService.queryListByFilterSort(" and award_id = '"+awardList.get(i).getUuid()+"'");
//                if(awardAssignList!=null&&awardAssignList.size()>0){
//                    takeUpRowNum = awardAssignList.size();
//                    assignNum = awardAssignList.size();
//                }
//
//                if(takeUpRowNum>1){
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 0, 0));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 1, 1));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 2, 2));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 3, 3));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 4, 4));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 5, 5));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 6, 6));
//                }
//                tableContentRow = sheet.createRow(startRow);
//                //tableContentRow.setHeightInPoints(20.0F);
//
//                //??????
//                tableContentCell = tableContentRow.createCell(0);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(i+1);
//                //??????
//                tableContentCell = tableContentRow.createCell(1);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getAuditStepName());
//                //????????????
//                tableContentCell = tableContentRow.createCell(2);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getOccDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(3);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getModiDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(4);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getAssignDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(5);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getNoticeNo());
//                //????????????
//                tableContentCell = tableContentRow.createCell(6);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getTeamNoName());
//
//                if(assignNum>0) {
//                    for (int j = 0; j < awardAssignList.size(); j++) {
//                        if(j>0) {
//                            tableContentRow = sheet.createRow(startRow + j);
//                            tableContentCell = tableContentRow.createCell(0);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(1);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(2);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(3);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(4);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(5);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(6);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            //tableContentRow.setHeightInPoints(20.0F);
//                        }
//                        //?????????
//                        tableContentCell = tableContentRow.createCell(7);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getEmpName());
//                        //??????
//                        tableContentCell = tableContentRow.createCell(8);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getEmpId());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(9);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getAwardBala().stripTrailingZeros().toPlainString());
//                        //????????????
//                        if(awardList.get(i).getAssessType().equals("1")){
//                            tableContentCell = tableContentRow.createCell(10);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell.setCellValue("??????");
//                        }
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(11);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getAssessTeamName());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(12);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getSignEmp());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(13);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getAssessCate());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(14);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getReason());
//                    }
//                }
//                startRow += takeUpRowNum;
//            }
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        ServletOutputStream os = null;
//        try {
//            response.reset();
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            os = response.getOutputStream();
//            workbook.write(os);
//        } catch (Exception var39) {
//            var39.printStackTrace();
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException var38) {
//                    var38.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    /**
//     * ????????????
//     * @param response
//     */
//    @RequestMapping(value = "/exportExcel4")
//    @ControllerLog(description = "??????????????????/????????????")
//    public void exportExcel4(HttpServletResponse response) {
//        String fileName = "?????????????????????????????????????????????????????????(???????????????)";
//
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet(fileName);
//        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
//        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
//        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
//        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
//        HSSFPrintSetup printSetup = sheet.getPrintSetup();
//        printSetup.setLandscape(true);
//        printSetup.setPaperSize((short) 9);
//        //????????????
//        sheet.setColumnWidth(0, 2500);
//        sheet.setColumnWidth(1, 2500);
//        sheet.setColumnWidth(2, 4000);
//        sheet.setColumnWidth(3, 4000);
//        sheet.setColumnWidth(4, 4000);
//        sheet.setColumnWidth(5, 4000);
//        sheet.setColumnWidth(6, 5000);
//        sheet.setColumnWidth(7, 3000);
//        sheet.setColumnWidth(8, 3000);
//        sheet.setColumnWidth(9, 4000);
//        sheet.setColumnWidth(10, 3000);
//        sheet.setColumnWidth(11, 5000);
//        sheet.setColumnWidth(12, 3000);
//        sheet.setColumnWidth(13, 3000);
//        sheet.setColumnWidth(14, 10000);
//
//        //?????????(??????)
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
//        HSSFRow headerRow = sheet.createRow(0);
//        headerRow.setHeightInPoints(50.0F);
//        HSSFCell cell = headerRow.createCell(0);
//        cell.setCellStyle(ExcelStyle.getHeaderStyle(workbook));
//        cell.setCellValue(fileName);
//        //?????????(?????????)
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 14));
//        HSSFRow dateRow = sheet.createRow(1);
//        dateRow.setHeightInPoints(30.0F);
//        HSSFCell cell1 = dateRow.createCell(0);
//        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
//        cell1.setCellValue("?????????: " + LoginInfo.getUserName() + "   ???????????????" + DateUtils.format(new Date(),"yyyy-MM-dd"));
//        //?????????(??????)
//        HSSFRow tableHeaderRow = sheet.createRow(2);
//        tableHeaderRow.setHeightInPoints(30.0F);
//        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(1);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(2);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(3);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(4);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(5);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(6);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(7);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("?????????");
//        tableHederCell = tableHeaderRow.createCell(8);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("??????");
//        tableHederCell = tableHeaderRow.createCell(9);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(10);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(11);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(12);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(13);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//        tableHederCell = tableHeaderRow.createCell(14);
//        tableHederCell.setCellStyle(ExcelStyle.getColumnStyle(workbook));
//        tableHederCell.setCellValue("????????????");
//
//        HSSFRow tableContentRow = null;
//        HSSFCell tableContentCell = null;
//        try{
//            int startRow = 3;//??????N?????????????????????
//            //????????????
//            HSSFFont hssfFont = workbook.createFont();
//            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
//
//            String filterSort = "";
//            filterSort = " and f.is_del !=1 and f.upd_status = '0'";
//            filterSort += " and f.assign_date < '"+sdfd2.format(new Date())+"'";
//            filterSort += " and f.status = 'DCCC14FCAEFC45F9A0CC1F5E56EE19F1'";
//            //????????????
//            String query_teamNo = request.getParameter("query_teamNo");
//            if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
//                filterSort += " and f.team_no = "+query_teamNo;
//            }
//            String query_assessCate = request.getParameter("query_assessCate");
//            if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
//                filterSort += " and f.assess_cate = '"+query_assessCate+"'";
//            }
//
////            String QryOrgStr = this.getUserQryOrgStr();
////            Boolean ifKhy = checkHaveAccess(Khy);//????????????
////            Boolean ifBsy = checkHaveAccess(Bsy);//????????????
////            if(ifKhy){
////                filterSort += " and (f.modi_team="+LoginInfo.getOrgId()+" OR f.team_no="+LoginInfo.getOrgId()+")";
////            }else if(ifBsy){
////                filterSort += " AND f.team_no="+LoginInfo.getOrgId();
////            }else{
////                filterSort += " AND f.team_no in "+QryOrgStr;
////            }
////            filterSort += " and status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
////            filterSort += " order by create_time desc";
//
////            filterSort += " and is_del !=1 and upd_status = '0'";
////            filterSort += " and status !='D2541FDD4DDC4E16B01A4991DC0FDC2D'";
////            filterSort += " and modi_team = " + LoginInfo.getOrgId();
//            filterSort += " order by create_time desc";
//
//            List<FAward> awardList = (List<FAward>)covDataListDic(fAwardService.queryList(filterSort),"assessCate#f.awardType");
//            for (int i = 0; i < awardList.size(); i++) {
//                int takeUpRowNum = 1;//????????????
//                int assignNum = 0;//????????????
//                List<FAwardAssign> awardAssignList = fAwardAssignService.queryListByFilterSort(" and award_id = '"+awardList.get(i).getUuid()+"'");
//                if(awardAssignList!=null&&awardAssignList.size()>0){
//                    takeUpRowNum = awardAssignList.size();
//                    assignNum = awardAssignList.size();
//                }
//
//                if(takeUpRowNum>1){
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 0, 0));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 1, 1));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 2, 2));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 3, 3));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 4, 4));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 5, 5));
//                    sheet.addMergedRegion(new CellRangeAddress(startRow, startRow+takeUpRowNum-1, 6, 6));
//                }
//                tableContentRow = sheet.createRow(startRow);
//                //tableContentRow.setHeightInPoints(20.0F);
//
//                //??????
//                tableContentCell = tableContentRow.createCell(0);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(i+1);
//                //??????
//                tableContentCell = tableContentRow.createCell(1);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getAuditStepName());
//                //????????????
//                tableContentCell = tableContentRow.createCell(2);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getOccDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(3);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getModiDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(4);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getAssignDate());
//                //????????????
//                tableContentCell = tableContentRow.createCell(5);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getNoticeNo());
//                //????????????
//                tableContentCell = tableContentRow.createCell(6);
//                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                tableContentCell.setCellValue(awardList.get(i).getTeamNoName());
//
//                if(assignNum>0) {
//                    for (int j = 0; j < awardAssignList.size(); j++) {
//                        if(j>0) {
//                            tableContentRow = sheet.createRow(startRow + j);
//                            tableContentCell = tableContentRow.createCell(0);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(1);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(2);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(3);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(4);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(5);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell = tableContentRow.createCell(6);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            //tableContentRow.setHeightInPoints(20.0F);
//                        }
//                        //?????????
//                        tableContentCell = tableContentRow.createCell(7);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getEmpName());
//                        //??????
//                        tableContentCell = tableContentRow.createCell(8);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getEmpId());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(9);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardAssignList.get(j).getAwardBala().stripTrailingZeros().toPlainString());
//                        //????????????
//                        if(awardList.get(i).getAssessType().equals("1")){
//                            tableContentCell = tableContentRow.createCell(10);
//                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                            tableContentCell.setCellValue("??????");
//                        }
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(11);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getAssessTeamName());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(12);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getSignEmp());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(13);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getAssessCate());
//                        //????????????
//                        tableContentCell = tableContentRow.createCell(14);
//                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
//                        tableContentCell.setCellValue(awardList.get(i).getReason());
//                    }
//                }
//                startRow += takeUpRowNum;
//            }
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        ServletOutputStream os = null;
//        try {
//            response.reset();
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            os = response.getOutputStream();
//            workbook.write(os);
//        } catch (Exception var39) {
//            var39.printStackTrace();
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException var38) {
//                    var38.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//}
