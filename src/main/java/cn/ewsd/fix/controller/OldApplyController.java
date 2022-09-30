package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.logistics.util.LLDReportTest;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.mdata.service.OrganizationService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.TCheck;
import cn.ewsd.system.service.SysUserQryOrgService;
import cn.ewsd.system.service.TCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/fix/oldApply")
public class OldApplyController extends FixBaseController {

    @Autowired
    private MStockService mstockService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MOutService mOutService;
    @Autowired
    private TCheckService tCheckService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;

    @Value("${my.upload-dir}")
    private String uploadDirPath;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        List<Organization> list = organizationService.getUserDeptSet(sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), "C0ZZ", "3");
        String teamNo = "";
        if(list.size()>0){
            teamNo = list.get(0).getId()+"";
        }
        request.setAttribute("teamNo", teamNo);
        request.setAttribute("occDate", XDate.dateTo10(XDate.getDate()));
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));

        return "fix/oldApply/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String storeQry = request.getParameter("storeQry");
        String matQry = request.getParameter("matQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MStock> pageSet = mstockService.getOldApplyList(pageParam, filterSort, storeQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitOldApply() {
        try {
            mOutService.submitOldApply(request, LoginInfo.get());
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailPageSet", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String matQry = request.getParameter("matQry1");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MOut> pageSet = mOutService.getOldApplyDetail(pageParam, filterSort, date1Qry, date2Qry, LoginInfo.getOrgId(), matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/print")
    public void print(HttpServletRequest request, HttpServletResponse response) {
        String drawNos = request.getParameter("drawNos");

        List<MOut> outList = mOutService.getDrawApplyPrint(drawNos);
        String checkNos = "";
        for(int i=0; i<outList.size(); i++){
            checkNos += (i==0 ? "" : ",") + outList.get(i).getCheckNo();
        }
        List<TCheck> tCheckList = tCheckService.getTCheckList(checkNos);
        DataCantainer<TCheck> checkDc = new DataCantainer<>((ArrayList)tCheckList);

        String uploadDir = uploadDirPath + "/uploads/pdf";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
        Date date = new Date();
        String ymd = sdf.format(date);
        uploadDir += File.separator + ymd + File.separator;

        File fileZ = new File(uploadDir);
        if (!fileZ.exists()) {
            fileZ.mkdirs();
        }
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + ".pdf";
        String filePath = uploadDir + "/" + newFileName;

        try {
            LLDReportTest.reportCreateB(filePath, outList, checkDc);

            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }

        //返回PDF文件
        File file = new File(filePath);
        byte[] data = null;
        try {
            // 解决请求头跨域问题（IE兼容性  也可使用该方法）
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/pdf");
            FileInputStream input = new FileInputStream(file);
            data = new byte[input.available()];
            input.read(data);
            response.getOutputStream().write(data);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
