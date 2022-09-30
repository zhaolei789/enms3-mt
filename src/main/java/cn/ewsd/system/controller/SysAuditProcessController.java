package cn.ewsd.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import cn.ewsd.system.model.SysAuditProcess;
import cn.ewsd.system.service.SysAuditProcessService;

/**
 * 
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-04-23 11:18:36
 */
@Controller
@RequestMapping("/system/sysAuditProcess")
public class SysAuditProcessController extends SystemBaseController {

    @Autowired
    private SysAuditProcessService sysAuditProcessService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysAuditProcess模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/sysAuditProcess/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getRootPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysAuditProcess分页集数据")
    public Object getRootPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort += " and level = 1 ";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysAuditProcess> pageSet = sysAuditProcessService.getPageSet(pageParam, filterSort.replace(" ORDER BY create_time DESC","  ORDER BY process_sequence asc"));
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysAuditProcess分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort += " and fuuid = '"+request.getParameter("fuuid")+"'";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysAuditProcess> pageSet = sysAuditProcessService.getPageSet(pageParam, filterSort.replace(" ORDER BY create_time DESC","  ORDER BY process_sequence asc"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysAuditProcess模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysAuditProcess sysAuditProcess = sysAuditProcessService.selectByPrimaryKey(uuid);
        return sysAuditProcess;
    }

    @ResponseBody
    @RequestMapping(value = "/getListByFuuid")
    @ControllerLog(description = "获得getListByFuuid模块详细数据")
    public Object getListByFuuid(String fuuid) {
        List<SysAuditProcess> sysAuditProcessList = sysAuditProcessService.queryListByFuuid(fuuid);
        return sysAuditProcessList;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysAuditProcess模块新增页面")
    public String add() {
        return "system/sysAuditProcess/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysAuditProcess模块数据")
    public Object save(@ModelAttribute SysAuditProcess sysAuditProcess) {
        if(sysAuditProcess.getLevel()==2){
            sysAuditProcessService.executeUpdateInitState("1",sysAuditProcess.getFuuid());
            SysAuditProcess Fsap = sysAuditProcessService.queryObject(sysAuditProcess.getFuuid());
            sysAuditProcess.setProcessNo(Fsap.getProcessNo()+sysAuditProcess.getProcessNo());
        }
        if(sysAuditProcessService.queryObjectByProcessNo(sysAuditProcess.getProcessNo())!=null){
            return failure("编码重复！");
        }
        int result = sysAuditProcessService.insertSelective(getSaveData(sysAuditProcess));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysAuditProcess模块编辑页面")
    public String edit() {
        return "system/sysAuditProcess/edit";
    }

    @RequestMapping("/editRoot")
    @ControllerLog(description = "打开SysAuditProcess模块编辑页面")
    public String editRoot() {
        return "system/sysAuditProcess/edit_Root";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysAuditProcess模块数据")
    public Object update(@ModelAttribute SysAuditProcess sysAuditProcess) {
        if(sysAuditProcess.getLevel()==2){
            sysAuditProcessService.executeUpdateInitState("1",sysAuditProcess.getFuuid());
            /*SysAuditProcess Fsap = sysAuditProcessService.queryObject(sysAuditProcess.getFuuid());
            sysAuditProcess.setProcessNo(Fsap.getProcessNo()+sysAuditProcess.getProcessNo());*/
        }
        SysAuditProcess Fsap2 = sysAuditProcessService.queryObjectByProcessNo(sysAuditProcess.getProcessNo());
        if(Fsap2!=null&&!Fsap2.getUuid().equals(sysAuditProcess.getUuid())){
            return failure("编码重复！");
        }
        int result = sysAuditProcessService.updateByPrimaryKeySelective(getUpdateData(sysAuditProcess));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysAuditProcess模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysAuditProcessService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }


    //初始化数据
    @ResponseBody
    @RequestMapping(value = "/initBatch", method = RequestMethod.POST)
    @ControllerLog(description = "初始化SysAuditProcess模块数据")
    public Object initBatch(@RequestParam String[] uuid) {
        int result = 0;
        String lastStep = "";
        String nextStep = "";
        List<SysAuditProcess> sysAuditProcessList = sysAuditProcessService.queryListByUuids(uuid);
        for (int i = 0; i < sysAuditProcessList.size() ; i++) {
            if(sysAuditProcessList.get(i).getLevel()!=1){continue;}
            //审批子步骤List
            List<SysAuditProcess> AuditProcessSubList = sysAuditProcessService.queryListByFuuid(sysAuditProcessList.get(i).getUuid());
            if(AuditProcessSubList.size()<=0){continue;}
            for (int j = 0; j < AuditProcessSubList.size(); j++) {
                if(j==0){
                    //第一个步骤
                    lastStep = "start";
                    nextStep = AuditProcessSubList.size()>1?AuditProcessSubList.get(j+1).getUuid():"end";
                }else if(j == AuditProcessSubList.size()-1){
                    //最后一个步骤
                    lastStep = (j-1)>=0?AuditProcessSubList.get(j-1).getUuid():"start";
                    nextStep = "end";
                }else{
                    lastStep = AuditProcessSubList.get(j-1).getUuid();
                    nextStep = AuditProcessSubList.get(j+1).getUuid();
                }
                sysAuditProcessService.executeUpdateStep(lastStep,nextStep,AuditProcessSubList.get(j).getUuid());
            }
            sysAuditProcessService.executeUpdateInitState("2",sysAuditProcessList.get(i).getUuid());
            result++;
        }
        return result > 0 ? success("操作成功！") : failure("初始化失败！");
    }

}
