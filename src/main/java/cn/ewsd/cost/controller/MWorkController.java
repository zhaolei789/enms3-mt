package cn.ewsd.cost.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.FilterUtils;
import cn.ewsd.base.utils.Snow;
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

import cn.ewsd.cost.model.MWork;
import cn.ewsd.cost.service.MWorkService;

/**
 * 生产
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
@Controller
@RequestMapping("/cost/mWork")
public class MWorkController extends CostBaseController {

    @Autowired
    private MWorkService mWorkService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开MWork模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        String Xdate = XDate.getDate();
        request.setAttribute("query_year",Xdate.substring(0,4));
        request.setAttribute("query_month",Xdate.substring(4,6));
        return "cost/mWork/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得MWork分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        //筛选
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");

        if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }
        filterSort += " and work_month = '"+query_year+query_month+"'";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        if(FilterUtils.checkHaveFilterField("team_no_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("team_no_name",filterSort,"o1.text");
        }
        PageSet<MWork> pageSet = mWorkService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得MWork模块详细数据")
    public Object getDetailByUuid(String uuid) {
        MWork mWork = mWorkService.queryObject(uuid);
        return mWork;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开MWork模块新增页面")
    public String add() {
        return "cost/mWork/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存MWork模块数据")
    public Object save(@ModelAttribute MWork mWork) {
        mWork.setWorkId(Snow.getUUID());
        if(mWork.getPrjNo()==null || "".equals(mWork.getPrjNo())){
            mWork.setPrjNo("0");
        }
        int result = mWorkService.insertSelective(getSaveData(mWork));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开MWork模块编辑页面")
    public String edit() {
        return "cost/mWork/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新MWork模块数据")
    public Object update(@ModelAttribute MWork mWork) {
        if(mWork.getPrjNo()==null || "".equals(mWork.getPrjNo())){
            mWork.setPrjNo("0");
        }
        int result = mWorkService.updateByPrimaryKeySelective(getUpdateData(mWork));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除MWork模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = mWorkService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    /**
     * 获取工程列表
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPrjListByTeamNo")
    public Object getPrjListByTeamNo(String teamNo) throws Exception {
        String sql = "SELECT prj_no,prj_name FROM m_prj WHERE team_no="+teamNo+" GROUP BY prj_no,prj_name";
        List<PageData> prjList = DbUtil.executeQueryList(sql);
        List<PageData> prjList2 = new ArrayList<>();
        PageData prj;
        for (int i = 0; i < prjList.size(); i++) {
        prj = new PageData();
            prj.put("prj_no",prjList.get(i).get("prj_no").toString());
            prj.put("prj_name",prjList.get(i).getString("prj_name"));
            prjList2.add(prj);
        }
        return prjList2;
    }

}
