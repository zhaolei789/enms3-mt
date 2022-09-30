package cn.ewsd.material.controller;

import cn.ewsd.base.utils.IParameter;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.material.model.MBudget;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.service.ClassBudgetService;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MMonthBudgetService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/classBudget")
public class ClassBudgetController extends MaterialBaseController {
    @Autowired
    private ClassBudgetService classBudgetService;

    //编辑页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/classBudget/index";
    }

    //列表数据
    @ResponseBody
    @RequestMapping(value = "/getTableData", method = RequestMethod.POST)
    public Object getClassBudgetList() {
        String useYearQry = request.getParameter("useYearQry");
        String useMonthQry = request.getParameter("useMonthQry");
        String teamNo = LoginInfo.getOrgId();

        return classBudgetService.getClassBudgetList(useYearQry, useMonthQry, teamNo);
    }

    //列表数据
    @ResponseBody
    @RequestMapping(value = "/getDeptTableData", method = RequestMethod.POST)
    public Object getDeptTableData() {
        String itemNo = request.getParameter("itemNo");
        String teamNo = LoginInfo.getOrgId();
        String occMonth = request.getParameter("occMonth");

        return classBudgetService.getClassForm(teamNo, occMonth, itemNo);
    }

    @RequestMapping("/classForm")
    public String getForm(@RequestParam Map<String, Object> params) {
        String itemNo = request.getParameter("itemNo");
        String teamNo = LoginInfo.getOrgId();
        String occMonth = request.getParameter("occMonth");

        List<MBudget> classList = classBudgetService.getClassForm(teamNo, occMonth, itemNo);
        request.setAttribute("dList", classList);

        return "material/classBudget/classForm";
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update(@ModelAttribute MBudget mBudget) {
        try {

            classBudgetService.saveData(mBudget, LoginInfo.get());

            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }
//
//    @ResponseBody
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public Object save() {
//        try{
//            IParameter iParameter = new IParameter(request);
//            String itemNoQry = request.getParameter("itemNoQry");
//            String useYearQry = request.getParameter("useYearQry");
//            String useMonthQry = request.getParameter("useMonthQry");
//            String[] teamNos = iParameter.getParameters("teamNo");
//            if(teamNos==null || teamNos.length<1){
//                return failure("请至少选择一个单位！");
//            }
//
//            HashMap<String, Double[]> map = new HashMap<String, Double[]>();
//
//            for(int i=0; i<teamNos.length; i++){
//                String teamNo = teamNos[i];
//                Double iniBala = iParameter.getDoubleParameter(teamNo+"_ini", 0);
//                Double addBala = iParameter.getDoubleParameter(teamNo+"_add", 0);
//                Double overBala = iParameter.getDoubleParameter(teamNo+"_over", 0);
//
//                map.put(teamNo, new Double[]{iniBala, addBala, overBala});
//            }
//
//            int result = mMonthBudgetService.saveData(itemNoQry, useYearQry+useMonthQry, map);
//            return result > 0 ? success("保存成功！") : failure("保存失败！");
//        }catch (Exception e){
//            e.printStackTrace();
//            return 0;
//        }
//    }
}
