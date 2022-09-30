package cn.ewsd.mdata.controller;

import java.util.List;
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

import cn.ewsd.mdata.model.TdeptType;
import cn.ewsd.mdata.service.TDeptTypeService;

/**
 * 
 *
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-20 11:40:20
 */
@Controller
@RequestMapping("/mdata/tDeptType")
public class TDeptTypeController extends MdataBaseController {

    @Autowired
    private TDeptTypeService tDeptTypeService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开TDeptType模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/tDeptType/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得TDeptType分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<TdeptType> pageSet = tDeptTypeService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得TDeptType模块详细数据")
    public Object getDetailByUuid(String uuid) {
        TdeptType tDeptType = tDeptTypeService.queryObject(uuid);
        return tDeptType;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getListByDeptId")
    @ControllerLog(description = "根据部门id获取部门属性")
    public Object getListByDeptId() {
        String deptId = request.getParameter("linkKey");
        List<TdeptType> tDeptTypeList = tDeptTypeService.getListByDeptId(deptId);
        return tDeptTypeList;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开TDeptType模块新增页面")
    public String add() {
        return "mdata/tDeptType/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存TDeptType模块数据")
    public Object save(TdeptType tDeptType) {
        if(tDeptTypeService.queryByDeptIdAndDictKey(tDeptType.getDeptId()+"", tDeptType.getDictKey())>0){
            return failure("请勿重复添加属性！");
        }
        if(tDeptType.getOrderNo()==null){
            tDeptType.setOrderNo(0);
        }
        int result = tDeptTypeService.executeSave(getSaveData(tDeptType));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开TDeptType模块编辑页面")
    public String edit() {
        return "mdata/tDeptType/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新TDeptType模块数据")
    public Object update(@ModelAttribute TdeptType tDeptType) {
        int result = tDeptTypeService.executeUpdate(getUpdateData(tDeptType));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除TDeptType模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = tDeptTypeService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
