package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthdataGroup;
import cn.ewsd.system.service.SysAuthdataGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据权限分组
 */
@Controller
@RequestMapping("/system/sysAuthdataGroup")
public class SysAuthdataGroupController extends SystemBaseController {

    @Autowired
    private SysAuthdataGroupService sysAuthdataGroupService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysAuthdataGroup模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysAuthdataGroup/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysAuthdataGroup分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request, getAuthFilter()).replace("create_time DESC", "sort ASC");
        PageSet<SysAuthdataGroup> pageSet = sysAuthdataGroupService.getPageSet(pageParam, filterSort);
        return pageSet;
    }
    /*
     * @MethodName getAllList
     * @Description 获取所有的数据集合
     * @Param pageParam
     * @Return java.lang.Object
     * @Author 宋景民<songjingmin@zuoyoutech.com>
     * @Date 2019/1/3 22:17
     */
    @ResponseBody
    @RequestMapping(value = "/getAllList", method = RequestMethod.POST)
    @ControllerLog(description = "获得数据集合")
    public Object getAllList() {
        List<SysAuthdataGroup> sysAuthdataGroupList = sysAuthdataGroupService.selectAll();
        return sysAuthdataGroupList;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysAuthdataGroup模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysAuthdataGroup sysAuthdataGroup = sysAuthdataGroupService.selectByPrimaryKey(uuid);
        return sysAuthdataGroup;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysAuthdataGroup模块新增页面")
    public String add() {
        return "system/sysAuthdataGroup/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysAuthdataGroup sysAuthdataGroup) {
        // 1.获取最大的ID数
        int maxId = sysAuthdataGroupService.getMaxById();
        // 2.设置ID值
        sysAuthdataGroup.setId(maxId + 1);
        int result = sysAuthdataGroupService.insertSelective(getSaveData(sysAuthdataGroup));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysAuthdataGroup模块编辑页面")
    public String edit() {
        return "system/sysAuthdataGroup/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysAuthdataGroup模块数据")
    public Object update(@ModelAttribute SysAuthdataGroup sysAuthdataGroup) {
        int result = sysAuthdataGroupService.updateByPrimaryKeySelective(getUpdateData(sysAuthdataGroup));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysAuthdataGroup模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysAuthdataGroupService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getGroupUuidsByIds", method = RequestMethod.GET)
    @ControllerLog(description = "根据权限组ID获得UUID")
    public String getGroupUuidsByIds(@RequestParam("ids") String ids) {
        return sysAuthdataGroupService.getGroupUuidsByIds(ids);
    }

}
