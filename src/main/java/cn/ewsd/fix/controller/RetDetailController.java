package cn.ewsd.fix.controller;

import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MHandIn;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MHandInService;
import cn.ewsd.system.service.SysUserQryOrgService;
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
import java.util.ArrayList;
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
@RequestMapping("/fix/retDetail")
public class RetDetailController extends FixBaseController {

    @Autowired
    private SysUserQryOrgService sysUserQryOrgService;
    @Autowired
    private MHandInService mHandInService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        return "fix/retDetail/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
        String teamNoQry = request.getParameter("teamNoQry");
        String matQry = request.getParameter("matQry");
        String typeQry = request.getParameter("typeQry");
        String flagQry = request.getParameter("flagQry");

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MHandIn> pageSet = mHandInService.getRetDetailPageSet(pageParam, filterSort, flagQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), date1Qry, date2Qry, teamNoQry, typeQry, matQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try {
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));
            String teamNoQry = request.getParameter("teamNoQry");
            String matQry = request.getParameter("matQry");
            String typeQry = request.getParameter("typeQry");
            String flagQry = request.getParameter("flagQry");

            List<MHandIn> list = mHandInService.getRetDetailList(flagQry, sysUserQryOrgService.getUserDeptId(LoginInfo.getUuid()), date1Qry, date2Qry, teamNoQry, typeQry, matQry);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("交旧明细");
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
            sheet.setColumnWidth(11, 5000);
            sheet.setColumnWidth(12, 5000);
            sheet.setColumnWidth(13, 5000);
            sheet.setColumnWidth(14, 5000);

            HSSFRow tableHeaderRow = sheet.createRow(0);
            tableHeaderRow.setHeightInPoints(30.0F);
            HSSFCell tableHederCell = null;
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单位");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("材料分类");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("分类描述");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("材料编码");
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("材料名称");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("计量单位");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单价");
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("领用日期");
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("应交数量");
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("交旧标准");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("交旧日期");
            tableHederCell = tableHeaderRow.createCell(11);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("交旧数量");
            tableHederCell = tableHeaderRow.createCell(12);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("登记人员");
            tableHederCell = tableHeaderRow.createCell(13);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("备注");
            tableHederCell = tableHeaderRow.createCell(14);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("未交旧数量");

            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFRow tableContentRow = null;
            HSSFCell tableContentCell = null;

            DataCantainer<MHandIn> dc = new DataCantainer<>((ArrayList) list);
            int sIdx = 1;
            int rIdx = 0;
            ArrayList<DataCantainer<MHandIn>> jjList = dc.getGroup("bill_no");
            for(int i=0; i<jjList.size(); i++){
                DataCantainer<MHandIn> d = jjList.get(i);
                double sjjAmount = d.getSum("jj_amount");
                for(int j=0; j<d.getRowCount(); j++){
                    MHandIn mHandIn = d.getRow(j);

                    rIdx++;

                    tableContentRow = sheet.createRow(rIdx);
                    tableContentRow.setHeightInPoints(20.0F);

                    tableContentCell = tableContentRow.createCell(0);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getDeptName());

                    tableContentCell = tableContentRow.createCell(1);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getErpType());

                    tableContentCell = tableContentRow.createCell(2);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getTypeName());

                    tableContentCell = tableContentRow.createCell(3);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    String matCode = mHandIn.getMatCode();
                    tableContentCell.setCellValue("X".equals(matCode.substring(0, 1)) ? matCode.substring(1) : matCode);

                    tableContentCell = tableContentRow.createCell(4);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getMatName());

                    tableContentCell = tableContentRow.createCell(5);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getMatUnit());

                    tableContentCell = tableContentRow.createCell(6);
                    tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getMatPrice().doubleValue());

                    tableContentCell = tableContentRow.createCell(7);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(XDate.dateTo10(mHandIn.getLyDate()));

                    tableContentCell = tableContentRow.createCell(8);
                    tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getLyAmount().doubleValue());

                    tableContentCell = tableContentRow.createCell(9);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getOldRate().doubleValue()*100 + "%");

                    tableContentCell = tableContentRow.createCell(10);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(XDate.dateTo10(mHandIn.getJjDate()));

                    tableContentCell = tableContentRow.createCell(11);
                    tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getJjAmount().doubleValue());

                    tableContentCell = tableContentRow.createCell(12);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getEmpName());

                    tableContentCell = tableContentRow.createCell(13);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getRemark());

                    tableContentCell = tableContentRow.createCell(14);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(mHandIn.getLyAmount().doubleValue() - sjjAmount);

                    if(j == d.getRowCount()-1){
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 0, 0));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 1, 1));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 2, 2));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 3, 3));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 4, 4));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 5, 5));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 6, 6));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 7, 7));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 8, 8));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 9, 9));
                        sheet.addMergedRegion(new CellRangeAddress(sIdx, rIdx, 14, 14));

                        sIdx = rIdx+1;
                    }
                }
            }

            ServletOutputStream os = null;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("交旧明细".getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
