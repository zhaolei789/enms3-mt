package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.repository.service.MOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/matAnaly")
public class MatAnalyController extends RepositoryBaseController {

    @Autowired
    MPlanService mPlanService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("beginDateQry", XDate.dateTo10(XDate.getYear()+"0101"));
        request.setAttribute("endDateQry", XDate.dateTo10(XDate.getLastDayOfMonth(XDate.getDate())));
        request.setAttribute("nowYear", XDate.getYear());

        return "repository/matAnaly/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String matQry = request.getParameter("matQry");
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        try{
            return mPlanService.getMatAnalyPageSet(pageParam, filterSort, matQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
//
//    @RequestMapping(value = "/export")
//    public void export(HttpServletResponse response) {
//        try{
//            String matCodeQry = request.getParameter("matCodeQry");
//            String matNameQry = request.getParameter("matNameQry");
//            String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
//            String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
//
//            List<MOut> storeList = mOutService.getOutStatQryItem(beginDateQry, endDateQry, matCodeQry, matNameQry);
//
//            List<HashMap<String, Object>> list = mOutService.getOutStatQryList(beginDateQry, endDateQry, matCodeQry, matNameQry);
//
//            String textName = "领料单位,合计";
//            String fieldName = "team,total";
//            for(int i=0; i<storeList.size(); i++){
//                MOut store = storeList.get(i);
//                textName += ","+store.getStoreName();
//                fieldName += ","+store.getStoreNo();
//            }
//            PoiUtils.exportExcelByListMap(response, "出库物资统计", textName, fieldName, list);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
//    public Object getDetail() {
//        String matCodeQry = request.getParameter("matCodeQry");
//        String matNameQry = request.getParameter("matNameQry");
//        String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
//        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
//        String teamNo = request.getParameter("teamNo");
//
//        try{
//            return mOutService.getOutStatQryDetail(teamNo, beginDateQry, endDateQry, matCodeQry, matNameQry);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @RequestMapping(value = "/exportDetail")
//    public void exportDetail(HttpServletResponse response) {
//        try{
//            String matCodeQry = request.getParameter("matCodeQry");
//            String matNameQry = request.getParameter("matNameQry");
//            String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
//            String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
//            String teamNo = request.getParameter("teamNo");
//
//            List<MOut> list = mOutService.getOutStatQryDetail(teamNo, beginDateQry, endDateQry, matCodeQry, matNameQry);
//
//            String textName = "仓库名称,ERP分类,分类描述,物料编码,物料描述,单位,领用类别,领用日期,发料人,数量,单价,金额,申请说明";
//            String fieldName = "storeName,erpType,typeName,matCode,matName,matUnit,outType,outDate,empName,outAmount,matPrice,bala,applyInfo";
//
//            PoiUtils.exportExcel(response, "出库物资统计", textName, fieldName, list, userInfo.getUserName());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
