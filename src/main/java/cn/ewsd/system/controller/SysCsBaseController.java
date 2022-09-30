package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysCsBase;
import cn.ewsd.system.service.SysCsBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/system/sysCsBase")
public class SysCsBaseController extends SystemBaseController {

    @Autowired
    private SysCsBaseService sysCsBaseService;

    @RequestMapping("/index")
    @ControllerLog(description = "打开SysCsBase模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysCsBase分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request);
//        String filterSort = "creatorOrgId IN (" + getFilterByOrgId() + ")" + BaseUtils.filterSort(request);
        PageSet<SysCsBase> pageSet = sysCsBaseService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysCsBase模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysCsBase sysCsBase = sysCsBaseService.selectByPrimaryKey(uuid);
        return sysCsBase;
    }

    @RequestMapping("/add")
    @ControllerLog(description = "打开SysCsBase模块新增页面")
    public String add() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysCsBase sysCsBase) {
        int result = sysCsBaseService.insertSelective(getSaveData(sysCsBase));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysCsBase模块编辑页面")
    public String edit() {
        return display();
    }

    /**
     * 更新数据
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysCsBase模块数据")
    public Object update(@ModelAttribute SysCsBase sysCsBase) {
        int result = sysCsBaseService.updateByPrimaryKeySelective(getUpdateData(sysCsBase));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysCsBase模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysCsBaseService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
