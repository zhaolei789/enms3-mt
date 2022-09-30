package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/stockQry")
public class StockQryController extends RepositoryBaseController {
    @Autowired
    UtilService utilService;
    @Autowired
    MStoreService mStoreService;
    @Autowired
    MStockService mStockService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2655);
        List<MStore> list = mStoreService.getPowerStore(ifPower, null);
        String storeNo = "";
        if(list.size() > 0 && params.get("warnFlag")==null){
            storeNo = list.get(0).getStoreNo();
        }
        request.setAttribute("storeNo", storeNo);
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getYear()+"0101"));
        request.setAttribute("warnFlag", params.get("warnFlag")==null ? "" : (String)params.get("warnFlag"));
        request.setAttribute("flagQry", params.get("flagQry")==null ? "" : (String)params.get("flagQry"));

        return "repository/stockQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String erpTypeQry = request.getParameter("erpTypeQry");
        String abcTypeQry = request.getParameter("abcTypeQry");
        String flagQry = request.getParameter("flagQry");
        String storeNoQry = request.getParameter("storeNoQry");
        String warnFlag = request.getParameter("warnFlag");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MStock> pageSet = mStockService.getStockQryPageSet(pageParam, filterSort, storeNoQry, abcTypeQry, flagQry, utilService.checkRight(LoginInfo.getRoleId(), 2655), matCodeQry, erpTypeQry, matNameQry, warnFlag);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String erpTypeQry = request.getParameter("erpTypeQry");
            String abcTypeQry = request.getParameter("abcTypeQry");
            String flagQry = request.getParameter("flagQry");
            String storeNoQry = request.getParameter("storeNoQry");
            String warnFlag = request.getParameter("warnFlag");

            List<MStock> list = mStockService.getStockQryList(storeNoQry, abcTypeQry, flagQry, utilService.checkRight(LoginInfo.getRoleId(), 2655), matCodeQry, erpTypeQry, matNameQry, warnFlag);
            for(int i=0; i<list.size(); i++){
                MStock mStock = list.get(i);
                String oldCode = mStock.getOldCode();
                if(!"".equals(oldCode)){
                    mStock.setOldCode(oldCode+":"+mStock.getOldAmount());
                }
            }

            String textName = "仓库,ERP分类,分类描述,物料编码,物料描述,单位,单价,最低库存,库存数量,金额,库存差,旧料库存,货位";
            String fieldName = "storeName,erpType,typeName,matCode,matName,matUnit,matPrice,redWarn,stockAmount,matBala,diffAmount,oldCode,siteCode";
            PoiUtils.exportExcelOld(response, "重点物资分析", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object getDetail() {
        String matNo = request.getParameter("matNo");
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2655);
        String storeNoQry = request.getParameter("storeNoQry");

        try {
            return mStockService.getStockQryDetail(matNo, date1Qry, date2Qry, storeNoQry, ifPower);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
