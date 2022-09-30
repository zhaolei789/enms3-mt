package cn.ewsd.material.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.ConsumeStock;
import cn.ewsd.material.service.ClassConsumeService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 班组材料消耗
 */
@Controller
@RequestMapping("/material/classConsume")
public class ZClassConsumeController extends MaterialBaseController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ClassConsumeService classConsumeService;
    @Autowired
    private MStoreService mStoreService;
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("occDate1Qry", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("occDate2Qry", XDate.dateTo10(XDate.getDate()));

        String deptNoQry = "";
        List<Organization> classList = organizationService.getTeamDownClass(LoginInfo.getOrgId());
        if(classList.size() > 0){
            deptNoQry = classList.get(0).getId()+"";
        }
        request.setAttribute("deptNoQry", deptNoQry);

        String storeNoQry = "";
        List<MStore> storeList = mStoreService.getStoreList(null, null, LoginInfo.getOrgId(), null, null, null, null, "r.storeLevel.2");
        if(storeList.size() > 0){
            storeNoQry = storeList.get(0).getStoreNo();
        }
        request.setAttribute("storeNoQry", storeNoQry);

        return "material/classConsume/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSetBzxh(PageParam pageParam) {
        String occDate1Qry = XDate.dateTo8(request.getParameter("occDate1Qry"));
        String occDate2Qry = XDate.dateTo8(request.getParameter("occDate2Qry"));
        String deptNoQry = request.getParameter("deptNoQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<ConsumeStock> pageSet = classConsumeService.getPageSet(pageParam, filterSort, LoginInfo.getOrgId(), occDate1Qry, occDate2Qry, deptNoQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSetXzcl", method = RequestMethod.POST)
    public Object getPageSetXzcl(PageParam pageParam) {
        String teamNo = LoginInfo.getOrgId();
        String storeNoQry = request.getParameter("storeNoQry");
        String statusQry = request.getParameter("statusQry");
        String deptNoQry = request.getParameter("deptNoQry");
        String occDate2Qry = XDate.dateTo8(request.getParameter("occDate2Qry"));
        String prjNoQry = request.getParameter("prjNoQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<ConsumeStock> pageSet = classConsumeService.getPageSetXzcl(pageParam, filterSort, teamNo, storeNoQry, statusQry, deptNoQry, occDate2Qry, prjNoQry, matCodeQry, matNameQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/saveMat")
    public Object submitCheck() {
        try {
            classConsumeService.saveMat(request, LoginInfo.get());
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete() {
        try {
            classConsumeService.delete(request, LoginInfo.get());
            return success("删除成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getItem")
    public Object getItem() {
        String occMonth = XDate.dateTo8(request.getParameter("occDate")).substring(0, 6);
        System.out.println(occMonth);
        return classConsumeService.getItem(LoginInfo.getOrgId(), occMonth);
    }
}
