package cn.ewsd.mdata.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.mdata.model.SysTenant;
import cn.ewsd.mdata.service.SysTenantService;

/**
 * 租户
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-06-02 18:20:18
 */
@Controller
@RequestMapping("/mdata/sysTenant")
public class SysTenantController extends MdataBaseController {

    @Autowired
    private SysTenantService sysTenantService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysTenant模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "mdata/sysTenant/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysTenant分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysTenant> pageSet = sysTenantService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysTenant模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysTenant sysTenant = sysTenantService.selectByPrimaryKey(uuid);
        return sysTenant;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysTenant模块新增页面")
    public String add() {
        return "mdata/sysTenant/add";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysTenant模块数据")
    public Object save(@ModelAttribute SysTenant sysTenant) {
        if (!strNotNull(sysTenant.getUuid())){
            return failure("租户ID必须输入");
        }
        if(sysTenantService.queryListByFS("uuid='"+sysTenant.getUuid()+"'").size()>0){
            return failure("租户已存在");
        }
        sysTenant.setLogo("");
        sysTenant.setOverdueTime(null);
        sysTenant.setType(1);
        sysTenant.setIsDel(0);
        int result = sysTenantService.insertSelective(getSaveData(sysTenant));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysTenant模块编辑页面")
    public String edit() {
        return "mdata/sysTenant/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysTenant模块数据")
    public Object update(@ModelAttribute SysTenant sysTenant) {
        int result = sysTenantService.updateByPrimaryKeySelective(getUpdateData(sysTenant));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysTenant模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysTenantService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
