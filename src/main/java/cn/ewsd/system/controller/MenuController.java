package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.common.Constants;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.JsonUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Menu;
import cn.ewsd.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"资源管理接口"})
@Controller
@RequestMapping("/system/menu")
public class MenuController extends SystemBaseController {

    @Resource
    private MenuService menuService;


    @Value("${spring.datasource.url}")
    private String datasourceUrl;


    @ApiOperation(value = "页面配置方法")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() throws Exception {
        return "system/menu/index";
    }

    @ApiOperation(value = "获取所有数据集合方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "父级编码", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getAllList", method = RequestMethod.POST)
    public Object getAllList(PageParam pageParam, String pid) throws Exception {
        String filterSort;
        if (pid != null) {
            filterSort = " pid = '" + pid + "'";
        } else {
            filterSort = " ";
        }

        filterSort = filterSort + BaseUtils.filter(request);
        filterSort = filterSort.replace("ORDER BY create_time DESC", "ORDER BY sort ASC");
        PageSet<Menu> pageSet = menuService.getPageSet(pageParam, filterSort);
        return pageSet;
    }


    @ApiOperation(value = "根据uuid获取所有数据方法")
    @ApiImplicitParam(name = "uuid",value = "标识",required = true,defaultValue = "",dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid", method = RequestMethod.GET)
    public Object getDetailByUuid(String uuid) throws Exception {
        return menuService.selectByPrimaryKey(uuid);
    }

    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ApiOperation(value = "保存资源管理模块数据方法")
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @CacheEvict(cacheNames={"authAccessView","authAccess"}, allEntries=true)
    public Object save(Menu menu) throws Exception {
        // menu.setUuid(BaseUtils.UUIDGenerator());
        menu.setCodeSetId("menu");
        // menu.setCreatorId(userInfo.getUserNameId());
        // menu.setCreator(getCurrentUserName());
        //  menu.setCreateTime(new Date());
        //  menu.setCreatorOrgId(Integer.parseInt(getSessionByName("orgId")));

        // 获取父级的leveId
        int pId = menu.getPid();
        int levelId = menuService.getLevelIdById(pId);

        menu.setLevelId(levelId + 1);
        //"SELECT MAX(t.id) FROM Menu t"
        menu.setId(menuService.getMaxById());
        int result = menuService.insertSelective(getSaveData(menu));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }


    @ApiOperation(value = "打开资源管理新增根界面方法")
    @RequestMapping(value = "editRoot", method = RequestMethod.GET)
    public String editRoot() throws Exception {
        return "system/menu/editRoot";
    }

    @ApiOperation(value = "打开资源管理编辑界面方法")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "system/menu/edit";
    }


    @ApiOperation(value = "更新资源管理模块数据方法")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @CacheEvict(cacheNames={"authAccessView","authAccess"}, allEntries=true)
    public Object update(Menu menu) throws Exception {
        try {
            String uuid = menu.getUuid();
            String codeSetId = menu.getCodeSetId();
            Integer pid = menu.getPid();
            String resourceType = menu.getResourceType();
            String text = menu.getText();
            String url = menu.getUrl();
            String state = menu.getState();
            Integer status = menu.getStatus();
            String iconCls = menu.getIconCls();
            Integer sort = menu.getSort();
            String remark = menu.getRemark();

            Menu parentMenu = menuService.getMenuById(pid);
            //如果没有上级菜单，则设置该菜单的层级为0，否则在上级菜单的层级基础上+1
            Integer levelId = StringUtils.isNullOrEmpty(parentMenu.getUuid()) ? 0 : parentMenu.getLevelId() + 1;

//            String hql = "update Menu set codeSetId = '" + codeSetId + "', levelId = '" + levelId + "', pid = '" + pid + "', resourceType = '" + resourceType + "', " +
//                    "text = '" + text + "', url = '" + url + "', state = '" + state + "', status = '" + status + "', " +
//                    "iconCls = '" + iconCls + "', sort = '" + sort + "', remark = '" + remark + "' WHERE uuid = '" + uuid + "'";
            int result = menuService.updateByUuid(codeSetId, levelId, pid, resourceType, text, url, state, status, iconCls, sort, remark, uuid);
            return result > 0 ? success("更新成功！") : failure("更新失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtils.exceptionJson(e);
        }
    }

    @ApiOperation(value = "删除资源管理模块数据方法")
    @ApiImplicitParam(name = "uuid",value = "标识数组",defaultValue = "",required = true,allowMultiple = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @CacheEvict(cacheNames={"authAccessView","authAccess"}, allEntries=true)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            if(datasourceUrl.contains("sqlserver")){ // sqlserver的删除操作
                result = menuService.cascadeDeleteByUuid(uuid[i]);
            }else{
                result = menuService.deleteByPrimaryKey(uuid[i]);
            } ;
            result++;
        }
        return result > 0 ? success("删除成功") : failure("删除失败");
    }

