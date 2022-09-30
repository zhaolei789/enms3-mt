package cn.ewsd.material.controller;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.*;
import cn.ewsd.material.service.*;
import cn.ewsd.system.service.ConfigService;
import cn.ewsd.system.service.DicItemService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/mPlan")
public class MPlanController extends MaterialBaseController {

    @Autowired
    private MItemService mItemService;
    @Autowired
    private MPrjService mPrjService;
    @Autowired
    private MPlanService mPlanService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private DicItemService dicItemService;
    @Autowired
    private MMaterialService mMaterialService;
    @Autowired
    private TCostCenterService tCostCenterService;
    @Autowired
    private MOfferService mOfferService;
    @Value("${my.upload-dir}")
    private String uploadDir;
    //工程索引页
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentMonth", XDate.getMonth());
        request.setAttribute("nextMonth", XDate.getNextMonth());
        request.setAttribute("nMon", Integer.parseInt(XDate.getNextMonth().substring(4, 6)));
        request.setAttribute("cMon", Integer.parseInt(XDate.getMon()));
        int addPlanBegin = Integer.parseInt(configService.getConfigByCode("CL_ADD_PLAN_BEGIN"));
        int nowDay = Integer.parseInt(XDate.getDate().substring(6,8));
        String planType = nowDay >= addPlanBegin ? "m.planType.2" : "m.planType.1";
        request.setAttribute("useDate", nowDay >= addPlanBegin ? XDate.getLastDayOfMonth(XDate.getNextMonth()+"01") : XDate.getLastDayOfMonth(XDate.getMonth()+"01"));
        request.setAttribute("nextMonLastDay", XDate.getLastDayOfMonth(XDate.getNextMonth()+"01"));
        request.setAttribute("planType", planType);
        request.setAttribute("planTypeName", dicItemService.getDicItemByValue(planType).getText());

