package cn.ewsd.system.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.CodeItem;
import cn.ewsd.system.service.AuthGroupService;
import cn.ewsd.system.service.CodeItemService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"树形字典接口"})
@Controller
@RequestMapping("/system/codeItem")
public class CodeItemController extends SystemBaseController {

    @Resource
    private CodeItemService codeItemService;

    @Resource
    private AuthGroupService groupService;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;


    @ApiOperation(value = "页面配置方法")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() throws Exception {

        return "system/codeItem/index";
    }


    @ApiOperation(value = "获取所有数据集合方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getAllList", method = RequestMethod.POST)
    public Object getAllList(PageParam pageParam) throws Exception {
        String filterSort = "";
        if (request.getParameter("codeSetId") != null) {
            filterSort = " AND codeSetId = '" + request.getParameter("codeSetId") + "'";
        } else {
            filterSort = "";
        }

        filterSort = BaseUtils.filter(request) + filterSort;

        PageSet<CodeItem> pageSet = codeItemService.getPageSet(pageParam, filterSort);
        return pageSet;
    }



    @ApiOperation(value = "根据uuid获取详情数据方法")
    @ApiImplicitParam(name="uuid",value = "标识",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid", method = RequestMethod.GET)
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        CodeItem info = codeItemService.selectByPrimaryKey(uuid);
        return info;
    }




    @ApiOperation(value = "打开树形字典模块新增子节点页面方法")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add() {
        return "system/codeItem/add";
    }




    @ApiOperation(value = "打开树形字典模块新增根节点页面方法")
    @RequestMapping(value = "editRoot", method = RequestMethod.GET)
    public String editRoot() {
        return "system/codeItem/editRoot";
    }

    /**
     * @param codeItem
     * @return java.lang.Integer
     * @functionName saveRoot
     * @description 保存根节点
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2018-05-24 18:36
     */
    @ApiOperation(value = "保存树形字典模块根节点数据方法")
    @ResponseBody
    @RequestMapping(value = "saveRoot", method = RequestMethod.POST)
    public Integer saveRoot(CodeItem codeItem) throws Exception {
        codeItem.setCodeSetId(codeItem.getCodeSetId().substring(codeItem.getCodeSetId().indexOf(",") + 1));
        codeItem.setId(BaseUtils.UUIDGenerator());
        codeItem.setLevelId(1);
        return codeItemService.insertSelective(getSaveData(codeItem));
    }

    /**
     * @param codeItem
     * @return java.lang.Integer
     * @functionName save
     * @description 保存子节点
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2018-05-24 18:37
     */
    @ApiOperation(value = "保存树形字典模块子节点数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sort",value = "排序",defaultValue = "",required = false,dataType = "Int"),
            @ApiImplicitParam(name="levelId",value = "层级",defaultValue = "",required = false,dataType = "Int"),
            @ApiImplicitParam(name="codeSetId",value = "体系代码",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="code",value = "代码",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="status",value = "状态",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="url",value = "地址",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="id",value = "编号",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="pid",value = "父级编号",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="text",value = "名称",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="state",value = "是否可展开",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="iconCls",value = "显示图标",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="checked",value = "勾选",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="attributes",value = "属性",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="children",value = "子级",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="type",value = "类型",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="remark",value = "备注",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "Int"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modify_flag", value = "修改人旗帜", defaultValue = "1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(CodeItem codeItem) throws Exception {
        codeItem.setCodeSetId(codeItem.getCodeSetId().substring(codeItem.getCodeSetId().indexOf(",") + 1));
        codeItem.setId(BaseUtils.UUIDGenerator());
//        codeItem.setLevelId(codeItem.getLevelId() + 1);
        // 获取levelId
        CodeItem pCodeItem = codeItemService.getCodeItemById(codeItem.getPid());
        codeItem.setLevelId(pCodeItem.getLevelId()!=null? pCodeItem.getLevelId()+1:0);

        return codeItemService.insertSelective(getSaveData(codeItem));
    }

    @ApiOperation(value = "打开树形字典模块编辑页面方法")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "system/codeItem/edit";
    }

