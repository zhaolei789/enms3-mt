package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.service.MPlanService;
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
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/rikuQry")
public class RikuQryController extends MaterialBaseController {

    @Autowired
    private MPlanService mPlanService;
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/rikuQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry + monQry;
        String teamQry = request.getParameter("teamQry");
        String stepQry = request.getParameter("stepQry");
        String matQry = request.getParameter("matQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        try{
            PageSet<MPlan> pageSet = mPlanService.getRikuQry(pageParam, filterSort, monthQry, teamQry, stepQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String monthQry = yearQry + monQry;
            String teamQry = request.getParameter("teamQry");
            String stepQry = request.getParameter("stepQry");
            String matQry = request.getParameter("matQry");

            List<MPlan> list = mPlanService.getRikuQryList(monthQry, teamQry, stepQry, matQry);
            for(int i=0; i<list.size(); i++){
                MPlan mPlan = list.get(i);
                mPlan.setPlanMonth(XDate.dateTo10(mPlan.getPlanMonth()));
                mPlan.setErpType(mPlan.getErpType()+" "+ mPlan.getTypeName());
                mPlan.setMatCode("X".equals(mPlan.getMatCode()) ? mPlan.getMatCode().substring(1) : mPlan.getMatCode());
                mPlan.setFactAmount(mPlan.getLkqAmount().subtract(mPlan.getLkhAmount()));
                mPlan.setMatBala(mPlan.getLkqAmount().subtract(mPlan.getLkhAmount()).multiply(mPlan.getMatPrice()));
                mPlan.setMatUse(mPlan.getMatUse() + " " + mPlan.getRemark());
            }

            String textName = "编制单位,计划月份,审批状态,材料分类,物料编码,物料描述,单位,上报数,利库后,利库数,采购数,价格,利库金额,提报单号,WBS,用途及备注";
            String fieldName = "teamName,planMonth,planStepName,erpType,matCode,matName,matUnit,lkqAmount,lkhAmount,factAmount,purAmount,matPrice,matBala,reserveNo,wbsElement,matUse";
            PoiUtils.exportExcelOld(response, "利库明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
