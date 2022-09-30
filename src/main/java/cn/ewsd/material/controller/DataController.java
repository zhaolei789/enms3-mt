package cn.ewsd.material.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.MSettleItem;
import cn.ewsd.material.service.DataService;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/data")
public class DataController extends MaterialBaseController {
    @Autowired
    private DataService dataService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/data/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monthQry");

        try{
            List<MSettleItem> list = dataService.getData(LoginInfo.getOrgId(), yearQry+monQry);
            DataCantainer<MSettleItem> dc = new DataCantainer<>((ArrayList)list);
            ArrayList<DataCantainer<MSettleItem>> itemList = dc.getGroup("item_no");

            ArrayList<HashMap<String, Object>> retList = new ArrayList<>();
            for(int i=0; i<itemList.size(); i++){
                HashMap<String, Object> map = new HashMap<>();
                DataCantainer<MSettleItem> itemDc = itemList.get(i);

                double jh = itemDc.findDataCantainer("data_type", "JH").getSum("occ_value");
                double sj = itemDc.findDataCantainer("data_type", "SJ").getSum("occ_value");

                map.put("itemNo", itemDc.getRow(0).getItemNo());
                map.put("itemName", itemDc.getRow(0).getItemName());
                map.put("itemUnit", itemDc.getRow(0).getItemUnit());
                map.put("jh", jh==0 ? "" : jh);
                map.put("sj", sj==0 ? "" : sj);

                retList.add(map);
            }

            return retList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getItem", method = RequestMethod.POST)
    public Object getItem() {
        try{
            List<MSettleItem> mSettleItems = dataService.getItem(LoginInfo.getOrgId());
            for(int i=0; i<mSettleItems.size(); i++){
                MSettleItem mSettleItem = mSettleItems.get(i);
                mSettleItem.setItemName(mSettleItem.getItemName()+"_"+mSettleItem.getItemUnit());
            }
            return mSettleItems;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try{
            dataService.save(request, LoginInfo.get());
            return success("保存成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }
}
