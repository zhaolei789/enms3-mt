package cn.ewsd.cost.controller;

import cn.ewsd.base.bean.PageData;
import cn.ewsd.base.utils.*;
import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.FAward;
import cn.ewsd.cost.model.FChargeFee;
import cn.ewsd.cost.model.FFeeReport;
import cn.ewsd.cost.model.Fee;
import cn.ewsd.cost.service.FChargeFeeService;
import cn.ewsd.cost.service.FFeeReportService;
import cn.ewsd.cost.util.BigDecimalUtils;
import cn.ewsd.cost.util.ExcelStyle;
import cn.ewsd.cost.util.LRfindValueUtil;
import cn.ewsd.mdata.service.OrganizationService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 统计
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-07 09:22:24
 */
@Controller
@RequestMapping("/cost/fTj")
public class FTjController extends CostBaseController {
    Integer Khy = 2641;//考核员
    Integer Bsy = 2642;//办事员

    @Autowired
    private FChargeFeeService fChargeFeeService;
    @Autowired
    private FFeeReportService fFeeReportService;
    @Resource
    private OrganizationService organizationService;

    //核减绩效统计
    @RequestMapping("/indexHjjxtj")
    public String indexHjjxtj(@RequestParam Map<String, Object> params) {
        String Xdate = XDate.getDate();
        request.setAttribute("query_start",XDate.getMonth()+"01");
        request.setAttribute("query_end",XDate.getDate());
        return "cost/tj/index_hjjxtj";
    }

    //核减绩效统计
    @ResponseBody
    @RequestMapping(value = "/getHjjxtj", method = RequestMethod.POST)
    @ControllerLog(description = "获得Hjjxtj数据")
    public Object getHjjxtj(PageParam pageParam) throws Exception {
        //String filterSort = "";
        //条件筛选
        String query_start = request.getParameter("query_start");
        String query_end = request.getParameter("query_end");
        if(query_start==null||query_start.equals("")||"undefined".equals(query_start)){
            query_start = XDate.getMonth()+"01";
        }
        if(query_end==null||query_end.equals("")||"undefined".equals(query_end)){
            query_end = XDate.getDate();
        }
        Boolean ifKhy = checkHaveAccess(Khy);//是考核员
        Boolean ifBsy = checkHaveAccess(Bsy);//是办事员

        Map map = new HashMap();
        map.put("p1", "sys_organization");
        map.put("p2", LoginInfo.getOrgId());
        map.put("p3", 1);
        map.put("p4", "idStr");
        organizationService.getChildIds(map);
        String ids = map.get("p3").toString();

        String sql_where = " AND f_award.is_del !=1 AND f_award.upd_status = 0 AND f_award.status = '484AE79DE2CE4A9CA527C9C279CCE055'" +
                " AND f_award.modi_date >= '"+query_start+"' AND f_award.modi_date < '"+query_end+"'";

        String sql = "SELECT o.id,o.text,o.uuid " +
                ",(SELECT SUM(award_bala) FROM f_award WHERE f_award.team_no = o.id "+sql_where+")AS all_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.1'"+sql_where+")AS aqkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.2'"+sql_where+")AS clkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.3'"+sql_where+")AS jdkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.4'"+sql_where+")AS mzkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.5'"+sql_where+")AS qykh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.6'"+sql_where+")AS swkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.7'"+sql_where+")AS sckh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.8'"+sql_where+")AS zlkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.9'"+sql_where+")AS tfkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.10'"+sql_where+")AS hbkh_award" +
                ",(SELECT SUM(award_bala) FROM f_award WHERE team_no = o.id AND assess_cate = 'f.awardType.11'"+sql_where+")AS yskh_award" +
                " FROM sys_organization o" +
                " LEFT JOIN t_dept_type t ON o.id = t.dept_id" +
                " WHERE o.is_del != 1 AND o.level_id = 3 AND t.dict_key = 'deptAttr.assType'";
        List<PageData> res = DbUtil.executeQueryList(sql);
        return res;
    }


    /**
     * 核减百分比分析
     * @param params
     * @return
     */
    @RequestMapping("/indexHjbfb")
    public String indexHjbfb(@RequestParam Map<String, Object> params) {
        String Xdate = XDate.getDate();
        request.setAttribute("query_year",Xdate.substring(0,4));
        request.setAttribute("query_month",Xdate.substring(4,6));
        return "cost/tj/index_hjbfb";
    }

    @RequestMapping("/indexHjbfb2")
    public String indexHjbfb2(@RequestParam Map<String, Object> params) throws Exception {
        //页面初始参数
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");
        //String query_month = "08";
        if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }

