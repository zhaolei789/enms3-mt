package cn.ewsd.material.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.PlanCheckIndex;
import cn.ewsd.material.service.*;
import cn.ewsd.system.service.SysAuditProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/planCheck")
public class PlanCheckController extends MaterialBaseController {
    @Autowired
    PlanCheckService planCheckService;
    @Autowired
    MItemService mItemService;
    @Autowired
    MPrjService mPrjService;
    @Autowired
    SysAuditProcessService sysAuditProcessService;
    @Autowired
    MPlanService mPlanService;
    //页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/planCheck/index";
    }

    //索引数据
    @ResponseBody
    @RequestMapping(value = "/getIndexData", method = RequestMethod.POST)
    public Object getIndexData(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        int userItemCnt = planCheckService.getUserItemCnt(LoginInfo.getUuid());

        PageSet<PlanCheckIndex> pageSet = planCheckService.getIndexData(pageParam, filterSort, LoginInfo.getOrgId(), LoginInfo.getUuid(), userItemCnt);

        System.out.println(LoginInfo.getOrgId()+" : "+LoginInfo.getUuid());

        return pageSet;
    }

    //计划列表
    @ResponseBody
    @RequestMapping(value = "/getPlanList", method = RequestMethod.POST)
    public Object getPlanList(PageParam pageParam) {
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String planType = request.getParameter("planType");
        String planStep = request.getParameter("planStep");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String itemQry = request.getParameter("itemQry");
        String teamNo = LoginInfo.getOrgId();

        PageSet<MPlan> pageSet = null;
        if("".equals(prjNo) || prjNo==null){
            return pageSet;
        }

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        pageSet = planCheckService.getPlanList(pageParam, filterSort, prjNo, planMonth, planType, planStep, teamNo, matCodeQry, matNameQry, itemQry);

        return pageSet;
    }

    //科目列表
    @ResponseBody
    @RequestMapping(value = "/getPlanItem")
    public Object getPlanItem() {
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String planType = request.getParameter("planType");
        String planStep = request.getParameter("planStep");
        try {
            return mItemService.getItemList(prjNo, planMonth, planType, planStep);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mItemService.getItemList(prjNo, planMonth, planType, planStep);
    }

    @ResponseBody
    @RequestMapping(value = "/planText")
    public Object planText() {
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String planStep = request.getParameter("planStep");

        MPlan mPlan = new MPlan();
        try{
            mPlan.setPrjNo(mPrjService.getPrjByPrjNo(prjNo).getPrjName());
            mPlan.setPlanMonth(XDate.getCDate8(planMonth));
            mPlan.setPlanStep(sysAuditProcessService.queryObjectByProcessNo(planStep).getProcessName());
        }catch (Exception e){
            e.printStackTrace();
        }

        return mPlan;
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submit() {
        String[] planNos = request.getParameterValues("planNo");
        String planStep = request.getParameter("planStep");

        if(planNos==null || planNos.length<1){
            return failure("请至少选择一条计划！");
        }

        String drawApply = configService.getConfigByCode("ETC_DRAW_APPLY");
        String agreeEmp = configService.getConfigByCode("CL_AUTO_CLSP_EMP");
        String clModel = configService.getConfigByCode("CL_MODEL");
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("ifMatUse", configService.getConfigByCode("CL_IF_MAT_USE"));
        paramMap.put("ifCtlPrice", configService.getConfigByCode("CL_CONTROL_1_PRICE"));
        paramMap.put("clLbItem", configService.getConfigByCode("CL_LB_ITEM"));

        try{
            mPlanService.submitPlan(request, paramMap, planNos, planStep, LoginInfo.getUuid(), LoginInfo.getUserName());
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("未知错误！");
        }

        return success("提交成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backPlan() {
        try{
            planCheckService.backPlan(request, LoginInfo.get());
            return success("退回成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败！");
        }
    }
}
