package cn.ewsd.material.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
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

import cn.ewsd.material.model.MMatType;
import cn.ewsd.material.service.MMatTypeService;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 11:04:46
 */
@Controller
@RequestMapping("/material/mMatType")
public class MMatTypeController extends MaterialBaseController {
    @Autowired
    private MMatTypeService mMatTypeService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/mMatType/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MMatType> pageSet = mMatTypeService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time DESC", "ORDER BY type_code"));
        return pageSet;
    }

    //新增页面
    @RequestMapping("/add")
    public String add() {
        return "material/mMatType/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MMatType mMatType) {
        String errorInfo = "";
        String typeCode = mMatType.getTypeCode();
        String typeName = mMatType.getTypeName();
        if("".equals(typeCode)){
            errorInfo = "请输入分类编码！";
        }else if(typeCode.length()!=2 && typeCode.length()!=4 && typeCode.length()!=6){
            errorInfo = "分类编码格式错误！";
        }else if("".equals(typeName)){
            errorInfo = "请输入分类名称！";
        }else if(typeName.length()>25){
            errorInfo = "分类名称最多25个字符！";
        }else {
            errorInfo = mMatTypeService.checkTypeCode(typeCode, null);
        }
        if(!"".equals(errorInfo)){
            return failure(errorInfo);
        }

        int result = mMatTypeService.executeSave(getSaveData(mMatType));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开MMatType模块编辑页面")
    public String edit() {
        return "material/mMatType/edit";
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得MMatType模块详细数据")
    public Object getDetailByUuid(String uuid) {
        MMatType mMatType = mMatTypeService.queryObject(uuid);
        return mMatType;
    }

    @ResponseBody
    @RequestMapping(value = "/getMatTypeByCode")
    @ControllerLog(description = "获得MMatType模块详细数据")
    public Object getMatTypeByCode(String typeCode) {
        MMatType mMatType = mMatTypeService.getMatTypeByCode(typeCode);
        return mMatType;
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新MMatType模块数据")
    public Object update(@ModelAttribute MMatType mMatType) {
        String errorInfo = "";
        String typeCode = mMatType.getTypeCode();
        String typeName = mMatType.getTypeName();
        if("".equals(typeCode)){
            errorInfo = "请输入分类编码！";
        }else if(typeCode.length()!=2 && typeCode.length()!=4 && typeCode.length()!=6){
            errorInfo = "分类编码格式错误！";
        }else if("".equals(typeName)){
            errorInfo = "请输入分类名称！";
        }else if(typeName.length()>25){
            errorInfo = "分类名称最多25个字符！";
        }else {
            errorInfo = mMatTypeService.checkTypeCode(typeCode, mMatType.getUuid());
        }
        if(!"".equals(errorInfo)){
            return failure(errorInfo);
        }

        int result = mMatTypeService.executeUpdate(getUpdateData(mMatType));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除MMatType模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        if(!mMatTypeService.checkDeleteBatch(uuid)){
            return failure("有分类已经在材料基础数据中使用，不能删除！");
        }

        int result = mMatTypeService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getMatTypeForSelect", method = RequestMethod.POST)
    public Object getMatTypeForSelect() {
        return mMatTypeService.getMatTypeForSelect();
    }

    @ResponseBody
    @RequestMapping(value = "/getListByKeywords", method = RequestMethod.POST)
    public Object getListByKeywords(String q) {
        String fs = "use_flag = 1";
        if(!StrUtil.hasBlank(q)){
            fs+= " and (" +
                    "type_code like '%"+q+"%'" +
                    " or type_name like '%"+q+"%'"+
                    ")";
        }
        fs += " order by type_code asc";
        return mMatTypeService.getMatTypeByFS(fs);
    }
}
