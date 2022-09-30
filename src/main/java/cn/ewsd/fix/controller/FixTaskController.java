package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.MBackPlanService;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.service.MMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/fixTask")
public class FixTaskController extends FixBaseController {

    @Autowired
    private MMaterialService mMaterialService;
    @Autowired
    private MBackPlanService mBackPlanService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "fix/fixTask/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String matQry = request.getParameter("matQry");
        String userTeam = LoginInfo.getOrgId();
        String mngTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MMaterial> pageSet = mMaterialService.getFixTaskPageSet(pageParam, filterSort, userTeam.equals(mngTeam) ? "" : userTeam, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String matQry = request.getParameter("matQry1");
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String userTeam = LoginInfo.getOrgId();

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MBackPlan> pageSet = mBackPlanService.getFixTaskList(pageParam, filterSort, userTeam, matQry, yearQry+monQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitFixTask() {
        try {
            // 这个参数不要 会影响到你其它接口吗,同事写的,好多这个,我刚试了下这样可以
            //这样行吗?还有挺多的这个,行的话我再改改?嗯
            mBackPlanService.submitFixTask(request, LoginInfo.get(), configService.getConfigByCode("XT_TEAM_MATERIAL"));
            return success("下达任务成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("下达任务失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object deleteFixTask() {
        String planNo = request.getParameter("planNo");

        try {
            mBackPlanService.deleteFixTask(planNo);
            return success("删除任务成功！");
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除任务失败！");
        }
    }
}
