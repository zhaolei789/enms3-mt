package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.logistics.util.LLDReportTest;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.service.MPlanService;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.model.TCheck;
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
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/drawApply")
public class drawApplyController extends RepositoryBaseController {
    @Autowired
    private MPlanService mPlanService;
    @Autowired
    private MOutService mOutService;
    @Autowired
    private TCheckService tCheckService;


    @Value("${my.upload-dir}")
    private String uploadDirPath;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        return "repository/drawApply/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String storeNo = request.getParameter("storeNo");
        String applyType = "r.outStockType.1";
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String teamNo = LoginInfo.getOrgId();

        try{
            PageSet<MPlan> pageSet = mPlanService.getDrawApply(pageParam, filterSort, teamNo, applyType, XDate.addDate(XDate.getDate(), -90), storeNo, matCodeQry, matNameQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submit() {

        String prjNo = request.getParameter("prjNo");
        //String item = request.getParameter("itemNo");
        String deptNo = request.getParameter("deptNo");
        //String ifSend = request.getParameter("ifSend");
        //String sendAddr = request.getParameter("sendAddr");
        //String sendTime = request.getParameter("sendTime");
        //String getEmp = request.getParameter("getEmp");

//        if("".equals(prjNo)){
//            return failure("物料领用时，必须选择具体的工程项目！");
//        }
//        if("1".equals(ifSend) && ("".equals(sendAddr) || "".equals(sendTime) || "".equals(getEmp))){
//            return failure("如需配送，必须指定配送地点、配送时间及接货人！");
//        }

        try {
            mOutService.insertDrawApply(prjNo, "", deptNo, "0", "", "", "", LoginInfo.get(), request);
            return success("提交成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailPageSet", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String storeNoQry = request.getParameter("storeNoQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String addrQry = request.getParameter("addrQry");
        String appTypeQry = request.getParameter("appTypeQry");
        String planStepQry = request.getParameter("planStepQry");

        try{
            PageSet<MOut> pageSet = mOutService.getOutApplyPageSet(pageParam, filterSort, date1Qry, date2Qry, LoginInfo.getOrgId(), storeNoQry, addrQry, appTypeQry, matCodeQry, matNameQry, planStepQry);
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

        double matPrice = 0;
        try{
            matPrice = Double.parseDouble(configService.getConfigByCode("CL_JYKZ_MAT_PRICE"));
        }catch (Exception e){

        }

        try {
            LLDReportTest.reportCreateA(filePath, outList, checkDc, matPrice);

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