    @ApiOperation(value = "打开组织编辑页面方法")
    @RequestMapping(value = "organizationEdit", method = RequestMethod.GET)
    public String organizationEdit() throws Exception {
        return display();
    }

    @ApiOperation(value = "打开团体组织编辑页面方法")
    @RequestMapping(value = "partyOrganizationEdit",method = RequestMethod.GET)
    public String partyOrganizationEdit() throws Exception {
        return display();
    }

    @ApiOperation(value = "打开层级管理编辑页面方法")
    @RequestMapping(value = "menuManagerEdit",method = RequestMethod.GET)
    public String codeSetEdit() throws Exception {
        return display();
    }

    @ApiOperation(value = "更新树形字典模块数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sort",value = "排序",defaultValue = "",required = false,dataType = "Int"),
            @ApiImplicitParam(name="levelId",value = "层级",defaultValue = "",required = false,dataType = "Int"),
            @ApiImplicitParam(name="codeSetId",value = "体系代码",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="code",value = "代码",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="status",value = "状态",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="url",value = "地址",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="id",value = "编号",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="pid",value = "父级编号",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="text",value = "名称",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="state",value = "是否可展开",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="iconCls",value = "显示图标",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="checked",value = "勾选",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="attributes",value = "属性",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="children",value = "子级",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="type",value = "类型",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name="remark",value = "备注",defaultValue = "",required = false,dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "Int"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modify_flag", value = "修改人旗帜", defaultValue = "1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(CodeItem codeItem) throws Exception {
        //String aa = request.getAttribute("levelId").toString();
        codeItem.setCodeSetId(codeItem.getCodeSetId().substring(codeItem.getCodeSetId().indexOf(",") + 1));
//        CodeItem oriData = codeItemService.selectByPrimaryKey(codeItem.getUuid());

        // 获取levelId
        CodeItem pCodeItem = codeItemService.getCodeItemById(codeItem.getPid());
        codeItem.setLevelId(pCodeItem.getLevelId()!=null? pCodeItem.getLevelId()+1:0);
        //        codeItem.setLevelId();
        //        codeItem.setCreatorId(oriData.getCreatorId());
//        codeItem.setCreator(oriData.getCreator());
//        codeItem.setCreateTime(oriData.getCreateTime());
//        codeItem.setModifierId(userInfo.getUserNameId());
//        codeItem.setModifier(getCurrentUserName());
//        codeItem.setModifyTime(new Date());
        return codeItemService.updateByPrimaryKey(getUpdateData(codeItem));
    }

    @ApiOperation(value = "批量删除删除树形字典模块数据方法")
    @ApiImplicitParam(name = "uuids",value = "标识数组集",required = true,allowMultiple = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            if(datasourceUrl.contains("sqlserver")){ // sqlserver的删除操作
                result = codeItemService.cascadeDeleteByUuid(uuid[i]);
            }else{
                result = codeItemService.deleteByPrimaryKey(uuid[i]);
            } ;
            result++;
        }
        return result > 0 ? success("删除成功") : failure("删除失败");
    }



    @ApiOperation(value = "根据LevelId获取数据集合")
    @ApiImplicitParam(name = "levalId",value = "层级Id",required = true,dataType = "String",defaultValue = "")
    @ResponseBody
    @RequestMapping(value = "getListByLevelId", method = RequestMethod.POST)
    public Object getListByLevelId(String levelId) {
        String filterStr = BaseUtils.filter(request, " and level_id = '" + levelId + "' ORDER BY sort ASC");
        List<CodeItem> list = codeItemService.getListByLevelId(filterStr);
        return list;
    }


