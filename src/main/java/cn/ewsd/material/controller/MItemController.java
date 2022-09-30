package cn.ewsd.material.controller;

import java.math.BigDecimal;
import java.util.Map;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.material.model.MItem;
import cn.ewsd.material.service.MItemService;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/mItem")
public class MItemController extends MaterialBaseController {

    @Autowired
    private MItemService mItemService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/mItem/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MItem> pageSet = mItemService.getPageSet(pageParam, filterSort.replace("ORDER BY ", "ORDER BY i."));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    public Object getDetailByUuid(String itemNo) {
        MItem mItem = mItemService.queryObject(itemNo);
        return mItem;
    }

    //新增页面
    @RequestMapping("/add")
    public String add() {
        return "material/mItem/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MItem mItem) {
        String errorInfo = "";
        String itemNo = mItem.getItemNo();
        String itemName = mItem.getItemName();
        BigDecimal warnLine = mItem.getWarnLine();
        if("".equals(itemNo)){
            errorInfo = "请输入科目编号！";
        }else if(itemNo.length()>6){
            errorInfo = "科目编号最多6位！";
        }else if("".equals(itemName)){
            errorInfo = "请输入科目名称！";
        }else if(warnLine==null) {
            mItem.setWarnLine(new BigDecimal("0.8"));
        }else if(warnLine.doubleValue() < 0 || warnLine.doubleValue() > 1){
            errorInfo = "预警基线必须是0-1的数字！";
        }else if(mItemService.queryObject(itemNo) != null){
            errorInfo = "科目编号重复！";
        }
        if(!"".equals(errorInfo)){
            return failure(errorInfo);
        }
        mItem.setBmcbjsCoe(new BigDecimal("0"));
        mItem.setBmfjkhCoe(new BigDecimal("0"));

        int result = mItemService.executeSave(getSaveData(mItem));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    public String edit() {
        return "material/mItem/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute MItem mItem) {
        String errorInfo = "";
        String itemNo = mItem.getItemNo();
        String itemName = mItem.getItemName();
        BigDecimal warnLine = mItem.getWarnLine();
        if("".equals(itemNo)){
            errorInfo = "请输入科目编号！";
        }else if(itemNo.length()>6){
            errorInfo = "科目编号最多6位！";
        }else if("".equals(itemName)){
            errorInfo = "请输入科目名称！";
        }else if(warnLine==null) {
            mItem.setWarnLine(new BigDecimal("0.8"));
        }else if(warnLine.doubleValue() < 0 || warnLine.doubleValue() > 1){
            errorInfo = "预警基线必须是0-1的数字！";
        }else if(mItemService.getItemByItemNoAndNotByUuid(itemNo, mItem.getUuid()) != null){
            errorInfo = "科目编号重复！";
        }
        if(!"".equals(errorInfo)){
            return failure(errorInfo);
        }

        int result = mItemService.executeUpdate(getUpdateData(mItem));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = mItemService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getItemList")
    public Object getItemList() {
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String planType = request.getParameter("planType");
        String planStep = request.getParameter("planStep");
        return mItemService.getItemList(prjNo, planMonth, planType, planStep);
    }

    @ResponseBody
    @RequestMapping(value = "/getMatItemSet")
    public Object getMatItemSet() {
        return mItemService.getMatItemSet(false, "");
    }

    @ResponseBody
    @RequestMapping(value = "/getItemList1")
    public Object getItemList1(){
        String teamNo = LoginInfo.getOrgId();
        String fMonth = XDate.getMonth();
        return mItemService.getItemList1(teamNo, fMonth);
    }
}
