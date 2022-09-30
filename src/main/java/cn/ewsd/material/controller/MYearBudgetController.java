package cn.ewsd.material.controller;

import cn.ewsd.base.utils.IParameter;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MYearBudgetService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mYearBudget")
public class MYearBudgetController extends MaterialBaseController {

    @Autowired
    private MYearBudgetService mYearBudgetService;
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

        return "material/mYearBudget/index";
    }

    //列表数据
    @ResponseBody
    @RequestMapping(value = "/getTableData", method = RequestMethod.POST)
    public Object getYearBudgetList() {
        String itemNoQry = request.getParameter("itemNoQry");
        String useYearQry = request.getParameter("useYearQry");
        String sysNoQry = request.getParameter("sysNoQry");

        try {
            return mYearBudgetService.getYearBudgetList(itemNoQry, useYearQry, sysNoQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save() {
        IParameter iParameter = new IParameter(request);
        String itemNoQry = request.getParameter("itemNoQry");
        String useYearQry = request.getParameter("useYearQry");
        String[] teamNos = iParameter.getParameters("teamNo");
        HashMap<String, Double[]> map = new HashMap<String, Double[]>();

        for(int i=0; i<teamNos.length; i++){
            String teamNo = teamNos[i];
            Double iniBala = iParameter.getDoubleParameter(teamNo+"_ini", 0);
            Double addBala = iParameter.getDoubleParameter(teamNo+"_add", 0);

            map.put(teamNo, new Double[]{iniBala, addBala});
        }

        int result = mYearBudgetService.saveData(itemNoQry, useYearQry, map);
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }
}
