package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthData;
import cn.ewsd.system.service.SysAuthDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags={"数据权限接口"})
@Controller
@RequestMapping("/system/sysAuthData")
public class SysAuthDataController extends SystemBaseController {

    @Autowired
    private SysAuthDataService sysAuthDataService;

    @ApiOperation(value="页面配置方法")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ControllerLog(description = "打开SysAuthData模块管理页面")
    public String index() {
        return "system/sysAuthData/index";
    }

    @ApiOperation(value="获取分页集方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysAuthData分页集数据方法")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request);
        PageSet<SysAuthData> pageSet = sysAuthDataService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ApiOperation(value="根据uuid获取详细数据方法")
    @ApiImplicitParam(name = "uuid",value = "标识",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid",method = RequestMethod.GET)
    @ControllerLog(description = "获得SysAuthData模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysAuthData sysAuthData = sysAuthDataService.selectByPrimaryKey(uuid);
        return sysAuthData;
    }

    @ApiOperation(value="打开数据权限新增页面方法")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ControllerLog(description = "打开SysAuthData模块新增页面")
    public String add() {
        return "system/sysAuthData/add";
    }

    @ApiOperation(value="保存数据权限模块数据方法")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysAuthData sysAuthData) {
        int id = sysAuthDataService.getIncreasementId();
        sysAuthData.setId(id);
        int result = sysAuthDataService.insertSelective(getSaveData(sysAuthData));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @ApiOperation(value="打开数据权限模块编辑域页面方法")
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    @ControllerLog(description = "打开SysAuthData模块编辑页面")
    public String edit() {
        return "system/sysAuthData/edit";
    }

    /**
     * 更新数据
     */
    @ApiOperation(value="更新数据权限模块数据方法")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysAuthData模块数据")
    public Object update(@ModelAttribute SysAuthData sysAuthData) {
        int result = sysAuthDataService.updateByPrimaryKeySelective(getUpdateData(sysAuthData));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ApiOperation(value="删除数据权限模块数据方法")
    @ApiImplicitParam(name="uuid",value = "标识",required = true,defaultValue = "",dataType = "String",allowMultiple = true)
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysAuthData模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysAuthDataService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ApiOperation(value="根据LevelId获取数据集合")
    @ApiImplicitParam(name = "levelId",value = "层级",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/getListByLevelId", method = RequestMethod.POST)
    public Object getListByLevelId(String levelId) {
        List<SysAuthData> sysAuthData = sysAuthDataService.getListByLevelId(levelId);
        return sysAuthData;
    }
    @ApiOperation(value="根据Pid获取数据集合")
    @ApiImplicitParam(name = "pid",value = "父级编码",dataType = "String",required = true,defaultValue = "")
    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid(String pid) {
        List<SysAuthData> sysAuthData = sysAuthDataService.getListByPid(pid);
        return sysAuthData;
    }

}
