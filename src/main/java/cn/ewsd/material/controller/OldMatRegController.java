package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MHandIn;
import cn.ewsd.material.service.MHandInService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MStoreService;
import cn.ewsd.system.service.SysUserQryOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/oldMatReg")
public class OldMatRegController extends MaterialBaseController {

    @Autowired
    private MHandInService mHandInService;
    @Autowired
    private MStoreService mStoreService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("endDate", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("startDate", XDate.dateTo10(XDate.getMonth()+"01"));

        List<MStore> storeList = mStoreService.getStoreList(null, null, LoginInfo.getOrgId()+"", null, null, null, "r.storeType2.61", "r.storeLevel.1");
        String storeNoQry = "";
        if(storeList.size() > 0){
            storeNoQry = storeList.get(0).getStoreNo();
        }
        request.setAttribute("storeNoQry", storeNoQry);

        List<Organization> orgList = organizationService.getOrgByLevel(3);
        String teamNo = "";
        if(orgList.size() > 0){
            teamNo = orgList.get(0).getId()+"";
        }
        request.setAttribute("teamNo", teamNo);

        boolean delRight = utilService.checkRight(LoginInfo.getRoleId(), 2);
        request.setAttribute("delRight", delRight);

        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "material/oldMatReg/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String teamNoQry = request.getParameter("teamNoQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");

        System.out.println(date1Qry+"---"+date2Qry+"---"+teamNoQry+"---"+matCodeQry+"---"+matNameQry);

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MHandIn> pageSet = mHandInService.getPageSet(pageParam, filterSort, date1Qry, date2Qry, teamNoQry, matCodeQry, matNameQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getTeamSet")
    public Object getTeamSet(String itemNo) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String teamNoQry = request.getParameter("teamNoQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");

        try{
            return mHandInService.getTeamSet(date1Qry, date2Qry, teamNoQry, matCodeQry, matNameQry);
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
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");

            List<MHandIn> list = mHandInService.getList(date1Qry, date2Qry, teamNoQry, matCodeQry, matNameQry);

            String textName = "交旧单位,材料编码,材料名称,计量单位,单价,领用日期,应交数量，已交数量";
            String fieldName = "teamName,matCode,matName,matUnit,matPrice,occDate,matAmount,backAmount";
            PoiUtils.exportExcelOld(response, "待交旧明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMatList")
    public Object getMatListForSel() {
        String q = request.getParameter("q");
        return mHandInService.getMatList(q, LoginInfo.getOrgId());
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try {
            mHandInService.saveOldMatReg(request, LoginInfo.get());
            return success("添加交旧成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("添加交旧失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Object del() {
        try {
            mHandInService.delOldMatReg(request, LoginInfo.get());
            return success("删除交旧成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除交旧失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object update() {
        try {
            mHandInService.updateOldMatReg(request, LoginInfo.get());
            return success("确认交旧成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("确认交旧失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailPageSet", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry + monQry;
        String teamNoQry = request.getParameter("teamNoQry1");
        String matCodeQry = request.getParameter("matCodeQry1");
        String matNameQry = request.getParameter("matNameQry1");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MHandIn> pageSet = mHandInService.getOldMatRegDetailPageSet(pageParam, filterSort, monthQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), teamNoQry, matCodeQry, matNameQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object back() {
        try {
            mHandInService.delOldMatReg(request, LoginInfo.get());
            return success("退回交旧成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回交旧失败！");
        }
    }
}
