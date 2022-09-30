package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.model.WzscPlan;
import cn.ewsd.material.service.WzscPlanService;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.system.service.SysUserQryOrgService;
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
@RequestMapping("/repository/arriveQry")
public class arriveQryController extends RepositoryBaseController {
    @Autowired
    private MInService mInService;
    @Autowired
    private WzscPlanService wzscPlanService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "repository/arriveQry/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry+monQry;
        String centerQry = request.getParameter("centerQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String reserveQry = request.getParameter("reserveQry");
        String over3Qry = request.getParameter("over3Qry");
        String over1Qry = request.getParameter("over1Qry");
        String nowDate = XDate.getDate();

        try{
            PageSet<WzscPlan> pageSet = wzscPlanService.getArriveQryPageSet(pageParam, filterSort, reserveQry, monthQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), centerQry, matCodeQry, over3Qry, matNameQry, over1Qry, nowDate);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try {
            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String monthQry = yearQry + monQry;
            String centerQry = request.getParameter("centerQry");
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String reserveQry = request.getParameter("reserveQry");
            String over3Qry = request.getParameter("over3Qry");
            String over1Qry = request.getParameter("over1Qry");
            String nowDate = XDate.getDate();

            List<WzscPlan> list = wzscPlanService.getArriveQryList(reserveQry, monthQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), centerQry, matCodeQry, over3Qry, matNameQry, over1Qry, nowDate);
            for (int i = 0; i < list.size(); i++) {
                WzscPlan wzscPlan = list.get(i);
                wzscPlan.setArriveDate(XDate.dateTo10(wzscPlan.getArriveDate()));
                wzscPlan.setCreateDate(XDate.dateTo10(wzscPlan.getCreateDate()));
            }

            String textName = "成本中心,中心描述,库存地,物料组,物料组描述,物料编码,物料描述,单位,价格,提报数量,金额,到货日期,已到货,到货金额,已出库,出库金额,剩余库存,库存金额,提报单号,提报日期,wbs元素,采购单号";
            String fieldName = "centerNo,centerName,matAddr,erpType,typeName,matCode,matName,matUnit,matPrice,matAmount,matBala,arriveDate,arriveAmount,arriveBala,outAmount,outBala,stockAmount,stockBala,epId,createDate,wbs,purchaseNo";
            PoiUtils.exportExcelOld(response, "区队到货记录", textName, fieldName, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
