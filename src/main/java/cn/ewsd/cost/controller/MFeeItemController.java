package cn.ewsd.cost.controller;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XException;
import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FAccount;
import cn.ewsd.cost.model.MFeeItem;
import cn.ewsd.cost.service.FAccountService;
import cn.ewsd.cost.service.MFeeItemService;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.mdata.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/mFeeItem")
public class MFeeItemController extends CostBaseController {

    @Autowired
    private MFeeItemService mFeeItemService;

    @ResponseBody
    @RequestMapping(value = "/getItemForSelect")
    public Object getItemForSelect() {
        String teamNo = request.getParameter("teamNo");
        if(teamNo==null){
            teamNo = "0";
        }
        return mFeeItemService.getItemForSelect("m.itemType.42", "1", teamNo);
    }

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/mFeeItem/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String itemType = "m.itemType.42";
        String userTeam = LoginInfo.getOrgId();
        String ifUseQry = request.getParameter("ifUseQry");
        String teamNoQry = request.getParameter("teamNoQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MFeeItem> pageSet = mFeeItemService.getFeeItemPageSet(pageParam, filterSort, itemType, ifUseQry, userTeam, teamNoQry);
        return pageSet;
    }

    @RequestMapping("/add")
    public String add() {
        return "cost/mFeeItem/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MFeeItem mFeeItem) {
        try {
            mFeeItemService.insertFeeItem(mFeeItem);
            return success("保存成功！");
        }catch (XException e){
            return e.getInfo();
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @RequestMapping("/edit")
    public String edit() {
        return "cost/mFeeItem/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByItemNo")
    public Object getDetailByItemNo(String itemNo) {
        MFeeItem mFeeItem = mFeeItemService.getFeeItemByItemNo(itemNo);
        return mFeeItem;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute MFeeItem mFeeItem) {
        try {
            mFeeItemService.updateFeeItem(mFeeItem);
            return success("更新成功！");
        }catch (XException e){
            return e.getInfo();
        }catch (Exception e){
            e.printStackTrace();
            return failure("更新失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Object delete(@RequestParam String itemNo[]) {
        try {
            mFeeItemService.deleteFeeItem(itemNo);
            return success("删除成功！");
        }catch (XException e){
            return e.getInfo();
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getTeamItemQuotaItem")
    public Object getTeamItemQuotaItem(){
        String teamNo = request.getParameter("teamNo");
        String mngTeam = LoginInfo.getOrgId();
        return mFeeItemService.getTeamItemQuotaItem(teamNo, mngTeam);
    }
}
