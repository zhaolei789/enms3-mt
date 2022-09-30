package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.common.Constants;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.JsonUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Channel;
import cn.ewsd.system.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller("systemChannelController")
@RequestMapping("/system/channel")
public class ChannelController extends SystemBaseController {

    @Resource
    public ChannelService channelService;

    public String hql = null;

    @ControllerLog(description = "打开Channel模块数据列表")
    @RequestMapping("index")
    public String index() {
        return display();
    }

    @ResponseBody
    @ControllerLog(description = "获取Channel模块分页数据")
    @RequestMapping(value = "getPageSetData")
    public Object getPageSetData(PageParam pageParam) throws Exception {
       // hql = "FROM Channel WHERE " + BaseUtils.filter(request) + " ORDER BY createTime DESC";
        String filterSort = BaseUtils.filter(request);
        PageSet<Channel> pageSet = channelService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @ControllerLog(description = "打开Channel模块新增界面")
    @RequestMapping("add")
    public String add() {
        return display();
    }

    @ControllerLog(description = "打开Channel模块编辑界面")
    @RequestMapping("edit")
    public String edit() {
        return display();
    }

    @ResponseBody
    @ControllerLog(description = "获取Channel模块详细数据")
    @RequestMapping(value = "getDetailByUuid")
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        Channel entity = channelService.selectByPrimaryKey(uuid);
        return entity;
    }

    @ResponseBody
    @ControllerLog(description = "保存Channel模块数据")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Integer save(Channel channel) throws Exception {
        channel.setViewNums(0);
        Integer result = channelService.insertSelective(getSaveData(channel));
        channelService.cacheChannel();
        return result;
    }

    @ResponseBody
    @ControllerLog(description = "更新Channel模块数据")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Integer update(Channel channel) throws Exception {
        Integer viewNums = channel.getViewNums();
        if (viewNums == null) {
            channel.setViewNums(0);
        }
        Integer result = channelService.updateByPrimaryKey(getUpdateData(channel));
        channelService.cacheChannel();
        return result;
    }

    @ResponseBody
    @ControllerLog(description = "删除Channel模块数据")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Object delete() throws Exception {
        String uuid = request.getParameter("uuid");
       // String hql = "DELETE FROM Product WHERE uuid IN (" + uuid + ")";
        int a=channelService.deleteByPrimaryKey(uuid);
        if ( a>0) {
            return JsonUtils.messageJson(200, Constants.OPERATE_TIPS, Constants.OPERATE_SUCCESS_MSG);
        } else {
            return JsonUtils.messageJson(300, Constants.OPERATE_TIPS, Constants.OPERATE_FAILURE_MSG);
        }
    }

    @ControllerLog(description = "导出Channel模块数据")
    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<Channel> channels = channelService.selectAll();
        //PoiUtils.exportExcel(response, request.getParameter("excelTitle"), request.getParameter("colName"), request.getParameter("fieldName"), channels);
    }

    @ResponseBody
    @RequestMapping(value = "getChannelsByLevelId")
    public Object getChannelsByLevelId(String levelId) {
       // String hql = "FROM Channel WHERE levelId = '" + levelId + "' ORDER BY sort ASC";
        List<Channel> channels = channelService.getListByLevelId(levelId);
        return channels;
    }

    /**
     * 用于树形导航中调用指定栏目（如网站栏目）下的所有栏目
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getChannelsById")
    public Object getChannelsById(String id) {
      //  String hql = String.format("FROM Channel WHERE id = '%s' ORDER BY sort ASC", id);
        List<Channel> channels = channelService.getListById(Integer.parseInt(id));
        return channels;
    }

    @ResponseBody
    @RequestMapping(value = "getChannelsByPid")
    public Object getChannelsByPid(String pid) {
       // String hql = "FROM Channel WHERE pid = '" + pid + "' AND levelId <> '0' ORDER BY sort ASC";
        List<Channel> channels = channelService.getListByPidAndLevelId(pid,0);
        return channels;
    }

    @ResponseBody
    @RequestMapping("getFatherIds")
    public Object getFatherIds(PageParam pageParam, String id) {
       // return channelService.callProAndReturn("{call p_get_father_ids(?,?,?,?)}", "sys_channel", id, 1, "@idStr");
        String filterSort = "";
        Map map = new HashMap();
        map.put("p1", "sys_channel");
        map.put("p2", 1);
        map.put("p3", "idStr");
        channelService.getChildIds(map);
        filterSort += BaseUtils.filterSort(request);
        PageSet<Channel> pageSet = channelService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

}