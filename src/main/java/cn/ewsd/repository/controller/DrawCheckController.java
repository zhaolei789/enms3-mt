package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/drawCheck")
public class DrawCheckController extends RepositoryBaseController {
    @Autowired
    private MOutService mOutService;
    @Autowired
    private MStockService mStockService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getLastDayOfMonth(XDate.getMonth()+"01")));
        return "repository/drawCheck/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getIndexData", method = RequestMethod.POST)
    public Object getIndexData() {
        try{
            return mOutService.getDrawCheckIndex(LoginInfo.getUuid(), LoginInfo.getOrgId());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String teamNo = request.getParameter("teamNo");
        String drawStep = request.getParameter("drawStep");
        String applyDate = request.getParameter("month");
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String drawNoQry = request.getParameter("drawNoQry");
        String storeNoQry = request.getParameter("storeNoQry");
        String matQry = request.getParameter("matQry");
        String typeQry = request.getParameter("typeQry");
        String userTeam = LoginInfo.getOrgId();
        String userNo = LoginInfo.getUuid();

        try{
            PageSet<MOut> pageSet = mOutService.getDrawCheckList(pageParam, filterSort, teamNo, drawStep, applyDate, date1Qry, date2Qry, drawNoQry, userNo, storeNoQry, matQry, typeQry, userTeam);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCSKC", method = RequestMethod.POST)
    public Object getPageSet() {
        String matNo = request.getParameter("matNo");
        String storeNo = request.getParameter("storeNo");

        double roadAmout = mOutService.getRoadAmount(matNo, storeNo);
        MStock mStock = mStockService.getStock(matNo, storeNo);
        double stockAmount = 0;
        if(mStock!=null){
            stockAmount = mStock.getStockAmount().doubleValue();
        }

        return new BigDecimal(stockAmount).subtract(new BigDecimal(roadAmout));
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitOutCheck() {
        try {
            mOutService.submitOutCheck(LoginInfo.get(), request);
            return success("提交成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backOutCheck() {
        try {
            mOutService.backOutCheck(LoginInfo.get(), request);
            return success("提交成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/hzLog")
    public Object hzLog() {
        String drawNo = request.getParameter("drawNo");

        try {
            return mOutService.getHzLog(drawNo);
        }catch (XException e){
            e.printStackTrace();
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("加载汇总信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/llLog")
    public Object llLog() {
        String drawNo = request.getParameter("drawNo");

        try {
            return mOutService.getDrawList(drawNo);
        }catch (XException e){
            e.printStackTrace();
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("加载汇总信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/xhLog")
    public Object xhLog() {
        String drawNo = request.getParameter("drawNo");

        try {
            return mOutService.getConsumeList(drawNo);
        }catch (Exception e){
            e.printStackTrace();
            return failure("加载汇总信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/kcLog")
    public Object kcLog() {
        String drawNo = request.getParameter("drawNo");

        try {
            return mOutService.getConsumeList(drawNo);
        }catch (Exception e){
            e.printStackTrace();
            return failure("加载汇总信息失败！");
        }
    }
}
