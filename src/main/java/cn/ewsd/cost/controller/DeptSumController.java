package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FChargeFee;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.cost.service.FChargeFeeService;
import cn.ewsd.cost.service.MOutAssessService;
import cn.ewsd.system.service.ConfigService;
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
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/deptSum")
public class DeptSumController extends CostBaseController {

    @Autowired
    private FChargeFeeService fChargeFeeService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "cost/deptSum/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String mon1Qry = request.getParameter("mon1Qry");
        String mon2Qry = request.getParameter("mon2Qry");
        String month1 = yearQry + mon1Qry;
        String month2 = yearQry + mon2Qry;
        String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        try{
            return fChargeFeeService.getDeptSumList(userDeptIds, yearQry, month1, month2);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletResponse response) {
        try{
            String yearQry = request.getParameter("yearQry");
            String mon1Qry = request.getParameter("mon1Qry");
            String mon2Qry = request.getParameter("mon2Qry");
            String month1 = yearQry + mon1Qry;
            String month2 = yearQry + mon2Qry;
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<FChargeFee> list = fChargeFeeService.getDeptSumList(userDeptIds, yearQry, month1, month2);

            String textName = "业务部门,年度总费用,当期定额费用,当期考核金额,当期节+超-金额,当期节+超-比例,累计发生金额,剩余金额,剩余比例";
            String fieldName = "teamName,yearBudText,monthBalaText,assessBalaText,deffBalaText,diffScaleText,sumOccText,leftBalaText,leftScaleText";
            PoiUtils.exportExcelOld(response, "分管部门汇总", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    public Object getDetail(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String mon1Qry = request.getParameter("mon1Qry");
        String mon2Qry = request.getParameter("mon2Qry");
        String month1 = yearQry + mon1Qry;
        String month2 = yearQry + mon2Qry;
        String teamNo = request.getParameter("teamNo");

        try{
            return fChargeFeeService.getDeptSumDetailList(yearQry, month1, month2, teamNo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
