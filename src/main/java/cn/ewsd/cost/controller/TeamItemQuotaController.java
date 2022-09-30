package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MAssessNorm;
import cn.ewsd.cost.model.MFeeItem;
import cn.ewsd.cost.service.MAssessNormService;
import cn.ewsd.cost.service.MFeeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/teamItemQuota")
public class TeamItemQuotaController extends CostBaseController {

    @Autowired
    private MAssessNormService mAssessNormService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/teamItemQuota/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String userTeam = LoginInfo.getOrgId();
        String prjQry = request.getParameter("prjQry");
        String teamNoQry = request.getParameter("teamNoQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MAssessNorm> pageSet = mAssessNormService.getTeamItemQuotaPageSet(pageParam, filterSort, userTeam, teamNoQry, prjQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try {
            mAssessNormService.saveAssessNorm(request);
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update() {
        try {
            mAssessNormService.updateAssessNorm(request);
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try {
            mAssessNormService.deleteAssessNorm(request);
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }
}
