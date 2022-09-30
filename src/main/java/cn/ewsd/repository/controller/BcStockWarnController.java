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
@RequestMapping("/repository/bcStockWarn")
public class BcStockWarnController extends RepositoryBaseController {
    @Autowired
    UtilService utilService;
    @Autowired
    MStoreService mStoreService;
    @Autowired
    MStockService mStockService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        List<MStore> list = mStoreService.getStoreList(null, null ,null, null, null, null, null, "r.storeLevel.1");
        String storeQry = "";
        if(list.size() > 0){
            storeQry = list.get(0).getStoreNo();
        }
        request.setAttribute("storeQry", storeQry);

        return "repository/bcStockWarn/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getList(PageParam pageParam) {
        String flagQry = request.getParameter("flagQry");
        String codeQry = request.getParameter("codeQry");
        String nameQry = request.getParameter("nameQry");
        String storeQry = request.getParameter("storeQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MStock> pageSet = mStockService.getWarnStockPageSet(pageParam, filterSort, storeQry, flagQry, codeQry, nameQry);
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
            String flagQry = request.getParameter("flagQry");
            String codeQry = request.getParameter("codeQry");
            String nameQry = request.getParameter("nameQry");
            String storeQry = request.getParameter("storeQry");

            List<MStock> list = mStockService.getWarnStockList(storeQry, flagQry, codeQry, nameQry);

            String textName = "仓库,物料分类,分类描述,物料编码,物料描述,单位,预警数量,库存数量";
            String fieldName = "storeName,erpType,typeName,matCode,matName,matUnit,warnAmount,stockAmount";
            PoiUtils.exportExcelOld(response, "库存预警机制", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
