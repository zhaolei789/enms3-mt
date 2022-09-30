package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.mapper.MOutMapper;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

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
@RequestMapping("/fix/oldOutQry")
public class oldOutQryController extends FixBaseController {

    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private MOutService mOutService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        return "fix/oldOutQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String teamNoQry = request.getParameter("teamNoQry");
        String outTeamQry = request.getParameter("outTeamQry");
        String matQry = request.getParameter("matQry");
        String stepQry = request.getParameter("stepQry");
        String storeQry = request.getParameter("storeQry");
        String typeQry = request.getParameter("typeQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MOut> pageSet = mOutService.getOldOutQryPageSet(pageParam, filterSort, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), LoginInfo.getOrgId(), teamNoQry, typeQry, stepQry, outTeamQry, date1Qry, date2Qry, storeQry, matQry);
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
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String teamNoQry = request.getParameter("teamNoQry");
            String outTeamQry = request.getParameter("outTeamQry");
            String matQry = request.getParameter("matQry");
            String stepQry = request.getParameter("stepQry");
            String storeQry = request.getParameter("storeQry");
            String typeQry = request.getParameter("typeQry");

            List<MOut> list = mOutService.getOldOutQryList(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), LoginInfo.getOrgId(), teamNoQry, typeQry, stepQry, outTeamQry, date1Qry, date2Qry, storeQry, matQry);
            for(int i=0; i<list.size(); i++){
                MOut m = list.get(i);
                m.setApplyDate(XDate.dateTo10(m.getApplyDate()));
                m.setDrawStep("72023".equals(m.getDrawStep()) ? m.getZnksName() : m.getDrawStepName());
                m.setOutDate(XDate.dateTo10(m.getOutDate()));
                m.setIfSend("1".equals(m.getIfSend()) ? "是" : "否");
                m.setPickDate(XDate.dateTo10(m.getPickDate()) + " " + ("".equals(m.getPickTime()) || m.getPickTime()==null ? "" : m.getPickTime().substring(0, 2)));
            }

            String textName = "记录号,申请日期,领料仓库,领料步骤,物料编码,物料描述,单位,申请数量,复用数量,复用单价,复用价值,成本中心,发料日期,是否配送,配送地点,配送时间,备注";
            String fieldName = "reserve2,applyDate,storeName,drawStep,matCode,matName,matUnit,applyAmount,outAmount,matPrice,matBala,centerName,outDate,ifSend,useAddr,pickDate,applyInfo";
            PoiUtils.exportExcelOld(response, "复用明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
