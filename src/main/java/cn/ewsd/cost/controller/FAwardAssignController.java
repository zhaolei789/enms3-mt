package cn.ewsd.cost.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.DateUtils;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.cost.model.FAwardAssignSelect_A;
import cn.ewsd.cost.util.BigDecimalUtils;
import cn.ewsd.cost.util.ExcelStyle;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.cost.model.FAwardAssign;
import cn.ewsd.cost.service.FAwardAssignService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 罚款分配
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-26 18:03:29
 */
@Controller
@RequestMapping("/cost/fAwardAssign")
public class FAwardAssignController extends CostBaseController {

    @Autowired
    private FAwardAssignService fAwardAssignService;
    @Resource
    private UserService userService;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开FAwardAssign模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "cost/fAwardAssign/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得FAwardAssign分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort += " and award_id = '"+request.getParameter("awardId")+"'";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<FAwardAssign> pageSet = fAwardAssignService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得FAwardAssign模块详细数据")
    public Object getDetailByUuid(String uuid) {
        FAwardAssign fAwardAssign = fAwardAssignService.selectByPrimaryKey(uuid);
        return fAwardAssign;
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开FAwardAssign模块新增页面")
    public String add() {
        return "cost/fAwardAssign/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存FAwardAssign模块数据")
    public Object save(@ModelAttribute FAwardAssign fAwardAssign) {
        //验证,如果没有选人,不予以通过
        if(fAwardAssign==null||fAwardAssign.getEmpId()==null||fAwardAssign.getEmpId().equals("")){
            return failure("请选择人员！");
        }
        User uu = userService.getUserByUserNameId(fAwardAssign.getEmpId());
        if(uu == null){
            return failure("人员错误！");
        }
        User user = userService.getUserByUserNameId(fAwardAssign.getEmpId());
        if(user == null){
            return failure("人员错误！");
        }
        fAwardAssign.setAssignId(Snow.getUUID());
        fAwardAssign.setAssignType("f.assignType.3");
        fAwardAssign.setModiDate(sdfd2.format(new Date()));
        fAwardAssign.setModiUser(LoginInfo.getUserNameId());
        fAwardAssign.setUserName(LoginInfo.getUserName());
        fAwardAssign.setIsDel(0);
        fAwardAssign.setLeadFlag(user.getLeadFlag());
        int result = fAwardAssignService.insertSelective(getSaveData(fAwardAssign));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开FAwardAssign模块编辑页面")
    public String edit() {
        return "cost/fAwardAssign/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新FAwardAssign模块数据")
    public Object update(@ModelAttribute FAwardAssign fAwardAssign) {
        fAwardAssign.setModiDate(sdfd2.format(new Date()));
        int result = fAwardAssignService.updateByPrimaryKeySelective(getUpdateData(fAwardAssign));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除FAwardAssign模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = fAwardAssignService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    /**
     * 导出领导统计
     * @param response
     */
    @RequestMapping(value = "/exportExcelByLd")
    @ControllerLog(description = "导出领导罚款统计")
    public void exportExcelByLd(HttpServletResponse response) {
        String fileName = "管理人员核减单";
        String sheet1Name = "副总";
        String sheet2Name = "科室";
        String sheet3Name = "区队";
        HSSFWorkbook workbook = new HSSFWorkbook();

        //sheet1表头===================================================================================================
        HSSFSheet sheet1 = workbook.createSheet(sheet1Name);
        //设置sheet样式
        sheet1.setMargin((short) 2, sheet1.getMargin((short) 2));
        sheet1.setMargin((short) 3, sheet1.getMargin((short) 3));
        sheet1.setMargin((short) 0, sheet1.getMargin((short) 0));
        sheet1.setMargin((short) 1, sheet1.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet1.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize((short) 9);
        //列宽设置
        sheet1.setColumnWidth(0, 2000);
        sheet1.setColumnWidth(1, 3000);
        sheet1.setColumnWidth(2, 4000);
        sheet1.setColumnWidth(3, 4000);
        sheet1.setColumnWidth(4, 4000);
        sheet1.setColumnWidth(5, 3000);
        sheet1.setColumnWidth(6, 4000);
        sheet1.setColumnWidth(7, 12000);
        sheet1.setColumnWidth(8, 3000);
        sheet1.setColumnWidth(9, 3000);
        //第一行(标题)
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        HSSFRow headerRow = sheet1.createRow(0);
        headerRow.setHeightInPoints(40.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(ExcelStyle.getHeaderStyle(workbook));
        cell.setCellValue("公司领导核减绩效工资明细表");
        //第三行(列头)
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 5, 5));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 6, 6));
        sheet1.addMergedRegion(new CellRangeAddress(1, 2, 7, 7));
        HSSFRow tableHeaderRow = sheet1.createRow(1);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell;
        tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("序号");
        tableHederCell = tableHeaderRow.createCell(1);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("责任人");
        tableHederCell = tableHeaderRow.createCell(2);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("发生日期");
        tableHederCell = tableHeaderRow.createCell(3);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("责任单位");
        tableHederCell = tableHeaderRow.createCell(4);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("考核单位");
        tableHederCell = tableHeaderRow.createCell(5);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("签发人员");
        tableHederCell = tableHeaderRow.createCell(6);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("考核分类");
        tableHederCell = tableHeaderRow.createCell(7);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("考核原因");
        //考核金额头
        sheet1.addMergedRegion(new CellRangeAddress(1, 1, 8, 9));
        tableHeaderRow.setHeightInPoints(15.0F);
        tableHederCell = tableHeaderRow.createCell(8);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("考核金额（元）");
        HSSFRow tableHeaderRow2 = sheet1.createRow(2);
        tableHeaderRow2.setHeightInPoints(15.0F);
        tableHederCell = tableHeaderRow2.createCell(8);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("小计");
        tableHederCell = tableHeaderRow2.createCell(9);
        tableHederCell.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell.setCellValue("合计");

        //sheet2表头===================================================================================================
        HSSFSheet sheet2 = workbook.createSheet(sheet2Name);
        //设置sheet样式
        sheet2.setMargin((short) 2, sheet2.getMargin((short) 2));
        sheet2.setMargin((short) 3, sheet2.getMargin((short) 3));
        sheet2.setMargin((short) 0, sheet2.getMargin((short) 0));
        sheet2.setMargin((short) 1, sheet2.getMargin((short) 1));
        HSSFPrintSetup printSetup2 = sheet2.getPrintSetup();
        printSetup2.setLandscape(true);
        printSetup2.setPaperSize((short) 9);
        //列宽设置
        sheet2.setColumnWidth(0, 2000);
        sheet2.setColumnWidth(1, 3000);
        sheet2.setColumnWidth(2, 4000);
        sheet2.setColumnWidth(3, 4000);
        sheet2.setColumnWidth(4, 4000);
        sheet2.setColumnWidth(5, 3000);
        sheet2.setColumnWidth(6, 4000);
        sheet2.setColumnWidth(7, 12000);
        sheet2.setColumnWidth(8, 3000);
        sheet2.setColumnWidth(9, 3000);
        //第一行(标题)
        sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        HSSFRow headerRow2 = sheet2.createRow(0);
        headerRow2.setHeightInPoints(40.0F);
        HSSFCell cell2 = headerRow2.createCell(0);
        cell2.setCellStyle(ExcelStyle.getHeaderStyle(workbook));
        cell2.setCellValue("科室管理人员核减绩效工资明细表");
        //第三行(列头)
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 5, 5));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 6, 6));
        sheet2.addMergedRegion(new CellRangeAddress(1, 2, 7, 7));
        HSSFRow tableHeaderRow3 = sheet2.createRow(1);
        tableHeaderRow3.setHeightInPoints(30.0F);
        HSSFCell tableHederCell2;
        tableHederCell2 = tableHeaderRow3.createCell(0);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("序号");
        tableHederCell2 = tableHeaderRow3.createCell(1);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("责任人");
        tableHederCell2 = tableHeaderRow3.createCell(2);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("发生日期");
        tableHederCell2 = tableHeaderRow3.createCell(3);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("责任单位");
        tableHederCell2 = tableHeaderRow3.createCell(4);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("考核单位");
        tableHederCell2 = tableHeaderRow3.createCell(5);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("签发人员");
        tableHederCell2 = tableHeaderRow3.createCell(6);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("考核分类");
        tableHederCell2 = tableHeaderRow3.createCell(7);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("考核原因");
        //考核金额头
        sheet2.addMergedRegion(new CellRangeAddress(1, 1, 8, 9));
        tableHeaderRow3.setHeightInPoints(15.0F);
        tableHederCell2 = tableHeaderRow3.createCell(8);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("考核金额（元）");
        HSSFRow tableHeaderRow4 = sheet2.createRow(2);
        tableHeaderRow4.setHeightInPoints(15.0F);
        tableHederCell2 = tableHeaderRow4.createCell(8);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("小计");
        tableHederCell2 = tableHeaderRow4.createCell(9);
        tableHederCell2.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell2.setCellValue("合计");


        //sheet3表头===================================================================================================
        HSSFSheet sheet3 = workbook.createSheet(sheet3Name);
        //设置sheet样式
        sheet3.setMargin((short) 2, sheet3.getMargin((short) 2));
        sheet3.setMargin((short) 3, sheet3.getMargin((short) 3));
        sheet3.setMargin((short) 0, sheet3.getMargin((short) 0));
        sheet3.setMargin((short) 1, sheet3.getMargin((short) 1));
        HSSFPrintSetup printSetup3 = sheet3.getPrintSetup();
        printSetup3.setLandscape(true);
        printSetup3.setPaperSize((short) 9);
        //列宽设置
        sheet3.setColumnWidth(0, 2000);
        sheet3.setColumnWidth(1, 3000);
        sheet3.setColumnWidth(2, 4000);
        sheet3.setColumnWidth(3, 4000);
        sheet3.setColumnWidth(4, 4000);
        sheet3.setColumnWidth(5, 3000);
        sheet3.setColumnWidth(6, 4000);
        sheet3.setColumnWidth(7, 12000);
        sheet3.setColumnWidth(8, 3000);
        sheet3.setColumnWidth(9, 3000);
        //第一行(标题)
        sheet3.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        HSSFRow headerRow3 = sheet3.createRow(0);
        headerRow3.setHeightInPoints(40.0F);
        HSSFCell cell3 = headerRow3.createCell(0);
        cell3.setCellStyle(ExcelStyle.getHeaderStyle(workbook));
        cell3.setCellValue("区队管理人员核减绩效工资明细表");
        //第三行(列头)
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 5, 5));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 6, 6));
        sheet3.addMergedRegion(new CellRangeAddress(1, 2, 7, 7));
        HSSFRow tableHeaderRow5 = sheet3.createRow(1);
        tableHeaderRow5.setHeightInPoints(30.0F);
        HSSFCell tableHederCell3;
        tableHederCell3 = tableHeaderRow5.createCell(0);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("序号");
        tableHederCell3 = tableHeaderRow5.createCell(1);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("责任人");
        tableHederCell3 = tableHeaderRow5.createCell(2);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("发生日期");
        tableHederCell3 = tableHeaderRow5.createCell(3);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("责任单位");
        tableHederCell3 = tableHeaderRow5.createCell(4);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("考核单位");
        tableHederCell3 = tableHeaderRow5.createCell(5);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("签发人员");
        tableHederCell3 = tableHeaderRow5.createCell(6);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("考核分类");
        tableHederCell3 = tableHeaderRow5.createCell(7);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("考核原因");
        //考核金额头
        sheet3.addMergedRegion(new CellRangeAddress(1, 1, 8, 9));
        tableHeaderRow5.setHeightInPoints(15.0F);
        tableHederCell3 = tableHeaderRow5.createCell(8);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("考核金额（元）");
        HSSFRow tableHeaderRow6 = sheet3.createRow(2);
        tableHeaderRow6.setHeightInPoints(15.0F);
        tableHederCell3 = tableHeaderRow6.createCell(8);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("小计");
        tableHederCell3 = tableHeaderRow6.createCell(9);
        tableHederCell3.setCellStyle(ExcelStyle.getDateStyleCenter_B(workbook));
        tableHederCell3.setCellValue("合计");


        //查询条件
        String filterSort = "";
        filterSort = " and f.is_del !=1 and f.upd_status = '0'";
        String query_teamNo = request.getParameter("query_teamNo");
        if(query_teamNo!=null&&!"".equals(query_teamNo)&&!"undefined".equals(query_teamNo)){
            filterSort += " and f.team_no = "+query_teamNo;
        }
        String query_assessCate = request.getParameter("query_assessCate");
        if(query_assessCate!=null&&!"".equals(query_assessCate)&&!"undefined".equals(query_assessCate)){
            filterSort += " and f.assess_cate = '"+query_assessCate+"'";
        }
        String query_modiStart = request.getParameter("query_modiStart");
        if(query_modiStart!=null&&!"".equals(query_modiStart)&&!"undefined".equals(query_modiStart)){
            filterSort += " and f.modi_date >= '"+query_modiStart+"'";
        }
        String query_modiEnd = request.getParameter("query_modiEnd");
        if(query_modiEnd!=null&&!"".equals(query_modiEnd)&&!"undefined".equals(query_modiEnd)){
            filterSort += " and f.modi_date <= '"+query_modiEnd+"'";
        }
        String query_assessTeam = request.getParameter("query_assessTeam");
        if(query_assessTeam!=null&&!"".equals(query_assessTeam)&&!"undefined".equals(query_assessTeam)){
            filterSort += " and f.assess_team = "+query_assessTeam;
        }
        String query_modiTeam = request.getParameter("query_modiTeam");
        if(query_modiTeam!=null&&!"".equals(query_modiTeam)&&!"undefined".equals(query_modiTeam)){
            filterSort += " and f.modi_team = "+query_modiTeam;
        }
        String query_status = request.getParameter("query_status");
        if(query_status!=null&&!"".equals(query_status)&&!"undefined".equals(query_status)){
            filterSort += " and f.status = '"+query_status+"'";
        }else {
            filterSort += " and f.status = '484AE79DE2CE4A9CA527C9C279CCE055'";//已完成
        }
        String query_noticeNo = request.getParameter("query_noticeNo");
        if(query_noticeNo!=null&&!"".equals(query_noticeNo)&&!"undefined".equals(query_noticeNo)){
            filterSort += " and f.notice_no like '%"+query_noticeNo+"%'";
        }
        String query_signEmp = request.getParameter("query_signEmp");
        if(query_signEmp!=null&&!"".equals(query_signEmp)&&!"undefined".equals(query_signEmp)){
            filterSort += " and f.sign_emp like '%"+query_signEmp+"%'";
        }
        String query_reason = request.getParameter("query_reason");
        if(query_reason!=null&&!"".equals(query_reason)&&!"undefined".equals(query_reason)){
            filterSort += " and f.reason like '%"+query_reason+"%'";
        }
        String query_person = request.getParameter("query_person");
        if(query_person!=null&&!"".equals(query_person)&&!"undefined".equals(query_person)) {
            filterSort += " and a.emp_name like '%" + query_person + "%'";
        }
        //预设对象
        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;
        HSSFFont hssfFont = workbook.createFont();
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        try{
            int startRow = 3;//从第X行开始写入数据
            int indexNum = 1;
            int itemPersonStartRow = 3;//人员占用开始行
            int itemPersonRowNum = 0;//人员占用行
            BigDecimal itemPersonTotal = new BigDecimal(0);//人员的金额合计
            List<FAwardAssignSelect_A> itemPersonDataList = new ArrayList<>();//临时存放考核数据容器


            //sheet1===================================================================================================
            //副总
            String filterSort_E = " and a.lead_flag = 'leadFlag.1' order by a.emp_id asc ";
            List<FAwardAssignSelect_A> awardAssignSelect_fz = fAwardAssignService.queryListByLeadExcel(filterSort+filterSort_E);
            for (int i = 0; i < awardAssignSelect_fz.size(); i++) {
                if(i == (awardAssignSelect_fz.size()-1)){//最后一行
                    //如果只有一行,或上一行与最后一行相同,加入itemPersonDataList后直接打印
                    if(i==0 || awardAssignSelect_fz.get(i-1).getEmpId().equals(awardAssignSelect_fz.get(i).getEmpId())){
                        itemPersonDataList.add(awardAssignSelect_fz.get(i));
                        itemPersonTotal = BigDecimalUtils.add(itemPersonTotal,awardAssignSelect_fz.get(i).getAwardBala(),2);
                        itemPersonRowNum ++;
                        for (int j = 0; j < itemPersonDataList.size(); j++) {
                            tableContentRow = sheet1.createRow(itemPersonStartRow+j);
                            if(j == 0 && itemPersonRowNum >1){
                                sheet1.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                            }
                            if(j == 0){
                                tableContentCell = tableContentRow.createCell(9);
                                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                                tableContentCell.setCellValue(itemPersonTotal.toString());
                            }
                            tableContentCell = tableContentRow.createCell(0);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(indexNum);
                            tableContentCell = tableContentRow.createCell(1);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                            tableContentCell = tableContentRow.createCell(2);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                            tableContentCell = tableContentRow.createCell(3);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                            tableContentCell = tableContentRow.createCell(4);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                            tableContentCell = tableContentRow.createCell(5);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                            tableContentCell = tableContentRow.createCell(6);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                            tableContentCell = tableContentRow.createCell(7);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                            tableContentCell = tableContentRow.createCell(8);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                            indexNum ++;
                        }
                    }else {
                        //分别打印,先打印itemPersonDataList中的,再打印最后一行
                        for (int j = 0; j < itemPersonDataList.size(); j++) {
                            tableContentRow = sheet1.createRow(itemPersonStartRow+j);
                            if(j == 0 && itemPersonRowNum >1){
                                sheet1.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                            }
                            if(j == 0){
                                tableContentCell = tableContentRow.createCell(9);
                                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                                tableContentCell.setCellValue(itemPersonTotal.toString());
                            }
                            tableContentCell = tableContentRow.createCell(0);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(indexNum);
                            tableContentCell = tableContentRow.createCell(1);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                            tableContentCell = tableContentRow.createCell(2);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                            tableContentCell = tableContentRow.createCell(3);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                            tableContentCell = tableContentRow.createCell(4);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                            tableContentCell = tableContentRow.createCell(5);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                            tableContentCell = tableContentRow.createCell(6);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                            tableContentCell = tableContentRow.createCell(7);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                            tableContentCell = tableContentRow.createCell(8);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                            indexNum ++;
                        }
                        //打印最后一行
                        tableContentRow = sheet1.createRow(itemPersonStartRow+itemPersonDataList.size());
                        tableContentCell = tableContentRow.createCell(0);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(indexNum);
                        tableContentCell = tableContentRow.createCell(1);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getEmpName());
                        tableContentCell = tableContentRow.createCell(2);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getOccDate());
                        tableContentCell = tableContentRow.createCell(3);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getTeamNoName());
                        tableContentCell = tableContentRow.createCell(4);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getAssessTeamName());
                        tableContentCell = tableContentRow.createCell(5);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getSignEmp());
                        tableContentCell = tableContentRow.createCell(6);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getAssessCateName());
                        tableContentCell = tableContentRow.createCell(7);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getReason());
                        tableContentCell = tableContentRow.createCell(8);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_fz.get(i).getAwardBala().toString());
                        tableContentCell = tableContentRow.createCell(9);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonTotal.toString());
                    }
                    //重置参数
                    itemPersonDataList = new ArrayList<>();
                    itemPersonRowNum = 0;
                    itemPersonStartRow = 3;
                    itemPersonTotal = new BigDecimal(0);
                }else if(i == 0){//首行
                    itemPersonTotal = awardAssignSelect_fz.get(i).getAwardBala();
                    itemPersonRowNum = 1;
                    itemPersonDataList = new ArrayList<>();
                    itemPersonStartRow = startRow + i;
                    itemPersonDataList.add(awardAssignSelect_fz.get(i));
                } else if(awardAssignSelect_fz.get(i-1).getEmpId().equals(awardAssignSelect_fz.get(i).getEmpId())){//本行与上一行相同人员
                    itemPersonTotal = BigDecimalUtils.add(itemPersonTotal,awardAssignSelect_fz.get(i).getAwardBala());
                    itemPersonRowNum ++;
                    itemPersonDataList.add(awardAssignSelect_fz.get(i));
                } else if(!awardAssignSelect_fz.get(i-1).getEmpId().equals(awardAssignSelect_fz.get(i).getEmpId())){//上一行与本行人员不同,打印数据并重置参数
                    //进行打印上一批数据
                    for (int j = 0; j < itemPersonDataList.size(); j++) {
                        tableContentRow = sheet1.createRow(itemPersonStartRow+j);
                        if(j == 0 && itemPersonRowNum >1){
                            sheet1.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                        }
                        if(j == 0){
                            tableContentCell = tableContentRow.createCell(9);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonTotal.toString());
                        }
                        tableContentCell = tableContentRow.createCell(0);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(indexNum);
                        tableContentCell = tableContentRow.createCell(1);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                        tableContentCell = tableContentRow.createCell(2);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                        tableContentCell = tableContentRow.createCell(3);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                        tableContentCell = tableContentRow.createCell(4);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                        tableContentCell = tableContentRow.createCell(5);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                        tableContentCell = tableContentRow.createCell(6);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                        tableContentCell = tableContentRow.createCell(7);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                        tableContentCell = tableContentRow.createCell(8);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                        indexNum++;
                    }
                    //重新计数
                    itemPersonTotal = awardAssignSelect_fz.get(i).getAwardBala();
                    itemPersonRowNum = 1;
                    itemPersonDataList = new ArrayList<>();
                    itemPersonStartRow = startRow + i;
                    //记录本行
                    itemPersonDataList.add(awardAssignSelect_fz.get(i));
                }
            }

            //sheet2===================================================================================================
            //科室
            startRow = 3;//从第X行开始写入数据
            indexNum = 1;
            itemPersonStartRow = 3;//人员占用开始行
            itemPersonRowNum = 0;//人员占用行
            itemPersonTotal = new BigDecimal(0);//人员的金额合计
            itemPersonDataList = new ArrayList<>();//临时存放考核数据容器

            filterSort_E = " and a.lead_flag = 'leadFlag.2' order by a.emp_id asc ";
            List<FAwardAssignSelect_A> awardAssignSelect_ks = fAwardAssignService.queryListByLeadExcel(filterSort+filterSort_E);
            for (int i = 0; i < awardAssignSelect_ks.size(); i++) {
                if(i == (awardAssignSelect_ks.size()-1)){//最后一行
                    //如果只有一行,或上一行与最后一行相同,加入itemPersonDataList后直接打印
                    if(i==0 || awardAssignSelect_ks.get(i-1).getEmpId().equals(awardAssignSelect_ks.get(i).getEmpId())){
                        itemPersonDataList.add(awardAssignSelect_ks.get(i));
                        itemPersonTotal = BigDecimalUtils.add(itemPersonTotal,awardAssignSelect_ks.get(i).getAwardBala(),2);
                        itemPersonRowNum ++;
                        for (int j = 0; j < itemPersonDataList.size(); j++) {
                            tableContentRow = sheet2.createRow(itemPersonStartRow+j);
                            if(j == 0 && itemPersonRowNum >1){
                                sheet2.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                            }
                            if(j == 0){
                                tableContentCell = tableContentRow.createCell(9);
                                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                                tableContentCell.setCellValue(itemPersonTotal.toString());
                            }
                            tableContentCell = tableContentRow.createCell(0);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(indexNum);
                            tableContentCell = tableContentRow.createCell(1);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                            tableContentCell = tableContentRow.createCell(2);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                            tableContentCell = tableContentRow.createCell(3);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                            tableContentCell = tableContentRow.createCell(4);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                            tableContentCell = tableContentRow.createCell(5);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                            tableContentCell = tableContentRow.createCell(6);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                            tableContentCell = tableContentRow.createCell(7);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                            tableContentCell = tableContentRow.createCell(8);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                            indexNum ++;
                        }
                    }else {
                        //分别打印,先打印itemPersonDataList中的,再打印最后一行
                        for (int j = 0; j < itemPersonDataList.size(); j++) {
                            tableContentRow = sheet2.createRow(itemPersonStartRow+j);
                            if(j == 0 && itemPersonRowNum >1){
                                sheet2.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                            }
                            if(j == 0){
                                tableContentCell = tableContentRow.createCell(9);
                                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                                tableContentCell.setCellValue(itemPersonTotal.toString());
                            }
                            tableContentCell = tableContentRow.createCell(0);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(indexNum);
                            tableContentCell = tableContentRow.createCell(1);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                            tableContentCell = tableContentRow.createCell(2);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                            tableContentCell = tableContentRow.createCell(3);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                            tableContentCell = tableContentRow.createCell(4);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                            tableContentCell = tableContentRow.createCell(5);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                            tableContentCell = tableContentRow.createCell(6);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                            tableContentCell = tableContentRow.createCell(7);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                            tableContentCell = tableContentRow.createCell(8);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                            indexNum ++;
                        }
                        //打印最后一行
                        tableContentRow = sheet2.createRow(itemPersonStartRow+itemPersonDataList.size());
                        tableContentCell = tableContentRow.createCell(0);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(indexNum);
                        tableContentCell = tableContentRow.createCell(1);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getEmpName());
                        tableContentCell = tableContentRow.createCell(2);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getOccDate());
                        tableContentCell = tableContentRow.createCell(3);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getTeamNoName());
                        tableContentCell = tableContentRow.createCell(4);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getAssessTeamName());
                        tableContentCell = tableContentRow.createCell(5);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getSignEmp());
                        tableContentCell = tableContentRow.createCell(6);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getAssessCateName());
                        tableContentCell = tableContentRow.createCell(7);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getReason());
                        tableContentCell = tableContentRow.createCell(8);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_ks.get(i).getAwardBala().toString());
                        tableContentCell = tableContentRow.createCell(9);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonTotal.toString());
                    }
                    //重置参数
                    itemPersonDataList = new ArrayList<>();
                    itemPersonRowNum = 0;
                    itemPersonStartRow = 3;
                    itemPersonTotal = new BigDecimal(0);
                }else if(i == 0){//首行
                    itemPersonTotal = awardAssignSelect_ks.get(i).getAwardBala();
                    itemPersonRowNum = 1;
                    itemPersonDataList = new ArrayList<>();
                    itemPersonStartRow = startRow + i;
                    itemPersonDataList.add(awardAssignSelect_ks.get(i));
                } else if(awardAssignSelect_ks.get(i-1).getEmpId().equals(awardAssignSelect_ks.get(i).getEmpId())){//本行与上一行相同人员
                    itemPersonTotal = BigDecimalUtils.add(itemPersonTotal,awardAssignSelect_ks.get(i).getAwardBala());
                    itemPersonRowNum ++;
                    itemPersonDataList.add(awardAssignSelect_ks.get(i));
                } else if(!awardAssignSelect_ks.get(i-1).getEmpId().equals(awardAssignSelect_ks.get(i).getEmpId())){//上一行与本行人员不同,打印数据并重置参数
                    //进行打印上一批数据
                    for (int j = 0; j < itemPersonDataList.size(); j++) {
                        tableContentRow = sheet2.createRow(itemPersonStartRow+j);
                        if(j == 0 && itemPersonRowNum >1){
                            sheet2.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                        }
                        if(j == 0){
                            tableContentCell = tableContentRow.createCell(9);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonTotal.toString());
                        }
                        tableContentCell = tableContentRow.createCell(0);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(indexNum);
                        tableContentCell = tableContentRow.createCell(1);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                        tableContentCell = tableContentRow.createCell(2);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                        tableContentCell = tableContentRow.createCell(3);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                        tableContentCell = tableContentRow.createCell(4);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                        tableContentCell = tableContentRow.createCell(5);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                        tableContentCell = tableContentRow.createCell(6);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                        tableContentCell = tableContentRow.createCell(7);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                        tableContentCell = tableContentRow.createCell(8);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                        indexNum++;
                    }
                    //重新计数
                    itemPersonTotal = awardAssignSelect_ks.get(i).getAwardBala();
                    itemPersonRowNum = 1;
                    itemPersonDataList = new ArrayList<>();
                    itemPersonStartRow = startRow + i;
                    //记录本行
                    itemPersonDataList.add(awardAssignSelect_ks.get(i));
                }
            }


            //sheet3===================================================================================================
            //区队
            startRow = 3;//从第X行开始写入数据
            indexNum = 1;
            itemPersonStartRow = 3;//人员占用开始行
            itemPersonRowNum = 0;//人员占用行
            itemPersonTotal = new BigDecimal(0);//人员的金额合计
            itemPersonDataList = new ArrayList<>();//临时存放考核数据容器

            filterSort_E = " and a.lead_flag = 'leadFlag.3' order by a.emp_id asc ";
            List<FAwardAssignSelect_A> awardAssignSelect_qd = fAwardAssignService.queryListByLeadExcel(filterSort+filterSort_E);
            for (int i = 0; i < awardAssignSelect_qd.size(); i++) {
                if(i == (awardAssignSelect_qd.size()-1)){//最后一行
                    //如果只有一行,或上一行与最后一行相同,加入itemPersonDataList后直接打印
                    if(i==0 || awardAssignSelect_qd.get(i-1).getEmpId().equals(awardAssignSelect_qd.get(i).getEmpId())){
                        itemPersonDataList.add(awardAssignSelect_qd.get(i));
                        itemPersonTotal = BigDecimalUtils.add(itemPersonTotal,awardAssignSelect_qd.get(i).getAwardBala(),2);
                        itemPersonRowNum ++;
                        for (int j = 0; j < itemPersonDataList.size(); j++) {
                            tableContentRow = sheet3.createRow(itemPersonStartRow+j);
                            if(j == 0 && itemPersonRowNum >1){
                                sheet3.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                            }
                            if(j == 0){
                                tableContentCell = tableContentRow.createCell(9);
                                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                                tableContentCell.setCellValue(itemPersonTotal.toString());
                            }
                            tableContentCell = tableContentRow.createCell(0);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(indexNum);
                            tableContentCell = tableContentRow.createCell(1);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                            tableContentCell = tableContentRow.createCell(2);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                            tableContentCell = tableContentRow.createCell(3);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                            tableContentCell = tableContentRow.createCell(4);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                            tableContentCell = tableContentRow.createCell(5);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                            tableContentCell = tableContentRow.createCell(6);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                            tableContentCell = tableContentRow.createCell(7);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                            tableContentCell = tableContentRow.createCell(8);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                            indexNum ++;
                        }
                    }else {
                        //分别打印,先打印itemPersonDataList中的,再打印最后一行
                        for (int j = 0; j < itemPersonDataList.size(); j++) {
                            tableContentRow = sheet3.createRow(itemPersonStartRow+j);
                            if(j == 0 && itemPersonRowNum >1){
                                sheet3.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                            }
                            if(j == 0){
                                tableContentCell = tableContentRow.createCell(9);
                                tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                                tableContentCell.setCellValue(itemPersonTotal.toString());
                            }
                            tableContentCell = tableContentRow.createCell(0);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(indexNum);
                            tableContentCell = tableContentRow.createCell(1);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                            tableContentCell = tableContentRow.createCell(2);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                            tableContentCell = tableContentRow.createCell(3);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                            tableContentCell = tableContentRow.createCell(4);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                            tableContentCell = tableContentRow.createCell(5);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                            tableContentCell = tableContentRow.createCell(6);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                            tableContentCell = tableContentRow.createCell(7);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                            tableContentCell = tableContentRow.createCell(8);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                            indexNum ++;
                        }
                        //打印最后一行
                        tableContentRow = sheet3.createRow(itemPersonStartRow+itemPersonDataList.size());
                        tableContentCell = tableContentRow.createCell(0);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(indexNum);
                        tableContentCell = tableContentRow.createCell(1);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getEmpName());
                        tableContentCell = tableContentRow.createCell(2);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getOccDate());
                        tableContentCell = tableContentRow.createCell(3);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getTeamNoName());
                        tableContentCell = tableContentRow.createCell(4);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getAssessTeamName());
                        tableContentCell = tableContentRow.createCell(5);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getSignEmp());
                        tableContentCell = tableContentRow.createCell(6);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getAssessCateName());
                        tableContentCell = tableContentRow.createCell(7);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getReason());
                        tableContentCell = tableContentRow.createCell(8);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(awardAssignSelect_qd.get(i).getAwardBala().toString());
                        tableContentCell = tableContentRow.createCell(9);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonTotal.toString());
                    }
                    //重置参数
                    itemPersonDataList = new ArrayList<>();
                    itemPersonRowNum = 0;
                    itemPersonStartRow = 3;
                    itemPersonTotal = new BigDecimal(0);
                }else if(i == 0){//首行
                    itemPersonTotal = awardAssignSelect_qd.get(i).getAwardBala();
                    itemPersonRowNum = 1;
                    itemPersonDataList = new ArrayList<>();
                    itemPersonStartRow = startRow + i;
                    itemPersonDataList.add(awardAssignSelect_qd.get(i));
                } else if(awardAssignSelect_qd.get(i-1).getEmpId().equals(awardAssignSelect_qd.get(i).getEmpId())){//本行与上一行相同人员
                    itemPersonTotal = BigDecimalUtils.add(itemPersonTotal,awardAssignSelect_qd.get(i).getAwardBala());
                    itemPersonRowNum ++;
                    itemPersonDataList.add(awardAssignSelect_qd.get(i));
                } else if(!awardAssignSelect_qd.get(i-1).getEmpId().equals(awardAssignSelect_qd.get(i).getEmpId())){//上一行与本行人员不同,打印数据并重置参数
                    //进行打印上一批数据
                    for (int j = 0; j < itemPersonDataList.size(); j++) {
                        tableContentRow = sheet3.createRow(itemPersonStartRow+j);
                        if(j == 0 && itemPersonRowNum >1){
                            sheet3.addMergedRegion(new CellRangeAddress(itemPersonStartRow, itemPersonStartRow+itemPersonRowNum-1, 9, 9));
                        }
                        if(j == 0){
                            tableContentCell = tableContentRow.createCell(9);
                            tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(itemPersonTotal.toString());
                        }
                        tableContentCell = tableContentRow.createCell(0);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(indexNum);
                        tableContentCell = tableContentRow.createCell(1);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getEmpName());
                        tableContentCell = tableContentRow.createCell(2);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getOccDate());
                        tableContentCell = tableContentRow.createCell(3);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getTeamNoName());
                        tableContentCell = tableContentRow.createCell(4);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessTeamName());
                        tableContentCell = tableContentRow.createCell(5);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getSignEmp());
                        tableContentCell = tableContentRow.createCell(6);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAssessCateName());
                        tableContentCell = tableContentRow.createCell(7);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getReason());
                        tableContentCell = tableContentRow.createCell(8);
                        tableContentCell.setCellStyle(ExcelStyle.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        tableContentCell.setCellValue(itemPersonDataList.get(j).getAwardBala().toString());
                        indexNum++;
                    }
                    //重新计数
                    itemPersonTotal = awardAssignSelect_qd.get(i).getAwardBala();
                    itemPersonRowNum = 1;
                    itemPersonDataList = new ArrayList<>();
                    itemPersonStartRow = startRow + i;
                    //记录本行
                    itemPersonDataList.add(awardAssignSelect_qd.get(i));
                }
            }



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
