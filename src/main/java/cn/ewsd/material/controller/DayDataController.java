package cn.ewsd.material.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.material.model.MSettleItem;
import cn.ewsd.material.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/material/dayData")
public class DayDataController extends MaterialBaseController {
    @Autowired
    private DataService dataService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/dayData/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monthQry");
        String itemNo = request.getParameter("itemNo");
        String occMonth = yearQry+monQry;

        int lastDay = Integer.parseInt(XDate.getLastDayOfMonth(occMonth+"01").substring(6, 8));
System.out.println(itemNo);
        try{
            List<MSettleItem> list = dataService.getDayList(occMonth, itemNo);
            DataCantainer<MSettleItem> dc = new DataCantainer<>((ArrayList)list);

            ArrayList<HashMap<String, Object>> retList = new ArrayList<>();
            for(int i=1; i<=lastDay; i++){
                HashMap<String, Object> map = new HashMap<>();
                String currentDay = i < 10 ? "0"+i : i+"";
System.out.println(dc.findDataCantainer("data_type", "JH", "occ_day", currentDay).getSum("occ_value"));
                map.put("day", currentDay);
                map.put("jh", dc.findDataCantainer("data_type", "JH", "occ_day", currentDay).getSum("occ_value"));
                map.put("sj", dc.findDataCantainer("data_type", "SJ", "occ_day", currentDay).getSum("occ_value"));

                retList.add(map);
            }

            return retList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try{
            dataService.saveData(request, LoginInfo.get());
            return success("保存成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }
}
