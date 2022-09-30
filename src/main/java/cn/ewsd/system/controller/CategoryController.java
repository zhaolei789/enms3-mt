package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Category;
import cn.ewsd.system.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"文章栏目接口"})
@Controller
@RequestMapping("/system/category")
public class CategoryController extends SystemBaseController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "打开文章栏目窗口")
    @ApiImplicitParam(value = "type",name = "类别",defaultValue = "",required = true,dataType = "String")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ControllerLog(description = "打开SysTopicCategory模块管理页面")
    public String index(String type) {
        request.setAttribute("type",type);
        return "system/category/index";
    }

    @ApiOperation(value = "获得文章栏目分业集数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysTopicCategory分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request);
        PageSet<Category> pageSet = categoryService.getPageSet(pageParam, filterSort);
        return pageSet;
    }


    @ApiOperation(value = "获得文章栏目模块详细数据")
    @ApiImplicitParam(name = "uuid",value = "标识",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid",method = RequestMethod.POST)
    @ControllerLog(description = "获得SysTopicCategory模块详细数据")
    public Object getDetailByUuid(String uuid) {
        Category oaCategory = categoryService.selectByPrimaryKey(uuid);
        return oaCategory;
    }

    @ApiOperation(value = "添加文章栏目")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ControllerLog(description = "打开SysTopicCategory模块新增页面")
    public String add() {
        return display();
    }

    @ApiOperation(value = "保存文章栏目模块信息")
    @ApiImplicitParam(name = "type",value = "类型",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute Category oaCategory) {
        oaCategory.setType(request.getParameter("type"));
        int id = categoryService.getIncreasementId();
        oaCategory.setId(id);
        oaCategory.setLevelId(oaCategory.getLevelId() + 1);
        int result = categoryService.insertSelective(getSaveData(oaCategory));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @ApiOperation(value = "编辑文章栏目模块信息")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ControllerLog(description = "打开SysTopicCategory模块编辑页面")
    public String edit() {
        return "system/category/edit";
    }

    /**
     * 更新数据
     */
    @ApiOperation(value = "更新文章栏目信息")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysTopicCategory模块数据")
    public Object update(@ModelAttribute Category oaCategory) {
        oaCategory.setModifierId(LoginInfo.getUserNameId());
        oaCategory.setModifier(LoginInfo.getUserName());
        oaCategory.setModifyTime(new Date());
        int result = categoryService.updateByPrimaryKeySelective(oaCategory);
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ApiOperation(value = "根据标识批量删除文章栏目信息")
    @ApiImplicitParam(name="uuids",value = "标识",defaultValue = "",dataType = "String",required = true,allowMultiple = true)
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysTopicCategory模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = categoryService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


    @ApiOperation(value = "根据文章栏目层级和栏目类别获取文章栏目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "levelId",value = "层级Id",required = true,defaultValue = "",dataType = "String"),
            @ApiImplicitParam(name = "type",value = "文章栏目类别",required = true,defaultValue = "",dataType = "文章栏目类别"),
    })
    @ResponseBody
    @RequestMapping(value = "/getCategorysByTypeAndLevelId",method = RequestMethod.POST)
    public Object getCategorysByTypeAndLevelId(String levelId,String type) {
        if("0".equals(levelId)){
            List<Category> oaCategories1= categoryService.getCategorysByTypeAndLevelId(levelId,"root");
            return oaCategories1;
        }else{
            List<Category> oaCategories= categoryService.getCategorysByTypeAndLevelId(levelId,type);
            return oaCategories;
        }

    }

    @ApiOperation(value = "根据id获取文章栏目集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value= "类别",required = true,defaultValue ="",dataType = "String"),
            @ApiImplicitParam(name= "pid",value = "父级标识",required = true,defaultValue = "",dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid(String type) {
        String pid = request.getParameter("pid");
        List<Category> oaCategories = categoryService.selectListByPid(pid,type);
        return oaCategories;
    }

    @ApiOperation(value = "保存根节点")
    @ApiImplicitParam(name = "type",value = "类别",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "saveRoot", method = RequestMethod.POST)
    public Object saveRoot(Category oaCategory,String type) throws Exception {
        oaCategory.setType(type);
        int id = categoryService.getIncreasementId();
        oaCategory.setId(id);
        int result = categoryService.insertSelective(getSaveData(oaCategory));
        return result > 0 ? success("成功！") : failure("失败！");
    }

    @ApiOperation(value = "编辑节点")
    @RequestMapping(value = "/editRoot",method = RequestMethod.POST)
    @ControllerLog(description = "编辑页面")
    public String editRoot() {
        return "system/category/editRoot";
    }


    @ApiOperation(value = "获取父级标识信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id",defaultValue = "",required = true,dataType ="String"),
            @ApiImplicitParam(name = "type",value = "类别",defaultValue = "",required = true,dataType ="String"),
    })
    @ResponseBody
    @RequestMapping(value = "getFatherIds",method = RequestMethod.POST)
    public Object getFatherIds(String id,String type) {
        Map map = new HashMap();
        map.put("p1", "sys_category");
        map.put("p2", id);
        map.put("p3",1);
        map.put("p4", "idStr");
        categoryService.getFatherId(map);
        String ids = map.get("p4").toString();
        return ids;
    }
}
