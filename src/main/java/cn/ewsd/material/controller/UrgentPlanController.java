package cn.ewsd.material.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.service.*;
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
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/urgentPlan")
public class UrgentPlanController extends MaterialBaseController {

    @Autowired
    private MStockService mStockService;
    @Autowired
    private MPlanService mPlanService;
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/urgentPlan/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getStockPageSet")
    public Object getStockPageSet(PageParam pageParam) {
        String matQry = request.getParameter("matQry");
        String storeQry = request.getParameter("storeQry");
        String reserveQry = request.getParameter("reserveQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        try{
            PageSet<MMaterial> pageSet = mStockService.getUrgentPlanStock(pageParam, filterSort, LoginInfo.getOrgId(), XDate.getMonth(), storeQry, reserveQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Object submitUrgentPlan() {
        try {
            mPlanService.submitUrgentPlan(request, LoginInfo.get());
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }
}
