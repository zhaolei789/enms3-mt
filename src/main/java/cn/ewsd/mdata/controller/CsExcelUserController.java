package cn.ewsd.mdata.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.CsExcelUser;
import cn.ewsd.mdata.service.CsExcelUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户导入导出测试
 *
 * @Author fengkai
 * @Email fengkai@ewsd.cn
 * @Date 2019-08-29 14:01:55
 */
@Controller
@RequestMapping("/mdata/csExcelUser")
public class CsExcelUserController extends MdataBaseController {

    @Autowired
    private CsExcelUserService csExcelUserService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开CsExcelUser模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "ucenter/csExcelUser/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得CsExcelUser分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<CsExcelUser> pageSet = csExcelUserService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得CsExcelUser模块详细数据")
    public Object getDetailByUuid(String uuid) {
        CsExcelUser csExcelUser = csExcelUserService.selectByPrimaryKey(uuid);
        return csExcelUser;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开CsExcelUser模块新增页面")
    public String add() {
        return "ucenter/csExcelUser/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存CsExcelUser模块数据")
    public Object save(@ModelAttribute CsExcelUser csExcelUser) {
        int result = csExcelUserService.insertSelective(getSaveData(csExcelUser));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开CsExcelUser模块编辑页面")
    public String edit() {
        return "ucenter/csExcelUser/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新CsExcelUser模块数据")
    public Object update(@ModelAttribute CsExcelUser csExcelUser) {
        int result = csExcelUserService.updateByPrimaryKeySelective(getUpdateData(csExcelUser));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除CsExcelUser模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = csExcelUserService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
