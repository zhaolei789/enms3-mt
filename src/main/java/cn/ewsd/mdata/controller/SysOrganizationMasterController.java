package cn.ewsd.mdata.controller;

import java.util.*;

import cn.ewsd.mdata.service.OrganizationService;
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

import cn.ewsd.mdata.model.SysOrganizationMaster;
import cn.ewsd.mdata.service.SysOrganizationMasterService;

import javax.annotation.Resource;

/**
 * 部门分管领导
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-04-14 11:12:56
 */
@Controller
@RequestMapping("/mdata/sysOrganizationMaster")
public class SysOrganizationMasterController extends MdataBaseController {

    @Autowired
    private SysOrganizationMasterService sysOrganizationMasterService;
    @Resource
    private OrganizationService organizationService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysOrganizationMaster模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "mdata/sysOrganizationMaster/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysOrganizationMaster分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        String orgId = request.getParameter("orgId");
        if(orgId!=null&&!"".equals(orgId)){
            filterSort += " and org_id = "+orgId;
        }else {
            return new PageSet<SysOrganizationMaster>();
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysOrganizationMaster> pageSet = sysOrganizationMasterService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysOrganizationMaster模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysOrganizationMaster sysOrganizationMaster = sysOrganizationMasterService.selectByPrimaryKey(uuid);
        return sysOrganizationMaster;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysOrganizationMaster模块新增页面")
    public String add() {
        return "mdata/sysOrganizationMaster/edit";
    }

    //保存数据
    //新增时,新增该部门下属部门分管领导
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysOrganizationMaster模块数据")
    public Object save(@ModelAttribute SysOrganizationMaster sysOrganizationMaster) {
        int result = 0;
        Map map = new HashMap();
        map.put("p1", "sys_organization");
        map.put("p2", sysOrganizationMaster.getOrgId());
        map.put("p3", 1);
        map.put("p4", "idStr");
        organizationService.getChildIds(map);
        String ChildIds = map.get("p3").toString();//对应orgId的子集
        List<SysOrganizationMaster> sysOrganizationMasterList_ins = new ArrayList<>();
        SysOrganizationMaster sysOrganizationMaster_ins;
        if(ChildIds!=null&&!"".equals(ChildIds)) {
            String childsArray[] = ChildIds.split(",");
            for (int j = 0; j < childsArray.length; j++) {
                sysOrganizationMaster_ins = new SysOrganizationMaster();
                sysOrganizationMaster_ins.setUuid(BaseUtils.UUIDGenerator());
                sysOrganizationMaster_ins.setIsDel(0);
                sysOrganizationMaster_ins.setOrgUuid("");
                sysOrganizationMaster_ins.setOrgId(Integer.parseInt(childsArray[j]));
                sysOrganizationMaster_ins.setOrgLevel(1);
                sysOrganizationMaster_ins.setMasterUuid(sysOrganizationMaster.getMasterUuid());
                sysOrganizationMaster_ins.setMasterName(sysOrganizationMaster.getMasterName());
                sysOrganizationMaster_ins.setMasterPositionName(sysOrganizationMaster.getMasterPositionName());
                sysOrganizationMasterList_ins.add(getSaveData(sysOrganizationMaster_ins));
            }
        }
        if(sysOrganizationMasterList_ins.size()>0){
            result = sysOrganizationMasterService.batchInsert(sysOrganizationMasterList_ins);
        }
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysOrganizationMaster模块编辑页面")
    public String edit() {
        return "mdata/sysOrganizationMaster/edit";
    }

    //更新数据
//    @ResponseBody
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @ControllerLog(description = "更新SysOrganizationMaster模块数据")
//    public Object update(@ModelAttribute SysOrganizationMaster sysOrganizationMaster) {
//        int result = sysOrganizationMasterService.updateByPrimaryKeySelective(getUpdateData(sysOrganizationMaster));
//        return result > 0 ? success("更新成功！") : failure("更新失败！");
//    }

    //删除数据
    //删除时,删除对应下属部门该领导数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysOrganizationMaster模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = 0;
        List<SysOrganizationMaster> sysOrganizationMasterList = sysOrganizationMasterService.queryListByIds(uuid);
        for (int i = 0; i < sysOrganizationMasterList.size(); i++) {
            Map map = new HashMap();
            map.put("p1", "sys_organization");
            map.put("p2", sysOrganizationMasterList.get(i).getOrgId());
            map.put("p3", 1);
            map.put("p4", "idStr");
            organizationService.getChildIds(map);
            String ChildIds = map.get("p3").toString();//对应orgId的子集
            result += sysOrganizationMasterService.executeDeleteByOrgBatch(ChildIds.split(","),sysOrganizationMasterList.get(i).getMasterUuid());
        }
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

}
