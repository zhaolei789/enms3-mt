package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.repository.model.MTeamBill;
import cn.ewsd.repository.service.MTeamBillService;
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
@RequestMapping("/repository/classMatQry")
public class classMatQryController extends RepositoryBaseController {

    @Autowired
    private MTeamBillService mTeamBillService;
    @Autowired
    private OrganizationService organizationService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getLastDayOfMonth(XDate.getDate())));

        List<Organization> list = organizationService.getOrgByLevel(3);
        String teamNoQry = "";
        if(list.size() > 0){
            teamNoQry = list.get(0).getId()+"";
        }
        request.setAttribute("teamNoQry", teamNoQry);

        return "repository/classMatQry/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String teamNoQry = request.getParameter("teamNoQry");
        String deptNoQry = request.getParameter("deptNoQry");
        String typeQry = request.getParameter("typeQry");
        String prjNameQry = request.getParameter("prjNameQry");
        String matNameQry = request.getParameter("matNameQry");

        try{
            PageSet<MTeamBill> pageSet = mTeamBillService.getClassMatQryPageSet(pageParam, filterSort, date1Qry, date2Qry, teamNoQry, deptNoQry, typeQry, prjNameQry, matNameQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletResponse response) {
        try{

            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String teamNoQry = request.getParameter("teamNoQry");
            String deptNoQry = request.getParameter("deptNoQry");
            String typeQry = request.getParameter("typeQry");
            String prjNameQry = request.getParameter("prjNameQry");
            String matNameQry = request.getParameter("matNameQry");
            List<MTeamBill> list = mTeamBillService.getClassMatQryList(date1Qry, date2Qry, teamNoQry, deptNoQry, typeQry, prjNameQry, matNameQry);

            String textName = "发生日期,区队名称,班组名称,领料仓库,核算类型,物料分类,分类描述,物料编码,物料描述,单位,单价,消耗数量,金额,用料工程,使用地点";
            String fieldName = "occDate,teamName,deptName,storeName,accountTypeName,erpType,typeName,matCode,matName,matUnit,matPrice,occAmount,bala,prjName,useAddr";
            for(int i=0; i<list.size();i++){
                MTeamBill mTeamBill = list.get(i);
                mTeamBill.setOccDate(XDate.dateTo10(mTeamBill.getOccDate()));
                String matCode = mTeamBill.getMatCode();
                if(matCode!=null && !"".equals(matCode)){
                    mTeamBill.setMatCode("X".equals(matCode.substring(0, 1)) ? matCode.substring(1) : matCode);
                }
            }
            PoiUtils.exportExcelOld(response, "队库操作明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
