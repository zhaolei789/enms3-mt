package cn.ewsd.system.controller;

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

import cn.ewsd.system.model.SysUserItem;
import cn.ewsd.system.service.SysUserItemService;

@Controller
@RequestMapping("/system/sysUserItem")
public class SysUserItemController extends SystemBaseController {

    @Autowired
    private SysUserItemService sysUserItemService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserItem模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysUserItem/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserItem分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        String userId = request.getParameter("userId");
        if(userId!=null&&!"".equals(userId)&&!"undefined".equals(userId)){
            filterSort += " and f.user_id = '"+userId+"'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserItem> pageSet = sysUserItemService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY f.create_time"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserItem模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserItem sysUserItem = sysUserItemService.queryObject(uuid);
        return sysUserItem;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserItem模块新增页面")
    public String add() {
        return "system/sysUserItem/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserItem模块数据")
    public Object save(@ModelAttribute SysUserItem sysUserItem) {
        int result = sysUserItemService.insertSelective(getSaveData(sysUserItem));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserItem模块编辑页面")
    public String edit() {
        return "system/sysUserItem/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserItem模块数据")
    public Object update(@ModelAttribute SysUserItem sysUserItem) {
        int result = sysUserItemService.updateByPrimaryKeySelective(getUpdateData(sysUserItem));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserItem模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserItemService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
