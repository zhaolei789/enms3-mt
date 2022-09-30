package cn.ewsd.material.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.model.SysAuditProcess;
import cn.ewsd.system.service.SysAuditProcessService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/planStepState")
public class PlanStepStateController extends MaterialBaseController {
    @Autowired
    private UtilService utilService;
    @Autowired
    private MPlanService mPlanService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private SysAuditProcessService sysAuditProcessService;
    @Autowired
    private MOutService mOutService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        List<SysAuditProcess> stepList = sysAuditProcessService.queryListByFuuid("9C5389EA53854E12A8031ACF5AB9A3DB");
        String stepStr = "";
        for(int i=0; i<stepList.size(); i++){
            SysAuditProcess sysAuditProcess = stepList.get(i);
            stepStr += i==0 ? sysAuditProcess.getProcessNo()+"|"+sysAuditProcess.getProcessName() : ","+sysAuditProcess.getProcessNo()+"|"+sysAuditProcess.getProcessName();
        }
        request.setAttribute("stepStr", stepStr);

        return "material/planStepState/index";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getPageSet() {
        String planYearQry = request.getParameter("planYearQry");
        String beginMonthQry = request.getParameter("beginMonthQry");
        String endMonthQry = request.getParameter("endMonthQry");
        String itemNoQry = request.getParameter("itemNoQry");
        String beginMonth = planYearQry+beginMonthQry;
        String endMonth = planYearQry+endMonthQry;

        try{
            String roleId = LoginInfo.getRoleId();
            boolean ifRole = utilService.checkRight(roleId, 2556);
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<MPlan> dataList = mPlanService.getPlanStepData(userDeptIds, beginMonth, endMonth, itemNoQry, ifRole, LoginInfo.getOrgId());
            List<SysAuditProcess> stepList = sysAuditProcessService.queryListByFuuid("9C5389EA53854E12A8031ACF5AB9A3DB");
            List<MPlan> teamList = mPlanService.getPlanStepTeamList(userDeptIds, beginMonth, endMonth, itemNoQry, ifRole, LoginInfo.getOrgId());

            DataCantainer<MPlan> dataDc = new DataCantainer<>((ArrayList<MPlan>) dataList);

            List<HashMap<String, String>> list = new ArrayList<>();

            HashMap<String, String> map = new HashMap<>();
            map.put("teamNo", "");
            map.put("teamName", "合计");
            map.put("total_cnt", dataDc.getSum("count")+"");
            map.put("total_bala", dataDc.getSum("mat_bala")+"");
            for(int i=0; i<stepList.size(); i++){
                SysAuditProcess sysAuditProcess = stepList.get(i);
                String processNo = sysAuditProcess.getProcessNo();
                map.put(processNo +"_cnt", processNo+","+dataDc.findDataCantainer("plan_step", processNo).getSum("count")+"");
                map.put(processNo+"_bala", dataDc.findDataCantainer("plan_step", processNo).getSum("mat_bala")+"");
            }
            list.add(map);

            for(int i=0; i<teamList.size(); i++){
                MPlan mPlan = teamList.get(i);
                String teamNo = mPlan.getTeamNo()+"";
                String teamName = mPlan.getTeamName();
                DataCantainer<MPlan> d = dataDc.findDataCantainer("team_no", teamNo);

                HashMap<String, String> map1 = new HashMap<>();
                map1.put("teamNo", teamNo);
                map1.put("teamName", teamName);
                map1.put("total_cnt", d.getSum("count")+"");
                map1.put("total_bala", d.getSum("mat_bala")+"");
                for(int j=0; j<stepList.size(); j++){
                    SysAuditProcess sysAuditProcess = stepList.get(j);
                    String processNo = sysAuditProcess.getProcessNo();
                    map1.put(processNo +"_cnt", processNo+","+d.findDataCantainer("plan_step", processNo).getSum("count")+"");
                    map1.put(processNo+"_bala", d.findDataCantainer("plan_step", processNo).getSum("mat_bala")+"");
                }
                list.add(map1);
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String teamNo = request.getParameter("teamNo");
        String planStep = request.getParameter("planStep");
        String itemNo = request.getParameter("itemNo");
        String beginMonth = request.getParameter("beginMonth");
        String endMonth = request.getParameter("endMonth");
        String planSrcQry = request.getParameter("planSrcQry");
        String matQry = request.getParameter("matQry");
        String balaFlag = request.getParameter("balaFlag");
        String matBalaQry = request.getParameter("matBalaQry");

        try{
            String roleId = LoginInfo.getRoleId();
            boolean ifRole = utilService.checkRight(roleId, 2556);

            PageSet<MPlan> pageSet = mPlanService.getDetailPageSet(pageParam, filterSort, beginMonth, endMonth, teamNo, planStep, itemNo, planSrcQry, balaFlag, matBalaQry, matQry, ifRole, LoginInfo.getOrgId());
            List<MPlan> list = pageSet.getRows();
            String planNos = "";
            for(int i=0; i<list.size(); i++){
                planNos += (i==0 ? "" : ",") + list.get(i).getPlanNo();
            }
            List<MOut> applySet = mOutService.getApplySet(planNos);
            DataCantainer<MOut> mOutDc = new DataCantainer<>((ArrayList<MOut>)applySet);

            for(int i=0; i<list.size(); i++){
                MPlan mPlan = list.get(i);
                String planNo = mPlan.getPlanNo();
                mPlan.setOldAmount(new BigDecimal(mOutDc.findDataCantainer("plan_no", planNo).getSum("chk_amount")));
            }

            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
