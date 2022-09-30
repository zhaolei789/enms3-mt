package cn.ewsd.cost.controller;

import cn.ewsd.base.utils.*;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.mapper.MRationMapper;
import cn.ewsd.cost.model.MNormFee;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.cost.model.MRation;
import cn.ewsd.cost.model.MWork;
import cn.ewsd.cost.service.MOutAssessService;
import cn.ewsd.cost.service.MRationService;
import cn.ewsd.cost.service.MWorkService;
import cn.ewsd.system.service.ConfigService;
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
 * 工程结算
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-03 08:39:36
 */
@Controller
@RequestMapping("/cost/rationAnal")
public class RationAnalController extends CostBaseController {

    @Autowired
    private MWorkService mWorkService;
    @Autowired
    private MRationService mRationService;

    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        String nowYear = XDate.getYear();
        String befOne = String.valueOf(Integer.parseInt(nowYear) - 1);
        String befTwo = String.valueOf(Integer.parseInt(nowYear) - 2);
        request.setAttribute("nowYear", nowYear);
        request.setAttribute("befOne", befOne);
        request.setAttribute("befTwo", befTwo);

        List<MWork> list = mWorkService.getRationAnalWorkList();
        DataCantainer<MWork> dc = new DataCantainer<>((ArrayList)list);
        String nowOut = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", nowYear).getSum("occ_saleout"), 4));
        String nowDig = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", nowYear).getSum("plan_dig"), 2));
        String nowRaw = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", nowYear).getSum("occ_rawout"), 2));
        String oneOut = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", befOne).getSum("occ_saleout"), 4));
        String oneDig = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", befOne).getSum("plan_dig"), 2));
        String oneRaw = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", befOne).getSum("occ_rawout"), 2));
        String twoOut = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", befTwo).getSum("occ_saleout"), 4));
        String twoDig = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", befTwo).getSum("plan_dig"), 2));
        String twoRaw = Data.normalToFinal(Data.trimDoubleNo0(dc.findDataCantainer("work_month", befTwo).getSum("occ_rawout"), 2));
        request.setAttribute("nowOut", nowOut);
        request.setAttribute("nowDig", nowDig);
        request.setAttribute("nowRaw", nowRaw);
        request.setAttribute("oneOut", oneOut);
        request.setAttribute("oneDig", oneDig);
        request.setAttribute("oneRaw", oneRaw);
        request.setAttribute("twoOut", twoOut);
        request.setAttribute("twoDig", twoDig);
        request.setAttribute("twoRaw", twoRaw);

        return "cost/rationAnal/index";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getList() {
        String rationQry = request.getParameter("rationQry");

        try{
            return mRationService.getRationAnalList(rationQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletResponse response) {
        try{
            String rationQry = request.getParameter("rationQry");

            List<MRation> list = mRationService.getRationAnalList(rationQry);

            String nowYear = XDate.getYear();
            String befOne = String.valueOf(Integer.parseInt(nowYear) - 1);
            String befTwo = String.valueOf(Integer.parseInt(nowYear) - 2);

            List<MWork> workList = mWorkService.getRationAnalWorkList();
            DataCantainer<MWork> workDc = new DataCantainer<>((ArrayList)workList);
            String nowOut = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", nowYear).getSum("occ_saleout"), 4));
            String nowDig = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", nowYear).getSum("plan_dig"), 2));
            String nowRaw = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", nowYear).getSum("occ_rawout"), 2));
            String oneOut = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", befOne).getSum("occ_saleout"), 4));
            String oneDig = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", befOne).getSum("plan_dig"), 2));
            String oneRaw = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", befOne).getSum("occ_rawout"), 2));
            String twoOut = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", befTwo).getSum("occ_saleout"), 4));
            String twoDig = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", befTwo).getSum("plan_dig"), 2));
            String twoRaw = Data.normalToFinal(Data.trimDoubleNo0(workDc.findDataCantainer("work_month", befTwo).getSum("occ_rawout"), 2));

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("单耗统计分析");
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
            tableHederCell.setCellValue("单耗项目名称");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("计量单位");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("关联分母");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue(nowYear);
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue(befOne);
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(11);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue(befTwo);
            tableHederCell = tableHeaderRow.createCell(12);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(13);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(14);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");

            tableHeaderRow = sheet.createRow(1);
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("商品煤：" + nowOut + "万吨    进尺：" + nowDig + "米");
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("原煤：" + nowRaw + "万吨");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("商品煤：" + oneOut + "万吨    进尺：" + oneDig + "米");
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("原煤：" + oneRaw + "万吨");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(11);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("商品煤：" + twoOut + "万吨    进尺：" + twoDig + "米");
            tableHederCell = tableHeaderRow.createCell(12);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(13);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("原煤：" + twoRaw + "万吨");
            tableHederCell = tableHeaderRow.createCell(14);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");

            tableHeaderRow = sheet.createRow(2);
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            tableHederCell = tableHeaderRow.createCell(11);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(12);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            tableHederCell = tableHeaderRow.createCell(13);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(14);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");

            sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 6));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 10));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 14));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 10));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 12));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 13, 14));

            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFRow tableContentRow = null;
            HSSFCell tableContentCell = null;

            for(int i=0; i<list.size(); i++){
                MRation mRation = list.get(i);

                tableContentRow = sheet.createRow(i + 3);
                tableContentRow.setHeightInPoints(20.0F);

                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getRationName());
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getRationUnit());
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getDenomName());
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getNowAmount());
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getNowBala());
                tableContentCell = tableContentRow.createCell(5);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getNowRawAmount());
                tableContentCell = tableContentRow.createCell(6);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getNowRawBala());
                tableContentCell = tableContentRow.createCell(7);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getOneAmount());
                tableContentCell = tableContentRow.createCell(8);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getOneBala());
                tableContentCell = tableContentRow.createCell(9);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getOneRawAmount());
                tableContentCell = tableContentRow.createCell(10);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getOneRawBala());
                tableContentCell = tableContentRow.createCell(11);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getTwoAmount());
                tableContentCell = tableContentRow.createCell(12);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getTwoBala());
                tableContentCell = tableContentRow.createCell(13);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getTwoRawAmount());
                tableContentCell = tableContentRow.createCell(14);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mRation.getTwoRawBala());

            }

            ServletOutputStream os = null;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("单耗统计分析".getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
