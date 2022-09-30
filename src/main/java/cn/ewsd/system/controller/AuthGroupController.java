package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.AuthGroup;
import cn.ewsd.system.service.AuthAccessService;
import cn.ewsd.system.service.AuthGroupService;
import cn.ewsd.system.service.MenuService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 角色组
 */
@Api(tags = {"角色权限接口"})
@Controller
@RequestMapping("/system/authGroup")
public class AuthGroupController extends SystemBaseController {

    @Resource
    private AuthGroupService authGroupService;

//    @Resource
//    private MenuService menuService;
//
//    @Resource
//    private AuthAccessService authAccessService;

    @ApiOperation(value = "页面配置方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String")
    })
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(PageParam pageParam) throws Exception {
//        String hql = "FROM AuthGroup" + Common.searchInfo(request);
//        request.setAttribute("pageSet", authGroupService.getPageSetByHql(request, hql));
//        return display();
        String filterSort = BaseUtils.filterSort(request);
        PageSet<AuthGroup> pageSet = authGroupService.getPageSet(pageParam, filterSort);
        request.setAttribute("pageSet", pageSet);
        return "system/authGroup/index";
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
    public Object getAllList(PageParam pageParam) throws Exception {
        String filterSort = "";
        if (request.getParameter("pid") != null) {
            filterSort = " AND pid = '" + request.getParameter("pid") + "'";
        } else {
            filterSort = " ";
        }
        filterSort = BaseUtils.filter(request) + filterSort + " ORDER BY sort ASC";
        PageSet<AuthGroup> pageSet = authGroupService.getPageSet(pageParam, filterSort);
        return pageSet;

    }

    @ApiOperation(value = "根据uuid获取详情数据方法")
    @ApiImplicitParam(value = "uuid",name = "uuid",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid", method = RequestMethod.GET)
    public Object getDetailByUuid(String uuid) throws Exception {
//        String uuid = "402881ef57548c63015755d6ffde0410";
        AuthGroup info = authGroupService.selectByPrimaryKey(uuid);
        return info;
    }

    @ApiOperation(value = "打开角色授权模块新增界面方法")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add() {
        return "system/authGroup/add";
    }

//    @ApiOperation(value = "保存角色授权模块数据方法")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pid", value = "父级编码", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "id", value = "id", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "menuId", value = "层级Id", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "levelId", value = "层级", defaultValue = "", required = false, dataType = "Int"),
//            @ApiImplicitParam(name = "remark", value = "备注", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "text", value = "分组名", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "status", value = "状态", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "state", value = "创建时间", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "code", value = "代码", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "codeSetId", value = "代码集ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
//            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
//    })
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(AuthGroup authGroup) throws Exception {
        //"SELECT MAX(t.id) FROM AuthGroup t"
        authGroup.setUuid(BaseUtils.UUIDGenerator());
        authGroup.setId(authGroupService.getMaxById() + 1);
        authGroup.setLevelId(authGroup.getLevelId() + 1);
        authGroup.setIsDel(0);
        authGroup.setCreateTime(new Date());
        return authGroupService.insertSelective(authGroup);
    }

    @ApiOperation(value = "打开角色授权模块编辑界面方法")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "system/authGroup/edit";
    }

//    @ApiOperation(value = "更新角色授权模块数据方法")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pid", value = "父级编码", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "id", value = "id", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "menuId", value = "层级Id", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "text", value = "", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "status", value = "状态", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "state", value = "创建时间", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "code", value = "代码", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "codeSetId", value = "代码集ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
//            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
//            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
//    })
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(AuthGroup group) throws Exception {
        group.setModifyTime(new Date());
        Integer result =  authGroupService.updateByPrimaryKeySelective(group);
        return result > 0 ? success("更新成功") : failure("更新失败");
    }

    @ApiOperation(value = "删除角色授权模块数据方法")
    @ApiImplicitParam(value = "uuid",name = "uuid",required = true,defaultValue = "",allowMultiple = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            // String hql = "DELETE FROM AuthGroup WHERE uuid = '" + uuid[i] + "'";
            result = authGroupService.deleteByPrimaryKey(uuid[i]);
            result++;
        }
        return result > 0 ? success("删除成功") : failure("删除失败");

//        try {
//            String[] uuidArr = uuid.split(",");
//            String allUuids;
//            for (int i = 0; i < uuidArr.length; i++) {
//                AuthGroup authGroup = authGroupService.get(AuthGroup.class, uuidArr[i].replace("'", ""));
//                // 获得指定角色及菜单ID下的子信息
//                List<AuthAccess> authAccesses = authAccessService.getListByHql(String.format("FROM AuthAccess WHERE roleId = '%s' AND pid = '%s'", authGroup.getId(), authGroup.getMenuId()));
//                for (AuthAccess authAccess : authAccesses) {
//                    // 获得自身及所有子节点uuid
//                    allUuids = authAccessService.getSelfAndChildUuids(authAccess.getUuid(), "$");
//                    authAccessService.deleteByUuids(AuthAccess.class, allUuids);
//                }
//                // 删除用户组
//                authGroupService.deleteByUuids(AuthGroup.class, uuid);
//            }
//            return 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
    }

    @ApiOperation(value = "获取数据集合")
    @ResponseBody
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public Object getList() {
        // String hql = "FROM AuthGroup ORDER BY sort ASC";
        List<AuthGroup> list = authGroupService.getListBySort();
        return JSON.toJSON(list);
    }

    @ApiOperation(value = "根据LevelId获取数据集合方法")
    @ApiImplicitParam(value = "层级",name = "levalId",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getListByLevelId", method = RequestMethod.POST)
    public Object getListByLevelId(String levelId) {
        //String hql = "FROM AuthGroup WHERE levelId = '" + levelId + "' ORDER BY sort ASC";
        List<AuthGroup> list = authGroupService.getListByLevelId(levelId);
        return list;
    }

    @ApiOperation(value = "根据CodeSetId和LevelId获取数据集合方法")
    @ApiImplicitParams({
            @ApiImplicitParam(value="代码集",name = "codeSetId",defaultValue = "",required = true,dataType = "String"),
            @ApiImplicitParam(value="层级",name = "levelId",defaultValue = "",required = true,dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetIdAndLevelId", method = RequestMethod.POST)
    public Object getListByCodeSetIdAndLevelId() {
        String codeSetId = request.getParameter("codeSetId");
        String levelId = request.getParameter("levelId");
        // String hql = "FROM AuthGroup WHERE codeSetId = '" + codeSetId + "' AND levelId = '" + levelId + "' ORDER BY sort ASC";
        List<AuthGroup> list = authGroupService.getListByCodeSetIdLevelId(codeSetId, levelId);
        return JSON.toJSON(list);
    }

    @ApiOperation(value = "根据Pid获取数据集合方法")
    @ApiImplicitParam(value = "父级编码",name ="pid",defaultValue ="" ,required = true,dataType ="String" )
    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid() {
        String pid = request.getParameter("pid");
        //  String hql = "FROM AuthGroup WHERE pid = '" + pid + "' AND levelId <> '0' ORDER BY sort ASC";
        List<AuthGroup> list = authGroupService.getListByPid(pid);
        return JSON.toJSON(list);
    }

}
