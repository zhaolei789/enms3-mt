package cn.ewsd.logistics.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import cn.ewsd.logistics.model.SysSequence;
import cn.ewsd.logistics.service.SysSequenceService;

/**
 * 
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-07-27 09:52:49
 */
@Controller
@RequestMapping("/logistics/sysSequence")
public class SysSequenceController extends LogisticsBaseController {

    @Autowired
    private SysSequenceService sysSequenceService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysSequence模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "logistics/sysSequence/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysSequence分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysSequence> pageSet = sysSequenceService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysSequence模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysSequence sysSequence = sysSequenceService.selectByPrimaryKey(uuid);
        return sysSequence;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysSequence模块新增页面")
    public String add() {
        return "logistics/sysSequence/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysSequence模块数据")
    public Object save(@ModelAttribute SysSequence sysSequence) {
        int result = sysSequenceService.insertSelective(getSaveData(sysSequence));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysSequence模块编辑页面")
    public String edit() {
        return "logistics/sysSequence/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysSequence模块数据")
    public Object update(@ModelAttribute SysSequence sysSequence) {
        int result = sysSequenceService.updateByPrimaryKeySelective(getUpdateData(sysSequence));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysSequence模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysSequenceService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
