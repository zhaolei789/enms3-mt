package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.cost.model.FChargeFee;
import cn.ewsd.cost.service.FChargeFeeService;
import cn.ewsd.material.service.MYearBudgetService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/costAnalysis")
public class CostAnalysisController extends CostBaseController {

    @Autowired
    private FChargeFeeService fChargeFeeService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private MYearBudgetService mYearBudgetService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "cost/costAnalysis/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String mon1Qry = request.getParameter("mon1Qry");
        String mon2Qry = request.getParameter("mon2Qry");
        String month1 = yearQry + mon1Qry;
        String month2 = yearQry + mon2Qry;
        String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        try{
            return fChargeFeeService.getCostAnalysisList(userDeptIds, yearQry, month1, month2);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletResponse response) {
        try{
            String yearQry = request.getParameter("yearQry");
            String mon1Qry = request.getParameter("mon1Qry");
            String mon2Qry = request.getParameter("mon2Qry");
            String month1 = yearQry + mon1Qry;
            String month2 = yearQry + mon2Qry;
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<FChargeFee> list = fChargeFeeService.getCostAnalysisList(userDeptIds, yearQry, month1, month2);

            String textName = "业务部门,年度总费用,当期定额费用,当期考核金额,当期节+超-金额,当期节+超-比例,累计发生金额,剩余金额,剩余比例";
            String fieldName = "teamName,yearBudText,monthBalaText,assessBalaText,deffBalaText,diffScaleText,sumOccText,leftBalaText,leftScaleText";
            PoiUtils.exportExcelOld(response, "成本费用分析", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public Object getDetail(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String mon1Qry = request.getParameter("mon1Qry");
        String mon2Qry = request.getParameter("mon2Qry");
        String month1 = yearQry + mon1Qry;
        String month2 = yearQry + mon2Qry;
        String teamNo = request.getParameter("teamNo");

        try{
            return fChargeFeeService.getCostAnalysisDetailList(yearQry, month1, month2, teamNo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMat", method = RequestMethod.POST)
    public Object getMat(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String mon1Qry = request.getParameter("mon1Qry");
        String mon2Qry = request.getParameter("mon2Qry");
        String month1 = yearQry + mon1Qry;
        String month2 = yearQry + mon2Qry;
        String teamNo = request.getParameter("teamNo");

        try{
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

            HashMap<String, Object> map = new HashMap();
            map.put("yearBud", Data.normalToFinal(Data.trimDoubleNo0(yearBud / 10000, 2)));
            map.put("yearOcc", Data.normalToFinal(Data.trimDoubleNo0(yearOcc / 10000, 2)));
            map.put("yearOut", Data.normalToFinal(Data.trimDoubleNo0(yearOut / 10000, 2)));
            map.put("yearAnalysis", Data.trimDoubleNo0(yearAnalysis, 2));
            map.put("budBala", Data.normalToFinal(Data.trimDoubleNo0(budBala / 10000, 2)));
            map.put("occBala", Data.normalToFinal(Data.trimDoubleNo0(occBala / 10000, 2)));
            map.put("economize", Data.normalToFinal(Data.trimDoubleNo0(economize, 2)));
            map.put("monthAnalysis", Data.trimDoubleNo0(monthAnalysis, 2));
            map.put("planSum", Data.normalToFinal(Data.trimDoubleNo0(planSum, 2)));
            map.put("addPlan", Data.normalToFinal(Data.trimDoubleNo0(addPlan, 2)));
            map.put("noOk", Data.normalToFinal(Data.trimDoubleNo0(noOk, 2)));

            return map;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
