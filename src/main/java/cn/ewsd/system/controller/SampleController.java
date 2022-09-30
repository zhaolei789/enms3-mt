package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.common.Constants;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.JsonUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Sample;
import cn.ewsd.system.service.SampleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@SuppressWarnings({"rawtypes","unchecked"})
@Controller("sampleController")
@RequestMapping("/system/sample")
public class SampleController extends SystemBaseController {

    @Resource
    private SampleService sampleService;


    @ControllerLog(description="打开Sample模块数据列表")
    @RequestMapping("index")
    public String index() {
        return display();
    }

    @ResponseBody
    @ControllerLog(description="获取Sample模块分页数据")
    @RequestMapping(value="getPageSet")
    public Object getPageSet(PageParam pageParam) throws Exception {
//        hql = "FROM Sample WHERE " + BaseUtils.filter(request) + " ORDER BY createTime DESC";
//        return sampleService.getPageSetByHql(request, hql);

        String filterSort = BaseUtils.filterSort(request);
        PageSet<Sample> pageSet = sampleService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ControllerLog(description="打开Sample模块新增界面")
    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ControllerLog(description="打开Sample模块编辑界面")
    @RequestMapping("edit")
    public String edit() {
        return display();
    }

    @ResponseBody
    @ControllerLog(description="获取Sample模块详细数据")
    @RequestMapping(value="getDetailByUuid")
    public Object getDetailByUuid(String uuid) throws Exception {
        return sampleService.selectByPrimaryKey(uuid);
    }

    @ResponseBody
    @ControllerLog(description="保存Sample模块数据")
    @RequestMapping(value="save", method= RequestMethod.POST)
    public Integer save(Sample sample) throws Exception {
        return sampleService.insertSelective(getSaveData(sample));
    }

    @ResponseBody
    @ControllerLog(description="更新Sample模块数据")
    @RequestMapping(value="update", method= RequestMethod.POST)
    public Integer update(Sample sample) throws Exception {
        return sampleService.updateByPrimaryKey(getUpdateData(sample));
    }

    @ResponseBody
    @ControllerLog(description="删除Sample模块数据")
    @RequestMapping(value="delete", method= RequestMethod.POST)
    public Object delete() throws Exception {
        String uuids = request.getParameter("uuids");
        //String hql = "DELETE FROM Sample WHERE uuid IN ("+ uuids + ")";
        if(sampleService.deleteByPrimaryKey(uuids) >= 1) {
            return JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
        } else {
            return JsonUtils.messageJson(300, Constants.OPERATE_TIPS, Constants.OPERATE_FAILURE_MSG);
        }
    }

    @ControllerLog(description="导出Sample模块数据")
    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<Sample> samples = sampleService.selectAll();
        //PoiUtils.exportExcel(response, request.getParameter("excelTitle"), request.getParameter("colName"), request.getParameter("fieldName"), samples);
    }

}