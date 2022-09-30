package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.model.SendMatPlan;
import cn.ewsd.material.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
@Controller
@RequestMapping("/material/sendMatPlan")
public class SendMatPlanController extends MaterialBaseController {
    @Autowired
    SendMatPlanService sendMatPlanService;
    @Autowired
    MPlanService mPlanService;
    @Autowired
    TCostCenterService tCostCenterService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "material/sendMatPlan/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String teamNoQry = request.getParameter("teamNoQry");
        String centerNoQry = request.getParameter("centerNoQry");
        String monthQry = request.getParameter("monthQry");
        String planTypeQry = request.getParameter("planTypeQry");
        String prjNoQry = request.getParameter("prjNoQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String itemNoQry = request.getParameter("itemNoQry");

        PageSet<SendMatPlan> pageSet = sendMatPlanService.getPageSet(pageParam, filterSort, monthQry, planTypeQry, prjNoQry, matCodeQry, matNameQry, teamNoQry, itemNoQry, centerNoQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getPlanMonth")
    public Object getPlanMonth() {
        return sendMatPlanService.getPlanMonth();
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        try{
            String planNo = request.getParameter("planNo");
            String moveType = request.getParameter("moveType");
            String factoryNo = request.getParameter("factoryNo");
            String matAddr = request.getParameter("matAddr");
            double planAmount = 0;
            try{
                planAmount = Double.parseDouble(request.getParameter("planAmount"));
            }catch (Exception e){
                return failure("计划数量必须是数字");
            }

            MPlan mPlan = mPlanService.getPlanByPlanNo(planNo);
            double oldAmount = mPlan.getMatAmount().doubleValue();
            String costCenter = mPlan.getCostCenter();

            if(planAmount>oldAmount && !"C102011018".equals(costCenter)){
                return failure("数量只能改小");
            }

            MPlan mPlan1 = new MPlan();
            mPlan1.setPlanNo(planNo);
            mPlan1.setMoveType(moveType);
            mPlan1.setItemType(moveType);
            mPlan1.setMatAmount(new BigDecimal(planAmount));
            mPlan1.setMatAddr(matAddr);
            mPlan1.setFactoryNo(factoryNo);
            mPlanService.updatePlan(mPlan1);
        }catch (Exception e){
            e.printStackTrace();
        }

        return success("保存成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object back() {
        String[] planNos = request.getParameterValues("planNo");

        String ret = sendMatPlanService.back(planNos, LoginInfo.get());
        if(!"".equals(ret)){
            return failure(ret);
        }

        return success("保存成功！");
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submit() {
        String[] planNos = request.getParameterValues("planNo");

        try {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("ifMatUse", configService.getConfigByCode("CL_IF_MAT_USE"));
            paramMap.put("ifCtlPrice", configService.getConfigByCode("CL_CONTROL_1_PRICE"));
            paramMap.put("clLbItem", configService.getConfigByCode("CL_LB_ITEM"));

            int result = sendMatPlanService.submit(planNos, LoginInfo.get());
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("更新失败！");
        }
        return success("提交成功！");
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
        try{
            String mineFlag = configService.getConfigByCode("MINE_FLAG");

            String textName = "计划类型,需求工厂编码,需求仓库编码,供应工厂编码,供应仓库编码,供应商编码,工作面编码,成本中心编码,WBS编码,生产/PM订单号,物料编码,需求日期,需求数量,工程名称,科目类型,采购组,备注,编制时间";
            String fieldName = "planType,needFactory,abcType,factoryNo,matAddr,offerNo,purchaseNo,costCenter,wbsElement,reserveNo,matCode,useDate,matAmount,prjNo,itemName,traxNo,remark,planDate";
            String teamNoQry = request.getParameter("teamNoQry");
            String centerNoQry = request.getParameter("centerNoQry");
            String monthQry = request.getParameter("monthQry");
            String planTypeQry = request.getParameter("planTypeQry");
            String prjNoQry = request.getParameter("prjNoQry");
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String itemNoQry = request.getParameter("itemNoQry");
            List<SendMatPlan> list = sendMatPlanService.getList(monthQry, planTypeQry, prjNoQry, matCodeQry, matNameQry, teamNoQry, itemNoQry, centerNoQry);
            for(int i=0; i<list.size();i++){
                SendMatPlan sendMatPlan = list.get(i);
                String useDate = sendMatPlan.getUseDate();
                String planMonth = sendMatPlan.getPlanMonth();
                String planDate = sendMatPlan.getPlanDate();
                String planTime = sendMatPlan.getPlanTime();
                sendMatPlan.setPlanType("用料计划");
                sendMatPlan.setAbcType("");
                sendMatPlan.setPurchaseNo("");
                String matCode = sendMatPlan.getMatCode();
                if(!"".equals(matCode) && matCode!=null){
                    matCode = "X".equals(matCode.substring(0,1)) ? matCode.substring(1) : matCode;
                }
                sendMatPlan.setMatCode(matCode);
                String factoryNo = sendMatPlan.getFactoryNo();
                if(!"".equals(factoryNo) || factoryNo!=null){
                    factoryNo = factoryNo.substring(factoryNo.lastIndexOf(".")+1);
                }
                sendMatPlan.setFactoryNo(factoryNo);
                String matAddr = sendMatPlan.getMatAddr();
                if(!"".equals(matAddr) || matAddr!=null){
                    matAddr = matAddr.substring(matAddr.lastIndexOf(".")+1);
                }
                sendMatPlan.setMatAddr(matAddr);
                sendMatPlan.setReserveNo("");
                sendMatPlan.setUseDate(XDate.dateTo10("".equals(useDate)?XDate.getLastDayOfMonth(planMonth+"01"):useDate)+" 00:00:00");
                sendMatPlan.setPrjNo("");
                sendMatPlan.setTraxNo("");
                sendMatPlan.setRemark("["+ mineFlag + ":" + sendMatPlan.getPlanNo() +"]" + sendMatPlan.getRemark() + sendMatPlan.getMatUse());
                sendMatPlan.setPlanDate(XDate.dateTo10(planDate) + XDate.timeTo8(planTime));
            }
            PoiUtils.exportExcelOld(response, "商城计划", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
