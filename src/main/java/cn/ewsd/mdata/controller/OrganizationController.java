package cn.ewsd.mdata.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.service.SysUserQryOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"组织机构接口"})
@Controller("organizationController")
@RequestMapping("/mdata/organization")
public class OrganizationController extends MdataBaseController {
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @Resource
    private OrganizationService organizationService;

    @ApiOperation(value = "打开组织机构列表界面方法")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() throws Exception {
        return "ucenter/organization/index";
    }
    @ApiOperation(value = "打开组织机构列表界面方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),

    })
    @ResponseBody
    @RequestMapping(value = "getPageSet", method = RequestMethod.GET)
    public Object getPageSet(PageParam pageParam) throws Exception {
        String filterSort = BaseUtils.filterSort(request);
        PageSet<Organization> pageSet = organizationService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "getPageSet2", method = RequestMethod.POST)
    public Object getPageSet2(PageParam pageParam) throws Exception {
        String filterSort = " and level_id=3 ";
        filterSort += " and id in ("+sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid())+") ";
        filterSort = BaseUtils.filterSort(request,filterSort + getAuthFilter());
        PageSet<Organization> pageSet = organizationService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time DESC"," ORDER BY sort ASC "));
        return pageSet;
    }

    @ApiOperation(value = "获取数据详情方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识",required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid", method = RequestMethod.GET)
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        Organization info = organizationService.selectByPrimaryKey(uuid);
        return info;
    }

