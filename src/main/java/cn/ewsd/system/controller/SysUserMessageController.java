package cn.ewsd.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.jwt.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.system.model.SysUserMessage;
import cn.ewsd.system.service.SysUserMessageService;

/**
 * 消息
 *
 */
@Controller
@RequestMapping("/system/sysUserMessage")
public class SysUserMessageController extends SystemBaseController {

    @Autowired
    private SysUserMessageService sysUserMessageService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserMessage模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysUserMessage/index";
    }

    @RequestMapping("/goBusinessIndex")
    @ControllerLog(description = "打开SysUserMessage模块管理页面")
    public String goBusinessIndex(@RequestParam Map<String, Object> params) {
        String messageId = request.getParameter("messageId");
        if(messageId.equals("9999999999")){
            return "system/sysUserMessage/index";
        }
        SysUserMessage sysUserMessage = sysUserMessageService.queryObject(messageId);
        //设为已读
        sysUserMessageService.executeReadBatch(new String[]{messageId});
        if(sysUserMessage.getMsgType()==2){
            return sysUserMessage.getMsgUrl();
        }else{
            return "system/sysUserMessage/index";
        }
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserMessage分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = " and is_del != 1";
        filterSort += " and user_uuid = '"+ LoginInfo.getUuid()+"'";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserMessage> pageSet = sysUserMessageService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserMessage模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserMessage sysUserMessage = sysUserMessageService.selectByPrimaryKey(uuid);
        return sysUserMessage;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserMessage模块新增页面")
    public String add() {
        return "system/sysUserMessage/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserMessage模块数据")
    public Object save(@ModelAttribute SysUserMessage sysUserMessage) {
        int result = sysUserMessageService.insertSelective(getSaveData(sysUserMessage));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserMessage模块编辑页面")
    public String edit() {
        return "system/sysUserMessage/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserMessage模块数据")
    public Object update(@ModelAttribute SysUserMessage sysUserMessage) {
        int result = sysUserMessageService.updateByPrimaryKeySelective(getUpdateData(sysUserMessage));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserMessage模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserMessageService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getNavRightHtml")
    @ControllerLog(description = "获得SysUserMessage模块详细数据")
    public Object getNavRightHtml() {
        Map<String,Object> res = new HashMap<>();
        String FS = "";
        int notReadNum = 0;
        notReadNum = sysUserMessageService.queryNotReadNum(LoginInfo.getUuid());
        String htmlListStr = "";
        FS = " user_uuid = '" +LoginInfo.getUuid()+ "' and is_del!= 1 and is_read != 1 order by is_read asc,create_time desc limit 5";
        List<SysUserMessage> messageList = sysUserMessageService.getListByFilterSort(FS);
        htmlListStr = " <div class=\"menu-line\" style=\"height: 62px;\"></div>";

        String str_tmp_a = "<div data-options=\"iconCls:'fa fa-bell'\" class=\"menu-item\" style=\"height: 30px;\"><div class=\"menu-text\" style=\"height: 30px; line-height: 30px;\">";
        String str_tmp_b = "";
        String str_tmp_c = "</div><div class=\"menu-icon fa fa-bell\"></div></div> ";
        for (int i = 0; i < messageList.size(); i++) {
            str_tmp_b = "<a href=\"javascript:window.parent.addParentTab({href:'/system/sysUserMessage/goBusinessIndex?messageId="+messageList.get(i).getUuid()+"'," +
                    "title:'"+messageList.get(i).getMsgUrlName()+"'})\" " +
                    "rel=\"noopener noreferrer\">"+messageList.get(i).getMsgText()+"</a>";
            htmlListStr += str_tmp_a+str_tmp_b+str_tmp_c;
        }
        str_tmp_b = " <a href=\"javascript:window.parent.addParentTab({href:'/system/sysUserMessage/goBusinessIndex?messageId=9999999999'," +
                "title:'消息中心'})\" " +
                "rel=\"noopener noreferrer\">更多消息</a> ";
        htmlListStr += str_tmp_a+str_tmp_b+str_tmp_c;
        res.put("notReadNum",notReadNum>99 ? 99 :notReadNum);
        res.put("htmlListStr",htmlListStr);
        return res;
    }

}
