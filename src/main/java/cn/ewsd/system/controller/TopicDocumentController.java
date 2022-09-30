package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.TopicDocument;
import cn.ewsd.system.service.TopicDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 主题内容
 *
 */
@Controller
@RequestMapping("/system/topicDocument")
public class TopicDocumentController extends SystemBaseController {

    @Autowired
    private TopicDocumentService topicDocumentService;

    @RequestMapping("/index")
    @ControllerLog(description = "打开OaTopicDocument模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得OaTopicDocument分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = BaseUtils.filterSort(request);
        PageSet<TopicDocument> pageSet = topicDocumentService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得OaTopicDocument模块详细数据")
    public Object getDetailByUuid(String uuid) {
        TopicDocument oaTopicDocument = topicDocumentService.selectByPrimaryKey(uuid);
        return oaTopicDocument;
    }

    @RequestMapping("/add")
    @ControllerLog(description = "打开OaTopicDocument模块新增页面")
    public String add() {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存Member模块数据")
    public Object save(@ModelAttribute TopicDocument oaTopicDocument) {
        oaTopicDocument.setUuid(BaseUtils.UUIDGenerator());
        oaTopicDocument.setCreatorId(getCurrentUserNameId());
        oaTopicDocument.setCreator(getCurrentUserName());
        oaTopicDocument.setCreateTime(new Date());
        int result = topicDocumentService.insertSelective(oaTopicDocument);
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @RequestMapping("/edit")
    @ControllerLog(description = "打开OaTopicDocument模块编辑页面")
    public String edit() {
        return display();
    }

    /**
     * 更新数据
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新OaTopicDocument模块数据")
    public Object update(@ModelAttribute TopicDocument oaTopicDocument) {
        oaTopicDocument.setModifierId(getCurrentUserNameId());
        oaTopicDocument.setModifier(getCurrentUserName());
        oaTopicDocument.setModifyTime(new Date());
        int result = topicDocumentService.updateByPrimaryKeySelective(oaTopicDocument);
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除OaTopicDocument模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = topicDocumentService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
