package cn.ewsd.cost.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.cost.model.BarMaster;
import cn.ewsd.cost.service.BarMasterService;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.UserService;
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

import cn.ewsd.cost.model.SysUserBarMaster;
import cn.ewsd.cost.service.SysUserBarMasterService;

import javax.annotation.Resource;

/**
 * 用户与矿长
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 14:44:35
 */
@Controller
@RequestMapping("/cost/sysUserBarMaster")
public class SysUserBarMasterController extends CostBaseController {

    @Autowired
    private SysUserBarMasterService sysUserBarMasterService;
    @Resource
    private UserService userService;
    @Autowired
    private BarMasterService barMasterService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserBarMaster模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/sysUserBarMaster/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserBarMaster分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        String userId = request.getParameter("userId");
        if(userId!=null&&!"".equals(userId)){
            filterSort += " and user_id = '"+userId+"'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserBarMaster> pageSet = sysUserBarMasterService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserBarMaster模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserBarMaster sysUserBarMaster = sysUserBarMasterService.selectByPrimaryKey(uuid);
        return sysUserBarMaster;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserBarMaster模块新增页面")
    public String add() {
        return "cost/sysUserBarMaster/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserBarMaster模块数据")
    public Object save(@ModelAttribute SysUserBarMaster sysUserBarMaster) throws Exception {
        String userId = request.getParameter("userId");
        if(userId==null||"".equals(userId)){
            return failure("获取用户信息失败！");
        }
        User user = userService.getUserByUuid(userId);
        if(user != null){
            sysUserBarMaster.setUserNameId(user.getUserNameId());
        }
        sysUserBarMaster.setUserId(userId);
        sysUserBarMaster.setIsDel(0);

        //查重复
        String checkSql = " select * from sys_user_bar_master where user_id='"+userId+"' and master_id='"+sysUserBarMaster.getMasterId()+"'";
        List<PageData> checkObjList = DbUtil.executeQueryList(checkSql);
        if(checkObjList!=null&&checkObjList.size()>0){
            return failure("保存失败,请勿重复添加！");
        }
        int result = sysUserBarMasterService.insertSelective(getSaveData(sysUserBarMaster));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserBarMaster模块编辑页面")
    public String edit() {
        return "cost/sysUserBarMaster/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserBarMaster模块数据")
    public Object update(@ModelAttribute SysUserBarMaster sysUserBarMaster) {
        int result = sysUserBarMasterService.updateByPrimaryKeySelective(getUpdateData(sysUserBarMaster));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserBarMaster模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserBarMasterService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
