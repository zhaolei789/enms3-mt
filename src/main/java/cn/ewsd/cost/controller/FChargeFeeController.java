package cn.ewsd.cost.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.cost.model.FFeeReport;
import cn.ewsd.cost.model.Fee;
import cn.ewsd.cost.service.FFeeReportService;
import cn.ewsd.cost.util.BigDecimalUtils;
import cn.ewsd.cost.util.LRfindValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.cost.model.FChargeFee;
import cn.ewsd.cost.service.FChargeFeeService;

import javax.servlet.http.HttpServletResponse;

/**
 * 分管费用
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:24
 */
@Controller
@RequestMapping("/cost/fChargeFee")
public class FChargeFeeController extends CostBaseController {

    @Autowired
    private FChargeFeeService fChargeFeeService;
    @Autowired
    private FFeeReportService fFeeReportService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开FChargeFee模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("query_year",XDate.getDate().substring(0,4));
        return "cost/fChargeFee/index";
    }

    //财务上报
    @RequestMapping("/indexSbCw")
    @ControllerLog(description = "打开FChargeFee模块管理页面")
    public String indexSbCw(@RequestParam Map<String, Object> params) {
        String Xdate = XDate.getDate();
        request.setAttribute("query_year",Xdate.substring(0,4));
        request.setAttribute("query_month",Xdate.substring(4,6));
        return "cost/fChargeFee/index_sb_cw";
    }

    //发生上报
    @RequestMapping("/indexSbFs")
    @ControllerLog(description = "打开FChargeFee模块管理页面")
    public String indexSbFs(@RequestParam Map<String, Object> params) {
        String Xdate = XDate.getDate();
        request.setAttribute("query_year",Xdate.substring(0,4));
        request.setAttribute("query_month",Xdate.substring(4,6));
        return "cost/fChargeFee/index_sb_fs";
    }

    //费用结算考核
    @RequestMapping("/indexSettle")
    @ControllerLog(description = "打开FChargeFee模块管理页面")
    public String indexSettle(@RequestParam Map<String, Object> params) {
        String Xdate = XDate.getDate();
        request.setAttribute("query_year",Xdate.substring(0,4));
        request.setAttribute("query_month",Xdate.substring(4,6));
        return "cost/fChargeFee/index_settle";
    }

    /**
     * 年度指标
     * @param pageParam
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得FChargeFee分页集数据")
    public Object getPageSet(PageParam pageParam) throws Exception {
        String filterSort = "";

        //条件筛选
        String query_fMonth = request.getParameter("query_fMonth");
        if(query_fMonth!=null&&!"".equals(query_fMonth)&&!"undefined".equals(query_fMonth)){
            filterSort += " and a.f_month = '"+query_fMonth+"'";
        }else{
            filterSort += " and a.f_month = '"+ XDate.getDate().substring(0,4) +"'";
        }

        String leader = request.getParameter("leader");
        if(leader!=null&&!"".equals(leader)){
            filterSort += " and a.leader = '"+leader+"'";
        }

        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        //过滤filterSort
        if(FilterUtils.checkHaveFilterField("f_month",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("f_month",filterSort,"a.f_month");
        }
        if(FilterUtils.checkHaveFilterField("item_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("item_name",filterSort,"a.item_name");
        }
        if(FilterUtils.checkHaveFilterField("team_no_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("team_no_name",filterSort,"o1.text");
        }
        if(FilterUtils.checkHaveFilterField("report_team_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("report_team_name",filterSort,"o2.text");
        }
        PageSet<FChargeFee> pageSet = fChargeFeeService.getPageSet(pageParam, filterSort);
        List<FChargeFee> fChargeFeeList =(List<FChargeFee>)covDataListDic(pageSet.getRows(),"calcWay#f.calcWay","assPeriod#f.khzq");
        /*for (int i = 0; i < fChargeFeeList.size(); i++) {
            if(fChargeFeeList.get(i).getYearBud()!=null) {
                fChargeFeeList.get(i).setYearBud(BigDecimalUtils.divide(fChargeFeeList.get(i).getYearBud(), new BigDecimal(10000)));
            }
            if(fChargeFeeList.get(i).getNormPrice()!=null) {
                fChargeFeeList.get(i).setNormPrice(BigDecimalUtils.divide(fChargeFeeList.get(i).getNormPrice(), new BigDecimal(10000)));
            }
        }*/
        pageSet.setRows(fChargeFeeList);
        return pageSet;
    }


    /**
     * 财务上报
     * @param pageParam
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPageSetSbCw", method = RequestMethod.POST)
    @ControllerLog(description = "获得FChargeFee分页集数据")
    public Object getPageSetSbCw(PageParam pageParam) throws Exception {
        String filterSort = "";

        //条件筛选
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");

        if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        filterSort += " and a.f_month = '"+query_year+"'";
        if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }

        String leader = request.getParameter("leader");
        if(leader!=null&&!"".equals(leader)){
            filterSort += " and a.leader = '"+leader+"'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        if(FilterUtils.checkHaveFilterField("team_no_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("team_no_name",filterSort,"o1.text");
        }
        if(FilterUtils.checkHaveFilterField("item_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("item_name",filterSort,"a.item_name");
        }
        PageSet<FChargeFee> pageSet = fChargeFeeService.getPageSetSbCw(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY a.create_time"),query_year+query_month);
        List<FChargeFee> fChargeFeeList = (List<FChargeFee>)covDataListDic(pageSet.getRows(),"calcWay#f.calcWay","assPeriod#f.khzq");
        /*for (int i = 0; i < fChargeFeeList.size(); i++) {
            if(fChargeFeeList.get(i).getFinanceBala()!=null) {
                fChargeFeeList.get(i).setFinanceBala(BigDecimalUtils.divide(fChargeFeeList.get(i).getFinanceBala(), new BigDecimal(10000)));
            }
        }*/
        pageSet.setRows(fChargeFeeList);
        return pageSet;
    }

    /**
     * 费用发生上报
     * @param pageParam
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPageSetSbFs", method = RequestMethod.POST)
    @ControllerLog(description = "获得FChargeFee分页集数据")
    public Object getPageSetSbFs(PageParam pageParam) throws Exception {
        String filterSort = "";

        //条件筛选
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");

        if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        filterSort += " and a.f_month = '"+query_year+"'";
        if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }

        String leader = request.getParameter("leader");
        if(leader!=null&&!"".equals(leader)){
            filterSort += " and a.leader = '"+leader+"'";
        }

        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        if(filterSort.contains("team_no_name LIKE")){
            filterSort = filterSort.replace("team_no_name LIKE","o1.text LIKE ");
        }
        if(filterSort.contains("item_name LIKE")){
            filterSort = filterSort.replace("item_name LIKE","a.item_name LIKE ");
        }
        PageSet<FChargeFee> pageSet = fChargeFeeService.getPageSetSbFs(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY a.create_time"),query_year+query_month);
        List<FChargeFee> fChargeFeeList = (List<FChargeFee>)covDataListDic(pageSet.getRows(),"calcWay#f.calcWay","assPeriod#f.khzq");
        pageSet.setRows(fChargeFeeList);
        return pageSet;
    }

    /**
     * 费用结算考核
     * @param pageParam
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPageSetSettle", method = RequestMethod.POST)
    @ControllerLog(description = "获得FChargeFee分页集数据")
    public Object getPageSetSettle(PageParam pageParam) throws Exception {
        String filterSort = "";

        //条件筛选
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");

        if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }
        filterSort += " and a.f_month = '"+query_year+query_month+"'";
        String leader = request.getParameter("leader");
        if(leader!=null&&!"".equals(leader)){
            filterSort += " and a.leader = '"+leader+"'";
        }
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        if(FilterUtils.checkHaveFilterField("team_no_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("team_no_name",filterSort,"o1.text");
        }
        if(FilterUtils.checkHaveFilterField("item_name",filterSort)){
            filterSort = FilterUtils.replaceFilterFieldName("item_name",filterSort,"a.item_name");
        }
        PageSet<FChargeFee> pageSet = fChargeFeeService.getPageSetSettle(pageParam, filterSort.replace("ORDER BY create_time","ORDER BY a.create_time"), query_year);
        List<FChargeFee> fChargeFeeList = (List<FChargeFee>)covDataListDic(pageSet.getRows(),"calcWay#f.calcWay","assPeriod#f.khzq");

        List<FChargeFee> sumList = fChargeFeeService.getSettleSum(query_year);
        DataCantainer<FChargeFee> sumDc = new DataCantainer<>((ArrayList)sumList);

        for(int i=0; i<fChargeFeeList.size(); i++){
            FChargeFee fChargeFee = fChargeFeeList.get(i);
            fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(fChargeFee.getYearBud().doubleValue(), 4)));
            fChargeFee.setMonthBudText(Data.trimDoubleNo0(fChargeFee.getMonthBud().doubleValue(), 4));
            fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(fChargeFee.getMonthBala().doubleValue(), 4)));
            fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(fChargeFee.getFinanceBala().doubleValue(), 4)));
            fChargeFee.setMonthAssessText(Data.trimDoubleNo0(fChargeFee.getMonthAssess().doubleValue(), 4));
            fChargeFee.setDiffBalaText(Data.trimDoubleNo0(fChargeFee.getDiffBala().doubleValue(), 2));
            fChargeFee.setDiffScaleText(Data.trimDoubleNo0(fChargeFee.getDiffScale().doubleValue(), 0));
            fChargeFee.setSurplusBalaText(Data.trimDoubleNo0(fChargeFee.getSurplusBala().doubleValue(), 4));

            LinkedHashMap<String, String> attrMap = new LinkedHashMap<String, String>();
            attrMap.put("leader", fChargeFee.getLeader());
            attrMap.put("team_no", fChargeFee.getTeamNo());
            attrMap.put("item_id", fChargeFee.getItemId());
            fChargeFee.setSumBalaText(Data.trimDoubleNo0(sumDc.findDataCantainer(attrMap).getSum("sum_bala"), 4));
            fChargeFee.setSumFinance(Data.trimDoubleNo0(sumDc.findDataCantainer(attrMap).getSum("sum_finc"), 4));
        }

        pageSet.setRows(fChargeFeeList);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/downloadSettle")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String query_year = request.getParameter("query_year");
            String query_month = request.getParameter("query_month");

            if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
                query_year = XDate.getDate().substring(0,4);
            }
            if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
                query_month = XDate.getDate().substring(4,6);
            }

            String filterSort = " a.f_month = '"+query_year+query_month+"' ORDER BY a.leader, a.team_no";

            List<FChargeFee> list = (List<FChargeFee>)covDataListDic(fChargeFeeService.getSettleList(filterSort, query_year),"calcWay#f.calcWay","assPeriod#f.khzq");
            List<FChargeFee> sumList = fChargeFeeService.getSettleSum(query_year);
            DataCantainer<FChargeFee> sumDc = new DataCantainer<>((ArrayList)sumList);

            for(int i=0; i<list.size(); i++){
                FChargeFee fChargeFee = list.get(i);
                fChargeFee.setYearBudText(Data.normalToFinal(Data.trimDoubleNo0(fChargeFee.getYearBud().doubleValue(), 4)));
                fChargeFee.setMonthBudText(Data.trimDoubleNo0(fChargeFee.getMonthBud().doubleValue(), 4));
                fChargeFee.setMonthBalaText(Data.normalToFinal(Data.trimDoubleNo0(fChargeFee.getMonthBala().doubleValue(), 4)));
                fChargeFee.setFinanceBalaText(Data.normalToFinal(Data.trimDoubleNo0(fChargeFee.getFinanceBala().doubleValue(), 4)));
                fChargeFee.setMonthAssessText(Data.trimDoubleNo0(fChargeFee.getMonthAssess().doubleValue(), 4));
                fChargeFee.setDiffBalaText(Data.trimDoubleNo0(fChargeFee.getDiffBala().doubleValue(), 4));
                fChargeFee.setDiffScaleText(Data.trimDoubleNo0(fChargeFee.getDiffScale().doubleValue(), 0));
                fChargeFee.setSurplusBalaText(Data.trimDoubleNo0(fChargeFee.getSurplusBala().doubleValue(), 4));

                LinkedHashMap<String, String> attrMap = new LinkedHashMap<String, String>();
                attrMap.put("leader",fChargeFee.getLeader());
                attrMap.put("team_no", fChargeFee.getTeamNo());
                attrMap.put("item_id", fChargeFee.getItemId());
                fChargeFee.setSumBalaText(Data.trimDoubleNo0(sumDc.findDataCantainer(attrMap).getSum("sum_bala"), 4));
                fChargeFee.setSumFinance(Data.trimDoubleNo0(sumDc.findDataCantainer(attrMap).getSum("sum_finc"), 4));
            }

            String textName = "领导,责任单位,费用项目,年度指标,测算依据,考核周期,单价,本月定额,月度费用,账面金额,考核费用,节超金额,节超比例,留存金额,累计发生,账面累计,备注";
            String fieldName = "leaderName,teamNoName,itemName,yearBudText,calcWay,assPeriod,normPrice,monthBudText,monthBalaText,financeBalaText,monthAssessText,diffBalaText,diffScaleText,surplusBalaText,sumBalaText,sumFinance,remark";
            PoiUtils.exportExcelOld(response, "费用结算考核", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得FChargeFee模块详细数据")
    public Object getDetailByUuid(String uuid) {
        FChargeFee fChargeFee = fChargeFeeService.queryObject(uuid);
        return fChargeFee;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开FChargeFee模块新增页面")
    public String add() {
        return "cost/fChargeFee/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存FChargeFee模块数据")
    public Object save(@ModelAttribute FChargeFee fChargeFee) throws Exception {
        String yearQry = fChargeFee.getfMonth();
        String teamNo = fChargeFee.getTeamNo();
        String reportTeam = fChargeFee.getReportTeam();
        String leader = fChargeFee.getLeader();
        String itemId = fChargeFee.getItemId();
//        String calcWay = fChargeFee.getCalcWay();
//        Integer ifOffice = fChargeFee.getIfOffice();
        BigDecimal yearBala = new BigDecimal(0);
        BigDecimal normPrice = new BigDecimal(0);
        if(fChargeFee.getYearBud()!=null&&!"".equals(fChargeFee.getYearBud())){
            //yearBala = fChargeFee.getYearBud();//BigDecimalUtils.multiply(fChargeFee.getYearBud(),new BigDecimal(10000));
            yearBala = BigDecimalUtils.multiply(fChargeFee.getYearBud(),new BigDecimal(10000),4);
            normPrice = fChargeFee.getNormPrice();
        }

        String sql = " SELECT uuid,item_name,item_type FROM f_item WHERE uuid='"+itemId+"'";
        PageData itemObj = DbUtil.executeQueryObject(sql);
        if(itemObj==null){
            return failure("未找到科目！");
        }
        String itemName = itemObj.getString("item_name");

        sql = " UPDATE f_charge_fee SET " +
                "year_bud="+yearBala+"," +
                "norm_price="+normPrice +
                " WHERE leader='"+ leader +"'"+
                " AND team_no= " +teamNo+
                " AND item_id= '" +itemId+"'"+
                " AND f_month= '" +yearQry+"'"+
                " AND report_team="+reportTeam;
        int cnt = DbUtil.executeUpdateKR2(sql);
        if(cnt <= 0){
            fChargeFee.setFeeId(Snow.getUUID());
            fChargeFee.setItemName(itemName);
            fChargeFee.setYearBud(yearBala);
            fChargeFee.setNormPrice(normPrice);
            fChargeFee.setItemType(itemObj.getString("item_type"));
            int result = fChargeFeeService.insertSelective(getSaveData(fChargeFee));
            return result > 0 ? success("保存成功！") : failure("保存失败！");
        }
        return success("保存成功!");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开FChargeFee模块编辑页面")
    public String edit() {
        return "cost/fChargeFee/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新FChargeFee模块数据")
    public Object update(@ModelAttribute FChargeFee fChargeFee) {
        fChargeFee.setYearBud(BigDecimalUtils.multiply(fChargeFee.getYearBud(),new BigDecimal(10000),4));
        int result = fChargeFeeService.updateByPrimaryKeySelective(getUpdateData(fChargeFee));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    /**
     * 年度指标编辑
     * @param fChargeFee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/eUpdate", method = RequestMethod.POST)
    @ControllerLog(description = "更新FChargeFee模块数据")
    public Object eUpdate(@ModelAttribute FChargeFee fChargeFee) {
        FChargeFee fChargeFee_tmp = new FChargeFee();
        fChargeFee_tmp.setUuid(fChargeFee.getUuid());
        //fChargeFee_tmp.setYearBud(fChargeFee.getYearBud());
        fChargeFee_tmp.setNormPrice(fChargeFee.getNormPrice());
        fChargeFee_tmp.setYearBud(BigDecimalUtils.multiply(fChargeFee.getYearBud(),new BigDecimal(10000),4));
        int result = fChargeFeeService.updateByPrimaryKeySelective(getUpdateData(fChargeFee_tmp));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    /**
     * 财务上报编辑
     * @param fChargeFee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/eUpdateSbCw", method = RequestMethod.POST)
    @ControllerLog(description = "更新FChargeFee模块数据")
    public Object eUpdateSbCw(@ModelAttribute FChargeFee fChargeFee) throws Exception {
        int result =0;
        //前置参数
        String query_year = request.getParameter("query_year");//选定的年份
        String query_month = request.getParameter("query_month");//选定的月份
        try {
            //业务参数eUpdateSbCw
            String month = "";
            if (query_year == null || query_month == null) {
                month = XDate.getDate().substring(0, 6);
            } else {
                month = query_year + query_month;
            }
            BigDecimal financeBala = BigDecimalUtils.multiply(fChargeFee.getFinanceBala(),new BigDecimal(10000),4);
            if (financeBala == null) {
                return failure("更新失败！");
            }
            //financeBala = BigDecimalUtils.multiply(financeBala,new BigDecimal(10000));

            //业务操作
            String sql = " UPDATE f_fee_report SET occ_bala=" +
                    financeBala +
                    " WHERE team_no=" +
                    fChargeFee.getTeamNo() +
                    " AND f_month=" +
                    "'" + month + "'" +
                    " AND item_id=" +
                    "'" + fChargeFee.getItemId() + "'" +
                    " AND leader=" +
                    "'" + fChargeFee.getLeader() + "'" +
                    " AND modi_team=" + LoginInfo.getOrgId();
            int cnt = DbUtil.executeUpdateKR2(sql);
            if (cnt <= 0) {
                FFeeReport fFeeReport = new FFeeReport();
                fFeeReport.setReportId(Snow.getUUID() + "");
                fFeeReport.setTeamNo(fChargeFee.getTeamNo());
                fFeeReport.setFMonth(month);
                fFeeReport.setItemId(fChargeFee.getItemId());
                fFeeReport.setItemName(fChargeFee.getItemName());
                fFeeReport.setLeader(fChargeFee.getLeader());
                fFeeReport.setOccBala(financeBala);
                fFeeReport.setModiDate(XDate.getDate());
                fFeeReport.setModiEmp(LoginInfo.getUserNameId());
                fFeeReport.setModiTeam(LoginInfo.getOrgId());
                fFeeReportService.insertSelective(getSaveData(fFeeReport));
            }

            sql = " UPDATE f_charge_fee SET finance_bala=" +
                    financeBala +
                    " WHERE team_no=" +
                    fChargeFee.getTeamNo() +
                    " AND f_month=" +
                    "'" + month + "'" +
                    " AND item_id=" +
                    "'" + fChargeFee.getItemId() + "'" +
                    " AND leader='" + fChargeFee.getLeader() + "'";
            cnt = DbUtil.executeUpdateKR2(sql);
            result += cnt;
            if (cnt <= 0) {
                sql = " SELECT * FROM f_charge_fee WHERE item_id=" +
                        "'" + fChargeFee.getItemId() + "'" +
                        " AND f_month=" +
                        "'" + query_year + "'" +
                        " AND team_no=" +
                        fChargeFee.getTeamNo() +
                        " AND leader='" + fChargeFee.getLeader() + "'";
                PageData set = DbUtil.executeQueryObject(sql);
                String itemName = set.getString("item_name");
                String calcWay = set.getString("calc_way");
                String ifOffice = set.get("if_office").toString();

                sql = " SELECT uuid,item_name,item_type FROM f_item WHERE uuid='"+fChargeFee.getItemId()+"'";
                PageData itemObj = DbUtil.executeQueryObject(sql);

                FChargeFee fChargeFee2 = new FChargeFee();
                fChargeFee2.setFeeId(Snow.getUUID());
                fChargeFee2.setLeader(fChargeFee.getLeader());
                fChargeFee2.setTeamNo(fChargeFee.getTeamNo());
                fChargeFee2.setIfOffice(Integer.parseInt(ifOffice));
                fChargeFee2.setfMonth(month);
                fChargeFee2.setItemId(fChargeFee.getItemId());
                fChargeFee2.setCalcWay(calcWay);
                fChargeFee2.setFinanceBala(financeBala);
                fChargeFee2.setItemName(itemName);
                fChargeFee2.setItemType(itemObj.getString("item_type"));
                result += fChargeFeeService.insertSelective(getSaveData(fChargeFee2));
            }
        }catch (Exception e){
            e.printStackTrace();
            // 使用 try catch 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return failure("执行失败！");
        }
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }


    /**
     * 费用发生上报
     * @param fChargeFee
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/eUpdateSbFs", method = RequestMethod.POST)
    @ControllerLog(description = "更新FChargeFee模块数据")
    public Object eUpdateSbFs(@ModelAttribute FChargeFee fChargeFee) throws Exception {
        int result =0;
        //前置参数
        String query_year = request.getParameter("query_year");//选定的年份
        String query_month = request.getParameter("query_month");//选定的月份

        try {
            //业务参数eUpdateSbFs
            String month = "";
            if (query_year == null || query_month == null) {
                month = XDate.getDate().substring(0, 6);
            } else {
                month = query_year + query_month;
            }
            BigDecimal monthBala = BigDecimalUtils.multiply(fChargeFee.getMonthBala(),new BigDecimal(10000),4);
            if (monthBala == null) {
                return failure("更新失败！");
            }
            String reportTeam = LoginInfo.getOrgId();
            //financeBala = BigDecimalUtils.multiply(financeBala,new BigDecimal(10000));

            //业务操作
            String sql = " UPDATE f_fee_report SET occ_bala=" +
                    monthBala +
                    " WHERE team_no=" +
                    fChargeFee.getTeamNo() +
                    " AND f_month=" +
                    "'" + month + "'" +
                    " AND item_id=" +
                    "'" + fChargeFee.getItemId() + "'" +
                    " AND leader=" +
                    "'" + fChargeFee.getLeader() + "'" +
                    " AND modi_team=" + reportTeam;
            int cnt = DbUtil.executeUpdateKR2(sql);
            if (cnt <= 0) {
                FFeeReport fFeeReport = new FFeeReport();
                fFeeReport.setReportId(Snow.getUUID() + "");
                fFeeReport.setTeamNo(fChargeFee.getTeamNo());
                fFeeReport.setFMonth(month);
                fFeeReport.setItemId(fChargeFee.getItemId());
                fFeeReport.setItemName(fChargeFee.getItemName());
                fFeeReport.setLeader(fChargeFee.getLeader());
                fFeeReport.setOccBala(monthBala);
                fFeeReport.setModiDate(XDate.getDate());
                fFeeReport.setModiEmp(LoginInfo.getUserNameId());
                fFeeReport.setModiTeam(reportTeam);
                fFeeReportService.insertSelective(getSaveData(fFeeReport));
            }

            sql = " UPDATE f_charge_fee SET month_bala=" +
                    monthBala +
                    ",report_team=" + reportTeam +
                    " WHERE team_no=" +
                    fChargeFee.getTeamNo() +
                    " AND f_month=" +
                    "'" + month + "'" +
                    " AND item_id=" +
                    "'" + fChargeFee.getItemId() + "'" +
                    " AND leader='" + fChargeFee.getLeader() + "'";
            cnt = DbUtil.executeUpdateKR2(sql);
            result += cnt;
            if (cnt <= 0) {
                sql = " SELECT * FROM f_charge_fee WHERE item_id=" +
                        "'" + fChargeFee.getItemId() + "'" +
                        " AND f_month=" +
                        "'" + query_year + "'" +
                        " AND team_no=" +
                        fChargeFee.getTeamNo() +
                        " AND leader='" + fChargeFee.getLeader() + "'";
                PageData set = DbUtil.executeQueryObject(sql);
                String itemName = set.getString("item_name");
                String calcWay = set.getString("calc_way");
                String ifOffice = set.get("if_office").toString();

                sql = " SELECT uuid,item_name,item_type FROM f_item WHERE uuid='"+fChargeFee.getItemId()+"'";
                PageData itemObj = DbUtil.executeQueryObject(sql);

                FChargeFee fChargeFee2 = new FChargeFee();
                fChargeFee2.setFeeId(Snow.getUUID());
                fChargeFee2.setLeader(fChargeFee.getLeader());
                fChargeFee2.setTeamNo(fChargeFee.getTeamNo());
                fChargeFee2.setIfOffice(Integer.parseInt(ifOffice));
                fChargeFee2.setfMonth(month);
                fChargeFee2.setItemId(fChargeFee.getItemId());
                fChargeFee2.setItemName(itemName);
                fChargeFee2.setCalcWay(calcWay);
                fChargeFee2.setMonthBala(monthBala);
                fChargeFee2.setReportTeam(reportTeam);
                fChargeFee2.setItemType(itemObj.getString("item_type"));
                result += fChargeFeeService.insertSelective(getSaveData(fChargeFee2));
            }
        }catch (Exception e){
            e.printStackTrace();
            // 使用 try catch 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return failure("执行失败！");
        }
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }


    @ResponseBody
    @RequestMapping(value = "/eUpdateSettle", method = RequestMethod.POST)
    @ControllerLog(description = "更新FChargeFee模块数据")
    public Object eUpdateSettle(@ModelAttribute FChargeFee fChargeFee) {
        //前置参数
        String query_year = request.getParameter("query_year");//选定的年份
        String query_month = request.getParameter("query_month");//选定的月份

        FChargeFee fChargeFee_tmp = new FChargeFee();
        fChargeFee_tmp.setUuid(fChargeFee.getUuid());
//        fChargeFee_tmp.setMonthBud(BigDecimalUtils.multiply(fChargeFee.getMonthBud(),new BigDecimal(10000),4));
//        fChargeFee_tmp.setMonthAssess(BigDecimalUtils.multiply(fChargeFee.getMonthAssess(),new BigDecimal(10000),4));
//        fChargeFee_tmp.setSurplusBala(BigDecimalUtils.multiply(fChargeFee.getSurplusBala(),new BigDecimal(10000),4));
        BigDecimal MonthBud = new BigDecimal(0.00);
        BigDecimal MonthAssess = new BigDecimal(0.00);
        BigDecimal SurplusBala = new BigDecimal(0.00);
        if(fChargeFee.getMonthBudText()!=null&&!"".equals(fChargeFee.getMonthBudText())){
            MonthBud = BigDecimalUtils.multiply(new BigDecimal(fChargeFee.getMonthBudText()),new BigDecimal(10000),4);
        }
        if(fChargeFee.getMonthAssessText()!=null&&!"".equals(fChargeFee.getMonthAssessText())){
            MonthAssess = BigDecimalUtils.multiply(new BigDecimal(fChargeFee.getMonthAssessText()),new BigDecimal(10000),4);
        }
        if(fChargeFee.getSurplusBalaText()!=null&&!"".equals(fChargeFee.getSurplusBalaText())){
            SurplusBala = BigDecimalUtils.multiply(new BigDecimal(fChargeFee.getSurplusBalaText()),new BigDecimal(10000),4);
        }
        fChargeFee_tmp.setMonthBud(MonthBud);
        fChargeFee_tmp.setMonthAssess(MonthAssess);
        fChargeFee_tmp.setSurplusBala(SurplusBala);
        fChargeFee_tmp.setRemark(fChargeFee.getRemark());

        BigDecimal diffBala = BigDecimalUtils.sub(fChargeFee.getMonthBud(),fChargeFee.getMonthAssess(),2);//节超（月费用 - 月考核）
        BigDecimal diffScale = (fChargeFee.getMonthBud() == null ||fChargeFee.getMonthBud().compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal(0):
                (BigDecimalUtils.divide(BigDecimalUtils.multiply(diffBala,new BigDecimal(100)),fChargeFee.getMonthBud()));
        fChargeFee_tmp.setDiffBala(diffBala);
        fChargeFee_tmp.setDiffScale(diffScale);
        int result = fChargeFeeService.updateByPrimaryKeySelective(getUpdateData(fChargeFee_tmp));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除FChargeFee模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = fChargeFeeService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    //结算数据
    @ResponseBody
    @RequestMapping(value = "/dealGetData", method = RequestMethod.POST)
    @ControllerLog(description = "费用结算")
    public Object dealGetData() throws Exception {
        int result = 1;
        DecimalFormat df = new DecimalFormat(".0000");
        String yearQry = request.getParameter("query_year");
        String monQry = request.getParameter("query_month");
        String month = yearQry+monQry;

        try {
            // 获取上个月的数据，年度清零
            String sql = " SELECT a.leader,a.item_id,a.team_no,a.surplus_bala" +
                    " FROM f_charge_fee a WHERE a.f_month=" + XDate.addMonth(month, -1) + " AND a.f_month LIKE '%" + yearQry + "%'";
            List<PageData> lastSet = DbUtil.executeQueryList(sql);
            // 当月产量
            sql = " SELECT * FROM m_work WHERE work_month=" + month + " AND team_no='18'";
            PageData workSet = DbUtil.executeQueryObject(sql);
            // 获取年度指标
            sql = " SELECT fy.leader,fy.item_id,fy.team_no,fy.year_bud,fy.if_office,fy.norm_price," +
                    "fy.calc_way,fi.ass_period,fi.item_name,fy.report_team,fi.get_type" +
                    " FROM f_charge_fee fy LEFT JOIN f_item fi ON fy.item_id=fi.uuid WHERE fy.f_month=" + yearQry;
            List<PageData> yearSet = DbUtil.executeQueryList(sql);
            // 月度数据

            //暂时不要这个 20210914
//            sql = " SELECT fp.leader,fp.team_no,fp.item_id,SUM(ma.occ_bala) bala" +
//                    " FROM m_assess ma INNER JOIN f_fee_pick fp ON ma.item_no=fp.link_item AND ma.team_no=fp.team_no" +
//                    " WHERE ma.occ_month=" + month + " GROUP BY fp.leader,fp.team_no,fp.item_id";
//            List<PageData> matOccSet = DbUtil.executeQueryList(sql);

            for (int i = 0; i < yearSet.size(); i++) {
                String leader = yearSet.get(i).getString("leader");
                String itemId = yearSet.get(i).getString("item_id");
                String teamNo = yearSet.get(i).getString("team_no");
                String reportTeam = yearSet.get(i).getString("report_team");
                int ifOffice = Integer.parseInt(yearSet.get(i).get("if_office").toString());
                double yearBud = Double.parseDouble(yearSet.get(i).get("year_bud").toString());
                double normPrice = Double.parseDouble(yearSet.get(i).get("norm_price").toString());
                String calcWay = yearSet.get(i).getString("calc_way");
                String getType = yearSet.get(i).get("get_type") == null ? "" :yearSet.get(i).get("get_type").toString();
                String assPeriod = yearSet.get(i).get("ass_period") == null ? "f.khzq.1" :yearSet.get(i).get("ass_period").toString();
                //String period = "".equals(assPeriod) ? "f.khzq.1" : assPeriod;
                String itemName = yearSet.get(i).getString("item_name");

                LinkedHashMap<String, String> attrMap = new LinkedHashMap<String, String>();
                attrMap.put("leader", leader);
                attrMap.put("item_id", itemId);
                attrMap.put("team_no", teamNo);
                // 上月结存数据
                double surplusBala = LRfindValueUtil.findDoubleByList(lastSet, "surplus_bala", attrMap);
                double out = 0;
                double monthBud = 0;
                // 月定额费用
                if (Fee.CALC_WAY_YM.equals(calcWay)) {// 原煤
                    out = Double.parseDouble(workSet==null||workSet.get("occ_rawout")==null?"0":workSet.get("occ_rawout").toString());
                    monthBud = Data.double2double((out * normPrice), 2);
                }
                if (Fee.CALC_WAY_DM.equals(calcWay)) {// 商品煤
                    out = Double.parseDouble(workSet==null||workSet.get("occ_saleout")==null?"0":workSet.get("occ_saleout").toString());
                    monthBud = Data.double2double((out * normPrice), 2);
                }
                if (Fee.CALC_WAY_YM.equals(calcWay)) {// 进尺
                    out = Double.parseDouble(workSet==null||workSet.get("occ_dig")==null?"0":workSet.get("occ_dig").toString());
                    monthBud = Data.double2double((out * normPrice), 2);
                }
                if (Fee.CALC_WAY_ZQ.equals(calcWay)) {// 周期；用年预算/周期；月度；季度：3，6，9，12；半年：6，12；年度：12；
                    if (Fee.ASS_PERIOD_YD.equals(assPeriod)) {//月度
                        monthBud = Data.double2double((yearBud / 12), 2);
                    }
                    if (Fee.ASS_PERIOD_JD.equals(assPeriod) && ("03".equals(monQry) || "06".equals(monQry) || "09".equals(monQry) || "12".equals(monQry))) {//季度
                        monthBud = Data.double2double((yearBud / 4), 2);
                    }
                    if (Fee.ASS_PERIOD_BN.equals(assPeriod) && ("06".equals(monQry) || "12".equals(monQry))) {//半年
                        monthBud = Data.double2double((yearBud / 2), 2);
                    }
                    if (Fee.ASS_PERIOD_ND.equals(assPeriod) && "12".equals(monQry)) {//年度
                        monthBud = Data.double2double((yearBud / 1), 2);
                    }
                }
                String mql = "";
                //月费用
                double monthBala = 0;
                double financeBala = 0;
                if (Fee.GET_WAY_SDLR.equals(getType)) {
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala,SUM(a.finance_bala) finance_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + month + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    monthBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "month_bala", attrMap);
                    financeBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                } else {
                    //暂时不用
                    //monthBala = LRfindValueUtil.findDoubleByList(matOccSet, "bala", attrMap);
                    return failure("流程改动暂不能执行！");
                }
                double assessBala = 0;//月度考核；月度，相等；季度，取本季度的发生累计；半年，取半年的发生累计；年度，取年度发生累计；
                if (Fee.ASS_PERIOD_YD.equals(assPeriod)) {//月度
                    assessBala = financeBala;//monthBala;
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "03".equals(monQry)) {//季度，3
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "01" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "06".equals(monQry)) {//季度，6
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "04" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "09".equals(monQry)) {//季度，9
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "07" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "12".equals(monQry)) {//季度，12
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "10" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "06".equals(monQry)) {//半年，6
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "01" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "12".equals(monQry)) {//半年，12
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "07" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                if (Fee.ASS_PERIOD_JD.equals(assPeriod) && "12".equals(monQry)) {//年度
                    mql = " SELECT a.leader,a.item_id,a.team_no,SUM(a.month_bala) month_bala" +
                            " FROM f_charge_fee a WHERE a.f_month>=" + yearQry + "01" + " AND a.f_month<=" + month +
                            " GROUP BY a.leader,a.item_id,a.team_no";
                    assessBala = LRfindValueUtil.findDoubleByList(DbUtil.executeQueryList(mql), "finance_bala", attrMap);
                }
                //double diffBala = Data.double2double((monthBud - assessBala), 2);//节超（月费用 - 月考核）
                double diffBala = Double.parseDouble(df.format(monthBud - assessBala));//节超（月费用 - 月考核）
                double diffScale = (monthBud == 0) ? 0 : ((diffBala * 100) / monthBud);//节超比例
                double sumBala = 0;//累计金额

                sql = " UPDATE f_charge_fee SET month_bud=" + monthBud + ",month_bala=" + monthBala + ",month_assess=" + assessBala + ",surplus_bala=" + surplusBala + ",diff_bala=" +
                        diffBala + ",diff_scale=" + diffScale +
                        " WHERE team_no='" + teamNo + "' AND f_month='" + month + "' AND item_id='" + itemId + "' AND leader='" + leader + "'";
                int cnt = DbUtil.executeUpdateKR2(sql);
                if (cnt <= 0) {
                    String sql2 = " SELECT uuid,item_name,item_type FROM f_item WHERE uuid='"+itemId+"'";
                    PageData itemObj = DbUtil.executeQueryObject(sql2);

                    FChargeFee fChargeFee = new FChargeFee();
                    fChargeFee.setFeeId(Snow.getUUID());
                    fChargeFee.setLeader(leader);
                    fChargeFee.setTeamNo(teamNo);
                    fChargeFee.setfMonth(month);
                    fChargeFee.setItemId(itemId);
                    fChargeFee.setItemName(itemName);
                    fChargeFee.setIfOffice(ifOffice);
                    fChargeFee.setCalcWay(calcWay);
                    fChargeFee.setMonthBud(new BigDecimal(monthBud));
                    fChargeFee.setMonthBala(new BigDecimal(monthBala));
                    fChargeFee.setMonthAssess(new BigDecimal(assessBala));
                    fChargeFee.setSurplusBala(new BigDecimal(surplusBala));
                    fChargeFee.setDiffBala(new BigDecimal(diffBala));
                    fChargeFee.setDiffScale(new BigDecimal(diffScale));
                    fChargeFee.setReportTeam(reportTeam);
                    fChargeFee.setItemType(itemObj.getString("item_type"));
                    result += fChargeFeeService.insertSelective(getSaveData(fChargeFee));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            // 使用 try catch 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return failure("执行失败！");
        }
        return result > 0 ? success("操作成功！") : failure("操作失败！");
    }

}
