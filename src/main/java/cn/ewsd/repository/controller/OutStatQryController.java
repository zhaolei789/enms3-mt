package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/outStatQry")
public class OutStatQryController extends RepositoryBaseController {
    @Autowired
    private MOutService mOutService;
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String beginDateQry = request.getParameter("beginDateQry");
        beginDateQry = beginDateQry==null ? XDate.getMonth()+"01" : XDate.dateTo8(beginDateQry);
        String endDateQry = request.getParameter("endDateQry");
        endDateQry = endDateQry==null ? XDate.getDate() : XDate.dateTo8(endDateQry);
        String matCodeQry = request.getParameter("matCodeQry")==null ? "" : request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry")==null ? "" : request.getParameter("matNameQry");

        request.setAttribute("beginDateQry", XDate.dateTo10(beginDateQry));
        request.setAttribute("endDateQry", XDate.dateTo10(endDateQry));
        request.setAttribute("matCodeQry", matCodeQry);
        request.setAttribute("matNameQry", matNameQry);

        List<MOut> storeList = mOutService.getOutStatQryItem(beginDateQry, endDateQry, matCodeQry, matNameQry);
        String storeStr = "";
        for(int i=0; i<storeList.size(); i++){
            MOut mOut = storeList.get(i);
            storeStr += (i==0 ? "" : ",") + mOut.getStoreNo() + "_" + mOut.getStoreName();
        }
        request.setAttribute("storeStr", storeStr);

        return "repository/outStatQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getList() {
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));

        try{
            return mOutService.getOutStatQryList(beginDateQry, endDateQry, matCodeQry, matNameQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
        try{
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
            String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));

            List<MOut> storeList = mOutService.getOutStatQryItem(beginDateQry, endDateQry, matCodeQry, matNameQry);

            List<HashMap<String, Object>> list = mOutService.getOutStatQryList(beginDateQry, endDateQry, matCodeQry, matNameQry);

            String textName = "领料单位,合计";
            String fieldName = "team,total";
            for(int i=0; i<storeList.size(); i++){
                MOut store = storeList.get(i);
                textName += ","+store.getStoreName();
                fieldName += ","+store.getStoreNo();
            }
            PoiUtils.exportExcelByListMap(response, "出库物资统计", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public Object getDetail() {
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
        String teamNo = request.getParameter("teamNo");

        try{
            return mOutService.getOutStatQryDetail(teamNo, beginDateQry, endDateQry, matCodeQry, matNameQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/exportDetail")
    public void exportDetail(HttpServletResponse response) {
        try{
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
            String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
            String teamNo = request.getParameter("teamNo");

            List<MOut> list = mOutService.getOutStatQryDetail(teamNo, beginDateQry, endDateQry, matCodeQry, matNameQry);

            String textName = "仓库名称,ERP分类,分类描述,物料编码,物料描述,单位,领用类别,领用日期,发料人,数量,单价,金额,申请说明";
            String fieldName = "storeName,erpType,typeName,matCode,matName,matUnit,outType,outDate,empName,outAmount,matPrice,bala,applyInfo";

            PoiUtils.exportExcelOld(response, "出库物资统计", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
