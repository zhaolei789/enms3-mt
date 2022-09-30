package cn.ewsd.material.controller;

import cn.ewsd.base.utils.IParameter;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MMonthBudgetService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mMonthBudget")
public class MMonthBudgetController extends MaterialBaseController {

    @Autowired
    private MMonthBudgetService mMonthBudgetService;
    @Autowired
    private MItemService mItemService;
    @Autowired
    private OrganizationService organizationService;
    //编辑页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("itemList", mItemService.getMatItemSet(true, LoginInfo.getUuid()));
        List<Organization> deptList = organizationService.getOrgByLevel(2);
        for(int i=0; i<deptList.size(); i++){
            Integer id = deptList.get(i).getId();
            if(id==5 || id==7){
                deptList.remove(deptList.get(i));
            }
        }
        request.setAttribute("deptList", deptList);
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/mMonthBudget/index";
    }

    //列表数据
    @ResponseBody
    @RequestMapping(value = "/getTableData", method = RequestMethod.POST)
    public Object getYearBudgetList() {
        String itemNoQry = request.getParameter("itemNoQry");
        String useYearQry = request.getParameter("useYearQry");
        String useMonthQry = request.getParameter("useMonthQry");
        String sysNoQry = request.getParameter("sysNoQry");

        return mMonthBudgetService.getMonthBudgetList(itemNoQry, useYearQry, useMonthQry, sysNoQry);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save() {
        try{
            IParameter iParameter = new IParameter(request);
            String itemNoQry = request.getParameter("itemNoQry");
            String useYearQry = request.getParameter("useYearQry");
            String useMonthQry = request.getParameter("useMonthQry");
            String[] teamNos = iParameter.getParameters("teamNo");
            if(teamNos==null || teamNos.length<1){
                return failure("请至少选择一个单位！");
            }

            HashMap<String, Double[]> map = new HashMap<String, Double[]>();

            for(int i=0; i<teamNos.length; i++){
                String teamNo = teamNos[i];
                Double iniBala = iParameter.getDoubleParameter(teamNo+"_ini", 0);
                Double addBala = iParameter.getDoubleParameter(teamNo+"_add", 0);
                Double overBala = iParameter.getDoubleParameter(teamNo+"_over", 0);

                map.put(teamNo, new Double[]{iniBala, addBala, overBala});
            }

            int result = mMonthBudgetService.saveData(itemNoQry, useYearQry+useMonthQry, map);
            return result > 0 ? success("保存成功！") : failure("保存失败！");
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
