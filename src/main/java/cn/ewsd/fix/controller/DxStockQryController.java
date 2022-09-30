package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
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
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/dxStockQry")
public class DxStockQryController extends FixBaseController {

    @Autowired
    private MStockService mstockService;
    @Autowired
    private MStoreService mStoreService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        List<MStore> list = mStoreService.getStoreList(null, null, null, null, null, null, "r.storeType2.21", "r.storeLevel.1");
        String storeNo = "";
        if(list.size()>0){
            storeNo = list.get(0).getStoreNo();
        }
        request.setAttribute("storeNo", storeNo);

        return "fix/dxStockQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String storeNoQry = request.getParameter("storeNoQry");
        String flagQry = request.getParameter("flagQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MStock> pageSet = mstockService.getDxStockPageSet(pageParam, filterSort, storeNoQry, flagQry, matCodeQry, matNameQry);
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
            String storeNoQry = request.getParameter("storeNoQry");
            String flagQry = request.getParameter("flagQry");
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");

            List<MStock> list = mstockService.getDxStockList(storeNoQry, flagQry, matCodeQry, matNameQry);

            String textName = "仓库,物料状态,物料编码,物料描述,单位,单价,库存数量,金额,货位";
            String fieldName = "storeName,matCode,matName,matUnit,matPrice,stockAmount,matBala,siteCode";
            PoiUtils.exportExcelOld(response, "回收情况", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
