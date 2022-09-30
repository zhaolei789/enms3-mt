package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.repository.service.MInService;
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
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/bcDrawQry")
public class BcDrawQryController extends RepositoryBaseController {
    @Autowired
    private UtilService utilService;
    @Autowired
    private MInService mInService;
    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String occDate = XDate.getDate();
        int day = Integer.parseInt(occDate.substring(6));
        String beginDate = day > 25 ? occDate.substring(0, 6)+"26" : XDate.addMonth(occDate.substring(0, 6), -1)+"26";
        String endDate = occDate;

        String d1Qry = request.getParameter("d1Qry");
        String d2Qry = request.getParameter("d2Qry");
        String periodQry = request.getParameter("periodQry");
        request.setAttribute("date1Qry", d1Qry == null ? XDate.dateTo10(beginDate) : d1Qry);
        request.setAttribute("date2Qry", d2Qry == null ? XDate.dateTo10(endDate) : d2Qry);
        request.setAttribute("periodQry", periodQry==null ? "" : "30");

        return "repository/bcDrawQry/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        try{
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String matQry = request.getParameter("matQry");
            String inTypeQry = request.getParameter("inTypeQry");
            String inStepQry = request.getParameter("inStepQry");
            String storeNoQry = request.getParameter("storeNoQry");
            String accountQry = request.getParameter("accountQry");
            String centerQry = request.getParameter("centerQry");
            String wbsQry = request.getParameter("wbsQry");
            String periodQry = request.getParameter("periodQry");
            if("".equals(periodQry)){
                periodQry = "0";
            }
            boolean ifAllQry = utilService.checkRight(LoginInfo.getRoleId(), 2565);
            if (!Data.isInteger(periodQry)) {
                return failure("周期，只能输入正整数！");
            }
            int period = Integer.parseInt(periodQry);
            String periodDay = XDate.addDate(XDate.getDate(), period);
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MIn> pageSet = mInService.getBcDrawQryPageSet(pageParam, filterSort, date1Qry, date2Qry, LoginInfo.getUuid(), ifAllQry, matQry, storeNoQry, inTypeQry, wbsQry, inStepQry, accountQry, centerQry, periodQry, periodDay, userDeptIds);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //退货
    @ResponseBody
    @RequestMapping(value = "/back", method = RequestMethod.POST)
    public Object back() {
        String billNo = request.getParameter("billNo");

        try {
            mInService.backDrawIn(billNo, LoginInfo.get());
            return success("退货成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退货失败！");
        }
    }

    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) {
        try{
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String matQry = request.getParameter("matQry");
            String inTypeQry = request.getParameter("inTypeQry");
            String inStepQry = request.getParameter("inStepQry");
            String storeNoQry = request.getParameter("storeNoQry");
            String accountQry = request.getParameter("accountQry");
            String centerQry = request.getParameter("centerQry");
            String wbsQry = request.getParameter("wbsQry");
            String periodQry = request.getParameter("periodQry");
            if("".equals(periodQry)){
                periodQry = "0";
            }
            boolean ifAllQry = utilService.checkRight(LoginInfo.getRoleId(), 2565);
            //这里需要处理报错信息
//            if (!Data.isInteger(periodQry)) {
//                return failure("周期，只能输入正整数！");
//            }
            int period = Integer.parseInt(periodQry);
            String periodDay = XDate.addDate(XDate.getDate(), period);
            String userDeptIds = sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid());

            List<MIn> list = mInService.getBcDrawQryList(date1Qry, date2Qry, LoginInfo.getUuid(), ifAllQry, matQry, storeNoQry, inTypeQry, wbsQry, inStepQry, accountQry, centerQry, periodQry, periodDay, userDeptIds);

            String textName = "计划单位,入库步骤,入库类型,入库仓库,材料编码,材料描述,单位,价格,申请日期,申请人员,申请数量,物入日期,复核人员,账入数量,账入金额,供应商,WBS元素,计划号,计划月份,物料组描述,备注";
            String fieldName = "centerName,inStepName,inTypeName,storeName,matCode,matName,matUnit,setPrice,applyDate,applyEmpName,applyAmount,billDate,checkEname,billAmount,bala,supplier,wbs,planNo,planMonth,erpType,remark";
            for(int i=0; i<list.size();i++){
                MIn mIn = list.get(i);
                String centerNo = mIn.getCenterNo();
                String centerName = mIn.getCenterName();
                mIn.setCenterName((centerNo==null ? "" : centerNo) +" "+ (centerName==null ? "" : centerName));
                mIn.setApplyDate(XDate.dateTo10(mIn.getApplyDate()));
                mIn.setBillDate(XDate.dateTo10(mIn.getBillDate()));
            }
            PoiUtils.exportExcelOld(response, "入库明细", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
