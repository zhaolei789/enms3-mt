package cn.ewsd.system.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Archive;
import cn.ewsd.system.model.Attachment;
import cn.ewsd.system.service.ArchiveService;
import cn.ewsd.system.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档列表
 * @ClassName ArchiveController
 * @Description
 * @Author
 * @Date 2017/4/23 21:39
 */
@Api(tags = {"本地文件管理接口"})
@Controller
@RequestMapping("/system/archive")
public class ArchiveController extends SystemBaseController {

    @Autowired
    private ArchiveService archivesService;

    @Autowired
    private AttachmentService attachmentService;


    @ApiOperation("打开本地文件管理窗口")
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index() throws Exception {
        return "system/archive/index";
    }

    @ApiOperation("获取页面详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "分类ID", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "getPageSetData",method = RequestMethod.POST)
    public Object getPageSetData(PageParam pageParam, String categoryId) throws Exception {
//        String filterStr = "";
//        if (!StringUtils.isNullOrEmpty(categoryId)) {
//            String idStr = archivesService.callProAndReturn("{call p_get_child_ids(?,?,?)}", "sys_channel", categoryId, "@idStr");
//            filterStr += "AND categoryId IN (" + idStr + ")";
//        }
//        hql = BaseUtils.filterSort(request, Archive.class, filterStr);
//        PageSet<Archive> pageSet = archivesService.getPageSetByHql(request, hql);
//        return pageSet;
//        //return Common.datagridJsonData(pageSet);


        String filterSort = "";
        if (!StringUtils.isNullOrEmpty(categoryId)) {
            Map map = new HashMap();
            map.put("p1", "sys_channel");
            map.put("p2", categoryId);
            map.put("p3", "idStr");
            archivesService.getChildIds(map);
            categoryId = map.get("p3").toString();
            filterSort = " AND category_id in (" + categoryId + " )";
        }
        filterSort = BaseUtils.filterSort(request, filterSort);
        PageSet<Archive> pageSet = archivesService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ApiOperation("同过标识获取详情")
    @ApiImplicitParam(name = "uuid",value = "标识",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "getDetailByUuid",method = RequestMethod.GET)
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        Archive info = archivesService.selectByPrimaryKey(uuid);
        return info;
    }

    @ApiOperation("打开添加窗口")
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String add() {
        return "system/archive/add";
    }

    @ApiOperation("打开保存窗口")
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(Archive resource) throws Exception {
        resource.setUuid(BaseUtils.UUIDGenerator());
        resource.setCreatorId(this.getCurrentUserNameId());
        resource.setCreator(this.getCurrentUserName());
        resource.setCreateTime(new Date());
        resource.setCreatorOrgId(Integer.parseInt(LoginInfo.getOrgId()));
        Integer integer=archivesService.insertSelective(resource);
        return integer;
    }

    @ApiOperation("打开编辑窗口")
    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public String edit() throws Exception {
        return "system/archive/edit";
    }

    @ApiOperation("打开详情窗口")
    @ApiImplicitParam(name = "uuid",value = "标识",required = true,dataType = "String")
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public String detail(String uuid) throws Exception {
        Archive archive = archivesService.selectByPrimaryKey(uuid);
        request.setAttribute("archive", archive);

        List<Attachment> attachments = attachmentService.getListByPuuid(archive.getUuid());
        request.setAttribute("attachments", attachments);
        return "system/archive/detail";
    }

    @ApiOperation("更新信息")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(Archive archive) throws Exception {
        Archive oriData = archivesService.selectByPrimaryKey(archive.getUuid());
        archive.setCreatorId(oriData.getCreatorId());
        archive.setCreator(oriData.getCreator());
        archive.setCreateTime(oriData.getCreateTime());
        archive.setModifierId(LoginInfo.getUserNameId());
        archive.setModifier(LoginInfo.getUserName());
        archive.setModifyTime(new Date());
        //FROM Attachment WHERE pid = '" + archive.getUuid() + "'
        List<Attachment> attachments = attachmentService.getListByUuid(archive.getUuid());
        //newArchive.setAttachments(attachments);
        return archivesService.updateByPrimaryKeySelective(archive);
    }

    @ApiOperation("根据uuid删除用户信息")
    @ApiImplicitParam(name = "uuid",value = "标识",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete() throws Exception {
        Integer result = 0;
        String uuid = request.getParameter("uuid");
        String[] uuids = uuid.split(",");
        for (int i = 0; i < uuids.length; i++) {
            result = archivesService.deleteByPrimaryKey(uuids[i]);
            result++;
        }
        return result > 0 ? success("删除成功") : failure("删除失败");
        // String hql = "DELETE FROM Archive WHERE uuid IN (" + uuid + ")";
//        if (archivesService.deleteByPrimaryKey(uuid) == 1) {
//            return JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
//        } else {
//            return JsonUtils.messageJson(300, Constants.OPERATE_TIPS, Constants.OPERATE_FAILURE_MSG);
//        }
    }

    @ApiOperation("微服务信息")
    @RequestMapping(value = "cloudIndex",method = RequestMethod.GET)
    public String cloudIndex() throws Exception {
        return "system/archive/cloudIndex";
    }

}
