package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthdataFilter;
import cn.ewsd.system.service.SysAuthdataFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据权限过滤器
 */
@Controller
@RequestMapping("/system/sysAuthdataFilter")
public class SysAuthdataFilterController extends SystemBaseController {

    @Autowired
    private SysAuthdataFilterService sysAuthdataFilterService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysAuthdataFilter模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysAuthdataFilter/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysAuthdataFilter分页集数据")
    public Object getPageSet(PageParam pageParam, String apiUuid) {
        String filterSort = " and api_uuid = '" + apiUuid + "'" + getAuthFilter();
        filterSort = BaseUtils.filterSort(request, filterSort).replace("create_time DESC", "sort ASC");
        PageSet<SysAuthdataFilter> pageSet = sysAuthdataFilterService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysAuthdataFilter模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysAuthdataFilter sysAuthdataFilter = sysAuthdataFilterService.selectByPrimaryKey(uuid);
        return sysAuthdataFilter;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysAuthdataFilter模块新增页面")
    public String add() {
        return "system/sysAuthdataFilter/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysAuthdataFilter sysAuthdataFilter) {
        int result = sysAuthdataFilterService.insertSelective(getSaveData(sysAuthdataFilter));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysAuthdataFilter模块编辑页面")
    public String edit() {
        return "system/sysAuthdataFilter/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysAuthdataFilter模块数据")
    public Object update(@ModelAttribute SysAuthdataFilter sysAuthdataFilter) {
        int result = sysAuthdataFilterService.updateByPrimaryKeySelective(getUpdateData(sysAuthdataFilter));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysAuthdataFilter模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysAuthdataFilterService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getListByApiUuid")
    @ControllerLog(description = "根据权限API获得UUID")
    public List<SysAuthdataFilter> getListByApiUuid(String apiUuid) {
        return sysAuthdataFilterService.getListByApiUuid(apiUuid);
    }

}
