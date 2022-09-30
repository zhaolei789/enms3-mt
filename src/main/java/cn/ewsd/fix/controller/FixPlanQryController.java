package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.MBackPlanService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/fixPlanQry")
public class FixPlanQryController extends FixBaseController {

    @Autowired
    private MBackPlanService mBackPlanService;
    @Autowired
    private MInService mInService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        boolean modiRight = utilService.checkRight(LoginInfo.getRoleId(), 2646);
        request.setAttribute("modiRight", modiRight);

        return "fix/fixPlanQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry + monQry;
        String teamNoQry  = request.getParameter("teamNoQry");
        String mngTeamQry = request.getParameter("mngTeamQry");
        String matQry = request.getParameter("matQry");
        String stepQry = request.getParameter("stepQry");
        String fixTypeQry = request.getParameter("fixTypeQry");

        boolean isZnks = utilService.checkRight(LoginInfo.getRoleId(), 2645);
        String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MBackPlan> pageSet = mBackPlanService.getFixPlanPageSet(pageParam, filterSort, monthQry, teamNoQry, mngTeamQry, isZnks, LoginInfo.getOrgId(), userDeptIds, stepQry, fixTypeQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMngTeam", method = RequestMethod.POST)
    public Object getMngTeam(PageParam pageParam) {
        String monthQry = request.getParameter("monthQry");

        return mBackPlanService.getBackPlanMngTeam(monthQry);
    }

    @RequestMapping("/modiPlan")
    public String modiPlan() {
        String planNo = request.getParameter("planNo");

        MBackPlan mBackPlan = mBackPlanService.getBackPlan(planNo);
        double inAmount = mInService.getBillAmount(planNo);
        request.setAttribute("backPlan", mBackPlan);
        request.setAttribute("inAmount", inAmount);

        return "fix/fixPlanQry/edit";
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
            String monthQry = yearQry + monQry;
            String teamNoQry  = request.getParameter("teamNoQry");
            String mngTeamQry = request.getParameter("mngTeamQry");
            String matQry = request.getParameter("matQry");
            String stepQry = request.getParameter("stepQry");
            String fixTypeQry = request.getParameter("fixTypeQry");

            boolean isZnks = utilService.checkRight(LoginInfo.getRoleId(), 2645);
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<MBackPlan> list = mBackPlanService.getFixPlanList(monthQry, teamNoQry, mngTeamQry, isZnks, LoginInfo.getOrgId(), userDeptIds, stepQry, fixTypeQry, matQry);
            for(int i=0; i<list.size(); i++){
                MBackPlan mBackPlan = list.get(i);
                mBackPlan.setPlanDate(XDate.dateTo10(mBackPlan.getPlanDate()));
                mBackPlan.setEndDate(XDate.dateTo10(mBackPlan.getEndDate()));
            }

            String textName = "步骤,物料编码,物料描述,单位,计划数,单价,原值,备注,完成日期,修复单位,管理科室,计划号,计划类型,编制日期";
            String fieldName = "planStepName,matCode,matName,matUnit,planAmount,matPrice,matBala,fixAddr,endDate,teamName,planTeamName,planNo,fixTypeName,planDate";
            PoiUtils.exportExcelOld(response, "一般修复，复用计划", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
