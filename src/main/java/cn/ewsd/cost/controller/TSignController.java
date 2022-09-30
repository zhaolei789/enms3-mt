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

import cn.ewsd.cost.model.TSign;
import cn.ewsd.cost.service.TSignService;

/**
 * 会签
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-17 10:27:06
 */
@Controller
@RequestMapping("/cost/tSign")
public class TSignController extends CostBaseController {

    @Autowired
    private TSignService tSignService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开TSign模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/tSign/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得TSign分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<TSign> pageSet = tSignService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得TSign模块详细数据")
    public Object getDetailByUuid(String uuid) {
        TSign tSign = tSignService.queryObject(uuid);
        return tSign;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开TSign模块新增页面")
    public String add() {
        return "cost/tSign/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存TSign模块数据")
    public Object save(@ModelAttribute TSign tSign) {
        int result = tSignService.insertSelective(getSaveData(tSign));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开TSign模块编辑页面")
    public String edit() {
        return "cost/tSign/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新TSign模块数据")
    public Object update(@ModelAttribute TSign tSign) {
        int result = tSignService.updateByPrimaryKeySelective(getUpdateData(tSign));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除TSign模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = tSignService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
