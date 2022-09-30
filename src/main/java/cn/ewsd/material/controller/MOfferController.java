package cn.ewsd.material.controller;

import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MOffer;
import cn.ewsd.material.model.MOfferMat;
import cn.ewsd.material.service.*;
import cn.ewsd.mdata.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mOffer")
public class MOfferController extends MaterialBaseController {

    @Autowired
    private MOfferService mOfferService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MOfferMatService mOfferMatService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/mOffer/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MOffer> pageSet = mOfferService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    @RequestMapping("/edit")
    public String edit() {
        return "material/mOffer/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/getNewOfferNo")
    public Object getNewOfferNo() {
        MOffer mOffer = new MOffer();
        String newOfferNo = mOfferService.getNewOfferNo();
        if(newOfferNo.length()>6){
            return failure("供应商编码不能超过六位！");
        }
        mOffer.setOfferNo(newOfferNo);
        return mOffer;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MOffer mOffer) {
        String offerName = mOffer.getOfferName();
        if("".equals(offerName)){
            return failure("供应商名称必须填写！");
        }
        ArrayList<String[]> condArr = new ArrayList<String[]>();
        condArr.add(new String[]{"offer_name", "=", offerName});
        if(utilService.checkColumnDataExist("m_offer", condArr)){
            return failure("供应商名称重复！");
        }

        int result = mOfferService.executeSave(getSaveData(mOffer));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    public Object getDetailByUuid(String uuid) {
        MOffer mOffer = mOfferService.queryObject(uuid);
        return mOffer;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute MOffer mOffer) {
        String offerName = mOffer.getOfferName();

        int result = mOfferService.executeUpdate(getUpdateData(mOffer));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam String uuid) {
        MOffer mOffer = mOfferService.queryObject(uuid);
        String offerNo = mOffer.getOfferNo();

        List<MOfferMat> offerMatList = mOfferMatService.getOfferMatByOfferNo(offerNo);
        if(offerMatList.size()>0){
            return failure("供应商存在供货信息，不能删除！");
        }

        int result = mOfferService.executeDelete(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getOfferByMatNo")
    public Object getOfferByMatNo(@RequestParam String matNo) {
        return mOfferMatService.getOfferMatByMatNo(matNo);
    }

    @ResponseBody
    @RequestMapping(value = "/getOfferList")
    public Object getOfferList() {
       try{
           String offerName = request.getParameter("offerName");

           return mOfferService.getOfferList(offerName);
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
}
