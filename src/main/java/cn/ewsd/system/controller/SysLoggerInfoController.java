package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysLoggerInfo;
import cn.ewsd.system.service.SysLoggerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/system/sysLoggerInfo")
public class SysLoggerInfoController extends SystemBaseController {

    @Autowired
    private SysLoggerInfoService sysLoggerInfoService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysLoggerInfo模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysLoggerInfo/index";
    }
    //分页页面
    @RequestMapping("/indexError")
    @ControllerLog(description = "打开SysLoggerInfo模块管理页面")
    public String indexError(@RequestParam Map<String, Object> params) {
        return "system/sysLoggerInfo/indexError";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysLoggerInfo分页集数据")
    public Object getPageSet(PageParam pageParam,String isError) {
        String filterSort = "";
        if(isError!=null){
            filterSort += " ali_http_status_code <> 200 and ";
        }
        filterSort += BaseUtils.filterSort(request, getAuthFilter());

        PageSet<SysLoggerInfo> pageSet = sysLoggerInfoService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysLoggerInfo模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysLoggerInfo sysLoggerInfo = sysLoggerInfoService.selectByPrimaryKey(uuid);
        return sysLoggerInfo;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysLoggerInfo模块新增页面")
    public String add() {
        return "system/sysLoggerInfo/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysLoggerInfo sysLoggerInfo) {
        int result = sysLoggerInfoService.insertSelective(getSaveData(sysLoggerInfo));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysLoggerInfo模块编辑页面")
    public String edit() {
        return "system/sysLoggerInfo/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysLoggerInfo模块数据")
    public Object update(@ModelAttribute SysLoggerInfo sysLoggerInfo) {
        int result = sysLoggerInfoService.updateByPrimaryKeySelective(getUpdateData(sysLoggerInfo));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysLoggerInfo模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysLoggerInfoService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
