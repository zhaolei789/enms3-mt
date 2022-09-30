package cn.ewsd.mdata.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.model.SysUserPost;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.mdata.service.SysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @Author zhaoxiace
 * @Email zhaoxiace@ewsd.cn
 * @Date 2019-05-06 14:04:50
 */
@Controller
@RequestMapping("/mdata/sysUserPost")
public class SysUserPostController extends MdataBaseController {

    @Autowired
    private SysUserPostService sysUserPostService;
    @Autowired
    private OrganizationService organizationService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserPost模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "mdata/sysUserPost/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserPost分页集数据")
    public Object getPageSet(PageParam pageParam,String userNameId) {
        String filterSort = "";
        filterSort =  "sup.user_name_id='"+userNameId+"' and " +BaseUtils.filterSort(request, filterSort + getAuthFilter()).replace("ORDER BY create_time DESC","ORDER BY sup.create_time DESC");
        PageSet<SysUserPost> pageSet = sysUserPostService.getPageSet(pageParam, filterSort,userNameId);
        for (int i=0;i<pageSet.getRows().size();i++){
            Map map = new HashMap();
            map.put("p1", "sys_organization");
            map.put("p2", pageSet.getRows().get(i).getOrgId());
            map.put("p3", 1);
            map.put("p4", "idStr");
            organizationService.getFatherIds(map);
            String ids = map.get("p4").toString();
            // 获取职位列表信息
            String postText = "";
            if(ids!=null && !ids.isEmpty()){
                List<Organization> organizationList = sysUserPostService.getGroupConcat(ids.split(","));
                for(int j = 0; j < organizationList.size(); j++){
                    if(organizationList.get(j).getId() != 1){
                        postText += organizationList.get(j).getText() ;
                        if(organizationList.size() != j+1){
                            postText += "/";
                        }
                    }
                }
            }
            pageSet.getRows().get(i).setCompany(postText);
            //pageSet.getRows().get(i).setPost(sysUserPostService.getPostText(pageSet.getRows().get(i).getPost()));
        }
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserPost模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserPost sysUserPost = sysUserPostService.queryObject(uuid);
        return sysUserPost;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserPost模块新增页面")
    public String add() {
        return "mdata/sysUserPost/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserPost模块数据")
    public Object save(@ModelAttribute SysUserPost sysUserPost) {
        int result = sysUserPostService.insertSelective(getSaveData(sysUserPost));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserPost模块编辑页面")
    public String edit() {
        return "mdata/sysUserPost/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserPost模块数据")
    public Object update(@ModelAttribute SysUserPost sysUserPost) {
        int result = sysUserPostService.updateByPrimaryKeySelective(getUpdateData(sysUserPost));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserPost模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserPostService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
