package cn.ewsd.fix.controller;

import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.fix.model.MBackQuota;
import cn.ewsd.fix.model.MFixAssess;
import cn.ewsd.fix.service.MBackQuotaService;
import cn.ewsd.fix.service.MFixAssessService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.MBackPlanService;

import javax.servlet.http.HttpServletResponse;

/**
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/mBackPlan")
public class MBackPlanController extends FixBaseController {

    @Autowired
    private MBackPlanService mBackPlanService;
    @Autowired
    private MBackQuotaService mBackQuotaService;
    @Autowired
    private MFixAssessService mFixAssessService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private MInService mInService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "fix/mBackPlan/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getBackQuota", method = RequestMethod.POST)
    public Object getBackQuota() {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String prjQry = request.getParameter("prjQry");
        String userTeam = LoginInfo.getOrgId();

        try{
            return mBackQuotaService.getBackQuotaList(monthQry, prjQry, userTeam);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/getBackPlan")
    public Object getBackPlan() {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String prjQry = request.getParameter("prjQry");
        String teamNoQry = request.getParameter("teamNoQry");

        try{
            List<MBackQuota> list =  mBackQuotaService.getBackPlanList(monthQry, prjQry, LoginInfo.getOrgId(), teamNoQry);

            MFixAssess mFixAssess = mFixAssessService.getFixAssess("m.fixType.1", monthQry, LoginInfo.getOrgId(), teamNoQry, prjQry);
            if(list.size()>0 && mFixAssess!=null){
                list.get(0).setPlanValue(mFixAssess.getPlanValue());
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/insertBackPlan")
    public Object insertBackPlan() {
        try{
            mBackPlanService.insertBackPlan(request, LoginInfo.get());
            return success("计算成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("计算失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save() {
        try{
            mBackPlanService.saveBackPlan(request, LoginInfo.get());
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete() {
        try{
            mBackPlanService.deleteBackPlan(request.getParameter("planNo"));
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping("/submit")
    public Object submit() {
        try{
            mBackPlanService.submitBackPlan(request);
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }

    @RequestMapping("/indexBackPlanQry")
    public String indexQry(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "fix/mBackPlan/index_back_plan_qry";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String teamNoQry = request.getParameter("teamNoQry");
        String ksTeamQry = request.getParameter("ksTeamQry");
        String matQry = request.getParameter("matQry");

        boolean ifZnks = utilService.checkRight(LoginInfo.getRoleId(), 2625);
        boolean hasEdit = utilService.checkRight(LoginInfo.getRoleId(), 2626);
        String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MBackPlan> pageSet = mBackPlanService.getPageSet(pageParam, filterSort, monthQry, teamNoQry, matQry, ksTeamQry, ifZnks, LoginInfo.getOrgId(), userDeptIds);
            if(pageSet.getRows().size()>0){
                pageSet.getRows().get(0).setCreator(hasEdit+"");
            }
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getBackPlanTeam")
    public Object getBackPlanTeam(){
        return mBackPlanService.getBackPlanTeam();
    }

    @RequestMapping("/modiPlan")
    public String modiPlan() {
        String planNo = request.getParameter("planNo");

        MBackPlan mBackPlan = mBackPlanService.getBackPlan(planNo);
        double inAmount = mInService.getBillAmount(planNo);
        request.setAttribute("backPlan", mBackPlan);
        request.setAttribute("inAmount", inAmount);

        return "fix/mBackPlan/edit";
    }

    @ResponseBody
    @RequestMapping("/updatePlan")
    public Object updatePlan() {
        try {
            mBackPlanService.updateBackPlan(request, LoginInfo.get());
            return success("调整成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("调整失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String monthQry = yearQry+monQry;
            String teamNoQry = request.getParameter("teamNoQry");
            String ksTeamQry = request.getParameter("ksTeamQry");
            String matQry = request.getParameter("matQry");

            boolean ifZnks = utilService.checkRight(LoginInfo.getRoleId(), 2625);
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<MBackPlan> list = mBackPlanService.getBackPlanList(monthQry, teamNoQry, matQry, ksTeamQry, ifZnks, LoginInfo.getOrgId(), userDeptIds);
            for(int i=0; i<list.size(); i++){
                MBackPlan mBackPlan = list.get(i);
                mBackPlan.setPlanMonth(XDate.dateTo10(mBackPlan.getPlanMonth()));
                mBackPlan.setUuid(mBackPlan.getMatPrice().multiply(mBackPlan.getPlanAmount()).doubleValue()+"");
                mBackPlan.setEndDate(XDate.dateTo10(mBackPlan.getEndDate()));
            }

            String textName = "状态,计划月份,物料编码,物料描述,单位,计划数,单价,金额,已回收数,周转回收数,回收单位,回收地点,备注,截止日期,计划号";
            String fieldName = "planStepName,planMonth,matCode,matName,matUnit,planAmount,matPrice,uuid,hsAmount,zzAmount,teamName,prjName,remark,endDate,planNo";
            PoiUtils.exportExcelOld(response, "回收计划", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
