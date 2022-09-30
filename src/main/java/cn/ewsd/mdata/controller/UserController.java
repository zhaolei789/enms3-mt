package cn.ewsd.mdata.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.word.WordExportUtil;
import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.EasyPoiUtil;
import cn.ewsd.base.utils.ExcelExportStylerImpl;
import cn.ewsd.base.utils.PingYinUtil;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.*;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.SysUserPost;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.mdata.service.SysUserPostService;
import cn.ewsd.mdata.service.UserService;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Api(tags = {"用户表管理接口"})
@Controller("userController")
@RequestMapping("/mdata/user")
public class UserController extends MdataBaseController {

    @Resource
    private UserService userService;

    @Autowired
    private SysUserPostService sysUserPostService;

    @Resource
    private OrganizationService organizationService;

    @RequestMapping("/userMsg")
    @ResponseBody
    public String userMsg() throws Exception {
        return "user server";
    }
    @ApiOperation(value = "打开用户信息列表配置页面")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() throws Exception {
        return "ucenter/user/index";
    }

    /**
     * 打开批量授权界面
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "打开批量授权界面")
    @RequestMapping(value = "/batchAuthorization", method = RequestMethod.GET)
    public String batchAuthorization() throws Exception {
        return "ucenter/user/batchAuthorization";
    }

    /**
     * 打开新增职务界面
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "打开新增职务界面")
    @RequestMapping(value = "/position", method = RequestMethod.GET)
    public String position() throws Exception {
        return "ucenter/user/position";
    }


    /**
     * 批量授权
     *
     * @param user      要批量授权的用户
     * @param dataAuth  数据权限
     * @param userGroup 角色权限
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "批量授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "要批量授权的用户", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dataAuth", value = "数据权限", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userGroup", value = "角色权限", defaultValue = "", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/batchAuthorizationSave", method = RequestMethod.POST)
    public Object batchAuthorizationSave(String user, String[] dataAuth, String[] userGroup) throws Exception {
        Integer result = userService.batchAuthorizationSave(user, dataAuth, userGroup);
        return result > 0 ? success("成功") : failure("失败");
    }

    /**
     * 保存职务信息
     *
     * @param userNameId 用户id
     * @param post      组织Id
     * @param postText
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "保存职务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNameId", value = "用户id", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "post", value = "组织Id", defaultValue = "", required = true, dataType = "String")

    })
    @Transient
    @ResponseBody
    @RequestMapping(value = "/savePosition", method = RequestMethod.POST)
    public Object savePosition(String userNameId, String post,String postText) throws Exception {
        String[] orgId = request.getParameterValues("orgId[]");
        if(sysUserPostService.isExistence(userNameId,post,orgId[0])){
            return failure("该数据已存在!!!");
        }
        SysUserPost sysUserPost = null;
        sysUserPost = new SysUserPost();
        sysUserPost.setUserNameId(userNameId);
        sysUserPost.setOrgId(orgId[0]);
        sysUserPost.setPost(post);
        Integer integer = sysUserPostService.insertSelective(getSaveData(sysUserPost));
        return integer > 0 ? success("保存成功") : failure("存失败");
    }

    @ApiOperation(value = "获取用户分页数据集方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userNameId", value = "用户ID", defaultValue = "desc", required = false, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "机构ID", defaultValue = "desc", required = false, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) throws Exception {
        //    String ids = userService.callProAndReturn("{call p_get_child_ids(?,?,?)}", "sys_organization", request.getParameter("id"), "@idStr");
        String filterSort;

        if (null != request.getParameter("id")) {
            Map map = new HashMap();
            map.put("p1", "sys_organization");
            map.put("p2", request.getParameter("id"));
            map.put("p3", "idStr");
            organizationService.getChildIds(map);
            String ids = map.get("p3").toString();
            filterSort = " and org_id in (" + ids + " )" + "AND is_del != 1";
        } else {
            filterSort = " and is_del != 1";
        }

        if(request.getParameter("userNameId")!=null && !"".equals(request.getParameter("userNameId"))){
            filterSort += " and user_name_id like '%"+ request.getParameter("userNameId").trim()+"%'";
        }
        if(request.getParameter("userGroup")!=null && !"".equals(request.getParameter("userGroup"))){
            filterSort += " and user_group like '%"+ request.getParameter("userGroup").trim()+"%'";
        }

        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<User> pageSet = userService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ApiOperation(value = "获取基础配置详细数据方法方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid", method = RequestMethod.GET)
    public Object getDetailByUuid(String uuid) {
        User info = userService.selectByPrimaryKey(uuid);
        return info;
    }

    @ApiOperation(value = "获取当前用户信息方法")
    @ResponseBody
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.POST)
    public Object getCurrentUser() {
        User user = userService.selectByPrimaryKey(LoginInfo.getUuid());
        return user;
    }

    @ApiOperation(value = "根据用户ID获取用户信息方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNameId", value = "用户ID", defaultValue = "", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/getUserByUserNameId", method = RequestMethod.POST)
    public Object getUserByUserNameId(String userNameId) throws Exception {
        User user = userService.getUserByUserNameId(userNameId);
        return user;
    }

    @ApiOperation(value = "获取全部用户数据集方法")
    @ResponseBody
    @RequestMapping(value = "/getListByUserName", method = RequestMethod.POST)
    public Object getListByUserName() throws Exception {
        List<User> users = userService.selectAllList();
        return users;
    }

    @RequestMapping("/add")
    public String add() {
        return display();
    }

    @ApiOperation(value = "保存用户方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "userNameId", value = "用户ID", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", defaultValue = "", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "country", value = "国家", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "生日", defaultValue = "", required = true, dataType = "String")

    })
    @Transient
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(User user) throws Exception {
        user.setIsDel(0);
        // 创建加密密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePw = encoder.encode(user.getUserNameId());

        user.setPassword(encodePw);
        User user1 = userService.getListByUserNameId(user.getUserNameId());
        if (null != user1) {
            return failure("人员已存在！");
            // return JsonUtils.messageJson(300, "操作提示", "人员已存在！");
        }
        //SysUse/mdata/user/editrPost sysUserPost = new SysUserPost();
        //sysUserPost.setOrgId(user.getOrgId());
        //sysUserPost.setUserNameId(user.getUserNameId());
        //sysUserPost.setPost(user.getPost());
        //// 判断职务唯一标识是否存在，存在就不用再添加了
        //SysUserPost sysUserPost1 = sysUserPostService.selectByPrimaryKey(sysUserPost.getUuid());
        //Integer integerSysUserPost = sysUserPost1==null ? sysUserPostService.insertSelective(getSaveData(sysUserPost)):1;

        //简拼
        user.setUserNameJp(PingYinUtil.getFirstSpell(user.getUserName()));
        if(user.getDriverId()!=null&&!"".equals(user.getDriverId().trim())&&!"undefined".equals(user.getDriverId())){
            user.setIsDriver(1);
        }else {
            user.setIsDriver(0);
        }
        user.setEntryWay(2);
        Integer integerUser = userService.insertSelective(getSaveData(user));
        //return integerSysUserPost > 0 && integerUser > 0 ? success("修改成功") : failure("修改失败");
        return integerUser > 0 ? success("修改成功") : failure("修改失败");
    }
    @ApiOperation(value = "打开用户编辑页面方法")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit() throws Exception {
        return "ucenter/user/edit";
    }

    /**
     * 打开个人信息修改界面
     *
     * @Description TODO
     * @Param
     * @Return java.lang.String
     * @Author zyj<zhuyongjing               @               zuoyoutech.com>
     * @Date 2019-01-26 13:22
     */
    @ApiOperation(value = "打开个人信息修改界面方法")
    @RequestMapping(value = "/personalUpdate", method = RequestMethod.GET)
    public String personalUpdate() throws Exception {
        String userNameId = getCurrentUserNameId();
        User user = userService.getListByUserNameId(userNameId);
        request.setAttribute("user", user);
        return "ucenter/user/personalUpdate";
    }
    @ApiOperation(value = "修改个人信息方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "userNameId", value = "用户ID", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", defaultValue = "", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "country", value = "国家", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "生日", defaultValue = "", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(User user) throws Exception {
        try {
            User user1 = userService.getListByUserNameIdAndUuid(user.getUserNameId(),user.getUuid());
            if (null != user1) {
                return failure("人员已存在！");
            }
            user.setIsDel(0);
            user.setUserNameJp(PingYinUtil.getFirstSpell(user.getUserName()));
            if(user.getDriverId()!=null&&!"".equals(user.getDriverId().trim())&&!"undefined".equals(user.getDriverId())){
                user.setIsDriver(1);
            }else {
                user.setIsDriver(0);
            }
            return userService.updateByPrimaryKeySelective(getUpdateData(user));
        } catch (Exception e) {
            e.printStackTrace();
            return success("人员信息修改失败！");
        }

    }

    /**
     * 个人基础信息修改
     *
     * @Description TODO
     * @Param user
     * @Return java.lang.Object
     * @Author zyj<zhuyongjing               @               zuoyoutech.com>
     * @Date 2019-01-26 15:25
     */
    @ApiOperation(value = "个人基础信息修改方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTime", value = "创建时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "creator", value = "创建人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorId", value = "创建人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creatorOrgId", value = "组织机构ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifier", value = "修改人", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifierId", value = "修改人ID", defaultValue = "", required = false, dataType = "String"),
            @ApiImplicitParam(name = "modifyTime", value = "修改人时间", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "userNameId", value = "用户ID", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", defaultValue = "", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "country", value = "国家", defaultValue = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "生日", defaultValue = "", required = true, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/foundationUpdate", method = RequestMethod.POST)
    public Object foundationUpdate(User user) throws Exception {
        int result = userService.updateByPrimaryKeySelective(user);
        return result > 0 ? success("修改成功") : failure("修改失败");

    }

    @ResponseBody
    @RequestMapping(value = "/saveByHql", method = RequestMethod.POST)
    public Integer saveByHql() throws Exception {
        String uuid = request.getParameter("uuid");
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        return userService.updateByUsernameAndNameAndUuid(username, name, uuid);
    }

    @RequestMapping("/editProfile")
    public String editProfile(String id) throws Exception {
        User info = userService.getListById(id);
        request.setAttribute("info", info);
        return display();
    }
    @ApiOperation(value = "打开修改密码界面方法")
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
    public String modifyPassword() throws Exception {
        String userNameId = getCurrentUserNameId();
        User user = userService.getListByUserNameId(userNameId);
        request.setAttribute("user", user);
        return "ucenter/user/modifyPassword";
    }

    @ApiOperation(value = "重置密码方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识", defaultValue = "", required = false, dataType = "Date"),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "", required = false, dataType = "String")

    })
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Object updatePassword(String uuid[], String password) throws Exception {
        Integer result = 0;
        //String enPassword;
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i = 0; i < uuid.length; i++) {
            //enPassword = MD5Utils.encodeByMd5With32Bit(password.split(",")[i].replace("'", ""));
            String encodePw = encoder.encode(password.split(",")[i].replace("'", ""));
            result += userService.updateByPasswordAndUuid(encodePw, uuid[i]);
        }
        Map<String, Object> jsonObj = new HashMap();
        if (result > 0) {
            jsonObj.put("statusCode", 200);
            jsonObj.put("title", "操作提示");
            jsonObj.put("message", "密码重置成功");
            return jsonObj;
        } else {
            jsonObj.put("statusCode", 300);
            jsonObj.put("title", "操作提示");
            jsonObj.put("message", "密码重置失败");
            return jsonObj;
        }
    }

    @ApiOperation(value = "修改密码方法")
    @ResponseBody
    @RequestMapping(value ="/updateModifyPassword", method = RequestMethod.POST)
    public Object updateModifyPassword(User user) throws Exception {
        //1.给密码加密
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());

        int result = userService.updateByPasswordAndUserNameId(password, user.getUserNameId());
        if (result > 0) {
            return JsonUtils.messageJson(200, "操作提示", "密码修改成功");
        } else {
            return JsonUtils.messageJson(300, "操作提示", "密码修改失败");
        }
    }

    @ApiOperation(value = "删除用户数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "标识(可传递多个)", defaultValue = "", required = false, dataType = "String"),

    })
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(String[] uuid) throws Exception {
        Integer result = 0;
        for (int i = 0; i < uuid.length; i++) {
            result = userService.updateByUuidAndIsDel(uuid[i]);
            result++;
        }
        return result > 0 ? success("删除成功") : failure("删除失败");
    }

    @ResponseBody
    @RequestMapping(value = "/getCountBySql")
    Integer getCountBySql(String userNameId, String passwordEncode) {
        return userService.getCountBySql(userNameId, passwordEncode);
    }

    @ResponseBody
    @RequestMapping(value = "/getListByDataAuth")
    public String getListByDataAuth(String currentUserNameId) {
        return userService.getListByDataAuth(currentUserNameId);
    }


    @ApiOperation(value = "根据用户名称模糊查询全部数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "用户名称", defaultValue = "", required = false, dataType = "String"),

    })
    @ResponseBody
    @RequestMapping(value = "/getListByKeywords", method = RequestMethod.POST)
    public Object getListByKeywords(String q) throws Exception {
        List<User> users = null;
        if (null == q || q.equals("")) {
            //users = userService.getLimitedListByQ(q);
            //users = userService.selectAllList();
            users = userService.queryListByFS(" is_del != 1 order by user_name_id asc limit 20 ");
        } else {
            //users = userService.getLimitedListByQ(q);
            users = userService.queryListByFS("  is_del != 1 and(user_name like '%" +q+"%' or user_name_id like '%" +q+ "%') order by user_name_id asc limit 20 ");
        }
        //  String hql = "FROM User WHERE userName LIKE '%" + q + "%' OR userNameId LIKE '%" + q + "%'";
        return users;
    }


    @ResponseBody
    @RequestMapping(value = "/getLdListByKeywords", method = RequestMethod.POST)
    public Object getLdListByKeywords(String q) throws Exception {
        List<User> users = null;
        if (null == q || q.equals("")) {
            users = userService.queryListByFS(" is_del != 1 and (lead_flag = 'leadFlag.1' or lead_flag = 'leadFlag.2') order by user_name_id asc limit 20 ");
        } else {
            users = userService.queryListByFS(" is_del != 1 and (lead_flag = 'leadFlag.1' or lead_flag = 'leadFlag.2') and(user_name like '%" +q+"%' or user_name_id like '%" +q+ "%') order by user_name_id asc limit 20 ");
        }
        return users;
    }

    @ResponseBody
    @RequestMapping(value = "/getFgLdList", method = RequestMethod.POST)
    public Object getFgLdList() throws Exception {
        List<User> usersList = userService
                .queryListByFS(" is_del != 1 and lead_flag = 'leadFlag.4' order by user_name_id asc");
        return usersList;
    }

    @ResponseBody
    @RequestMapping(value = "/getLdList124", method = RequestMethod.POST)
    public Object getLdList124() throws Exception {
        List<User> usersList = userService
                .queryListByFS(" is_del != 1 and lead_flag in ('leadFlag.1','leadFlag.2','leadFlag.4') order by user_name_id asc");
        return usersList;
    }


    @ApiOperation(value = "根据用户名称或者用户ID模糊查询全部数据方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "leaderId", value = "用户名称或者用户ID", defaultValue = "", required = false, dataType = "String"),

    })
    @ResponseBody
    @RequestMapping(value = "/getUsersByKeywords", method = RequestMethod.POST)
    public Object getUsersByKeywords(PageParam pageParam, String orgId, String leaderId) throws Exception {
        String filterSort = "";
        if (orgId != null && !orgId.equals("")) {
            Map map = new HashMap();
            map.put("p1", "sys_organization");
            map.put("p2", orgId);
            map.put("p3", "idStr");
            organizationService.getChildIds(map);
            String ids = map.get("p3").toString();
            filterSort = " org_id in (" + ids + " )" + " AND is_del != 1";
        }
        filterSort += " AND (user_name LIKE '%" + leaderId + "%' OR user_name_id LIKE '%" + leaderId + "%')";
        filterSort = filterSort + BaseUtils.filterSort(request);
        PageSet<User> pageSet = userService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    /**
     * @MethodName generateExcel
     * @Description 生成EXCEL文档并下载
     * @Param request
     * @Param response
     * @Param uuid
     * @Return void
     * @Date 2018-12-28 17:18
     */
    @ApiOperation(value = "生成EXCEL文档并下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", defaultValue = "", required = true, dataType = "String"),

    })
    @RequestMapping(value = "/generateExcel", method = RequestMethod.POST)
    public void generateExcel(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        /*Enumeration<String> paramNames = request.getParameterNames();
        //通过循环将表单参数放入键值对映射中
        while(paramNames.hasMoreElements()){
            String key  = paramNames.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }*/
        User user = userService.getUserByUuid(uuid);
        //给map填充数据
        map.put("date", DateUtils.convertDate2DateString(DateUtils.getSystemDate()));
        map.put("user", user);
        map.put("creator", getCurrentUserName());

        /*File file = FreemarkerUtils.createFile(request, "app/system/user/user.ftl", "xls", map);
        FreemarkerUtils.downloadFile(response, "人员报表", "xls", file);*/
        //FreemarkerUtils.createAndDownloadFile(request, response, "app/system/user/user.ftl", user.getUserName() + "个人信息", "xls", map);
    }

    /**
     * @MethodName generateDoc
     * @Description 生成WORD文档并下载
     * @Param request
     * @Param response
     * @Param uuid
     * @Return void
     * @Date 2018-12-28 17:18
     */
    @RequestMapping("/generateDoc")
    public void generateDoc(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        /*Enumeration<String> paramNames = request.getParameterNames();
        //通过循环将表单参数放入键值对映射中
        while(paramNames.hasMoreElements()){
            String key  = paramNames.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }*/
        User user = userService.getUserByUuid(uuid);
        //给map填充数据
        map.put("user", user);

        List subs = new ArrayList();
        for (int i = 0; i < 5; i++) {
            User u = new User();
            u.setUserName("小新" + (i + 1));
            u.setSex("男");
            u.setAge(32 + i);
            u.setEducation("社会大学" + (i + 1));
            subs.add(u);
        }
        map.put("subs", subs);

        // 生成Word文件
        //File file = FreemarkerUtils.createFile(request, "app/system/user/resume.ftl", "doc", map);
        // 将Word文件输出到浏览器下载
        //FreemarkerUtils.downloadFile(response, user.getUserName() + "个人简历", "pdf", file);
        // 生成Word文件同时将Word文件输出到浏览器下载
        //FreemarkerUtils.createAndDownloadFile(request, response, ResourceUtils.getString("templates/ucenter/user/resume.ftl"), user.getUserName() + "个人简历", "doc", map);
    }

    /**
     * @MethodName generateHtml
     * @Description 生成HTML文件
     * @Param uuid
     * @Return java.io.File
     * @Date 2018-12-28 17:18
     */

    public File generateHtml(String uuid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.getUserByUuid(uuid);
        //给map填充数据
        String serverUrl = UrlUtils.getServerNameAndPortUrl(request);
        String aa = UrlUtils.getRefererUrl(request);
        String bb = UrlUtils.getRefererUri(request);
        map.put("serverUrl", serverUrl);
        map.put("date", DateUtils.convertDate2DateString(DateUtils.getSystemDate()));
        map.put("user", user);
        map.put("creator", getCurrentUserName());

        /*File file = FreemarkerUtils.createFile(request, "app/system/user/pdf.ftl", "html", map);
        return file;*/
        return null;
    }

    /**
     * @MethodName generatePdf
     * @Description HTML转为PDF并下载
     * @Param response
     * @Param uuid
     * @Return void
     * @Date 2018-12-28 17:19
     */
    @RequestMapping("/generatePdf")
    public void generatePdf(HttpServletResponse response, String uuid) throws Exception {
        /**
         * 通过Html转Pdf
         */
        File file = generateHtml(uuid);
        Html2Pdf.generatePdf(response, file, "PDF导出测试");

        /**
         * 通过Jacob将Word转Pdf
         */
        /*Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.getUserByUuid(uuid);
        map.put("user", user);

        List subs = new ArrayList();
        for (int i = 0; i < 5; i++) {
            User u = new User();
            u.setUserName("小新" + (i + 1));
            u.setSex(1);
            u.setAge(32 + i);
            u.setEducation("社会大学" + (i + 1));
            subs.add(u);
        }
        map.put("subs", subs);
        // 生成Word文件
        File file = FreemarkerUtils.createFile(request, "app/system/user/resume.ftl", "doc", map);
        // 生成Pdf的路径及文件名
        String tempPdf = "E:\\apache-tomcat-8.0.26\\bin\\temp" + (int) (Math.random() * 100000) + ".pdf";
        // 将Word转换为Pdf
        JacobUtils.word2PDF(file.getAbsolutePath(), tempPdf);
        // 将Pdf文件输出到浏览器下载
        FreemarkerUtils.downloadFile(response, user.getUserName() + "个人简历", "pdf", new File(tempPdf));*/
    }

    /**
     * @MethodName export
     * @Description https://blog.csdn.net/qq_37598011/article/details/80918565
     * @Param response
     * @Return void
     * @Date 2018-12-28 18:05
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<User> users = userService.selectAllList();
        //导出操作
        //EasyPoiUtil.exportExcel(users, "佐佑科技用户信息", "第一页", User.class, "佐佑科技用户信息.xls", response);

        ExportParams exportParams = new ExportParams("佐佑科技用户信息", "第一页");
        exportParams.setStyle(ExcelExportStylerImpl.class);
        exportParams.setTitleHeight((short) 20);
        exportParams.setSecondTitleHeight((short) 15);
        EasyPoiUtil.defaultExport(users, User.class, "佐佑科技用户信息.xls", response, exportParams);
    }

    /**
     * 模版导出
     *
     * @return
     */
    @RequestMapping("/exportExcelByTemplate")
    public void fe_map() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
                "cn/ewsd/mdata/template/excel/专项支出用款申请书_map.xls");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2014-12-25");
        map.put("money", 2000000.00);
        map.put("upperMoney", "贰佰万");
        map.put("company", "执笔潜行科技有限公司");
        map.put("bureau", "财政局");
        map.put("person", "JueYue");
        map.put("phone", "1879740****");
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("id", i + 1 + "");
            lm.put("zijin", i * 10000 + "");
            lm.put("bianma", "A001");
            lm.put("mingcheng", "设计");
            lm.put("xiangmumingcheng", "EasyPoi " + i + "期");
            lm.put("quancheng", "开源项目");
            lm.put("sqje", i * 10000 + "");
            lm.put("hdje", i * 10000 + "");

            listMap.add(lm);
        }
        map.put("maplist", listMap);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/export/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/export/专项支出用款申请书_map.xls");
        workbook.write(fos);
        fos.close();
    }

    /**
     * @MethodName importExcel
     * @Description https://blog.csdn.net/qq_37598011/article/details/80918565
     * @Param file
     * @Return void
     * @Date 2018-12-28 18:05
     */
    @PostMapping("/importExcel")
    public void importExcel(@RequestParam("file") MultipartFile file) {
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);

        // 需要验证
        importParams.setNeedVerify(true);

        try {
            ExcelImportResult<User> result = ExcelImportUtil.importExcelMore(file.getInputStream(), User.class, importParams);

            List<User> users = result.getList();
            for (User user : users) {
                System.out.println(users);
            }
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    /**
     * @MethodName exportWord
     * @Description TODO
     * @Param
     * @Return void
     * @Author 罗章恒<luozhangheng   @   zuoyoutech.com>
     * @Date 2018-12-29 13:58
     */
    @RequestMapping("/exportWord")
    public void exportWord() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("department", "Easypoi");
        map.put("person", "JueYue");
        map.put("time", DateUtil.format(new Date(), "yyyy-mm-dd"));
        map.put("me", "JueYue");
        map.put("date", "2015-01-03");
        try {
            XWPFDocument doc = WordExportUtil.exportWord07("cn/ewsd/mdata/template/word/simple.docx", map);
            FileOutputStream fos = new FileOutputStream("D:/export/simple.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @MethodName getUserByUuid
     * @Description TODO
     * @Param uuid
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:07
     */
    @ResponseBody
    @RequestMapping("/getUserByUuid")
    public User getUserByUuid(String uuid) {
        return userService.getUserByUuid(uuid);
    }

    /**
     * @MethodName
     * @Description TODO
     * @Param null
     * @Return
     * @Date 2018-12-29 13:30
     */
    @ResponseBody
    @RequestMapping("/deleteUserByUuid")
    public Integer deleteUserByUuid(String uuid) {
        return userService.deleteUserByUuid(uuid);
    }

    /**
     * @MethodName getRoleIdByUserNameId
     * @Description TODO
     * @Param uuid
     * @Return java.lang.String
     * @Date 2018-12-29 13:30
     */
    @ResponseBody
    @RequestMapping("/getRoleIdByUserNameId")
    public String getRoleIdByUserNameId(String userNameId) {
        return userService.getRoleIdByUserNameId(userNameId);
    }

    /**
     * @MethodName checkUserExistByUserNameId
     * @Description TODO
     * @Param uuid
     * @Return java.lang.Integer
     * @Date 2018-12-29 13:30
     */
    @ResponseBody
    @RequestMapping("/checkUserExistByUserNameId")
    public Integer checkUserExistByUserNameId(String uuid) {
        return userService.checkUserExistByUserNameId(uuid);
    }

    @ResponseBody
    @GetMapping("/doUserLogin")
    public User doUserLogin(User user) {
        return userService.doUserLogin(user);
    }

    /**
     * @MethodName getUserByOrgIdAndPosition
     * @Description TODO
     * @Param orgId
     * @Param position
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:31
     */
    @ResponseBody
    @RequestMapping("/getUserByOrgIdAndPosition")
    public User getUserByOrgIdAndPosition(String orgId, String position) {
        return userService.getUserByOrgIdAndPosition(orgId, position);
    }

    /**
     * @MethodName getListByNameAndOrgid
     * @Description TODO
     * @Param userDutyName
     * @Param userOrgId
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:31
     */
    @ResponseBody
    @RequestMapping("/getListByNameAndOrgid")
    public User getListByNameAndOrgid(String userDutyName, String userOrgId) {
        return userService.getListByNameAndOrgid(userDutyName, userOrgId);
    }

    /**
     * @MethodName updateByUuidAndIsDel
     * @Description TODO
     * @Param uuid
     * @Return java.lang.Integer
     * @Date 2018-12-29 13:31
     */
    @RequestMapping("/updateByUuidAndIsDel")
    public Integer updateByUuidAndIsDel(String uuid) {
        return userService.updateByUuidAndIsDel(uuid);
    }

    /**
     * @MethodName updateByPasswordAndUserNameId
     * @Description TODO
     * @Param password
     * @Param userNameId
     * @Return int
     * @Date 2018-12-29 13:31
     */
    @RequestMapping("/updateByPasswordAndUserNameId")
    public int updateByPasswordAndUserNameId(String password, String userNameId) {
        return userService.updateByPasswordAndUserNameId(password, userNameId);
    }

    /**
     * @MethodName updateByPasswordAndUuid
     * @Description TODO
     * @Param enPassword
     * @Param s
     * @Return java.lang.Integer
     * @Date 2018-12-29 13:59
     */
    @RequestMapping("/updateByPasswordAndUuid")
    public Integer updateByPasswordAndUuid(String enPassword, String s) {
        return userService.updateByPasswordAndUuid(enPassword, s);
    }


    /**
     * @MethodName getListByUserNameId
     * @Description TODO
     * @Param userNameId
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:34
     */
    @RequestMapping("/getListByUserNameId")
    public User getListByUserNameId(String userNameId) {
        return userService.getListByUserNameId(userNameId);
    }

    /**
     * @MethodName getListById
     * @Description TODO
     * @Param id
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:37
     */
    @ResponseBody
    @RequestMapping("/getListById")
    public User getListById(String id) {
        return userService.getListById(id);
    }

    /**
     * @MethodName updateByUsernameAndNameAndUuid
     * @Description TODO
     * @Param username
     * @Param name
     * @Param uuid
     * @Return java.lang.Integer
     * @Date 2018-12-29 13:36
     */
    @ResponseBody
    @RequestMapping("/updateByUsernameAndNameAndUuid")
    public Integer updateByUsernameAndNameAndUuid(String username, String name, String uuid) {
        return userService.updateByUsernameAndNameAndUuid(username, name, uuid);
    }

    /**
     * @MethodName getPageSet
     * @Description TODO0
     * @Param pageParam
     * @Param filterSort
     * @Return cn.ewsd.common.utils.easyui.PageSet<cn.ewsd.mdata.model.User>
     * @Date 2018-12-29 13:43
     */
    @RequestMapping("/getPageSet")
    public PageSet<User> getPageSet(PageParam pageParam, String filterSort) {
        return userService.getPageSet(pageParam, filterSort);
    }

    /**
     * @MethodName getUserByCuuid
     * @Description TODO
     * @Param myUuid
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:45
     */
    @ResponseBody
    @RequestMapping("/getUserByCuuid")
    public User getUserByCuuid(String myUuid) {
        return userService.getUserByCuuid(myUuid);
    }

    /**
     * @MethodName getCountByRemoteUser
     * @Description TODO
     * @Param remoteUser
     * @Return java.lang.Integer
     * @Date 2018-12-29 13:47
     */
    @ResponseBody
    @RequestMapping("/getCountByRemoteUser")
    public Integer getCountByRemoteUser(String remoteUser) {
        return userService.getCountByRemoteUser(remoteUser);
    }

    /**
     * @MethodName getDetailByHql
     * @Description TODO
     * @Param hql
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:48
     */
    @ResponseBody
    @RequestMapping("/getDetailByHql")
    public User getDetailByHql(String hql) {
        return userService.getDetailByHql(hql);
    }

    /**
     * @MethodName getCountByHql
     * @Description TODO
     * @Param userNameId
     * @Param passwordEncode
     * @Return java.lang.Integer
     * @Date 2018-12-29 13:50
     */
    @ResponseBody
    @RequestMapping("/getCountByHql")
    public Integer getCountByHql(String userNameId, String passwordEncode) {
        return userService.getCountByHql(userNameId, passwordEncode);
    }

    /**
     * @MethodName getDetailByLikeUserAndPass
     * @Description TODO
     * @Param userNameId
     * @Param passwordEncode
     * @Return cn.ewsd.mdata.model.User
     * @Date 2018-12-29 13:50
     */
    @RequestMapping("/getDetailByLikeUserAndPass")
    public User getDetailByLikeUserAndPass(String userNameId, String passwordEncode) {
        return userService.getDetailByLikeUserAndPass(userNameId, passwordEncode);

    }

    /**
     * @MethodName getLimitedListByHql
     * @Description TODO
     * @Param q
     * @Param i
     * @Param i1
     * @Return java.util.List<cn.ewsd.mdata.model.User>
     * @Date 2018-12-29 13:51
     */
    @RequestMapping("/getLimitedListByHql")
    public List<User> getLimitedListByHql(String q, int i, int i1) {
        return userService.getLimitedListByHql(q, i, i1);
    }

    /**
     * @MethodName getListByHql
     * @Description TODO
     * @Param hql
     * @Return java.util.List<cn.ewsd.mdata.model.User>
     * @Date 2018-12-29 13:54
     */
    @ResponseBody
    @RequestMapping("/getListByHql")
    public List<User> getListByHql(String hql) {
        return userService.getListByHql(hql);
    }

    /**
     * @MethodName getLimitedListByQ
     * @Description TODO
     * @Param q
     * @Return java.util.List<cn.ewsd.mdata.model.User>
     * @Date 2018-12-29 13:55
     */
    @ResponseBody
    @RequestMapping("/getLimitedListByQ")
    public List<User> getLimitedListByQ(String q) {
        return userService.getLimitedListByQ(q);
    }

    /**
     * 根据 openid 更新手机号码
     * @param openid
     * @param cellphone
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateCellphone")
    public Integer updateCellphone(String openid,String cellphone) {
        return userService.updateCellphone(openid,cellphone);
    }

    @ResponseBody
    @RequestMapping("/setAllUserJp")
    public String setAllUserJp() throws Exception {
        String sql = "select uuid,user_name from sys_user";
        List<PageData> allUserList = DbUtil.executeQueryList(sql);
        for (int i = 0; i < allUserList.size(); i++) {
            if(allUserList.get(i).getString("user_name")!=null&&!"".equals(allUserList.get(i).getString("user_name"))) {
                sql = "update sys_user set user_name_jp = '" + PingYinUtil.getFirstSpell(allUserList.get(i).getString("user_name")) +
                        "' where uuid = '" + allUserList.get(i).getString("uuid") + "'";
                DbUtil.executeUpdateKR2(sql);
                Thread.sleep(200);
            }
        }
        return "ok";
    }

}
