package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/queryPlan")
public class QueryPlanController extends MaterialBaseController {
    @Autowired
    private MPlanService mPlanService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MOutService mOutService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        boolean planModi = utilService.checkRight(LoginInfo.getRoleId(), 2558);
        boolean planModiOk = utilService.checkRight(LoginInfo.getRoleId(), 2559);
        request.setAttribute("planModi", planModi);
        request.setAttribute("planModiOk", planModiOk);

        return "material/queryPlan/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String teamQry = request.getParameter("teamQry");
        String prjStatusQry = request.getParameter("prjStatusQry");
        String prjNoQry = request.getParameter("prjNoQry");
        String itemNoQry = request.getParameter("itemNoQry");
        String yearQry = request.getParameter("yearQry");
        String mon1Qry = request.getParameter("mon1Qry");
        String mon2Qry = request.getParameter("mon2Qry");
        String planStepQry = request.getParameter("planStepQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String reserveQry = request.getParameter("reserveQry");
        String matBalaQry = request.getParameter("matBalaQry");
        String balaFlag = request.getParameter("balaFlag");
        String reseFlagQry = request.getParameter("reseFlagQry");

        try{
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MPlan> pageSet = mPlanService.getQueryPlanList(pageParam, filterSort, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamQry, prjStatusQry, matBalaQry, balaFlag, prjNoQry, itemNoQry, reserveQry, mon1Qry, mon2Qry, yearQry, planStepQry, matNameQry, matCodeQry, reseFlagQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletResponse response) {
        try{

            String teamQry = request.getParameter("teamQry");
            String prjStatusQry = request.getParameter("prjStatusQry");
            String prjNoQry = request.getParameter("prjNoQry");
            String itemNoQry = request.getParameter("itemNoQry");
            String yearQry = request.getParameter("yearQry");
            String mon1Qry = request.getParameter("mon1Qry");
            String mon2Qry = request.getParameter("mon2Qry");
            String planStepQry = request.getParameter("planStepQry");
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String reserveQry = request.getParameter("reserveQry");
            String matBalaQry = request.getParameter("matBalaQry");
            String balaFlag = request.getParameter("balaFlag");
            String reseFlagQry = request.getParameter("reseFlagQry");
            List<MPlan> list = mPlanService.getQueryPlanDownLoad(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamQry, prjStatusQry, matBalaQry, balaFlag, prjNoQry, itemNoQry, reserveQry, mon1Qry, mon2Qry, yearQry, planStepQry, matNameQry, matCodeQry, reseFlagQry);

            String textName = "成本中心,成本中心描述,编制单位,审批状态,材料分类,分类描述,材料编码,材料名称,库存地,单位,计划数,价格,金额,可领数,预留号,工程名称,计划月份,计划源,计划号,使用日期,用途备注,类型,科目,WBS,上报日期";
            String fieldName = "costCenter,centerName,teamName,planStep,erpType,typeName,matCode,matName,matAddr,matUnit,matAmount,matPrice,matBala,usableAmount,reserveNo,prjName,planMonth,planSrcName,planNo,useDate,matUse,planTypeName,itemName,wbsElement,planDate";
            for(int i=0; i<list.size();i++){
                MPlan mPlan = list.get(i);
                int backCount = mPlan.getCount();
                String planStep = mPlan.getPlanStep();
                String deptName = "71052".equals(planStep) ? mPlan.getZnksTeamName() : "71057".equals(planStep) ? mPlan.getOfferDeptName() : "";
                if(backCount > 0 && "71050".equals(planStep)){
                    mPlan.setPlanStep(mPlan.getDirect());
                }else{
                    mPlan.setPlanStep("71052".equals(planStep) || "71057".equals(planStep) ? deptName : mPlan.getPlanStepName());
                }
                String matCode = mPlan.getMatCode();
                if(matCode!=null && !"".equals(matCode)){
                    mPlan.setMatCode("X".equals(matCode.substring(0, 1)) ? matCode.substring(1) : matCode);
                }
                mPlan.setUseDate(XDate.dateTo10(mPlan.getUseDate()));
                mPlan.setMatUse(mPlan.getMatUse()+" "+ mPlan.getRemark());
                mPlan.setPlanDate(XDate.dateTo10(mPlan.getPlanDate()));
            }
            PoiUtils.exportExcelOld(response, "计划明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMatHis")
    public Object getMatHis(PageParam pageParam) {
        String matNo = request.getParameter("matNo");
        String year = XDate.getYear();
        String userDept = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MPlan> pageSet = mPlanService.getMatHis(pageParam, filterSort, userDept, year, matNo);
        return pageSet;
    }

    @RequestMapping("/planModi")
    public String planModi() {
        String planNo = request.getParameter("planNo");

        MPlan mPlan = mPlanService.getPlanByPlanNo(planNo);
        request.setAttribute("mPlan", mPlan);
        request.setAttribute("flag", true);

        return "material/queryPlan/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/savePlanModi")
    public Object savePlanModi() {
        String planNo = request.getParameter("planNo");
        String offerNo = request.getParameter("offerNo");

        MPlan mPlan = new MPlan();
        mPlan.setPlanNo(planNo);
        mPlan.setOfferNo(offerNo);
        mPlanService.updatePlan(mPlan);

        return success("保存成功！");
    }

    @RequestMapping("/planModiOk")
    public String planModiOk() {
        String planNo = request.getParameter("planNo");

        MPlan mPlan = mPlanService.getPlanByPlanNo(planNo);
        request.setAttribute("mPlan", mPlan);
        request.setAttribute("flag", false);
        MOut mOut = mOutService.getZaiTuByPlanNo(planNo);

        System.out.println(mPlan.getUsableAmount().doubleValue()+"&&&&&&&&&&");
        System.out.println(mOut.getOutAmount().doubleValue()+"&&&&&&&&&&");

        request.setAttribute("usableAmount", mPlan.getUsableAmount().doubleValue()-mOut.getOutAmount().doubleValue());

        return "material/queryPlan/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/savePlanModiOk")
    public Object savePlanModiOk() {
        String planNo = request.getParameter("planNo");
        double minusAmount = 0;
        try{
            minusAmount = Double.parseDouble(request.getParameter("minusAmount"));
        }catch (Exception e){
            return failure("减少数量必须是数字！");
        }
        String remark = request.getParameter("remark");
        String planStep = request.getParameter("planStep");
        String reserveNo = request.getParameter("reserveNo");
        String purchaseNo = request.getParameter("purchaseNo");
        String isDelete = request.getParameter("isDelete");

        MPlan mPlan = mPlanService.getPlanByPlanNo(planNo);
        double usableAmount = mPlan.getUsableAmount().doubleValue();
        String stepCode = mPlan.getPlanStep();
        String planRemark = mPlan.getRemark();
        String checkNo = mPlan.getCheckNo();

        if(!("7105F".equals(stepCode) || "7105O".equals(stepCode) || "7105Q".equals(stepCode))){
            return failure("本功能只支持修改定稿计划");
        }
        MOut mOut = mOutService.getZaiTuByPlanNo(planNo);
        double applyAmount = mOut.getOutAmount().doubleValue();
        if(minusAmount > usableAmount-applyAmount){
            return failure("超过最大可减数量，可领用数量为"+usableAmount+"，其中"+applyAmount+"被申请占用");
        }

        String r = isDelete==null ? planRemark+"，调整："+remark+"；调整数："+minusAmount : planRemark+"；删除："+remark;

        try {
            mPlanService.adjustPlan(isDelete, planNo, purchaseNo, reserveNo, planStep, r, minusAmount, checkNo, LoginInfo.get());
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }


}