        return "material/mPlan/index";
    }

    //工程数据
    @ResponseBody
    @RequestMapping(value = "/getPrjData", method = RequestMethod.POST)
    public Object getPrjData() {
        return mPrjService.getPrjByStatusAndTeamNoAndItemNo("m.prjStatus.0", LoginInfo.getOrgId(), "");
    }

    //工程科目
    @ResponseBody
    @RequestMapping(value = "/prjItem", method = RequestMethod.POST)
    public Object getPrjItem(@RequestParam String prjNo) {
        return mItemService.getItemSet(prjNo);
    }

    //计划列表
    @ResponseBody
    @RequestMapping("/planList")
    public Object planList(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String teamNo = LoginInfo.getOrgId();
        String itemNo = request.getParameter("itemNo");
        String planStepQry = request.getParameter("planStepQry");

        if(prjNo==null || "".equals(prjNo)){
            return null;
        }

        try{
            PageSet<MPlan> pageSet = mPlanService.getPageSet(pageParam, filterSort.replace("ORDER BY ", "ORDER BY a."), prjNo, planMonth, teamNo, itemNo, planStepQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //计划合计文本
    @ResponseBody
    @RequestMapping("/planSumText")
    public Object planSumText() {
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String teamNo = LoginInfo.getOrgId();
        String itemNo = request.getParameter("itemNo");
        String planStepQry = request.getParameter("planStepQry");

        MPrj mPrj = mPrjService.getPrjByPrjNo(prjNo);
        String prjType1 = mPrj.getPrjType1();

        double sumBala = mPlanService.getSumBala(prjNo, planMonth, teamNo, itemNo, planStepQry);

        String payTeam = mItemService.getPayTeam(prjNo, itemNo, teamNo);
        double budgetBala = mPlanService.getBudgetBala(prjNo, payTeam, planMonth, itemNo, prjType1);

        double planBala = mPlanService.getPlanBala(prjNo, payTeam, planMonth, itemNo, prjType1);
        double usableBala = budgetBala - planBala;

        MPlan mPlan = new MPlan();
        mPlan.setMatBala(new BigDecimal(sumBala));
        mPlan.setSumAmount(new BigDecimal(budgetBala));
        mPlan.setUsableAmount(new BigDecimal(usableBala));

        return mPlan;
    }

    //工厂列表
    @ResponseBody
    @RequestMapping("/getFactory")
    public Object getFactory(){
        return mPlanService.getFactory();
    }

    //材料列表
    @ResponseBody
    @RequestMapping("/matList")
    public Object getMatList(PageParam pageParam) {
        try{
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

            String teamNo = LoginInfo.getOrgId();
            String factoryNo = request.getParameter("factoryNo");
            String itemNo = request.getParameter("itemNo");
            String planType = request.getParameter("planType");
            String prjNo = request.getParameter("prjNo");
            String planMonth = request.getParameter("planMonth");
            String matCodeQry = request.getParameter("matCodeQry");
            String matTypeQry = request.getParameter("matTypeQry");
            String matNameQry = request.getParameter("matNameQry");
            String lastPlan = request.getParameter("lastPlan");
            String lastMonth = XDate.getLastMonth(planMonth);

            if(itemNo==null || "".equals(itemNo)){
                return null;
            }

            PageSet<MMaterial> pageSet = mMaterialService.getMatPageSet(pageParam, filterSort.replace("ORDER BY ", "ORDER BY m."), factoryNo, itemNo, planType, prjNo, planMonth, matCodeQry, matTypeQry, matNameQry, lastPlan, lastMonth);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //新增计划
    @ResponseBody
    @RequestMapping(value = "/addPlan")
    public Object addPlan() {
        String prjNo = request.getParameter("prjNo");
        String teamNo = LoginInfo.getOrgId();
        String itemNo = request.getParameter("itemNo");
        String planType = request.getParameter("planType");
        String planMonth = request.getParameter("planMonth");
        String factoryNo = request.getParameter("factoryNo");
        String costCenter = request.getParameter("costCenter");
        String wbsElement = request.getParameter("wbsElement");
        String clModel = configService.getConfigByCode("CL_MODEL");
        String matNosStr = request.getParameter("matNos");
        String planAmountsStr = request.getParameter("planAmounts");
        String matAddrsStr = request.getParameter("matAddrs");

        if("".equals(itemNo)){
            return failure("清先选择科目！");
        }
        if("".equals(costCenter)){
            return failure("没有成本中心不能做计划！");
        }
        if("".equals(matNosStr)){
            return failure("请至少选择一种材料！");
        }
        String[] matNos = matNosStr.split(",");
        String[] planAmounts = planAmountsStr.split(",");
        String[] matAddrs = matAddrsStr.split(",");

        String itemType = tCostCenterService.getMoveType(costCenter, wbsElement);
        if("".equals(itemType) || itemType==null){
            return failure("科目类型不正确！");
        }

        if(configService.getConfigByCode("CL_ITEM_AQFY").equals(itemNo)){
            itemType = "m.erpItem.3";
            if("".equals(wbsElement)){
                return failure("科目类型：资金类计划-设备/材料，这类计划必须输入WBS元素信息！");
            }
        }

        if(!"".equals(wbsElement)){
            itemType = "m.erpItem.3";
        }

        String needComp = configService.getConfigByCode("ETC_NEED_COMP");
        String needFactory = configService.getConfigByCode("ETC_NEED_FACTORY");
        String purOrg = configService.getConfigByCode("ETC_PURCHASE_ORG");
        String purGroup = configService.getConfigByCode("ETC_PURCHASE_GROUP");
        int period = Integer.parseInt(configService.getConfigByCode("CL_PLAN_END_PERIOD"));

        try{
            mPlanService.addPlan(prjNo, matNos, clModel, planType, planMonth, itemNo, teamNo, period, factoryNo, costCenter, wbsElement, itemType, needComp, needFactory, purOrg, purGroup, LoginInfo.getUuid(), LoginInfo.getUserName(), planAmounts, matAddrs);
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update() {
        String[] planNos = request.getParameterValues("planNo");

        if(planNos==null || planNos.length<1){
            return failure("请至少选择一条计划！");
        }

        ArrayList<MPlan> list = new ArrayList<>();
        for(int i=0; i<planNos.length; i++){
            String planNo = planNos[i];
            double matAmount = 0;
            try{
                matAmount = Double.parseDouble(request.getParameter("amount_"+planNo));
            }catch (Exception e){
                return failure("计划数必须是大于0的数字！");
            }
            if(matAmount<=0){
                return failure("计划数必须是大于0的数字！");
            }
            String useDate = request.getParameter("date_"+planNo);
            String remark = request.getParameter("remark_"+planNo);
            String matUse = request.getParameter("use_"+planNo);
            String offerNo = request.getParameter("offer_"+planNo);

            MPlan mPlan = new MPlan();
            mPlan.setPlanNo(planNo);
            mPlan.setMatAmount(new BigDecimal(matAmount));
            mPlan.setUseDate(useDate);
            mPlan.setRemark(remark);
            mPlan.setMatUse(matUse);
            mPlan.setOfferNo(offerNo);
            list.add(mPlan);
        }

        mPlanService.update(list);

        return success("保存成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public Object deleteBatch() {
        String[] planNos = request.getParameterValues("planNo");

        if(planNos==null || planNos.length<1){
            return failure("请至少选择一条计划！");
        }

        mPlanService.deleteBatch(planNos);

        return success("删除成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Object submit() {
        String[] planNos = request.getParameterValues("planNo");

        if(planNos==null || planNos.length<1){
            return failure("请至少选择一条计划！");
        }

        String drawApply = configService.getConfigByCode("ETC_DRAW_APPLY");
        String agreeEmp = configService.getConfigByCode("CL_AUTO_CLSP_EMP");
        String clModel = configService.getConfigByCode("CL_MODEL");
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("ifMatUse", configService.getConfigByCode("CL_IF_MAT_USE"));
        paramMap.put("ifCtlPrice", configService.getConfigByCode("CL_CONTROL_1_PRICE"));
        paramMap.put("clLbItem", configService.getConfigByCode("CL_LB_ITEM"));

        try{
            mPlanService.submitPlan(request, paramMap, planNos, "71050", LoginInfo.getUuid(), LoginInfo.getUserName());
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("未知错误！");
        }

        return success("提交成功！");
    }

    @RequestMapping("/importPage")
    public String importPage() {
        request.setAttribute("prjNo", request.getParameter("prjNo"));
        request.setAttribute("planMonth", request.getParameter("planMonth"));
        request.setAttribute("planType", request.getParameter("planType"));
        request.setAttribute("itemNo", request.getParameter("itemNo"));

        return "material/mPlan/import";
    }

    @ResponseBody
    @RequestMapping(value = "/import")
    public Object importData(String address) {
        //判断是否Excel文档
        String strsub =address.substring(address.length() -4);
        if (!".xls".equals(strsub) && !strsub.equals("xlsx")){
            return failure("请上传正确的Excel文档！");
        }

        String teamNo = LoginInfo.getOrgId();
        String prjNo = request.getParameter("prjNo");
        String planMonth = request.getParameter("planMonth");
        String planType = request.getParameter("planType");
        String itemNo = request.getParameter("itemNo");

        if("".equals(prjNo) || "".equals(planMonth) || "".equals(planType)){
            return failure("请选择要导入计划的工程！");
        }
        if("".equals(itemNo)){
            return failure("请选择要导入计划的科目！");
        }

        //时间处理
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取系统判断存储地址
//        Properties props=System.getProperties();
//        String osName = props.getProperty("os.name");
//        boolean status = osName.contains("Windows");
//        File pdfFile =null;
//        pdfFile = new File("D:/zysd/"+address);
//
        File pdfFile = new File(uploadDir+address);
        try {
            //解析Excel文件数据
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            //调ImportExeclUtil工具类
            List<Map<String, String>> varList = ImportExeclUtil.readExcel(multipartFile, 0, 0, 0);
            if (varList.size() <= 1) {
                return failure("Excel文档没有数据！");
            }

            //删除第一个下标（是字段无需添加）
            varList.remove(0);

            List<MOffer>  offerList = mOfferService.getOfferList(null);
            DataCantainer<MOffer> offerDc = new DataCantainer<>((ArrayList)offerList);
            List<TCostCenter> centerList = tCostCenterService.getCenterList();
            DataCantainer<TCostCenter> centerDc = new DataCantainer<>((ArrayList)centerList);

            String errorInfo = "";
            for (int i = 0; i < varList.size(); i++) {
                String costCenter = varList.get(i).get("成本中心编号");
                costCenter = costCenter==null ? "" : costCenter;
                String matCode = varList.get(i).get("物料编码(8位)");
                matCode = matCode==null ? "" : matCode;
                String mCode = (matCode.length() == 8) ? "X" + matCode : matCode;
                String matAmount = varList.get(i).get("计划数量");
                matAmount = matAmount==null ? "" : matAmount;
                String offer = varList.get(i).get("供应商号");
                offer = offer==null ? "" : offer;

                if ("".equals(costCenter) || centerDc.findDataCantainer("center_no", costCenter).getRowCount() < 1) {
                    errorInfo += "第" + (i + 2) + "行：成本中心为空或不存在！<br/>";
                }

                MMaterial mMaterial = mMaterialService.getMatByCode(mCode);
                if("".equals(matCode) || mMaterial==null){
                    errorInfo += "第" + (i + 2) + "行：物料代码为空或不存在！<br/>";
                }

                if(!"".equals(matAmount) && !Data.isDouble(matAmount)){
                    errorInfo += "第" + (i + 2) + "行：计划数量不是数字：\""+ matAmount +"\"！<br/>";
                }

                if(!"".equals(offer) && offerDc.findDataCantainer("offer_no", offer).getRowCount() < 1){
                    errorInfo += "第" + (i + 2) + "行：供应商号，不存在：\""+ offer +"\"！<br/>";
                }
            }
            if (!"".equals(errorInfo)) {
                return failure(errorInfo);
            }

            mPlanService.importPlan(varList, LoginInfo.get(), teamNo, itemNo, planMonth, planType, prjNo);
            return success("导入成功！");
        }catch (XException e){
            return failure(e.getInfo());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return failure("导入失败！");
        } catch (IOException e) {
            e.printStackTrace();
            return failure("导入失败！");
        } catch (Exception e){
            e.printStackTrace();
            return failure("导入失败！");
        }
    }
}