    @ApiOperation(value = "根据CodeSetId和LevelId获取数据集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="codeSetId" ,value ="代码集" ,defaultValue = "",required =true ,dataType = "String"),
            @ApiImplicitParam(name = "levelId",value ="层级" ,defaultValue ="" ,required = true,dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetIdAndLevelId", method = RequestMethod.GET)
    public Object getListByCodeSetIdAndLevelId() {
        String codeSetId = request.getParameter("codeSetId");
        String levelId = request.getParameter("levelId");
        List<Menu> list = menuService.getListByCodeSetIdAndLevelId(codeSetId, levelId);
        return list;
    }

    @ApiOperation(value = "根据LevelId获取数据集合")
    @ApiImplicitParam(name = "levelId",value = "层级",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getListByLevelId", method = RequestMethod.POST)
    public Object getListByLevelId(String levelId) {
        List<Menu> list = menuService.getListByLevelId(levelId);
        return list;
    }


    @ApiOperation(value = "通过代码集和层级获取集合" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeSetId",value = "代码集",defaultValue = "",required = true,dataType = "String"),
            @ApiImplicitParam(name = "levelId",value = "层级",defaultValue = "",required = true,dataType = "String"),
    })
    @ResponseBody
    @RequestMapping(value = "/api/getListByCodeSetIdAndLevelId",method = RequestMethod.POST)
    public Object getListByCodeSetIdAndLevelIdApi() {
        String codeSetId = request.getParameter("codeSetId");
        String levelId = request.getParameter("levelId");
        List<Menu> list = menuService.getListByCodeSetIdAndLevelId(codeSetId, levelId);
        return list;
    }

    @ApiOperation(value = "根据Pid获取数据集合")
    @ApiImplicitParam(name = "pid",value = "父级编码",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid(String pid) {
        List<Menu> list = menuService.getListByPid(pid);
        return list;
    }


    @ApiOperation(value = "通过父级编码获取资源集合Api")
    @ApiImplicitParam(name = "pid",value = "父级编码",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/api/getListByPid",method = RequestMethod.GET)
    public Object getListByPidApi(String pid) {
        List<Menu> list = menuService.getListByPid(pid);
        return list;
    }

    @RequestMapping("menuManager")
    public String menuManager() throws Exception {
        return display();
    }

    @ApiOperation(value = "新增功能菜单")
    @ApiImplicitParam(name = "url",value = "资源地址",defaultValue = "",required = true,dataType ="String")
    @ControllerLog(description = "新增功能菜单")
    @RequestMapping(value = "functionMenuAdd", method = RequestMethod.POST)
    public String functionMenuAdd(String url) {

        if (url.indexOf("/") > -1) {
            String[] urlArr = url.split("/");
            request.setAttribute("pathSystemName", urlArr[1]);
            request.setAttribute("pathModelName", urlArr[2]);
        }
        return display();
    }

    @ApiOperation(value = "保存功能菜单")
    @ResponseBody
    @ControllerLog(description = "保存功能菜单")
    @RequestMapping(value = "functionMenuSave", method = RequestMethod.POST)
    public Object functionMenuSave(Menu menu) throws Exception {
        //  menu.setCreatorId(userInfo.getUserNameId());
        //   menu.setCreator(getCurrentUserName());
        //  menu.setCreateTime(new Date());
        menu.setState("open");
        Integer levelId = Integer.parseInt(request.getParameter("levelId")) + 1;
        menu.setLevelId(levelId);

        String systemName = request.getParameter("systemName");
        String modelName = request.getParameter("modelName");
        String addName = request.getParameter("add").trim();
        String editName = request.getParameter("edit").trim();
        String deleteName = request.getParameter("delete").trim();
        String importName = request.getParameter("import").trim();
        String exportName = request.getParameter("export").trim();
        if (!addName.equals("")) {
            //   menu.setUuid(BaseUtils.UUIDGenerator());
            menu.setId(menuService.getMaxById());
            menu.setText(addName);
            menu.setResourceType("window");
            menu.setUrl(modelName + "AddDialog");
            menuService.insertSelective(getSaveData(menu));
        }
        if (!editName.equals("")) {
            //    menu.setUuid(BaseUtils.UUIDGenerator());
            menu.setId(menuService.getMaxById());
            menu.setText(editName);
            menu.setResourceType("window");
            menu.setUrl(modelName + "EditDialog");
            menuService.insertSelective(getSaveData(menu));
        }
        if (!deleteName.equals("")) {
            //  menu.setUuid(BaseUtils.UUIDGenerator());
            menu.setId(menuService.getMaxById());
            menu.setText(deleteName);
            menu.setResourceType("url");
            menu.setUrl("/" + systemName + "/" + modelName + "/delete");
            menuService.insertSelective(getSaveData(menu));
        }
        if (!importName.equals("")) {
            //  menu.setUuid(BaseUtils.UUIDGenerator());
            menu.setId(menuService.getMaxById());
            menu.setText(importName);
            menu.setResourceType("window");
            menu.setUrl(modelName + "ImportDialog");
            menuService.insertSelective(getSaveData(menu));
        }
        if (!exportName.equals("")) {
            //  menu.setUuid(BaseUtils.UUIDGenerator());
            menu.setId(menuService.getMaxById());
            menu.setText(exportName);
            menu.setResourceType("url");
            menu.setUrl("/" + systemName + "/" + modelName + "/exportExcel");
            menuService.insertSelective(getSaveData(menu));
        }

        // String updateHql = "UPDATE Menu SET state = 'closed' WHERE id = '" + menu.getPid() + "'";
        menuService.updatePid(menu.getPid());

        return JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
    }

    @ApiOperation(value = "生成代码对话框")
    @ApiImplicitParam(name = "url",value = "资源地址",defaultValue = "",required = true,dataType = "String" )
    @ControllerLog(description = "生成代码对话框")
    @RequestMapping(value = "codeGenerateAdd", method = RequestMethod.POST)
    public String codeGenerateAdd(String url) {
        if (url.indexOf("/") > -1) {
            String[] urlArr = url.split("/");
            request.setAttribute("thisSystemName", urlArr[1]);
            request.setAttribute("thisModelName", urlArr[2].substring(0, 1).toUpperCase() + urlArr[2].substring(1));

            String path = request.getSession().getServletContext().getRealPath("");
            String savePath = path.replace("\\webapp", "");
            request.setAttribute("savePath", savePath);
        }
        return display();
    }

    @ApiOperation(value = "获取父级编码")
    @ApiImplicitParam(name ="id" ,value = "编码",defaultValue ="" ,required =true ,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getFatherIds", method = RequestMethod.POST)
    public Object getFatherIds(String id) {
        // return menuService.callProAndReturn("{call p_get_father_ids(?,?,?,?)}", "sys_menu", id, 1, "@idStr");
        Map map = new HashMap();
        map.put("p1", "sys_menu");
        map.put("p2", id);
        map.put("p3", 1);
        map.put("p4", "idStr");
        menuService.getFatherId(map);
        String ids = map.get("p4").toString();
        return ids;
    }

    @ApiOperation(value = "编码")
    @ApiImplicitParam(name = "id",value = "编码",required = true,defaultValue = "",dataType = "Int")
    @ResponseBody
    @RequestMapping(value = "getTreeNodes",method = RequestMethod.GET)
    public Object getTreeNodes(Integer id) {
        return menuService.getTreeNodes(id);
    }

}
