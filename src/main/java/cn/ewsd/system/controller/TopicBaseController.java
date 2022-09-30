package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.TopicBase;
import cn.ewsd.system.model.TopicDocument;
import cn.ewsd.system.service.CategoryService;
import cn.ewsd.system.service.TopicBaseService;
import cn.ewsd.system.service.TopicDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 主题表
 *
 */
@Api(tags = {"文章列表接口"})
@Controller
@RequestMapping("/system/topicBase")
public class TopicBaseController extends SystemBaseController {

    @Autowired
    private TopicDocumentService topicDocumentService;


    @Autowired
    private TopicBaseService topicBaseService;

    @Autowired
    private CategoryService categoryService;


    @ApiOperation(value = "打开文章列表页面")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ControllerLog(description = "打开TopicBase模块管理页面")
    public String index() {
        return "system/topicBase/index";
    }

    @ApiOperation(value = "获取文章列表分业集数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "id", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得TopicBase分页集数据")
    public Object getPageSet(PageParam pageParam, String id) {
        String filterSort = "";
        if (id != null) {
            Map map = new HashMap();
            map.put("p1", "sys_category");
            map.put("p2", id);
            map.put("p3", "idStr");
            categoryService.getChildIds(map);
            id = map.get("p3").toString();
            filterSort = " and category_id in (" + id + " )";
        }
        filterSort = BaseUtils.filterSort(request, filterSort);
        PageSet<TopicBase> pageSet = topicBaseService.getPageSet(pageParam, filterSort);
        return pageSet;
    }


    @ApiOperation(value = "获取文章文章分业集数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "id", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/getPageSetHot", method = RequestMethod.POST)
    @ControllerLog(description = "获得TopicBase分页集数据")
    public Object getPageSetHot(PageParam pageParam, String id) {
        String filterSort = "";
        if (id != null) {
            Map map = new HashMap();
            map.put("p1", "sys_category");
            map.put("p2", id);
            map.put("p3", "idStr");
            categoryService.getChildIds(map);
            id = map.get("p3").toString();
            filterSort = " and category_id in (" + id + " )";
        }
        filterSort = BaseUtils.filterSort(request, filterSort).replace("ORDER BY create_time DESC","ORDER BY reply_count DESC");
        PageSet<TopicBase> pageSet = topicBaseService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ApiOperation(value = "获取文章列表详细数据")
    @ApiImplicitParam(name = "uuid",value = "标识",defaultValue = "",required = true,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid",method = RequestMethod.GET)
    @ControllerLog(description = "获得TopicBase模块详细数据")
    public Object getDetailByUuid(String uuid) {
        HashMap<String, Object> map = topicBaseService.getDetails(uuid);
        return map;
    }

    @ApiOperation(value = "打开文章列表新增窗口")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ControllerLog(description = "打开TopicBase模块新增页面")
    public String add() {
        return display();
    }


    @ApiOperation(value = "保存文章列表模块数据")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute TopicBase topicBase, @ModelAttribute TopicDocument topicDocument) {
        int result = topicBaseService.insertSelective(getSaveData(topicBase));
        topicDocument.setTopicUuid(topicBase.getUuid());
        int oaTopicDocumentresult = topicDocumentService.insertSelective(getSaveData(topicDocument));
        return result > 0 && oaTopicDocumentresult > 0 ? success("保存成功！") : failure("保存失败！");
    }


    @ApiOperation(value = "打开文章列表编辑窗口")
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    @ControllerLog(description = "打开TopicBase模块编辑页面")
    public String edit() {
        return "system/topicBase/edit";
    }

    /**
     * 更新数据
     */
    @ApiOperation(value = "跟新文章列表窗口")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新TopicBase模块数据")
    public Object update(@ModelAttribute TopicBase topicBase, @ModelAttribute TopicDocument topicDocument) {
        topicBase.setModifierId(LoginInfo.getUserNameId());
        topicBase.setModifier(LoginInfo.getUserName());
        topicBase.setModifyTime(new Date());
        int result = topicBaseService.updateByPrimaryKeySelective(topicBase);
        int resultOaTopicDocumentContent = topicDocumentService.updateTopicDocumentContent(topicDocument.getContent(), topicBase.getUuid());
        return result > 0 && resultOaTopicDocumentContent > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ApiOperation(value = "删除文章列表窗口")
    @ApiImplicitParam(name = "uuid",value = "标识数组",defaultValue = "",dataType = "String",required = true,allowMultiple = true)
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除TopicBase模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = topicBaseService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ApiOperation(value = "查看文章详情")
    @ApiImplicitParam(name = "uuid",value = "标识",defaultValue = "",required = true,dataType = "String")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ControllerLog(description = "详情")
    public Object detail(String uuid) {
        // 获得内容外的信息
            TopicBase topicBase = topicBaseService.selectByPrimaryKey(uuid);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formatStr = formatter.format(topicBase.getCreateTime());
        request.setAttribute("CreateTime", formatStr);
        request.setAttribute("oaTopicBase", topicBase);
        // 获得内容
        TopicDocument topicDocument = topicDocumentService.getDetailBytopicUuid(uuid);
        request.setAttribute("oaTopicDocument", topicDocument);
        return "system/topicBase/detail";
    }


    @ApiOperation(value = "我的发布")
    @RequestMapping(value = "/myRelease",method = RequestMethod.GET)
    @ControllerLog(description = "我的发布")
    public String myRelease() {
        return "system/topicBase/myRelease";
    }

    @ApiOperation(value = "我的回复")
    @RequestMapping(value = "/myReply",method = RequestMethod.GET)
    @ControllerLog(description = "我的回复")
    public String myReply() {
        return display();
    }


    @ApiOperation(value = "获得文章列表分业集数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "rows", value = "一页显示多少条记录", defaultValue = "20", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "sort", value = "排序", defaultValue = "createTime", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "排序规则", defaultValue = "desc", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "id", defaultValue = "", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/myRelease", method = RequestMethod.POST)
    @ControllerLog(description = "获得TopicBase分页集数据")
    public Object myRelease(PageParam pageParam, String id) {
        String filterSort = "";
        if (id != null) {
            Map map = new HashMap();
            map.put("p1", "sys_category");
            map.put("p2", id);
            map.put("p3", "idStr");
            categoryService.getChildIds(map);
            id = map.get("p3").toString();
            filterSort = "AND category_id in (" + id + " ) AND creator_id = " + LoginInfo.getUserNameId() + " ";
        }
        filterSort += "creatorId = " + LoginInfo.getUserNameId() + "" + BaseUtils.filterSort(request);
        PageSet<TopicBase> pageSet = topicBaseService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

}
