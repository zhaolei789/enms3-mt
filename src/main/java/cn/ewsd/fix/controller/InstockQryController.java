package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MIn;
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
 * 回收计划
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-12 14:32:59
 */
@Controller
@RequestMapping("/fix/instockQry")
public class InstockQryController extends FixBaseController {

    @Autowired
    private MInService mInService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        return "fix/instockQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String teamNoQry = request.getParameter("teamNoQry");
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String statusQry = request.getParameter("statusQry");
        String matQry = request.getParameter("matQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MIn> pageSet = mInService.getInstockQryPageSet(pageParam, filterSort, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamNoQry, statusQry, date1Qry, date2Qry, matQry);
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
            String teamNoQry = request.getParameter("teamNoQry");
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String statusQry = request.getParameter("statusQry");
            String matQry = request.getParameter("matQry");

            List<MIn> list = mInService.getInstockQryList(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamNoQry, statusQry, date1Qry, date2Qry, matQry);
            for(int i=0; i<list.size(); i++){
                MIn m = list.get(i);
                m.setInDate(XDate.dateTo10(m.getInDate()));
            }

            String textName = "状态,申请日期,物料编码,物料描述,单位,数量,原值单价,原值金额,修复单价,修复金额,申请单位,审核部门,仓库,修复地点,维修人,验收人";
            String fieldName = "inStepName,inDate,matCode,matName,matUnit,inAmount,matPrice,matBala,price1,fixBala,offerTeamName,teamName,storeName,checkType,checkInfo,checkRemark";
            PoiUtils.exportExcelOld(response, "修复入库明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
