package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserDept;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.service.SysUserDeptService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/system/sysUserDept")
public class SysUserDeptController extends SystemBaseController {

    @Autowired
    private SysUserDeptService sysUserDeptService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserDept模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysUserDept/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserDept分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        String userId = request.getParameter("userId");
        if(userId!=null&&!"".equals(userId)&&!"undefined".equals(userId)){
            filterSort += " and ud.user_id = '"+userId+"'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserDept> pageSet = sysUserDeptService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY ud.create_time"));
        return pageSet;
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserDept模块编辑页面")
    public String edit() {
        return "system/sysUserDept/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserDept模块数据")
    public Object save(@ModelAttribute SysUserDept sysUserDept) {
        int result = sysUserDeptService.insertSelective(getSaveData(sysUserDept));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserDept模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserDeptService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


//    //获取详情
//    @ResponseBody
//    @RequestMapping(value = "/getDetailByUuid")
//    @ControllerLog(description = "获得SysUserStore模块详细数据")
//    public Object getDetailByUuid(String uuid) {
//        SysUserStore sysUserStore = sysUserStoreService.queryObject(uuid);
//        return sysUserStore;
//    }
//
//    //新增页面
//    @RequestMapping("/add")
//    @ControllerLog(description = "打开SysUserStore模块新增页面")
//    public String add() {
//        return "system/sysUserStore/edit";
//    }
//
//    //更新数据
//    @ResponseBody
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @ControllerLog(description = "更新SysUserStore模块数据")
//    public Object update(@ModelAttribute SysUserStore sysUserStore) {
//        int result = sysUserStoreService.updateByPrimaryKeySelective(getUpdateData(sysUserStore));
//        return result > 0 ? success("更新成功！") : failure("更新失败！");
//    }
//
//

}
