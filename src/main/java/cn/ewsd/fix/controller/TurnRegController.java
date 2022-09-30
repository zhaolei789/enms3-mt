package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.fix.model.MBackPlan;
import cn.ewsd.fix.service.MBackPlanService;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MInService;
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
@RequestMapping("/fix/turnReg")
public class TurnRegController extends FixBaseController {

    @Autowired
    private MBackPlanService mBackPlanService;
    @Autowired
    private MInService mInService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));
        request.setAttribute("curDate", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));

        return "fix/turn/index_get_wait_back";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String teamQry = request.getParameter("teamQry");
        String addrQry = request.getParameter("addrQry");
        String matQry = request.getParameter("matQry");
        String userTeam = LoginInfo.getOrgId();

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MBackPlan> pageSet = mBackPlanService.getTurnRegPageSet(pageParam, filterSort, monthQry, userTeam, addrQry, teamQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/insert")
    public Object insertTurnReg() {
        try {
            mInService.insertTurnReg(request, LoginInfo.get());
            return success("保存数据成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存数据失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String stepQry = request.getParameter("stepQry");
        String matQry = request.getParameter("matQry1");
        String userTeam = LoginInfo.getOrgId();

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MIn> pageSet = mInService.getTurnRegPageSet(pageParam, filterSort, date1Qry, date2Qry, matQry, stepQry, userTeam);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete() {
        String billNo = request.getParameter("billNo");
        System.out.println("billNo--->"+billNo);
        try {
            mInService.deleteTurnReg(billNo);
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }
}
