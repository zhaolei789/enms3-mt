package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.WzscPlan;
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
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/inApply")
public class InApplyController extends RepositoryBaseController {
    @Autowired
    private MInService mInService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "repository/inApply/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String codeQry = request.getParameter("codeQry");
        String nameQry = request.getParameter("nameQry");
        String typeQry = request.getParameter("typeQry");
        String purcQry = request.getParameter("purcQry");
        String statusQry = request.getParameter("statusQry");

        String occDate = XDate.getDate();
        String monLast = XDate.getLastDayOfMonth(("".equals(monQry) ? XDate.getMonth() : monthQry)+"01");
        String beginDate = XDate.addDate(monLast, -365);

        try{
            PageSet<WzscPlan> pageSet = mInService.getEpPlanPageSet(pageParam, filterSort, beginDate, occDate, monthQry, codeQry, typeQry, purcQry, nameQry, statusQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        String storeQry = request.getParameter("storeQry");
        String ifStockQry = request.getParameter("ifStockQry");
        double curAmount = 0;
        try {
            curAmount = Double.parseDouble(request.getParameter("curAmount"));
        }catch (Exception e){
            return failure("本次入库数必须是数字！");
        }
        if(curAmount <= 0){
            return failure("本次入库数必须是大于0的数字！");
        }
        String offerNo = request.getParameter("offerNo");
        if("".equals(offerNo)){
            return failure("请选择供应商！");
        }
        if("".equals(storeQry)){
            return failure("请选择入库仓库！");
        }
        String remark = request.getParameter("remark");
        String epId = request.getParameter("epId");

        try {
            mInService.submitApply(storeQry, ifStockQry, curAmount, offerNo, remark, epId, LoginInfo.get());
            return success("入库成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("入库失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object getInDetail(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        try{
            PageSet<MIn> pageSet = mInService.getDetailPageSet(pageParam, filterSort);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        String billNo = request.getParameter("billNo");

        try {
            mInService.deletePlanIn(billNo);
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }
}
