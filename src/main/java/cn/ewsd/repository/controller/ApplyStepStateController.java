package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.model.SysAuditProcess;
import cn.ewsd.system.service.SysAuditProcessService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/applyStepState")
public class ApplyStepStateController extends RepositoryBaseController {
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private MOutService mOutService;
    @Autowired
    private SysAuditProcessService sysAuditProcessService;
    @Autowired
    private UtilService utilService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        List<SysAuditProcess> stepList = sysAuditProcessService.queryListByFuuid("D0180DF7E4324DD091306C26F2207145");
        String stepStr = "";
        for(int i=0; i<stepList.size(); i++){
            SysAuditProcess sysAuditProcess = stepList.get(i);
            stepStr += i==0 ? sysAuditProcess.getProcessNo()+"|"+sysAuditProcess.getProcessName() : ","+sysAuditProcess.getProcessNo()+"|"+sysAuditProcess.getProcessName();
        }
        request.setAttribute("stepStr", stepStr);

        return "repository/applyStepState/index";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getList() {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String deptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

        try{
            List<HashMap<String, String>> list = mOutService.getApplyStepList(date1Qry, date2Qry, deptIds);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailPageSet")
    public Object getDetailPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String drawStep = request.getParameter("drawStep");
        String teamNo = request.getParameter("teamNo");
        String matQry =request.getParameter("matQry");
        boolean ifRole = utilService.checkRight(LoginInfo.getRoleId(), 2588);

        try{
            PageSet<MOut> pageSet = mOutService.getApplyStepDetail(pageParam, filterSort, teamNo, date1Qry, date2Qry, drawStep, "", "", matQry, LoginInfo.getOrgId(), ifRole);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
