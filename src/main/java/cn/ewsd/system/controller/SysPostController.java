package cn.ewsd.system.controller;

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

import cn.ewsd.system.model.SysPost;
import cn.ewsd.system.service.SysPostService;

@Controller
@RequestMapping("/system/sysPost")
public class SysPostController extends SystemBaseController {

    @Autowired
    private SysPostService sysPostService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysPost模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysPost/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysPost分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        if(request.getParameter("postType")!=null&&!request.getParameter("postType").equals("")){
            filterSort += " and (post_type = '"+request.getParameter("postType")+"' or post_type = 't.postType.4') ";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysPost> pageSet = sysPostService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysPost模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysPost sysPost = sysPostService.selectByPrimaryKey(uuid);
        return sysPost;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysPost模块新增页面")
    public String add() {
        return "system/sysPost/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysPost模块数据")
    public Object save(@ModelAttribute SysPost sysPost) {
        int checkCodeHave = sysPostService.queryTotalByFS(" post_code = '"+sysPost.getPostCode()+"' ");
        if(checkCodeHave>0){
            return failure("编码可能存在!");
        }
        sysPost.setIsDel(0);
        int result = sysPostService.insertSelective(getSaveData(sysPost));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysPost模块编辑页面")
    public String edit() {
        return "system/sysPost/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysPost模块数据")
    public Object update(@ModelAttribute SysPost sysPost) {
        SysPost sysPost_old = sysPostService.selectByPrimaryKey(sysPost.getUuid());
        if(!sysPost_old.getPostCode().equals(sysPost.getPostCode())){
            int checkCodeHave = sysPostService.queryTotalByFS(" post_code = '"+sysPost.getPostCode()+"' ");
            if(checkCodeHave>0){
                return failure("编码可能存在!");
            }
        }
        int result = sysPostService.updateByPrimaryKeySelective(getUpdateData(sysPost));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysPost模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysPostService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


    @ResponseBody
    @RequestMapping(value = "/getListByPostType", method = RequestMethod.POST)
    @ControllerLog(description = "getListByPostType")
    public Object getListByPostType(String postType) {
        String filterSort = " 1 = 1 and (post_type = 't.postType.4' or post_type = '"+postType+"') ";
        List<SysPost> sysPostList = sysPostService.queryListByFS(filterSort);
        return sysPostList;
    }

}
