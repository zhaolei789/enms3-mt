package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.cost.service.MOutAssessService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import cn.ewsd.common.utils.easyui.PageSet;

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
@RequestMapping("/cost/detailUpData")
public class DetailUpDataController extends CostBaseController {

    @Autowired
    private MOutAssessService mOutAssessService;
    @Autowired
    private ConfigService configService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "cost/detailUpData/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String teamQry = request.getParameter("teamQry");
        String storeQry = request.getParameter("storeQry");
        String reserveNoQry = request.getParameter("reserveNoQry");
        String matQry = request.getParameter("matQry");
        String addrQry = request.getParameter("addrQry");
        String drawSrcQry = request.getParameter("drawSrcQry");
        String itemNameQry = request.getParameter("itemNameQry");
        String monthQry = yearQry + monQry;
        String purchaseQry = request.getParameter("purchaseQry");

        String userTeam = LoginInfo.getOrgId();
        String matMngTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");
        boolean ifMngDept= matMngTeam.equals(userTeam)?true:false;

        try{
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MOutAssess> pageSet = mOutAssessService.getDetailUpDataPageSet(pageParam, filterSort, monthQry, teamQry, storeQry, addrQry, purchaseQry, reserveNoQry, drawSrcQry, itemNameQry, matQry, ifMngDept, matMngTeam);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getItem")
    public Object getItem() {
        String monthQry = request.getParameter("monthQry");
        return mOutAssessService.getItemList(monthQry);
    }

    @ResponseBody
    @RequestMapping(value = "/getItemForSelect")
    public Object getItemForSelect() {
        String teamNo = request.getParameter("teamNo");
        String matMngTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");
        boolean ifMngDept= matMngTeam.equals(teamNo)?true:false;

        return mOutAssessService.getItemForSelect(teamNo, ifMngDept);
    }

    @ResponseBody
    @RequestMapping(value = "/getPrjForSelect")
    public Object getPrjForSelect() {
        String teamNo = request.getParameter("teamNo");

        return mOutAssessService.getPrjForSelect(teamNo);
    }

    @RequestMapping(value = "/download")
        public void export(HttpServletResponse response) {
            try{
                String yearQry = request.getParameter("yearQry");
                String monQry = request.getParameter("monQry");
                String teamQry = request.getParameter("teamQry");
                String storeQry = request.getParameter("storeQry");
                String reserveNoQry = request.getParameter("reserveNoQry");
                String matQry = request.getParameter("matQry");
                String addrQry = request.getParameter("addrQry");
                String drawSrcQry = request.getParameter("drawSrcQry");
                String itemNameQry = request.getParameter("itemNameQry");
                String monthQry = yearQry + monQry;
                String purchaseQry = request.getParameter("purchaseQry");

                String userTeam = LoginInfo.getOrgId();
                String matMngTeam = configService.getConfigByCode("XT_TEAM_MATERIAL");
                boolean ifMngDept= matMngTeam.equals(userTeam)?true:false;

                List<MOutAssess> list = mOutAssessService.getDetailUpDataList(monthQry, teamQry, storeQry, addrQry, purchaseQry, reserveNoQry, drawSrcQry, itemNameQry, matQry, ifMngDept, matMngTeam);

                String textName = "出账单位,物料分类,分类名称,物料编码,物料描述,价格,单位,数量,金额,关联费用,工程项目,领料仓库,发生日期,考核日期,出账编号,拆分号,计划预留,出账来源,WBS";
                String fieldName = "teamName,erpType,typeName,matCode,matName,matPrice,matUnit,matAmount,matBala,itemName,prjName,storeName,occDate,assDate,drawNo,splitNo,reserveNo,drawSrcName,wbs";
                for(int i=0; i<list.size();i++){
                    MOutAssess mOutAssess = list.get(i);
                    String matCode = mOutAssess.getMatCode();
                    if(matCode!=null && !"".equals(matCode)){
                        mOutAssess.setMatCode("X".equals(matCode.substring(0, 1)) ? matCode.substring(1) : matCode);
                    }
                    mOutAssess.setOccDate(XDate.dateTo10(mOutAssess.getOccDate()));
                    mOutAssess.setAssDate(XDate.dateTo10(mOutAssess.getAssDate()));
                }
                PoiUtils.exportExcelOld(response, "考核信息", textName, fieldName, list);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    @RequestMapping("/add")
    public String edit() {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        request.setAttribute("occDate", XDate.dateTo10(year+month+"25"));
        request.setAttribute("yearQry", year);
        request.setAttribute("monQry", month);
        return "cost/detailUpData/add";
    }

    @ResponseBody
    @RequestMapping(value = "/insert")
    public Object insert() {
        try {
            mOutAssessService.insertDetailUpData(request, LoginInfo.get());
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/insData")
    public Object insData() {
        try {
            mOutAssessService.insDetailUpData(request, LoginInfo.get());
            return success("提取成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提取失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try {
            mOutAssessService.deleteDetailUpData(request, LoginInfo.get());
            return success("删除成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try {
            mOutAssessService.saveDetailUpData(request, LoginInfo.get());
            return success("保存成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败");
        }
    }
}