        //统计数据
        List<PageData> res_data =  new ArrayList<>();
        String sql = "SELECT o.uuid,o.text,fa.team_no,fa.dept_type,fa.assess_cate,SUM(fa.award_bala) bala,COUNT(*) cnt" +
                "  FROM f_award fa " +
                "  LEFT JOIN sys_organization o ON fa.team_no=o.id" +
                "  WHERE fa.status = '484AE79DE2CE4A9CA527C9C279CCE055'" +
                "  AND fa.is_del!=1" +
                "  AND fa.modi_date LIKE '"+query_year+"-"+query_month+"%'" +
                "  GROUP BY fa.team_no,fa.dept_type,fa.assess_cate";
        List<PageData> res = DbUtil.executeQueryList(sql);
        for (int i = 0; i < res.size(); i++) {
            PageData res_tmp = res.get(i);//本次查到的临时数据
            PageData res_tmp2 = new PageData();
            boolean haveObj = false;
            if(res_data!=null&&res_data.size()>0){
                for (int j = 0; j < res_data.size(); j++) {
                    if(res_data.get(j).get("team_no").toString().equals(res_tmp.get("team_no").toString())){
                        //存在相同的id
                        res_tmp2 = res_data.get(j);
                        String assess_cate = res_tmp.getString("assess_cate");
                        String [] assess_cate_type_arr = assess_cate.split("\\.");
                        String assess_cate_type = assess_cate_type_arr[assess_cate_type_arr.length-1];
                        if(res_tmp.get("bala")!=null&&!res_tmp.get("bala").toString().equals("")){
                            res_tmp2.put("bala_"+assess_cate_type,res_tmp.get("bala").toString());
                            res_tmp2.put("bala_all",BigDecimalUtils.add(new BigDecimal(res_tmp.get("bala").toString()),new BigDecimal(res_tmp2.get("bala_all").toString()),2));
                            //res_tmp2.put("bala_all",Double.parseDouble(res_tmp.get("bala").toString())+Double.parseDouble(res_tmp2.get("bala").toString()));
                        }else{
                            res_tmp2.put("bala_"+assess_cate_type,0);
                        }
                        res_tmp2.put("bala_"+assess_cate_type,res_tmp.get("bala").toString());
                        res_tmp2.put("cnt_"+assess_cate_type,res_tmp.get("cnt").toString());
                        res_tmp2.put("cnt_all",Integer.parseInt(res_tmp.get("cnt").toString())+Integer.parseInt(res_tmp2.get("cnt_all").toString()));
                        res_data.remove(j);
                        res_data.add(res_tmp2);
                        haveObj = true;
                    }
                }
            }

            if(!haveObj){
                res_tmp2.put("team_no",res_tmp.get("team_no").toString());
                res_tmp2.put("text",res_tmp.getString("text"));
                String assess_cate = res_tmp.getString("assess_cate");
                String [] assess_cate_type_arr = assess_cate.split("\\.");
                String assess_cate_type = assess_cate_type_arr[assess_cate_type_arr.length-1];
                if(res_tmp.get("bala")!=null&&!res_tmp.get("bala").toString().equals("")){
                    res_tmp2.put("bala_"+assess_cate_type,new BigDecimal(res_tmp.get("bala").toString()));
                }else{
                    res_tmp2.put("bala_"+assess_cate_type,new BigDecimal(0));
                }
                res_tmp2.put("cnt_"+assess_cate_type,res_tmp.get("cnt").toString());
                res_tmp2.put("bala_all",res_tmp.get("bala").toString());
                res_tmp2.put("cnt_all",res_tmp.get("cnt").toString());
                res_data.add(res_tmp2);
            }
        }
        //格式化数据
        BigDecimal all_bala_all = new BigDecimal(0);
        for (int i = 0; i < res_data.size(); i++) {
            //PageData data = res_data.get(i);
            BigDecimal bala_all;
            if(res_data.get(i).get("cnt_all")!=null) {
                res_data.get(i).put("cnt_all", Integer.parseInt(res_data.get(i).get("cnt_all").toString()));
            }else{
                res_data.get(i).put("cnt_all", 0);
            }
            if(res_data.get(i).get("bala_all")!=null) {
                bala_all = new BigDecimal(res_data.get(i).get("bala_all").toString());
                res_data.get(i).put("bala_all", bala_all);
                all_bala_all = BigDecimalUtils.add(all_bala_all,bala_all);
            }else{
                res_data.get(i).put("bala_all", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_1")!=null) {
                res_data.get(i).put("cnt_1", Integer.parseInt(res_data.get(i).get("cnt_1").toString()));
            }else{
                res_data.get(i).put("cnt_1", 0);
            }
            if(res_data.get(i).get("bala_1")!=null) {
                res_data.get(i).put("bala_1", new BigDecimal(res_data.get(i).get("bala_1").toString()));
            }else{
                res_data.get(i).put("bala_1", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_2")!=null) {
                res_data.get(i).put("cnt_2", Integer.parseInt(res_data.get(i).get("cnt_2").toString()));
            }else{
                res_data.get(i).put("cnt_2", 0);
            }
            if(res_data.get(i).get("bala_2")!=null) {
                res_data.get(i).put("bala_2", new BigDecimal(res_data.get(i).get("bala_2").toString()));
            }else{
                res_data.get(i).put("bala_2", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_3")!=null) {
                res_data.get(i).put("cnt_3", Integer.parseInt(res_data.get(i).get("cnt_3").toString()));
            }else{
                res_data.get(i).put("cnt_3", 0);
            }
            if(res_data.get(i).get("bala_3")!=null) {
                res_data.get(i).put("bala_3", new BigDecimal(res_data.get(i).get("bala_3").toString()));
            }else{
                res_data.get(i).put("bala_3", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_4")!=null) {
                res_data.get(i).put("cnt_4", Integer.parseInt(res_data.get(i).get("cnt_4").toString()));
            }else{
                res_data.get(i).put("cnt_4", 0);
            }
            if(res_data.get(i).get("bala_4")!=null) {
                res_data.get(i).put("bala_4", new BigDecimal(res_data.get(i).get("bala_4").toString()));
            }else{
                res_data.get(i).put("bala_4", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_5")!=null) {
                res_data.get(i).put("cnt_5", Integer.parseInt(res_data.get(i).get("cnt_5").toString()));
            }else{
                res_data.get(i).put("cnt_5", 0);
            }
            if(res_data.get(i).get("bala_5")!=null) {
                res_data.get(i).put("bala_5", new BigDecimal(res_data.get(i).get("bala_5").toString()));
            }else{
                res_data.get(i).put("bala_5", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_6")!=null) {
                res_data.get(i).put("cnt_6", Integer.parseInt(res_data.get(i).get("cnt_6").toString()));
            }else{
                res_data.get(i).put("cnt_6", 0);
            }
            if(res_data.get(i).get("bala_6")!=null) {
                res_data.get(i).put("bala_6", new BigDecimal(res_data.get(i).get("bala_6").toString()));
            }else{
                res_data.get(i).put("bala_6", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_7")!=null) {
                res_data.get(i).put("cnt_7", Integer.parseInt(res_data.get(i).get("cnt_7").toString()));
            }else{
                res_data.get(i).put("cnt_7", 0);
            }
            if(res_data.get(i).get("bala_7")!=null) {
                res_data.get(i).put("bala_7", new BigDecimal(res_data.get(i).get("bala_7").toString()));
            }else{
                res_data.get(i).put("bala_7", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_8")!=null) {
                res_data.get(i).put("cnt_8", Integer.parseInt(res_data.get(i).get("cnt_8").toString()));
            }else{
                res_data.get(i).put("cnt_8", 0);
            }
            if(res_data.get(i).get("bala_8")!=null) {
                res_data.get(i).put("bala_8", new BigDecimal(res_data.get(i).get("bala_8").toString()));
            }else{
                res_data.get(i).put("bala_8", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_9")!=null) {
                res_data.get(i).put("cnt_9", Integer.parseInt(res_data.get(i).get("cnt_9").toString()));
            }else{
                res_data.get(i).put("cnt_9", 0);
            }
            if(res_data.get(i).get("bala_9")!=null) {
                res_data.get(i).put("bala_9", new BigDecimal(res_data.get(i).get("bala_9").toString()));
            }else{
                res_data.get(i).put("bala_9", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_10")!=null) {
                res_data.get(i).put("cnt_10", Integer.parseInt(res_data.get(i).get("cnt_10").toString()));
            }else{
                res_data.get(i).put("cnt_10", 0);
            }
            if(res_data.get(i).get("bala_10")!=null) {
                res_data.get(i).put("bala_10", new BigDecimal(res_data.get(i).get("bala_10").toString()));
            }else{
                res_data.get(i).put("bala_10", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_11")!=null) {
                res_data.get(i).put("cnt_11", Integer.parseInt(res_data.get(i).get("cnt_11").toString()));
            }else{
                res_data.get(i).put("cnt_11", 0);
            }
            if(res_data.get(i).get("bala_11")!=null) {
                res_data.get(i).put("bala_11", new BigDecimal(res_data.get(i).get("bala_11").toString()));
            }else{
                res_data.get(i).put("bala_11", new BigDecimal(0));
            }
        }
        //计算百分比
        for (int i = 0; i < res_data.size(); i++) {
            res_data.get(i).put("bl_all",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_all").toString()),all_bala_all,4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_1",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_1").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_2",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_2").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_3",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_3").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_4",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_4").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_5",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_5").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_6",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_6").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_7",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_7").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_8",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_8").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_9",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_9").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_10",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_10").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_11",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_11").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
        }


        request.setAttribute("res_data_list",res_data);
        request.setAttribute("query_year",query_year);
        request.setAttribute("query_month",query_month);
        return "cost/tj/index_hjbfb2";
    }

    @ResponseBody
    @RequestMapping(value = "/getHjbfb", method = RequestMethod.POST)
    @ControllerLog(description = "获得getHjbfb数据")
    public Object getHjbfb(PageParam pageParam) throws Exception {
        //String filterSort = "";
        //条件筛选
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");

        if(query_year==null||query_year.equals("")||"undefined".equals(query_year)){
            query_year = XDate.getDate().substring(0,4);
        }
        if(query_month==null||query_month.equals("")||"undefined".equals(query_month)){
            query_month = XDate.getDate().substring(4,6);
        }

        List<PageData> res_data =  new ArrayList<>();

        String sql = "SELECT o.uuid,o.text,fa.team_no,fa.dept_type,fa.assess_cate,SUM(fa.award_bala) bala,COUNT(*) cnt" +
                "  FROM f_award fa " +
                "  LEFT JOIN sys_organization o ON fa.team_no=o.id" +
                "  WHERE fa.status = '484AE79DE2CE4A9CA527C9C279CCE055'" +
                "  AND fa.is_del!=1" +
                "  AND fa.modi_date LIKE '"+query_year+"-"+query_month+"%'" +
                "  GROUP BY fa.team_no,fa.dept_type,fa.assess_cate";
        List<PageData> res = DbUtil.executeQueryList(sql);
        for (int i = 0; i < res.size(); i++) {
            PageData res_tmp = res.get(i);//本次查到的临时数据
            PageData res_tmp2 = new PageData();
            boolean haveObj = false;
            if(res_data!=null&&res_data.size()>0){
                for (int j = 0; j < res_data.size(); j++) {
                    if(res_data.get(j).get("team_no").toString().equals(res_tmp.get("team_no").toString())){
                        //存在相同的id
                        res_tmp2 = res_data.get(j);
                        String assess_cate = res_tmp.getString("assess_cate");
                        String [] assess_cate_type_arr = assess_cate.split("\\.");
                        String assess_cate_type = assess_cate_type_arr[assess_cate_type_arr.length-1];
                        if(res_tmp.get("bala")!=null&&!res_tmp.get("bala").toString().equals("")){
                            res_tmp2.put("bala_"+assess_cate_type,res_tmp.get("bala").toString());
                            res_tmp2.put("bala_all",BigDecimalUtils.add(new BigDecimal(res_tmp.get("bala").toString()),new BigDecimal(res_tmp2.get("bala_all").toString()),2));
                            //res_tmp2.put("bala_all",Double.parseDouble(res_tmp.get("bala").toString())+Double.parseDouble(res_tmp2.get("bala").toString()));
                        }else{
                            res_tmp2.put("bala_"+assess_cate_type,0);
                        }
                        res_tmp2.put("bala_"+assess_cate_type,res_tmp.get("bala").toString());
                        res_tmp2.put("cnt_"+assess_cate_type,res_tmp.get("cnt").toString());
                        res_tmp2.put("cnt_all",Integer.parseInt(res_tmp.get("cnt").toString())+Integer.parseInt(res_tmp2.get("cnt_all").toString()));
                        res_data.remove(j);
                        res_data.add(res_tmp2);
                        haveObj = true;
                    }
                }
            }

            if(!haveObj){
                res_tmp2.put("team_no",res_tmp.get("team_no").toString());
                res_tmp2.put("text",res_tmp.getString("text"));
                String assess_cate = res_tmp.getString("assess_cate");
                String [] assess_cate_type_arr = assess_cate.split("\\.");
                String assess_cate_type = assess_cate_type_arr[assess_cate_type_arr.length-1];
                if(res_tmp.get("bala")!=null&&!res_tmp.get("bala").toString().equals("")){
                    res_tmp2.put("bala_"+assess_cate_type,new BigDecimal(res_tmp.get("bala").toString()));
                }else{
                    res_tmp2.put("bala_"+assess_cate_type,new BigDecimal(0));
                }
                res_tmp2.put("cnt_"+assess_cate_type,res_tmp.get("cnt").toString());
                res_tmp2.put("bala_all",res_tmp.get("bala").toString());
                res_tmp2.put("cnt_all",res_tmp.get("cnt").toString());
                res_data.add(res_tmp2);
            }
        }
        //System.out.println(res_data.size());

        //格式化数据
        BigDecimal all_bala_all = new BigDecimal(0);
        for (int i = 0; i < res_data.size(); i++) {
            //PageData data = res_data.get(i);
            BigDecimal bala_all;
            if(res_data.get(i).get("cnt_all")!=null) {
                res_data.get(i).put("cnt_all", Integer.parseInt(res_data.get(i).get("cnt_all").toString()));
            }else{
                res_data.get(i).put("cnt_all", 0);
            }
            if(res_data.get(i).get("bala_all")!=null) {
                bala_all = new BigDecimal(res_data.get(i).get("bala_all").toString());
                res_data.get(i).put("bala_all", bala_all);
                all_bala_all = BigDecimalUtils.add(all_bala_all,bala_all);
            }else{
                res_data.get(i).put("bala_all", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_1")!=null) {
                res_data.get(i).put("cnt_1", Integer.parseInt(res_data.get(i).get("cnt_1").toString()));
            }else{
                res_data.get(i).put("cnt_1", 0);
            }
            if(res_data.get(i).get("bala_1")!=null) {
                res_data.get(i).put("bala_1", new BigDecimal(res_data.get(i).get("bala_1").toString()));
            }else{
                res_data.get(i).put("bala_1", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_2")!=null) {
                res_data.get(i).put("cnt_2", Integer.parseInt(res_data.get(i).get("cnt_2").toString()));
            }else{
                res_data.get(i).put("cnt_2", 0);
            }
            if(res_data.get(i).get("bala_2")!=null) {
                res_data.get(i).put("bala_2", new BigDecimal(res_data.get(i).get("bala_2").toString()));
            }else{
                res_data.get(i).put("bala_2", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_3")!=null) {
                res_data.get(i).put("cnt_3", Integer.parseInt(res_data.get(i).get("cnt_3").toString()));
            }else{
                res_data.get(i).put("cnt_3", 0);
            }
            if(res_data.get(i).get("bala_3")!=null) {
                res_data.get(i).put("bala_3", new BigDecimal(res_data.get(i).get("bala_3").toString()));
            }else{
                res_data.get(i).put("bala_3", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_4")!=null) {
                res_data.get(i).put("cnt_4", Integer.parseInt(res_data.get(i).get("cnt_4").toString()));
            }else{
                res_data.get(i).put("cnt_4", 0);
            }
            if(res_data.get(i).get("bala_4")!=null) {
                res_data.get(i).put("bala_4", new BigDecimal(res_data.get(i).get("bala_4").toString()));
            }else{
                res_data.get(i).put("bala_4", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_5")!=null) {
                res_data.get(i).put("cnt_5", Integer.parseInt(res_data.get(i).get("cnt_5").toString()));
            }else{
                res_data.get(i).put("cnt_5", 0);
            }
            if(res_data.get(i).get("bala_5")!=null) {
                res_data.get(i).put("bala_5", new BigDecimal(res_data.get(i).get("bala_5").toString()));
            }else{
                res_data.get(i).put("bala_5", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_6")!=null) {
                res_data.get(i).put("cnt_6", Integer.parseInt(res_data.get(i).get("cnt_6").toString()));
            }else{
                res_data.get(i).put("cnt_6", 0);
            }
            if(res_data.get(i).get("bala_6")!=null) {
                res_data.get(i).put("bala_6", new BigDecimal(res_data.get(i).get("bala_6").toString()));
            }else{
                res_data.get(i).put("bala_6", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_7")!=null) {
                res_data.get(i).put("cnt_7", Integer.parseInt(res_data.get(i).get("cnt_7").toString()));
            }else{
                res_data.get(i).put("cnt_7", 0);
            }
            if(res_data.get(i).get("bala_7")!=null) {
                res_data.get(i).put("bala_7", new BigDecimal(res_data.get(i).get("bala_7").toString()));
            }else{
                res_data.get(i).put("bala_7", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_8")!=null) {
                res_data.get(i).put("cnt_8", Integer.parseInt(res_data.get(i).get("cnt_8").toString()));
            }else{
                res_data.get(i).put("cnt_8", 0);
            }
            if(res_data.get(i).get("bala_8")!=null) {
                res_data.get(i).put("bala_8", new BigDecimal(res_data.get(i).get("bala_8").toString()));
            }else{
                res_data.get(i).put("bala_8", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_9")!=null) {
                res_data.get(i).put("cnt_9", Integer.parseInt(res_data.get(i).get("cnt_9").toString()));
            }else{
                res_data.get(i).put("cnt_9", 0);
            }
            if(res_data.get(i).get("bala_9")!=null) {
                res_data.get(i).put("bala_9", new BigDecimal(res_data.get(i).get("bala_9").toString()));
            }else{
                res_data.get(i).put("bala_9", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_10")!=null) {
                res_data.get(i).put("cnt_10", Integer.parseInt(res_data.get(i).get("cnt_10").toString()));
            }else{
                res_data.get(i).put("cnt_10", 0);
            }
            if(res_data.get(i).get("bala_10")!=null) {
                res_data.get(i).put("bala_10", new BigDecimal(res_data.get(i).get("bala_10").toString()));
            }else{
                res_data.get(i).put("bala_10", new BigDecimal(0));
            }
            if(res_data.get(i).get("cnt_11")!=null) {
                res_data.get(i).put("cnt_11", Integer.parseInt(res_data.get(i).get("cnt_11").toString()));
            }else{
                res_data.get(i).put("cnt_11", 0);
            }
            if(res_data.get(i).get("bala_11")!=null) {
                res_data.get(i).put("bala_11", new BigDecimal(res_data.get(i).get("bala_11").toString()));
            }else{
                res_data.get(i).put("bala_11", new BigDecimal(0));
            }
        }

        //计算百分比
        for (int i = 0; i < res_data.size(); i++) {
            res_data.get(i).put("bl_all",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_all").toString()),all_bala_all,4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_1",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_1").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_2",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_2").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_3",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_3").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_4",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_4").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_5",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_5").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_6",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_6").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_7",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_7").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_8",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_8").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_9",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_9").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_10",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_10").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
            res_data.get(i).put("bl_11",
                    BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(new BigDecimal(res_data.get(i).get("bala_11").toString()),new BigDecimal(res_data.get(i).get("bala_all").toString()),4),new BigDecimal(100),2
                    ).toString()+"%");
        }
        return res_data;
    }


    /**
     * 考核汇总表
     * @param response
     */
    @RequestMapping(value = "/exportDataKhhz")
    public void exportDataKhhz(HttpServletResponse response) throws Exception {
        //参数
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");
        String fileName = "考核汇总表";

        String bala = "0.00";
        String sql = "SELECT SUM(award_bala) AS bala FROM f_award WHERE f_award.is_del !=1 " +
                " AND f_award.upd_status = 0 " +
                " AND f_award.status = '484AE79DE2CE4A9CA527C9C279CCE055' " +
                " AND f_award.modi_date LIKE '"+query_year+"-"+query_month+"%'";
        PageData res = DbUtil.executeQueryObject(sql);
        if(res!=null&&res.get("bala")!=null){
            bala = res.get("bala").toString();
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);

        //设置列宽
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 6000);

        //第一行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(40.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue("鄂尔多斯市转龙湾煤炭有限公司");

        //第二行
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
        headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40.0F);
        cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue(query_year+"年"+query_month+"月考核统计汇总表");

        //第三行
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
        HSSFRow dateRow = sheet.createRow(2);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("时间:"+DateUtils.format(new Date(),"yyyy年MM月dd日"));
        cell1 = dateRow.createCell(2);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("单位:元");

        //表格列头
        HSSFRow tableHeaderRow = sheet.createRow(3);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("序号");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("名称");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("金额");

        //表内容
        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;
        HSSFFont hssfFont = workbook.createFont();
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

        try{
        //内部单位
        tableContentRow = sheet.createRow(4);
        tableContentRow.setHeightInPoints(30.0F);
        tableContentCell = tableContentRow.createCell(0);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("1");
        tableContentCell = tableContentRow.createCell(1);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("内部单位");
        tableContentCell = tableContentRow.createCell(2);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue(bala);

        //协同单位
        tableContentRow = sheet.createRow(5);
        tableContentRow.setHeightInPoints(30.0F);
        tableContentCell = tableContentRow.createCell(0);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("2");
        tableContentCell = tableContentRow.createCell(1);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("协同单位");
        tableContentCell = tableContentRow.createCell(2);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("");

        //合计
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
        tableContentRow = sheet.createRow(6);
        tableContentRow.setHeightInPoints(30.0F);
        tableContentCell = tableContentRow.createCell(0);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("合计");
        tableContentCell = tableContentRow.createCell(1);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue(bala);
        tableContentCell = tableContentRow.createCell(2);
        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
        tableContentCell.setCellValue("");

        //尾部
        tableContentRow = sheet.createRow(7);
        tableContentRow.setHeightInPoints(30.0F);
        tableContentCell = tableContentRow.createCell(0);
        tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
        tableContentCell.setCellValue("制表:");
        tableContentCell = tableContentRow.createCell(1);
        tableContentCell.setCellStyle(ExcelStyle.getDateStyle(workbook));
        tableContentCell.setCellValue("");
        tableContentCell = tableContentRow.createCell(2);
        tableContentCell.setCellStyle(ExcelStyle.getDateStyle(workbook));
        tableContentCell.setCellValue("审核:");
        }catch (Exception e) {
            e.printStackTrace();
        }
        ServletOutputStream os = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);
        } catch (Exception var39) {
            var39.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException var38) {
                    var38.printStackTrace();
                }
            }
        }

    }


    /**
     * 考核表
     */
    @RequestMapping(value = "/exportDataTdhj")
    public void exportDataTdhj(HttpServletResponse response) throws Exception {
        //参数
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");
        String fileName = "考核表";

        String sql = "SELECT fa.team_no," +
                " (select o.text from sys_organization o where fa.team_no = o.id) team_name," +
                " fa.dept_type," +
                " (select d.text from sys_dic_item d where fa.dept_type = d.value) dept_type_name," +
                " SUM(fa.award_bala) bala FROM f_award fa" +
                " WHERE fa.is_del !=1 AND fa.upd_status = 0 AND fa.status = '484AE79DE2CE4A9CA527C9C279CCE055' " +
                " AND fa.modi_date LIKE '"+query_year+"-"+query_month+"%'" +
                " GROUP BY fa.team_no,fa.dept_type" +
                " ORDER BY fa.dept_type ASC";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);

        //设置列宽
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 13000);
        sheet.setColumnWidth(3, 5000);

        //第一行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(40.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue("鄂尔多斯市转龙湾煤炭有限公司");

        //第二行
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40.0F);
        cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue(query_year+"年"+query_month+"月份核减绩效工资分类汇总表");

        //第三行
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
        HSSFRow dateRow = sheet.createRow(2);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("时间:"+DateUtils.format(new Date(),"yyyy年MM月dd日"));
        cell1 = dateRow.createCell(3);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("单位:元");

        //表格列头
        HSSFRow tableHeaderRow = sheet.createRow(3);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("序号");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("分类");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("单位名称");
        tableHederCell = tableHeaderRow.createCell(3);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("合计");

        //表内容
        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;
        HSSFFont hssfFont = workbook.createFont();
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

        try{
            List<PageData> res_list = DbUtil.executeQueryList(sql);
            int startRow = 4;//从第4行开始写入数据
            for (int i = 0; i < res_list.size(); i++) {
                PageData res = res_list.get(i);
                tableContentRow = sheet.createRow(startRow);
                tableContentRow.setHeightInPoints(30.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(i+1);
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.getString("dept_type"));
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.getString("team_name"));
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.get("bala").toString());
                startRow++;
            }

            //尾部
            tableContentRow = sheet.createRow(startRow);
            tableContentRow.setHeightInPoints(30.0F);
            tableContentCell = tableContentRow.createCell(0);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("制表:");
            tableContentCell = tableContentRow.createCell(1);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyle(workbook));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(2);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("审核:");
            tableContentCell = tableContentRow.createCell(3);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyle(workbook));
            tableContentCell.setCellValue("");
        }catch (Exception e) {
            e.printStackTrace();
        }
        ServletOutputStream os = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);
        } catch (Exception var39) {
            var39.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException var38) {
                    var38.printStackTrace();
                }
            }
        }
    }


    /**
     * 考核明细
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportDataFkmx")
    public void exportDataFkmx(HttpServletResponse response) throws Exception {
        //参数
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");
        String fileName = "考核明细";

        String sql = "SELECT fa.team_no,fa.assess_type,COUNT(*) cnt," +
                " (SELECT o.text FROM sys_organization o WHERE fa.team_no = o.id) AS team_name," +
                " SUM(fa.award_bala) AS bala FROM f_award fa" +
                " WHERE fa.is_del !=1 AND fa.upd_status = 0 AND fa.status = '484AE79DE2CE4A9CA527C9C279CCE055' " +
                " AND fa.modi_date LIKE '"+query_year+"-"+query_month+"%'" +
                " GROUP BY fa.team_no,fa.assess_type";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);

        //设置列宽
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 5000);

        //第一行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(40.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue("鄂尔多斯市转龙湾煤炭有限公司");

        //第二行
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40.0F);
        cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue(query_year+"年"+query_month+"月份核减绩效工资统计汇总表");

        //第三行
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
        HSSFRow dateRow = sheet.createRow(2);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("时间:"+DateUtils.format(new Date(),"yyyy年MM月dd日"));
        cell1 = dateRow.createCell(5);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("单位:元");

        //表格列头
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
        HSSFRow tableHeaderRow = sheet.createRow(3);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("序号");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("单位名称");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("个人考核");
        tableHederCell = tableHeaderRow.createCell(3);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        tableHederCell = tableHeaderRow.createCell(4);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("合计");
        tableHederCell = tableHeaderRow.createCell(5);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");

        tableHeaderRow = sheet.createRow(4);
        tableHeaderRow.setHeightInPoints(30.0F);
        tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("通知单数");
        tableHederCell = tableHeaderRow.createCell(3);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("核减金额(元)");
        tableHederCell = tableHeaderRow.createCell(4);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("通知单数");
        tableHederCell = tableHeaderRow.createCell(5);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("核减金额(元)");

        //表内容
        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;
        HSSFFont hssfFont = workbook.createFont();
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

        try{
            List<PageData> res_list = DbUtil.executeQueryList(sql);
            int startRow = 5;//从第5行开始写入数据
            BigDecimal all_bala = new BigDecimal(0);
            int cnt_all = 0;
            for (int i = 0; i < res_list.size(); i++) {
                PageData res = res_list.get(i);
                tableContentRow = sheet.createRow(startRow);
                tableContentRow.setHeightInPoints(30.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(i+1);
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.getString("team_name"));
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.get("cnt").toString());
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.get("bala").toString());
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.get("cnt").toString());
                tableContentCell = tableContentRow.createCell(5);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(res.get("bala").toString());
                if(res.get("bala")!=null){
                    all_bala = BigDecimalUtils.add(all_bala,new BigDecimal(res.get("bala").toString()),2);
                    cnt_all += Integer.parseInt(res.get("cnt").toString());
                }
                startRow++;
            }

            //合计
            sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 1));
            tableContentRow = sheet.createRow(startRow);
            tableContentRow.setHeightInPoints(30.0F);
            tableContentCell = tableContentRow.createCell(0);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue("合计");
            tableContentCell = tableContentRow.createCell(1);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(2);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(cnt_all);
            tableContentCell = tableContentRow.createCell(3);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(all_bala.toString());
            tableContentCell = tableContentRow.createCell(4);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(cnt_all);
            tableContentCell = tableContentRow.createCell(5);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(all_bala.toString());
            startRow ++;

            //尾部
            tableContentRow = sheet.createRow(startRow);
            tableContentRow.setHeightInPoints(30.0F);
            tableContentCell = tableContentRow.createCell(0);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(1);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("制表:");
            tableContentCell = tableContentRow.createCell(2);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(3);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(4);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("审核:");
            tableContentCell = tableContentRow.createCell(5);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("");
        }catch (Exception e) {
            e.printStackTrace();
        }
        ServletOutputStream os = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);
        } catch (Exception var39) {
            var39.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException var38) {
                    var38.printStackTrace();
                }
            }
        }
    }


    /**
     * 罚款百分比
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportDataFkbfb")
    public void exportDataFkbfb(HttpServletResponse response) throws Exception {
        //参数
        String query_year = request.getParameter("query_year");
        String query_month = request.getParameter("query_month");
        String fileName = "考核百分比分析";

        String rql = "SELECT fa.team_no,fa.dept_type,fa.assess_cate," +
                " COUNT(*) cnt,SUM(fa.award_bala) AS bala," +
                " (SELECT o.text FROM sys_organization o WHERE fa.team_no = o.id) AS team_name," +
                " (SELECT d.text FROM sys_dic_item d WHERE fa.assess_cate = d.value) AS assess_cate_name" +
                " FROM f_award fa "+
                " WHERE fa.is_del !=1 AND fa.upd_status = 0 AND fa.status = '484AE79DE2CE4A9CA527C9C279CCE055' " +
                " AND fa.modi_date LIKE '"+query_year+"-"+query_month+"%'" +
                " GROUP BY fa.team_no,fa.dept_type,fa.assess_cate";
        //全部数据
        List<PageData> res_all_list = DbUtil.executeQueryList(rql);

        String sql = " SELECT a.team_no,a.team_name,a.dept_type FROM ("+rql+") a GROUP BY a.team_no,a.dept_type";
        //部门列表
        List<PageData> team_list = DbUtil.executeQueryList(sql);

        sql = " SELECT a.assess_cate,a.assess_cate_name FROM ("+rql+") a GROUP BY a.assess_cate";
        //类型列表
        List<PageData> type_list = DbUtil.executeQueryList(sql);

        //计算总额
        BigDecimal all_bala = new BigDecimal(0);
        int all_cnt = 0;
        for (int i = 0; i < res_all_list.size(); i++) {
            if(res_all_list!=null&&res_all_list.get(i).get("bala")!=null){
                all_bala = BigDecimalUtils.add(all_bala,new BigDecimal(res_all_list.get(i).get("bala").toString()));
            }
            if(res_all_list!=null&&res_all_list.get(i).get("cnt")!=null){
                all_cnt += Integer.parseInt(res_all_list.get(i).get("cnt").toString());
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);

        //设置列宽
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 3000);
        int td_num = 5;
        for (int i = 0; i < type_list.size(); i++) {
            sheet.setColumnWidth(td_num, 3000);
            sheet.setColumnWidth(td_num+1, 5000);
            sheet.setColumnWidth(td_num+2, 3000);
            td_num += 3;
        }

        //第一行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, td_num-1));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(40.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue("鄂尔多斯市转龙湾煤炭有限公司");

        //第二行
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, td_num-1));
        headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40.0F);
        cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle2(workbook));
        cell.setCellValue(query_year+"年"+query_month+"月份核减绩效工资统计汇总表");

        //第三行
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
        HSSFRow dateRow = sheet.createRow(2);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("时间:"+DateUtils.format(new Date(),"yyyy年MM月dd日"));
        cell1 = dateRow.createCell(td_num-2);
        cell1.setCellStyle(ExcelStyle.getDateStyle(workbook));
        cell1.setCellValue("单位:元");

        //表格列头
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(3, 4, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));
        int td2 = 5;
        for (int i = 0; i < type_list.size(); i++) {
            sheet.addMergedRegion(new CellRangeAddress(3, 3, td2, td2+2));
            td2 += 3;
        }
        HSSFRow tableHeaderRow = sheet.createRow(3);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("序号");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("单位名称");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("绩效工资核减总额");
        tableHederCell = tableHeaderRow.createCell(3);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        tableHederCell = tableHeaderRow.createCell(4);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        td2 = 5;
        for (int i = 0; i < type_list.size(); i++) {
            tableHederCell = tableHeaderRow.createCell(td2);
            tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
            tableHederCell.setCellValue(type_list.get(i).getString("assess_cate_name"));
            tableHederCell = tableHeaderRow.createCell(td2+1);
            tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(td2+2);
            tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
            tableHederCell.setCellValue("");
            td2 += 3;
        }
        //双头
        tableHeaderRow = sheet.createRow(4);
        tableHeaderRow.setHeightInPoints(30.0F);
        tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("通知单数");
        tableHederCell = tableHeaderRow.createCell(3);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("核减金额(元)");
        tableHederCell = tableHeaderRow.createCell(4);
        tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
        tableHederCell.setCellValue("百分比");
        td2 = 5;
        for (int i = 0; i < type_list.size(); i++) {
            tableHederCell = tableHeaderRow.createCell(td2);
            tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
            tableHederCell.setCellValue("通知单数");
            tableHederCell = tableHeaderRow.createCell(td2+1);
            tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
            tableHederCell.setCellValue("核减金额(元)");
            tableHederCell = tableHeaderRow.createCell(td2+2);
            tableHederCell.setCellStyle(ExcelStyle.getCommonStyle(workbook));
            tableHederCell.setCellValue("百分比");
            td2 += 3;
        }

        //表内容
        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;
        HSSFFont hssfFont = workbook.createFont();
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

        try{
            int startRow = 5;//从第5行开始写入数据
            for (int i = 0; i < team_list.size(); i++) {
                PageData team_obj = team_list.get(i);//部门数据

                //区队总额
                BigDecimal dept_bala = new BigDecimal(0);
                int dept_cnt = 0;
                for (int j = 0; j < res_all_list.size(); j++) {
                    if(res_all_list.get(j).get("team_no").toString().equals(team_obj.get("team_no").toString())&&res_all_list!=null&&res_all_list.get(j).get("bala")!=null){
                        dept_bala = BigDecimalUtils.add(dept_bala,new BigDecimal(res_all_list.get(j).get("bala").toString()));
                    }
                    if(res_all_list.get(j).get("team_no").toString().equals(team_obj.get("team_no").toString())&&res_all_list!=null&&res_all_list.get(j).get("cnt")!=null){
                        dept_cnt += Integer.parseInt(res_all_list.get(j).get("cnt").toString());
                    }
                }

                tableContentRow = sheet.createRow(startRow);
                tableContentRow.setHeightInPoints(30.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(i+1);
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(team_obj.getString("team_name"));
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(dept_cnt);
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(dept_bala.toString());
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(
                        BigDecimalUtils.multiply(
                            BigDecimalUtils.divide(dept_bala,all_bala,4),new BigDecimal(100),2
                        ).toString()+"%");
                int cell_num = 5;
                for (int j = 0; j < type_list.size(); j++) {
                    PageData type_obj = type_list.get(j);//类型数据
                    String type_cnt_print = "";
                    String type_bala_print = "";
                    String type_bfb_print = "";

                    for (int k = 0; k < res_all_list.size(); k++) {
                        PageData res_all_obj = res_all_list.get(k);
                        if(team_obj.get("team_no").toString().equals(res_all_obj.get("team_no").toString())&&
                                res_all_obj.get("assess_cate").toString().equals(type_obj.getString("assess_cate"))){
                            type_cnt_print = res_all_obj.get("cnt").toString();
                            type_bala_print = res_all_obj.get("bala").toString();
                            type_bfb_print =
                                    BigDecimalUtils.multiply(
                                        BigDecimalUtils.divide(new BigDecimal(type_bala_print),dept_bala,4),new BigDecimal(100),2
                                    ).toString()+"%";
                            break;
                        }
                    }
                    tableContentCell = tableContentRow.createCell(cell_num);
                    tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(type_cnt_print);
                    tableContentCell = tableContentRow.createCell(cell_num+1);
                    tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(type_bala_print);
                    tableContentCell = tableContentRow.createCell(cell_num+2);
                    tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(type_bfb_print);

                    //合计数据
                    if(!type_cnt_print.equals("")){
                        if(type_obj.get("type_cnt")!=null){
                            //累加数据
                            type_list.get(j).put("type_cnt",Integer.parseInt(type_obj.get("type_cnt").toString())+Integer.parseInt(type_cnt_print)+"");
                            type_list.get(j).put("type_bala",BigDecimalUtils.add(new BigDecimal(type_obj.get("type_bala").toString()),new BigDecimal(type_bala_print)).toString());
                        }else{
                            //初次存储
                            type_list.get(j).put("type_cnt",type_cnt_print);
                            type_list.get(j).put("type_bala",type_bala_print);
                        }
                    }
                    cell_num += 3;
                }
                startRow++;
            }

            //合计
            sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 1));
            tableContentRow = sheet.createRow(startRow);
            tableContentRow.setHeightInPoints(30.0F);
            tableContentCell = tableContentRow.createCell(0);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue("总额");
            tableContentCell = tableContentRow.createCell(1);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(2);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(all_cnt);
            tableContentCell = tableContentRow.createCell(3);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(all_bala.toString());
            tableContentCell = tableContentRow.createCell(4);
            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue("100%");
            int cell_num = 5;
            for (int i = 0; i < type_list.size(); i++) {
                PageData type_obj = type_list.get(i);//类型数据
                String type_cnt_print = "";
                String type_bala_print = "";
                String type_bfb_print = "";
                if(type_obj!=null&&type_obj.get("type_cnt")!=null){
                    type_cnt_print = type_obj.get("type_cnt").toString();
                    type_bala_print = type_obj.get("type_bala").toString();
                    type_bfb_print =
                            BigDecimalUtils.multiply(
                                    BigDecimalUtils.divide(new BigDecimal(type_bala_print),all_bala,4),new BigDecimal(100),2
                            ).toString()+"%";
                }
                tableContentCell = tableContentRow.createCell(cell_num);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(type_cnt_print);
                tableContentCell = tableContentRow.createCell(cell_num+1);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(type_bala_print);
                tableContentCell = tableContentRow.createCell(cell_num+2);
                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(type_bfb_print);

                cell_num += 3;
            }
            startRow++;

            //尾部
            tableContentRow = sheet.createRow(startRow);
            tableContentRow.setHeightInPoints(30.0F);
            tableContentCell = tableContentRow.createCell(0);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("");
            tableContentCell = tableContentRow.createCell(1);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("制表:");
            tableContentCell = tableContentRow.createCell(cell_num-3);
            tableContentCell.setCellStyle(ExcelStyle.getDateStyleRight(workbook));
            tableContentCell.setCellValue("审核:");
        }catch (Exception e) {
            e.printStackTrace();
        }
        ServletOutputStream os = null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);
        } catch (Exception var39) {
            var39.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException var38) {
                    var38.printStackTrace();
                }
            }
        }
    }

}
