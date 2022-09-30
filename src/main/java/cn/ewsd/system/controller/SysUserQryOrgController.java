package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.SysUserStore;
import cn.ewsd.system.service.SysUserQryOrgService;
import cn.ewsd.system.service.SysUserStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
@Controller
@RequestMapping("/system/sysUserQryOrg")
public class SysUserQryOrgController extends SystemBaseController {

    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserQryOrg模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysUserQryOrg/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserQryOrg分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        String userId = request.getParameter("userId");

        System.out.println("userId--->"+userId);

        if(userId!=null&&!"".equals(userId)&&!"undefined".equals(userId)){
            filterSort += " and dq.user_id = '"+userId+"'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserQryOrg> pageSet = sysUserQryOrgService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY dq.create_time"));
        return pageSet;
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserQryOrg模块编辑页面")
    public String edit() {
        return "system/sysUserQryOrg/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserQryOrg模块数据")
    public Object save(@ModelAttribute SysUserQryOrg sysUserQryOrg) {
        int result = sysUserQryOrgService.insertSelective(getSaveData(sysUserQryOrg));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserQryOrg模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserQryOrgService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDeptSet")
    public Object getDetailByUuid() {
        try{
            String deptLevel = request.getParameter("deptLevel");
            if(deptLevel==null) {
                deptLevel = "3";
            }
            String justVirtual = request.getParameter("justVirtual");
            return sysUserQryOrgService.getDeptSet(LoginInfo.getUuid(), deptLevel, justVirtual==null ? true : false);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
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
