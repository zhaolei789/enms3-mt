package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysGenerator;
import cn.ewsd.system.service.SysGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/system/sysGenerator")
public class SysGeneratorController extends SystemBaseController {

    @Autowired
    private SysGeneratorService sysGeneratorService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysGenerator模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysGenerator/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysGenerator分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request, getAuthFilter());
        PageSet<SysGenerator> pageSet = sysGeneratorService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysGenerator模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysGenerator sysGenerator = sysGeneratorService.selectByPrimaryKey(uuid);
        return sysGenerator;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysGenerator模块新增页面")
    public String add() {
        return "system/sysGenerator/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysGenerator sysGenerator) {
        int result = sysGeneratorService.insertSelective(getSaveData(sysGenerator));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysGenerator模块编辑页面")
    public String edit() {
        return "system/sysGenerator/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysGenerator模块数据")
    public Object update(@ModelAttribute SysGenerator sysGenerator) {
        int result = sysGeneratorService.updateByPrimaryKeySelective(getUpdateData(sysGenerator));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysGenerator模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysGeneratorService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
