package cn.ewsd.material.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.material.service.MYearBudgetService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/budOccAnal")
public class BudOccAnalController extends MaterialBaseController {

    @Autowired
    private MYearBudgetService mYearBudgetService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String yearQry = XDate.getYear();
        String monQry = XDate.getMon();
        request.setAttribute("yearQry", yearQry);
        request.setAttribute("monQry", monQry);

        return "material/budOccAnal/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getList(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry + monQry;
        String itemNoQry = request.getParameter("itemNoQry");
        String teamNoQry = request.getParameter("teamNoQry");

        try {
            return mYearBudgetService.getBudOccAnalList(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), monthQry, itemNoQry, teamNoQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
