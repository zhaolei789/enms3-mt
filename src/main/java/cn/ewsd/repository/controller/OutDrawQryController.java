package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
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
@RequestMapping("/repository/outDrawQry")
public class OutDrawQryController extends RepositoryBaseController {
    @Autowired
    private MOutService mOutService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UtilService utilService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        String teamNoQry = "";
        List<Organization> orgList = organizationService.getUserDeptSet(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), "C0ZZ", "3");
        if(orgList.size() > 0){
            teamNoQry = orgList.get(0).getId()+"";
        }
        request.setAttribute("teamNoQry", teamNoQry);

        boolean cancelRight = utilService.checkRight(LoginInfo.getRoleId(), 2597);
        request.setAttribute("cancelRight", cancelRight);

        return "repository/outDrawQry/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String teamNoQry = request.getParameter("teamNoQry");
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String drawNoQry = request.getParameter("drawNoQry");
        String storeNoQry = request.getParameter("storeNoQry");
        String erpTypeQry = request.getParameter("erpTypeQry");
        String itemQry = request.getParameter("itemQry");
        String stepQry = request.getParameter("stepQry");
        String urgentQry = request.getParameter("urgentQry");
        String newOldQry = request.getParameter("newOldQry");
        if("m.dSrc.x".equals(newOldQry)){
            newOldQry = "m.dataSrc.3";
        }
        if("m.dSrc.j".equals(newOldQry)){
            newOldQry = "m.dataSrc.P";
        }

        try{
            PageSet<MOut> pageSet = mOutService.getOutDrawPageSet(pageParam, filterSort, date1Qry, date2Qry, teamNoQry, stepQry, itemQry, urgentQry, drawNoQry, storeNoQry, erpTypeQry, matCodeQry, matNameQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), LoginInfo.getUuid(), newOldQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
        try{

            String teamNoQry = request.getParameter("teamNoQry");
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String drawNoQry = request.getParameter("drawNoQry");
            String storeNoQry = request.getParameter("storeNoQry");
            String erpTypeQry = request.getParameter("erpTypeQry");
            String itemQry = request.getParameter("itemQry");
            String stepQry = request.getParameter("stepQry");
            String urgentQry = request.getParameter("urgentQry");
            String newOldQry = request.getParameter("newOldQry");
            if("m.dSrc.x".equals(newOldQry)){
                newOldQry = "m.dataSrc.3";
            }
            if("m.dSrc.j".equals(newOldQry)){
                newOldQry = "m.dataSrc.P";
            }
            List<MOut> list = mOutService.getOutDrawList(date1Qry, date2Qry, teamNoQry, stepQry, itemQry, urgentQry, drawNoQry, storeNoQry, erpTypeQry, matCodeQry, matNameQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), LoginInfo.getUuid(), newOldQry);

            String textName = "单据编号,状态,申请单位,领用单位,领用日期,物料分类,分类描述,物料编码,物料描述,单位,领用数量,价格,金额,领料班组,费用来源,工程项目,出库仓库,WBS元素,备注信息,审批人员,审批时间,提报单号";
            String fieldName = "reserve2,drawStepName,planTeamName,teamName,outDate,erpType,typeName,matCode,matName,matUnit,outAmount,matPrice,bala,useAddr,itemName,prjName,storeName,wbsElement,applyInfo,empName,agreeDate,reserveNo";
            for(int i=0; i<list.size();i++){
                MOut mOut = list.get(i);
                mOut.setOutDate(XDate.dateTo10(mOut.getOutDate()));
                mOut.setAgreeDate(XDate.dateTo10(mOut.getAgreeDate())+" "+XDate.timeTo8(mOut.getAgreeTime()));
            }
            PoiUtils.exportExcelOld(response, "领料明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Object doCancel() {
        String drawNo = request.getParameter("drawNo");

        try {
            mOutService.doCancel(drawNo, LoginInfo.get());
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("冲单失败！");
        }
        return success("冲单成功！");
    }
}
