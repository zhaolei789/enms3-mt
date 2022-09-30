package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.cost.model.MNormFee;
import cn.ewsd.cost.service.MNormFeeService;
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
import java.util.List;
import java.util.Map;

/**
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/matAssItem")
public class matAssItemController extends CostBaseController {

    @Autowired
    private MNormFeeService mNormFeeService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);
        request.setAttribute("currentMonth", Integer.parseInt(XDate.getMon()));

        return "cost/matAssItem/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String yearQry = request.getParameter("yearQry");
        String monQry = request.getParameter("monQry");
        String monthQry = yearQry + monQry;
        String beginDate = XDate.addMonth(monthQry, -1) + "26";
        String endDate = monthQry + "25";

        try{
            return mNormFeeService.getMatAssItemList(monthQry, beginDate, endDate);
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
            String monthQry = yearQry + monQry;
            String assType = "m.assType.4";

            List<MNormFee> list = mNormFeeService.getMatAssDcList(monthQry, assType);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("地材考核汇总");
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

            HSSFRow tableHeaderRow = sheet.createRow(0);
            tableHeaderRow.setHeightInPoints(30.0F);
            HSSFCell tableHederCell = null;
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("施工单位");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("指标项目");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单位");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单价");
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("混凝土用量");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("地坪定额");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("喷浆定额");
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("定额费用");
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("上月结存");
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("当月到货");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("本月考核");
            tableHederCell = tableHeaderRow.createCell(11);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("本月结存");
            tableHederCell = tableHeaderRow.createCell(12);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("节+超-额");

            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFRow tableContentRow = null;
            HSSFCell tableContentCell = null;

            for(int i=0; i<list.size(); i++){
                MNormFee mNormFee = list.get(i);

                tableContentRow = sheet.createRow(i + 1);
                tableContentRow.setHeightInPoints(20.0F);

                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getTeamName());
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getItemName());
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getItemUnit());
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getAssPriceText());
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                String occDigText = mNormFee.getOccDigText();
                String text = "";
                if(occDigText!=null){
                    text = occDigText.replace("@|$|@", "");
                }
                tableContentCell.setCellValue(text);
                tableContentCell = tableContentRow.createCell(5);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getAddBalaText());
                tableContentCell = tableContentRow.createCell(6);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getNormBalaAssText());
                tableContentCell = tableContentRow.createCell(7);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getNormBalaText());
                tableContentCell = tableContentRow.createCell(8);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getLastStockText());
                tableContentCell = tableContentRow.createCell(9);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getInBalaText());
                tableContentCell = tableContentRow.createCell(10);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getOccBalaText());
                tableContentCell = tableContentRow.createCell(11);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getStockBalaText());
                tableContentCell = tableContentRow.createCell(12);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mNormFee.getDiffBalaText());
            }

            for(int i=0; i<list.size(); i++){
                MNormFee mNormFee = list.get(i);
                int rowspan = mNormFee.getRowSpan();
                int cnt = mNormFee.getCnt();
                if(rowspan != 0){
                    sheet.addMergedRegion(new CellRangeAddress(i+1, i+rowspan, 0, 0));
                }
                if(cnt != 0){
                    sheet.addMergedRegion(new CellRangeAddress(i+1, i+cnt, 4, 4));
                }
                if("合计".equals(mNormFee.getTeamName())){
                    sheet.addMergedRegion(new CellRangeAddress(i+1, i+1, 0, 2));
                }
            }

            ServletOutputStream os = null;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("地材考核汇总".getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
