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
@RequestMapping("/repository/allStockQry")
public class allStockQryController extends RepositoryBaseController {
    @Autowired
    UtilService utilService;
    @Autowired
    MStoreService mStoreService;
    @Autowired
    MStockService mStockService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "repository/allStockQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String erpTypeQry = request.getParameter("erpTypeQry");
        String stLevelQry = request.getParameter("stLevelQry");
        String storeNoQry = request.getParameter("storeNoQry");
        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2730);

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MStock> pageSet = mStockService.getAllStockQryPageSet(pageParam, filterSort, storeNoQry, stLevelQry, ifPower, matCodeQry, erpTypeQry, matNameQry);
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
            String stLevelQry = request.getParameter("stLevelQry");
            String storeNoQry = request.getParameter("storeNoQry");
            boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2730);

            List<MStock> list = mStockService.getAllStockQryList(storeNoQry, stLevelQry, ifPower, matCodeQry, erpTypeQry, matNameQry);
            for(int i=0; i<list.size(); i++){
                MStock mStock = list.get(i);
                String matCode = mStock.getMatCode();
                if(!"".equals(matCode) && matCode!=null){
                    mStock.setMatCode("X".equals(matCode.substring(0, 1)) ? matCode.substring(1) : matCode);
                }
            }

            String textName = "仓库级别,超市仓库,ERP分类,粉料描述,物料编码,物料描述,单位,单价,库存数量,金额,货位信息";
            String fieldName = "storeLevelName,storeName,erpType,typeName,matCode,matName,matUnit,matPrice,amount,matBala,siteCode";
            PoiUtils.exportExcelOld(response, "全矿库存", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
