package cn.ewsd.material.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.material.model.MItem;
import cn.ewsd.material.model.MPrjItem;
import cn.ewsd.material.service.MItemService;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.material.service.MPrjItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mPrjItem")
public class MPrjItemController extends MaterialBaseController {

    @Autowired
    private MPrjItemService mPrjItemService;
    @Autowired
    private MItemService mItemService;
    @Autowired
    private MPlanService mPlanService;

    @ResponseBody
    @RequestMapping(value = "/getPrjItemList", method = RequestMethod.POST)
    public Object getPrjItemList() {
        try{
            String prjNo = request.getParameter("prjNo");
            if(prjNo==null){
                return new ArrayList<MPrjItem>();
            }
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            return mPrjItemService.getPrjItemListByPrjNo(prjNo, filterSort.replace("ORDER BY create_time DESC", "").replace("pay_type_name", "di.text"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    public Object saveData(@RequestParam String[] itemNo) {
        String prjNo = request.getParameter("prjNo");

        if(itemNo.length < 1){
            return failure("请至少选择一个科目！");
        }

        String[] oldItemNos = mPrjItemService.getItemNosByPrjNo(prjNo);
        Set<String> newSet = new HashSet<String>(Arrays.asList(itemNo));
        Set<String> oldSet = new HashSet<String>(Arrays.asList(oldItemNos));
        oldSet.removeAll(newSet);
        String[] toCheckItemNos = oldSet.toArray(new String[oldSet.size()]);
        if(toCheckItemNos.length>0 && mPlanService.checkPrjItemHasPlaned(prjNo, toCheckItemNos)>0) {
            return failure("不能删除已经做过计划的科目！");
        }

        ArrayList<MPrjItem> list = new ArrayList<MPrjItem>();
        for(int i=0; i<itemNo.length; i++){
            String iNo = itemNo[i];
            MItem mItem = mItemService.queryObject(iNo);

            MPrjItem mPrjItem = new MPrjItem();
            mPrjItem.setPrjNo(prjNo);
            mPrjItem.setItemNo(iNo);
            if("m.payType.3".equals(mItem.getPayType())){
                try{
                    BigDecimal matBala = new BigDecimal(request.getParameter("matBala_"+iNo));
                    if(matBala.doubleValue() <= 0){
                        return failure("预算必须大于0！");
                    }
                    mPrjItem.setMatBala(matBala);
                }catch (Exception e){
                    return failure("请输入科目："+mItem.getItemName()+"的预算！");
                }
            }else{
                mPrjItem.setMatBala(new BigDecimal(0));
            }

            list.add(getSaveData(mPrjItem));
        }

        return mPrjItemService.saveData(list, prjNo) > 0 ? success("保存数据成功！") : failure("保存数据失败！");
    }
}
