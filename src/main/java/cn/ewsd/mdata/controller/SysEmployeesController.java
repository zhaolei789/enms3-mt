package cn.ewsd.mdata.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.PingYinUtil;
import cn.ewsd.mdata.model.User;
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

import cn.ewsd.mdata.model.SysEmployees;
import cn.ewsd.mdata.service.SysEmployeesService;

/**
 * 
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-03-23 11:33:15
 */
@Controller
@RequestMapping("/mdata/sysEmployees")
public class SysEmployeesController extends MdataBaseController {

    @Autowired
    private SysEmployeesService sysEmployeesService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysEmployees模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "mdata/sysEmployees/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysEmployees分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysEmployees> pageSet = sysEmployeesService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysEmployees模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysEmployees sysEmployees = sysEmployeesService.selectByPrimaryKey(uuid);
        return sysEmployees;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysEmployees模块新增页面")
    public String add() {
        return "mdata/sysEmployees/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysEmployees模块数据")
    public Object save(@ModelAttribute SysEmployees sysEmployees) {
        SysEmployees sysEmployees1 = sysEmployeesService.getByEmpNo(sysEmployees.getEmpNo());
        sysEmployees.setEmpNameJp(PingYinUtil.getFirstSpell(sysEmployees.getEmpName()));
        int result = 0;
        if(null !=  sysEmployees1) {
            result = sysEmployeesService.insertSelective(getSaveData(sysEmployees));
        }
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysEmployees模块编辑页面")
    public String edit() {
        return "mdata/sysEmployees/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysEmployees模块数据")
    public Object update(@ModelAttribute SysEmployees sysEmployees) {
        sysEmployees.setEmpNameJp(PingYinUtil.getFirstSpell(sysEmployees.getEmpName()));
        int result = sysEmployeesService.updateByPrimaryKeySelective(getUpdateData(sysEmployees));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }


    @ResponseBody
    @RequestMapping(value = "/getListByKeywords", method = RequestMethod.POST)
    public Object getListByKeywords(String q) throws Exception {
        List<SysEmployees> sysEmployeesList = null;
        if (null == q || q.equals("")) {
            sysEmployeesList = sysEmployeesService.selectAllList();
        } else {
            sysEmployeesList = sysEmployeesService.getLimitedListByQ(q);
        }
        return sysEmployeesList;
    }



    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysEmployees模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysEmployeesService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }



}
