package cn.ewsd.cost.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.DbUtil;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.cost.model.FAward;
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

import cn.ewsd.cost.model.FItem;
import cn.ewsd.cost.service.FItemService;

/**
 * 费用项目
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:21
 */
@Controller
@RequestMapping("/cost/fItem")
public class FItemController extends CostBaseController {

    @Autowired
    private FItemService fItemService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开FItem模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/fItem/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得FItem分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<FItem> pageSet = fItemService.getPageSet(pageParam, filterSort);
        pageSet.setRows((List<FItem>)covDataListDic(pageSet.getRows(),"getType#f.getType","assPeriod#f.khzq","denomType#f.denomType","itemType#f.itemType"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得FItem模块详细数据")
    public Object getDetailByUuid(String uuid) {
        FItem fItem = fItemService.selectByPrimaryKey(uuid);
        return fItem;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开FItem模块新增页面")
    public String add() {
        return "cost/fItem/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存FItem模块数据")
    public Object save(@ModelAttribute FItem fItem) throws Exception {
        fItem.setItemId(Snow.getUUID());
        if(fItem.getWarnLine()==null||fItem.getWarnLine().equals("")){
            fItem.setWarnLine(new BigDecimal(0.8));
        }
        if(fItem.getOrderNo()==null||fItem.getOrderNo().equals("")){
            fItem.setOrderNo(0);
        }
        if(fItem.getItemName()==null||fItem.getItemName().equals("")){
            return failure("项目名称，必须输入！");
        }
        String checkSql = " SELECT count(uuid) as NUM FROM f_item WHERE item_name='"+fItem.getItemName()+"'";
        PageData checkNum = DbUtil.executeQueryObject(checkSql);
        if(checkNum!=null&&checkNum.get("NUM")!=null&&Integer.parseInt(checkNum.get("NUM").toString())>0){
            return failure("科目重复！");
        }
        int result = fItemService.insertSelective(getSaveData(fItem));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开FItem模块编辑页面")
    public String edit() {
        return "cost/fItem/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新FItem模块数据")
    public Object update(@ModelAttribute FItem fItem) {
        int result = fItemService.updateByPrimaryKeySelective(getUpdateData(fItem));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除FItem模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = fItemService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getItemList")
    public Object getItemList() throws Exception {
        String sql = "SELECT uuid,item_id,item_name FROM f_item WHERE if_on='1'";
        List<PageData> itemList = DbUtil.executeQueryList(sql);
        return itemList;
    }

}
