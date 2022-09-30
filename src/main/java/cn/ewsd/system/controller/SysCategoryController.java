package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.SysCategory;
import cn.ewsd.system.service.SysCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/sysCategory")
public class SysCategoryController extends SystemBaseController {

    @Autowired
    private SysCategoryService sysCategoryService;

    @RequestMapping("/index")
    @ControllerLog(description = "打开SysTopicCategory模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysTopicCategory分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request);
        PageSet<SysCategory> pageSet = sysCategoryService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysTopicCategory模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysCategory sysCategory = sysCategoryService.selectByPrimaryKey(uuid);
        return sysCategory;
    }

    @RequestMapping("/add")
    @ControllerLog(description = "打开SysTopicCategory模块新增页面")
    public String add() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute SysCategory sysCategory) {
        sysCategory.setType(request.getParameter("type"));
        int id = sysCategoryService.getIncreasementId();
        sysCategory.setId(id);
        sysCategory.setLevelId(sysCategory.getLevelId() + 1);
        int result = sysCategoryService.insertSelective(getSaveData(sysCategory));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysTopicCategory模块编辑页面")
    public String edit() {
        return display();
    }

    /**
     * 更新数据
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysTopicCategory模块数据")
    public Object update(@ModelAttribute SysCategory sysCategory) {
        sysCategory.setModifierId(LoginInfo.getUserNameId());
        sysCategory.setModifier(LoginInfo.getUserName());
        sysCategory.setModifyTime(new Date());
        int result = sysCategoryService.updateByPrimaryKeySelective(sysCategory);
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysTopicCategory模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysCategoryService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


    @ResponseBody
    @RequestMapping(value = "/getCategorysByTypeAndLevelId")
    public Object getCategorysByTypeAndLevelId(String levelId,String type) {
        if("0".equals(levelId)){
            List<SysCategory> sysCategories1= sysCategoryService.getCategorysByTypeAndLevelId(levelId,"root");
            return sysCategories1;
        }else{
            List<SysCategory> sysCategories= sysCategoryService.getCategorysByTypeAndLevelId(levelId,type);
            return sysCategories;
        }

    }

    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid(String type) {
        String pid = request.getParameter("pid");
        List<SysCategory> sysCategories = sysCategoryService.selectListByPid(pid,type);
        return sysCategories;
    }

    @ResponseBody
    @RequestMapping(value = "saveRoot", method = RequestMethod.POST)
    public Object saveRoot(SysCategory sysCategory,String type) throws Exception {
        sysCategory.setType(type);
        int id = sysCategoryService.getIncreasementId();
        sysCategory.setId(id);
        int result = sysCategoryService.insertSelective(getSaveData(sysCategory));
        return result > 0 ? success("成功！") : failure("失败！");
    }

    @RequestMapping("/editRoot")
    @ControllerLog(description = "编辑页面")
    public String editRoot() {
        return display();
    }

    @ResponseBody
    @RequestMapping("getFatherIds")
    public Object getFatherIds(String id,String type) {
        Map map = new HashMap();
        map.put("p1", "sys_category");
        map.put("p2", id);
        map.put("p3",1);
        map.put("p4", "idStr");
        sysCategoryService.getFatherId(map);
        String ids = map.get("p4").toString();
        return ids;
    }
}
