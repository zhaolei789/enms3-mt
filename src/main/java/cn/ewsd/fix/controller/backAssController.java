package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.cost.model.MNormFee;
import cn.ewsd.fix.model.MAssessDetail;
import cn.ewsd.fix.service.MAssessDetailService;
import cn.ewsd.fix.service.MBackPlanService;
import cn.ewsd.fix.service.MFixAssessService;
import cn.ewsd.mdata.model.Organization;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
@RequestMapping("/fix/backAss")
public class backAssController extends FixBaseController {

    @Autowired
    private MAssessDetailService mAssessDetailService;
    @Autowired
    private MBackPlanService mBackPlanService;
    @Autowired
    private MFixAssessService mFixAssessService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String year = XDate.getYear();
        String month = XDate.getMon();
        request.setAttribute("currentYear", Integer.parseInt(year));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(month));
        List<Organization> teamList = mFixAssessService.getFixAssessTeam(year+month, LoginInfo.getOrgId());
        String teamQry = "";
        if(teamList.size() > 0){
            teamQry = teamList.get(0).getId()+"";
        }
        request.setAttribute("teamQry", teamQry);

        return "fix/backAss/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getMngTeam", method = RequestMethod.POST)
    public Object getMngTeam(PageParam pageParam) {
        try {
            return mFixAssessService.getMngTeam();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String assMonth = yearQry+monQry;
        String teamQry = request.getParameter("teamQry");
        String prjQry = request.getParameter("prjQry");
        String mngTeamQry = request.getParameter("mngTeamQry");
        if(mngTeamQry==null){
            mngTeamQry = "";
        }

        try {
            return mAssessDetailService.getBackAssessList(assMonth, LoginInfo.getOrgId(), teamQry, prjQry, mngTeamQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    //下载条数过多会卡主
    public void downloadPlan(HttpServletResponse response) {
        try{
            String yearQry = request.getParameter("yearQry");
            String monQry = request.getParameter("monQry");
            String assMonth = yearQry+monQry;
            String teamQry = request.getParameter("teamQry");
            String prjQry = request.getParameter("prjQry");
            String mngTeamQry = request.getParameter("mngTeamQry");
            if(mngTeamQry==null){
                mngTeamQry = "";
            }

            List<MAssessDetail> list = mAssessDetailService.getBackAssessList(assMonth, LoginInfo.getOrgId(), teamQry, prjQry, mngTeamQry);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("专项回收考核");
            sheet.setMargin((short) 2, sheet.getMargin((short) 2));
            sheet.setMargin((short) 3, sheet.getMargin((short) 3));
            sheet.setMargin((short) 0, sheet.getMargin((short) 0));
            sheet.setMargin((short) 1, sheet.getMargin((short) 1));
            HSSFPrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            printSetup.setPaperSize((short) 9);

            sheet.setColumnWidth(0, 5000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 5000);
            sheet.setColumnWidth(7, 5000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 5000);

            HSSFRow tableHeaderRow = sheet.createRow(0);
            tableHeaderRow.setHeightInPoints(30.0F);
            HSSFCell tableHederCell = null;
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("物料编码");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("物料描述");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单位");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("每米回收量");
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("考核数量");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("实际数量");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("完成率");
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单价");
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("考核结果");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("备注");

            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFRow tableContentRow = null;
            HSSFCell tableContentCell = null;

            for(int i=0; i<list.size(); i++){
                MAssessDetail mAssessDetail = list.get(i);

                tableContentRow = sheet.createRow(i + 1);
                tableContentRow.setHeightInPoints(20.0F);

                if("1".equals(mAssessDetail.getPrjRow())){
                    tableContentCell = tableContentRow.createCell(0);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    String matCode = mAssessDetail.getPrjName();
                    tableContentCell.setCellValue(matCode);
                    continue;
                }

                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                String matCode = "X".equals(mAssessDetail.getMatCode().substring(0, 1)) ? mAssessDetail.getMatCode().substring(1) : mAssessDetail.getMatCode();
                tableContentCell.setCellValue(matCode);
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getMatName());
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getMatUnit());
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getBackNorm()==null ? "0" : mAssessDetail.getBackNorm().doubleValue()+"");
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getBackAmount()==null ? "0" : mAssessDetail.getBackAmount().doubleValue()+"");
                tableContentCell = tableContentRow.createCell(5);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getMatAmount().doubleValue()+"");
                tableContentCell = tableContentRow.createCell(6);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getOverScale().doubleValue()+"%");
                tableContentCell = tableContentRow.createCell(7);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getMatPrice().doubleValue()+"");
                tableContentCell = tableContentRow.createCell(8);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getMatPrice().multiply(mAssessDetail.getMatAmount()).doubleValue()+"");
                tableContentCell = tableContentRow.createCell(9);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getResult());
                tableContentCell = tableContentRow.createCell(10);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mAssessDetail.getRemark());

                if("1".equals(mAssessDetail.getPrjRow())){
                    sheet.addMergedRegion(new CellRangeAddress(i+1, i+1, 0, 10));
                }
            }

            ServletOutputStream os = null;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("专项回收考核".getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getPrj")
    public Object getBackAssPrj() {
        try {
            String planMonth = request.getParameter("planMonth");
            String teamNo = request.getParameter("teamNo");
            return mBackPlanService.getBackAssPrj(planMonth, teamNo);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/calc")
    public Object dealBackAss() {
        try {
            mAssessDetailService.dealBackAss(request, LoginInfo.get());
            return success("提取成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提取失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object saveBackAss() {
        try {
            mAssessDetailService.saveBackAss(request, LoginInfo.get());
            return success("保存成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitBackAss() {
        try {
            mAssessDetailService.submitBackAss(request, LoginInfo.get());
            return success("发布成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("发布失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/back")
    public Object backBackAss() {
        try {
            mAssessDetailService.backBackAss(request, LoginInfo.get());
            return success("退回成功!");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败！");
        }
    }
}
