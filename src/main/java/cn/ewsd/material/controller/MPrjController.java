package cn.ewsd.material.controller;

import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPrj;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.material.service.MPrjItemService;
import cn.ewsd.material.service.MPrjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mPrj")
public class MPrjController extends MaterialBaseController {

    @Autowired
    private MPrjService mPrjService;
    @Autowired
    private MPlanService mPlanService;
    @Autowired
    private MPrjItemService mPrjItemService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/mPrj/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MPrj> pageSet = mPrjService.getPageSet(pageParam, filterSort.replace("ORDER BY ", "ORDER BY p."));
        return pageSet;
    }

    @RequestMapping("/edit")
    public String edit() {
        return "material/mPrj/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MPrj mPrj) {
        String orgId = LoginInfo.getOrgId();
        mPrj.setCheckTeam(Integer.parseInt(orgId));

        String beginDate = mPrj.getBeginDate();
        String endDate = mPrj.getEndDate();
        if(beginDate.length() > 0){
            mPrj.setBeginDate(XDate.dateTo8(beginDate.substring(0, 10)));
        }
        if(endDate.length() > 0){
            mPrj.setEndDate(XDate.dateTo8(endDate.substring(0, 10)));
        }

        String prjType2 = mPrj.getPrjType2();

        mPrj.setPrjNo(Snow.getUUID()+"");
        mPrj.setPrjType1(prjType2.substring(prjType2.length()-2, prjType2.length()-1));
        mPrj.setPrjAddr("");
        mPrj.setPrjInfo("");
        mPrj.setAccessory("");

        int result = mPrjService.executeSave(getSaveData(mPrj));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getUpNameAll", method = RequestMethod.POST)
    public Object getUpNameAll() {
        return mPrjService.getUpNameAll();
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    public Object getDetailByUuid(@RequestParam String uuid) {
        MPrj mPrj = mPrjService.queryObject(uuid);
        return mPrj;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute MPrj mPrj) {
        String beginDate = mPrj.getBeginDate();
        String endDate = mPrj.getEndDate();
        if(beginDate.length() > 0){
            mPrj.setBeginDate(XDate.dateTo8(beginDate.substring(0, 10)));
        }
        if(endDate.length() > 0){
            mPrj.setEndDate(XDate.dateTo8(endDate.substring(0, 10)));
        }
        String prjType2 = mPrj.getPrjType2();
        mPrj.setPrjType1(prjType2.substring(prjType2.length()-2, prjType2.length()-1));

        int result = mPrjService.executeUpdate(getUpdateData(mPrj));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam String uuid) {
        MPrj mPrj = (MPrj) getDetailByUuid(uuid);
        String prjNo = mPrj.getPrjNo();
        if(mPlanService.getCountByPrjNo(prjNo)>0){
            return failure("该工程已开始编制材料计划，不能删除！");
        }

        mPrjItemService.deleteByPrjNo(prjNo);
        int result = mPrjService.executeDelete(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjList")
    public Object getPrjList(){

        String teamNo = request.getParameter("teamNo");
        if(teamNo == null){
            teamNo = configService.getConfigByCode("XT_TEAM_MATERIAL");
        }
        String prjStatus = request.getParameter("prjStatus");
        if(prjStatus==null){
            prjStatus = "m.prjStatus.0";
        }

        return mPrjService.getPrjList(teamNo, prjStatus);
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjList1")
    public Object getPrjList1(){
           String teamNo = request.getParameter("teamNo");
           return mPrjService.getPrjList(teamNo, "m.prjStatus.0");
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjList2")
    public Object getPrjList2(){
        return mPrjService.getPrjList(LoginInfo.getOrgId(), "m.prjStatus.0");
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjList3")
    public Object getPrjList3(String teamNo){
        return mPrjService.getPrjList(teamNo, "m.prjStatus.0");
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjFromBackQuota")
    public Object getPrjFromBackQuota(){
        return mPrjService.getPrjFromBackQuota();
    }
}
