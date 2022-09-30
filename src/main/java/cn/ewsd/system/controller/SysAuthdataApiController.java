package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysAuthdataApi;
import cn.ewsd.system.service.SysAuthdataApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据权限接口
 *
 */
@Controller
@RequestMapping("/system/sysAuthdataApi")
public class SysAuthdataApiController extends SystemBaseController {

    @Autowired
    private SysAuthdataApiService sysAuthdataApiService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysAuthdataApi模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysAuthdataApi/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysAuthdataApi分页集数据")
    public Object getPageSet(PageParam pageParam, String groupUuid) {
        String myFilter = " and group_uuid = '" + groupUuid + "'" + getAuthFilter();
        String filterSort = BaseUtils.filterSort(request, myFilter).replace("create_time DESC", "sort ASC");
        PageSet<SysAuthdataApi> pageSet = sysAuthdataApiService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysAuthdataApi模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysAuthdataApi sysAuthdataApi = sysAuthdataApiService.selectByPrimaryKey(uuid);
        return sysAuthdataApi;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysAuthdataApi模块新增页面")
    public String add() {
        return "system/sysAuthdataApi/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save( SysAuthdataApi sysAuthdataApi,String groupUuid) {
        if(groupUuid == null || groupUuid.trim().equals("") ){
            return failure("保存失败！");
        }
        sysAuthdataApi.setGroupUuid(groupUuid);
        int result = sysAuthdataApiService.insertSelective(getSaveData(sysAuthdataApi));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysAuthdataApi模块编辑页面")
    public String edit() {
        return "system/sysAuthdataApi/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysAuthdataApi模块数据")
    public Object update(@ModelAttribute SysAuthdataApi sysAuthdataApi) {
        int result = sysAuthdataApiService.updateByPrimaryKeySelective(getUpdateData(sysAuthdataApi));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysAuthdataApi模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysAuthdataApiService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getListByGroupUuidsAndUrl", method = RequestMethod.GET)
    @ControllerLog(description = "根据权限获得url")
    public List<SysAuthdataApi> getListByGroupUuidsAndUrl(@RequestParam("uuids") String uuids, @RequestParam("url") String url) {
        String[] groupUuids = uuids.split(",");
        List<SysAuthdataApi> list = sysAuthdataApiService.getListByGroupUuidsAndUrl(groupUuids, url);
        return list;
    }
}