    @ApiOperation(value = "根据SetId和LevelId获取数据集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeSetId",value = "代码集ID",defaultValue = "",required = true,dataType = "String"),
            @ApiImplicitParam(name = "levelId",value = "层级ID",defaultValue = "",required = true,dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetIdAndLevelId", method = RequestMethod.POST)
    public Object getListByCodeSetIdAndLevelId(String codeSetId, String levelId) {
        // String hql = "FROM CodeItem WHERE codeSetId = '" + codeSetId + "' AND levelId = '" + levelId + "' ORDER BY sort ASC";
        List<CodeItem> list = codeItemService.getListByCodeSetIdAndLevelId(codeSetId, levelId);
        return list;
    }

    @ApiOperation(value = "根据CodeSetId和Pid获取数据集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeSetId",value = "代码集ID",defaultValue = "",required =true,dataType = "String"),
            @ApiImplicitParam(name = "pid",value = "",defaultValue = "",required =true,dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetIdAndPid", method = RequestMethod.POST)
    public Object getListByCodeSetIdAndPid() {
        String codeSetId = request.getParameter("codeSetId");
        String pid = request.getParameter("pid");
        List<CodeItem> list = null;
        if (codeSetId == null) {
            //"FROM CodeItem WHERE pid = '" + pid + "' ORDER BY sort ASC";
            list = codeItemService.getListByPid(pid);
        } else {
            //"FROM CodeItem WHERE codeSetId = '" + codeSetId + "' AND pid = '" + pid + "' ORDER BY sort ASC";
            list = codeItemService.getListByCodeSetIdAndPid(codeSetId, pid);
        }
        return JSON.toJSON(list);
    }

    @ApiOperation(value = "根据CodeSetId获取数据集合")
    @ApiImplicitParam(name = "codeSetId",value = "代码集Id",required = true,defaultValue = "",dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetId", method = RequestMethod.POST)
    public Object getListByCodeSetId() {
        String codeSetId = request.getParameter("codeSetId");
        // String hql = "FROM CodeItem WHERE codeSetId = '" + codeSetId + "' ORDER BY sort ASC";
        List<CodeItem> list = codeItemService.getListByCodeSetId(codeSetId);
        System.out.println(JSON.toJSON(list));
        return JSON.toJSON(list);
    }

    @ApiOperation(value = "根据Pid获取数据集合")
    @ApiImplicitParam(name = "pid",value = "",dataType="String",defaultValue = "",required = true)
    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid() {
        String pid = request.getParameter("pid");
        // String hql = "FROM CodeItem WHERE pid = '" + pid + "' AND levelId <> '0' ORDER BY codeSetId ASC,sort ASC";
        List<CodeItem> list = codeItemService.getListByPidAndLevelId(pid, 0);
        System.out.println(JSON.toJSON(list));
        return JSON.toJSON(list);
    }

    @ApiOperation(value = "根据codeSetId获取所有数据集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "codeItemId", value = "代码项Id", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "codeSetId", value = "代码集Id", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getAllListMenu", method = RequestMethod.POST)
    public Object getAllListMenu(PageParam pageParam) throws Exception {
        String filterSort = null;
        if (request.getParameter("codeItemId") != null) {
            filterSort = "code_set_id = '" + request.getParameter("codeSetId") + "' AND pid = '" + request.getParameter("codeItemId") + "'";
        } else {
            filterSort = "code_set_id = '" + request.getParameter("codeSetId") + "'";
        }

        filterSort = filterSort + BaseUtils.filter(request);
        filterSort = filterSort.replace(" ORDER BY create_time DESC ", "ORDER BY sort ASC");
        PageSet<CodeItem> pageSet = codeItemService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ApiOperation(value = "打开层级管理窗口")
    @RequestMapping(value = "menuManager",method = RequestMethod.GET)
    public String menuManager() throws Exception {
        return display();
    }


    @ApiOperation(value = "打开组织管理窗口")
    @RequestMapping(value = "organization",method = RequestMethod.GET)
    public String organization() throws Exception {
        return display();
    }

    @ApiOperation(value = "打开团体组织窗口")
    @RequestMapping(value = "partyOrganization",method = RequestMethod.GET)
    public String partyOrganization() throws Exception {
        return display();
    }

    @ApiOperation(value = "根据pid获取所有数据集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "codeItemId", value = "代码Id", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getAllListCode", method = RequestMethod.POST)
    public Object getAllListCode(PageParam pageParam) throws Exception {
        String filterSort = null;
        if (request.getParameter("codeItemId") != null) {
            filterSort = "pid = '" + request.getParameter("codeItemId") + "'";
        } else {
            filterSort = "";
        }

        filterSort = filterSort + BaseUtils.filter(request);
        filterSort = filterSort.replace(" ORDER BY createTime DESC ", "ORDER BY codeSetId ASC,sort ASC");
        PageSet<CodeItem> pageSet = codeItemService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ApiOperation(value = "打开代码及集窗口")
    @RequestMapping(value = "index2",method = RequestMethod.GET)
    public String codeSet() throws Exception {
        return display();
    }

    @ApiOperation(value = "获得父级Id")
    @ApiImplicitParam(name = "id",value = "id",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getFatherIds",method = RequestMethod.GET)
    public String getFatherIds(String id) {
        //return codeItemService.getFatherIds(codeSetId, id, levelId);
//        String ids = codeItemService.callProAndReturn("{call p_get_father_ids_varchar(?,?,?,?)}", "sys_code_item", id, "23DB879B877C3C44E055000000000001", "@idStr");
        String filterSort = "";
        Map map = new HashMap();
        map.put("p1", "sys_code_item");
        map.put("p2", id);
        map.put("p3", "23DB879B877C3C44E055000000000001");
        map.put("p4", "idStr");
        codeItemService.getFatherIdsVarchar(map);
        String ids = map.get("p4").toString();
        return ids;
    }

    @ApiOperation(value = "根据ID获取回填区数据")
    @ApiImplicitParam(name = "id",value = "回填区数据ID",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getZoneFillbackData",method = RequestMethod.GET)
    public Object getZoneFillbackData(String id) {
        //String ids = codeItemService.callProAndReturn("{call p_get_father_ids_varchar(?,?,?,?)}", "sys_code_item", id, "31643765E6824628BBB51AEB99949E2B", "@idStr");
        Map map1 = new HashMap();
        map1.put("p1", "sys_code_item");
        map1.put("p2", id);
        map1.put("p3", "31643765E6824628BBB51AEB99949E2B");
        map1.put("p4", "idStr");
        codeItemService.getFatherIdsVarchar(map1);
        String ids = map1.get("p4").toString();

        String[] idArr = ids.split(",");
        Map<String, Object> map = new HashMap<String, Object>();

        if (idArr.length == 1) {
            map.put("country", codeItemService.getTextById(idArr[0]));
            map.put("province", "");
            map.put("city", "");
            map.put("district", "");
        }
        if (idArr.length == 2) {
            map.put("country", codeItemService.getTextById(idArr[1]));
            map.put("province", codeItemService.getTextById(idArr[0]));
            map.put("city", "");
            map.put("district", "");
        }
        if (idArr.length == 3) {
            map.put("country", codeItemService.getTextById(idArr[2]));
            map.put("province", codeItemService.getTextById(idArr[1]));
            map.put("city", codeItemService.getTextById(idArr[0]));
            map.put("district", "");
        }
        if (idArr.length == 4) {
            map.put("country", codeItemService.getTextById(idArr[3]));
            map.put("province", codeItemService.getTextById(idArr[2]));
            map.put("city", codeItemService.getTextById(idArr[1]));
            map.put("district", codeItemService.getTextById(idArr[0]));
        }
        return map;
    }

}
