package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.MTeamStock;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.material.service.MTeamStockService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.system.model.SysUserQryOrg;
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
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/teamStockQry")
public class TeamStockQryController extends MaterialBaseController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MTeamStockService mTeamStockService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        List<Organization> list = organizationService.getOrgByLevel(3);
        String teamNoQry = "";
        if(list.size() > 0){
            teamNoQry = list.get(0).getId()+"";
        }
        request.setAttribute("teamNoQry", teamNoQry);
        request.setAttribute("bDateQry", XDate.dateTo10(XDate.getYear()+"0101"));
        request.setAttribute("eDateQry", XDate.dateTo10(XDate.getDate()));

        return "material/teamStockQry/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String teamNoQry = request.getParameter("teamNoQry");
        String storeNoQry = request.getParameter("storeNoQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        try{
            PageSet<MTeamStock> pageSet = mTeamStockService.getTeamStockQryPageSet(pageParam, filterSort, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamNoQry, storeNoQry, matCodeQry, matNameQry);
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
            String teamNoQry = request.getParameter("teamNoQry");
            String storeNoQry = request.getParameter("storeNoQry");

            List<MTeamStock> list = mTeamStockService.getTeamStockQryList(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamNoQry, storeNoQry, matCodeQry, matNameQry);
            for(int i=0; i<list.size(); i++){
                MTeamStock mTeamStock = list.get(i);
                String matCode = mTeamStock.getMatCode();
                if(matCode!=null && !"".equals(matCode)){
                    mTeamStock.setMatCode("X".equals(matCode.substring(0, 1)) ? matCode.substring(0, 1) : matCode);
                }
            }

            String textName = "部门,仓库,物料状态,物料分类,分类描述,物料编码,物料描述,计量单位,库存数量,价格,金额,四号定位";
            String fieldName = "teamName,storeName,matStatus,erpType,typeName,matCode,matName,matUnit,matAmount,matPrice,matBala,siteCode";
            PoiUtils.exportExcelOld(response, "队库实时库存", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object getDetail() {
        String matNo = request.getParameter("matNo");
        String teamNoQry = request.getParameter("teamNoQry");
        String bDateQry = XDate.dateTo8(request.getParameter("bDateQry"));
        String eDateQry = XDate.dateTo8(request.getParameter("eDateQry"));
        String storeNoQry = request.getParameter("storeNoQry");

        try{
            return mTeamStockService.getTeamStockQryDetail(matNo, storeNoQry, bDateQry, eDateQry, teamNoQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/mat", method = RequestMethod.POST)
    public Object getMat() {
        String matNo = request.getParameter("matNo");
        String teamNo = request.getParameter("teamNo");
        String storeNo = request.getParameter("storeNo");

        try{
            return mTeamStockService.getTeamStockQryMatList(matNo, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), storeNo, teamNo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
