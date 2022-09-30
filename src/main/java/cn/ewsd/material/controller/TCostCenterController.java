package cn.ewsd.material.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.TCostCenter;
import cn.ewsd.material.service.TCostCenterService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/costCenter")
public class TCostCenterController extends MaterialBaseController {

    @Autowired
    private TCostCenterService tCostCenterService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/TCostCenter/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        if("".equals(filterSort)){
            filterSort = " 1=1 GROUP BY cc.center_no, cc.center_name, cc.move_type, di.text";
        }else{
            filterSort = filterSort.replace("1 = 1 ORDER BY ", "1=1 GROUP BY cc.center_no, cc.center_name, cc.move_type, di.text ORDER BY cc.");
        }
        PageSet<TCostCenter> pageSet = tCostCenterService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @RequestMapping("/edit")
    public String edit() {
        return "material/TCostCenter/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute TCostCenter tCostCenter) {
        tCostCenter.setCenterNo(tCostCenter.getCenterNo().toUpperCase());
        if(tCostCenter.getCenterNo().length()>10){
            return failure("成本中心最多10个字符！");
        }
        if(tCostCenter.getCenterName().length()>50){
            return failure("中心名称最多50个字符！");
        }
        if(tCostCenterService.checkCostCenterExistByNoOrName(tCostCenter.getCenterNo(), tCostCenter.getCenterName())>0){
            return failure("该成本中心已经存在！");
        }
        String[] deptNos = request.getParameterValues("deptNo");
        if(deptNos==null || deptNos.length<1){
            return failure("请选择关联单位！");
        }

        int result = tCostCenterService.executeSave(getSaveData(tCostCenter), deptNos);
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByCenterNo")
    public Object getDetailByCenterNo(String centerNo) {
        TCostCenter tCostCenter = tCostCenterService.queryObject(centerNo);
        return tCostCenter;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute TCostCenter tCostCenter) {
        if(tCostCenter.getCenterNo().length()>10){
            return failure("成本中心最多10个字符！");
        }
        if(tCostCenter.getCenterName().length()>50){
            return failure("中心名称最多50个字符！");
        }
        String[] deptNos = request.getParameterValues("deptNo");
        if(deptNos==null || deptNos.length<1){
            return failure("请选择关联单位！");
        }
        ArrayList<String[]> condArr = new ArrayList<String[]>();
        condArr.add(new String[]{"center_no", "!=", tCostCenter.getCenterNo()});
        condArr.add(new String[]{"center_name", "=", tCostCenter.getCenterName()});
        if(utilService.checkColumnDataExist("t_cost_center", condArr)){
            return failure("该成本中心已经存在！");
        }

        int result = tCostCenterService.executeUpdate(getUpdateData(tCostCenter), deptNos);
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam String centerNo) {
        String deptNo = LoginInfo.getOrgId();

        int result = tCostCenterService.executeDelete(centerNo, deptNo);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getTeamCenter", method = RequestMethod.POST)
    public Object getTeamCenter(String teamNo) {
        if(teamNo==null || "".equals(teamNo)){
            teamNo = LoginInfo.getOrgId();
        }

        return tCostCenterService.getTeamCenter(teamNo);
    }

    @ResponseBody
    @RequestMapping(value = "/getCenterSet")
    public Object getCenterSet() {
        String userId = request.getParameter("userId");
        String userDeptIds = "";
        if(userId!=null){
            userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());
        }

        try{
            return tCostCenterService.getCenterSet(userDeptIds);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
