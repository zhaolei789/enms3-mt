package cn.ewsd.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.jwt.LoginInfo;
import com.alibaba.fastjson.JSON;
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

import cn.ewsd.system.model.SysUserStep;
import cn.ewsd.system.service.SysUserStepService;

/**
 * 
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:19:10
 */
@Controller
@RequestMapping("/system/sysUserStep")
public class SysUserStepController extends SystemBaseController {

    @Autowired
    private SysUserStepService sysUserStepService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserStep模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysUserStep/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserStep分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        if(request.getParameter("userId")!=null&&!"".equals(request.getParameter("userId"))&&!"undefined".equals(request.getParameter("userId"))){
            filterSort += " and f.user_id = '"+request.getParameter("userId")+"'";
        }else{
            filterSort += " and f.user_id = 'EEEEEEEEEEE'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserStep> pageSet = sysUserStepService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY f.create_time"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserStep模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserStep sysUserStep = sysUserStepService.selectByPrimaryKey(uuid);
        return sysUserStep;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserStep模块新增页面")
    public String add() {
        return "system/sysUserStep/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserStep模块数据")
    public Object save(@ModelAttribute SysUserStep sysUserStep,String auditOrgs) {
        int result = 0;

        String stepArray[] = sysUserStep.getStepUuid().split(",");
        String orgArray[] =null;
        if(auditOrgs!=null&&!"".equals(auditOrgs)&&!"undefined".equals(auditOrgs)){
            orgArray = auditOrgs.split(",");
        }
        for (int i = 0; i < stepArray.length; i++) {
            if(orgArray==null||orgArray.length<1){//未选部门
                sysUserStep.setStepUuid(stepArray[i]);
                sysUserStep.setAuditOrg(99999);
                sysUserStep.setUuid(BaseUtils.UUIDGenerator());
                sysUserStep.setCreatorId(this.getCurrentUserNameId());
                sysUserStep.setCreator(this.getCurrentUserName());
                sysUserStep.setCreateTime(new Date());
                sysUserStep.setCreatorOrgId(Integer.parseInt(LoginInfo.getOrgId()));
                result += sysUserStepService.insertSelective(sysUserStep);
            }else{
                for (int j = 0; j < orgArray.length; j++) {
                    sysUserStep.setStepUuid(stepArray[i]);
                    sysUserStep.setAuditOrg(Integer.parseInt(orgArray[j]));
                    sysUserStep.setUuid(BaseUtils.UUIDGenerator());
                    sysUserStep.setCreatorId(this.getCurrentUserNameId());
                    sysUserStep.setCreator(this.getCurrentUserName());
                    sysUserStep.setCreateTime(new Date());
                    sysUserStep.setCreatorOrgId(Integer.parseInt(LoginInfo.getOrgId()));
                    result += sysUserStepService.insertSelective(sysUserStep);
                }
            }
        }

        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserStep模块编辑页面")
    public String edit() {
        return "system/sysUserStep/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserStep模块数据")
    public Object update(@ModelAttribute SysUserStep sysUserStep) {
        int result = sysUserStepService.updateByPrimaryKeySelective(getUpdateData(sysUserStep));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserStep模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserStepService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/testq", method = RequestMethod.GET)
    public Object testq(String stepId,String userId,String orgId) {
        Map<String,Object> res = new HashMap<>();
        res.put("checkUserHavePms",sysUserStepService.checkUserHavePms(stepId,userId,orgId));
        res.put("selectUserPmsOrg",sysUserStepService.selectUserPmsOrg(stepId,userId));
        return JSON.toJSONString(res);
    }


}
