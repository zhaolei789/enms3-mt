package cn.ewsd.cost.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.XDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.cost.model.BarMaster;
import cn.ewsd.cost.service.BarMasterService;

/**
 * 矿长
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
@Controller
@RequestMapping("/cost/barMaster")
public class BarMasterController extends CostBaseController {

    @Autowired
    private BarMasterService barMasterService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开BarMaster模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/barMaster/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得BarMaster分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<BarMaster> pageSet = barMasterService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSetYearFee", method = RequestMethod.POST)
    @ControllerLog(description = "获得BarMaster分页集数据")
    public Object getPageSetYearFee(PageParam pageParam) {
        String filterSort = "";
        String fMonth = request.getParameter("fMonth");
        if(fMonth == null||"".equals(fMonth)||"undefined".equals(fMonth)){
            fMonth = XDate.getDate().substring(0,4);
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<BarMaster> pageSet = barMasterService.getPageSetYearFee(pageParam, filterSort,fMonth);
        return pageSet;
    }

    /**
     * 财务上报
     * @param pageParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPageSetSbCw", method = RequestMethod.POST)
    @ControllerLog(description = "获得BarMaster分页集数据")
    public Object getPageSetSbCw(PageParam pageParam) {
        String filterSort = "";
        String query_year = request.getParameter("query_year");
        if(query_year == null||"".equals(query_year)||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        String query_month = request.getParameter("query_month");
        if(query_month == null||"".equals(query_month)||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<BarMaster> pageSet = barMasterService.getPageSetSbCw(pageParam, filterSort,query_year,query_year+query_month);
        return pageSet;
    }

    /**
     * 费用发生上报
     * @param pageParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPageSetSbFs", method = RequestMethod.POST)
    @ControllerLog(description = "获得BarMaster分页集数据")
    public Object getPageSetSbFs(PageParam pageParam) {
        String filterSort = "";
        String query_year = request.getParameter("query_year");
        if(query_year == null||"".equals(query_year)||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        String query_month = request.getParameter("query_month");
        if(query_month == null||"".equals(query_month)||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<BarMaster> pageSet = barMasterService.getPageSetSbFs(pageParam, filterSort,query_year,query_year+query_month);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSetSettle", method = RequestMethod.POST)
    @ControllerLog(description = "获得BarMaster分页集数据")
    public Object getPageSetSettle(PageParam pageParam) {
        String filterSort = "";
        String query_year = request.getParameter("query_year");
        if(query_year == null||"".equals(query_year)||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        String query_month = request.getParameter("query_month");
        if(query_month == null||"".equals(query_month)||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<BarMaster> pageSet = barMasterService.getPageSetSettle(pageParam, filterSort,query_year,query_year+query_month);
        return pageSet;
    }


    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得BarMaster模块详细数据")
    public Object getDetailByUuid(String uuid) {
        BarMaster barMaster = barMasterService.queryObject(uuid);
        return barMaster;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开BarMaster模块新增页面")
    public String add() {
        return "cost/barMaster/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存BarMaster模块数据")
    public Object save(@ModelAttribute BarMaster barMaster) {
        barMaster.setIsDel(0);
        int result = barMasterService.insertSelective(getSaveData(barMaster));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开BarMaster模块编辑页面")
    public String edit() {
        return "cost/barMaster/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新BarMaster模块数据")
    public Object update(@ModelAttribute BarMaster barMaster) {
        int result = barMasterService.updateByPrimaryKeySelective(getUpdateData(barMaster));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除BarMaster模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = barMasterService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    /**
     * 获取分管领导列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getLeaderList")
    public Object getLeaderList() throws Exception {
        String sql = "select uuid,user_name,position_name,user_no from bar_master";
        List<PageData> leaderList = DbUtil.executeQueryList(sql);
        return leaderList;
    }

}
