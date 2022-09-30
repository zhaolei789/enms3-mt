package cn.ewsd.cost.controller;

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

import cn.ewsd.cost.model.SysUserLikeMenu;
import cn.ewsd.cost.service.SysUserLikeMenuService;

/**
 * 用户喜欢的菜单
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-30 09:10:09
 */
@Controller
@RequestMapping("/cost/sysUserLikeMenu")
public class SysUserLikeMenuController extends CostBaseController {

    @Autowired
    private SysUserLikeMenuService sysUserLikeMenuService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserLikeMenu模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/sysUserLikeMenu/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserLikeMenu分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserLikeMenu> pageSet = sysUserLikeMenuService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserLikeMenu模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserLikeMenu sysUserLikeMenu = sysUserLikeMenuService.selectByPrimaryKey(uuid);
        return sysUserLikeMenu;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserLikeMenu模块新增页面")
    public String add() {
        return "cost/sysUserLikeMenu/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserLikeMenu模块数据")
    public Object save(@ModelAttribute SysUserLikeMenu sysUserLikeMenu) {
        int result = sysUserLikeMenuService.insertSelective(getSaveData(sysUserLikeMenu));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserLikeMenu模块编辑页面")
    public String edit() {
        return "cost/sysUserLikeMenu/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserLikeMenu模块数据")
    public Object update(@ModelAttribute SysUserLikeMenu sysUserLikeMenu) {
        int result = sysUserLikeMenuService.updateByPrimaryKeySelective(getUpdateData(sysUserLikeMenu));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserLikeMenu模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserLikeMenuService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