//    @ApiOperation(value = "打开新增子界面方法")
    @RequestMapping(value ="add", method = RequestMethod.GET)
    public String add() {
        return "ucenter/organization/add";
    }
    @ApiOperation(value = "打开编辑界面方法")
    @RequestMapping(value ="edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "ucenter/organization/edit";
    }

    @ApiOperation(value = "打开新增根界面方法")
    @RequestMapping(value ="editRoot", method = RequestMethod.GET)
    public String editRoot() throws Exception {
        return "ucenter/organization/editRoot";
    }

    @RequestMapping("organizationEdit")
    public String organizationEdit() throws Exception {
        return "ucenter/organization/organizationEdit";
    }

    @RequestMapping("partyOrganizationEdit")
    public String partyOrganizationEdit() throws Exception {
        return "ucenter/organization/partyOrganizationEdit";
    }

    @RequestMapping("menuManagerEdit")
    public String codeSetEdit() throws Exception {
        return "ucenter/organization/menuManagerEdit";
    }

    /**
     * @param organization
     * @return java.lang.Integer
     * @functionName save
     * @description 保存子节点
     * @author 小策一喋<xvpindex @ qq.com>
     * @date 2018-05-28 15:58
     */
    @ApiOperation(value = "保存子节点方法")
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(Organization organization) throws Exception {
        Organization organization1 = organizationService.getListByCode(organization.getCode());
        if (organization1 == null || "".equals(organization.getCode())) {
            organization.setIsDel(0);

            // 获取组织机构的Id
            Organization pOrganization = organizationService.getOrganizationById(organization.getPid());
            organization.setLevelId(pOrganization.getLevelId()+1);

            //organization.setLevelId(organization.getLevelId() + 1);
            organization.setId(organizationService.getMaxById() + 1);
            organization.setEntryWay(2);
            return organizationService.insertSelective(getSaveData(organization));
        }
        return failure("新增失败，代码重复！");
    }

    /**
     * @param organization
     * @return java.lang.Integer
     * @functionName saveRoot
     * @description 保存根节点
     * @author 小策一喋<xvpindex @ qq.com>
     * @date 2018-05-28 15:58
     */
    @ApiOperation(value = "保存根节点方法")
    @ResponseBody
    @RequestMapping(value = "saveRoot", method = RequestMethod.POST)
    public Object saveRoot(Organization organization) throws Exception {
        Organization organization1 = organizationService.getListByCode(organization.getCode());
        if (organization1 == null) {
            organization.setIsDel(0);
            organization.setId(organizationService.getMaxById() + 1);
            int result = organizationService.insertSelective(getSaveData(organization));
            return result > 0 ? success("新增成功！") : failure("新增失败！");
        }
        return failure("新增失败，代码重复！");
    }
    @ApiOperation(value = "机构数据修改方法")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(Organization organization) throws Exception {
        Boolean isCodeExist = organizationService.getListByCodeAndUuid(organization.getCode(), organization.getUuid());
        if (!isCodeExist || "".equals(organization.getCode())) {
            organization.setIsDel(0);

            // 获取组织机构的Id
            Organization pOrganization = organizationService.getOrganizationById(organization.getPid());
            organization.setLevelId(pOrganization.getLevelId()+1);
            //Organization oriData = organizationService.selectByPrimaryKey(request.getParameter("uuid"));
            return organizationService.updateByPrimaryKeySelective(organization);
        }
        return failure("更新失败，代码重复！");

    }

    @ApiOperation(value = "机构数据删除方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识",required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            result = organizationService.updateByIsDelAndUuid(uuid[i]);
            result++;
        }
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ApiOperation(value = "根据代码ID和层级ID获取数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeSetId", value = "代码", defaultValue = "1", required = true, dataType = "String"),
            @ApiImplicitParam(name = "levelId", value = "层级ID", defaultValue = "1", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetIdAndLevelId", method = RequestMethod.POST)
    public Object getListByCodeSetIdAndLevelId() {
        String codeSetId = request.getParameter("codeSetId");
        String levelId = request.getParameter("levelId");
        List<Organization> list = organizationService.getListByCodeSetIdAndLevelId(codeSetId, levelId);
        return list;
    }
    @ApiOperation(value = "根据层级ID获取数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "levelId", value = "层级ID", defaultValue = "1", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "getListByLevelId", method = RequestMethod.POST)
    public Object getListByLevelId(String levelId) {
        List<Organization> list = organizationService.getListByLevelId(levelId);
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getOrgListByTypeAndTenant", method = RequestMethod.POST)
    public Object getOrgListByTypeAndTenant(String orgType) {
        List<Organization> list = organizationService.getOrgListByTypeAndTenant(orgType,LoginInfo.getTenantId());
        return list;
    }

    @ApiOperation(value = "根据代码ID和父ID获取子数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeSetId", value = "代码", defaultValue = "1", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "父ID", defaultValue = "1", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "getListByCodeSetIdAndPid", method = RequestMethod.GET)
    public Object getListByCodeSetIdAndPid() {
        String codeSetId = request.getParameter("codeSetId");
        String pid = request.getParameter("pid");
        List<Organization> list = null;
        if (codeSetId == null) {
            list = organizationService.getListByPid(pid);
        } else {
            list = organizationService.getListByCodeSetIdAndPid(codeSetId, pid);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "getFirstLevelUNList", method = RequestMethod.GET)
    public Object getFirstLevelUNList() {
        List<Organization> list = organizationService.getListByCodesetidAndLevelId();
        return list;
    }

    @ApiOperation(value = "获取子节点数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "父ID", defaultValue = "1", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "getListByPid", method = RequestMethod.POST)
    public Object getListByPid() {
        String pid = request.getParameter("pid");
        if(pid==null){
            pid = LoginInfo.getOrgId();
        }
        List<Organization> list = organizationService.getListByIsDelAndPidAndLevelId(pid);
        return list;
    }


    @ResponseBody
    @RequestMapping(value = "getAllListMenu", method = RequestMethod.GET)
    public Object getAllListMenu(PageParam pageParam) throws Exception {
        String filterStr = null;
        if (request.getParameter("pid") != null) {
            filterStr = " pid = '" + request.getParameter("pid") + "'";
        } else {
            filterStr = "";
        }

        filterStr = filterStr + BaseUtils.filter(request);
        filterStr = filterStr.replace(" ORDER BY createTime DESC ", "ORDER BY sort ASC");
        PageSet<Organization> pageSet = organizationService.getPageSet(pageParam, filterStr);
        return pageSet;
    }

    @RequestMapping("menuManager")
    public String menuManager() throws Exception {
        return display();
    }

    @RequestMapping("organization")
    public String organization() throws Exception {
        return display();
    }

    @RequestMapping("partyOrganization")
    public String partyOrganization() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "getAllListCode")
    public Object getAllListCode(PageParam pageParam) throws Exception {

        String filterSort = null;
        if (request.getParameter("codeItemId") != null) {
            filterSort = "pid = '" + request.getParameter("codeItemId") + "'";
        } else {
            filterSort = "";
        }
        filterSort = filterSort + BaseUtils.filter(request) + " ORDER BY sort ASC";
        filterSort = filterSort.replace(" ORDER BY createTime DESC ", "ORDER BY sort ASC");
        PageSet<Organization> pageSet = organizationService.getPageSet(pageParam, filterSort);
        return pageSet;


    }

    @RequestMapping("index2")
    public String codeSet() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "getFatherIds", method = RequestMethod.POST)
    public Object getFatherIds(String id) {
        // return organizationService.callProAndReturn("{call p_get_father_ids(?,?,?,?)}", "sys_organization", id, 1, "@idStr");
        Map map = new HashMap();
        map.put("p1", "sys_organization");
        map.put("p2", id);
        map.put("p3", 1);
        map.put("p4", "idStr");
        organizationService.getFatherIds(map);
        String ids = map.get("p4").toString();
        return ids;
    }

    @ApiOperation(value = "获取全部父级数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "自身ID",  required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "getFatherIdsList", method = RequestMethod.GET)
    public Object getFatherIdsList(PageParam pageParam, String id) {
        // return channelService.callProAndReturn("{call p_get_father_ids(?,?,?,?)}", "sys_channel", id, 1, "@idStr");
        String filterSort = "";
        Map map = new HashMap();
        map.put("p1", "sys_organization");
        map.put("p2", id);
        map.put("p3", 1);
        map.put("p4", "idStr");
        organizationService.getChildIds(map);
        filterSort += BaseUtils.filterSort(request);
        PageSet<Organization> pageSet = organizationService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    /**
     *  流程返回 userNameId
     * @param orgId
     * @param orgType
     * @return
     */
    @ApiOperation(value = "流程返回方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "机构ID", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orgType", value = "机构类型", defaultValue = "", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public String getUserInfo(String orgId, String orgType) {
        String userNameId = organizationService.getUserInfo(orgId, orgType);
        if (StringUtils.isNullOrEmpty(userNameId)) {
            return "";
        } else {
            return userNameId;
        }
    }

    @ResponseBody
    @RequestMapping(value = "getOrgByLevel", method = RequestMethod.POST)
    public Object getOrgByLevel(Integer level) {
        return organizationService.getOrgByLevel(level);
    }

    @ResponseBody
    @RequestMapping(value="getUserDeptSet")
    public Object getUserDeptSet(){
        return organizationService.getUserDeptSet(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), "C0ZZ", "3");
    }

    @ResponseBody
    @RequestMapping(value="getWorkDeptSet")
    public Object getWorkDeptSet(){
        return organizationService.getWorkDeptSet();
    }

    @ResponseBody
    @RequestMapping(value = "getUserTeamDownClass")
    public Object getUserTeamDownClass(){
        return organizationService.getTeamDownClass(LoginInfo.getOrgId());
    }

    @ResponseBody
    @RequestMapping(value = "getTeamDownClass")
    public Object getTeamDownClass(){
        String teamNo = request.getParameter("teamNo");
        return organizationService.getTeamDownClass(teamNo);
    }
}
