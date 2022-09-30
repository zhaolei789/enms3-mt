package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MStock;
import cn.ewsd.material.service.MStockService;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MStoreService;
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
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/inOutStock")
public class InOutStockController extends RepositoryBaseController {
    @Autowired
    UtilService utilService;
    @Autowired
    MStoreService mStoreService;
    @Autowired
    MStockService mStockService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2726);
        List<MStore> list = mStoreService.getPowerStore(ifPower, LoginInfo.getUuid());
        String storeQry = "";
        if(list.size() > 0){
            storeQry = list.get(0).getStoreNo();
        }
        request.setAttribute("storeQry", storeQry);
        request.setAttribute("beginDateQry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("endDateQry", XDate.dateTo10(XDate.getDate()));

        return "repository/inOutStock/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
        String matNameQry = request.getParameter("matNameQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String storeQry = request.getParameter("storeQry");

        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2726);

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            PageSet<MStock> pageSet = mStockService.getInOutStockPageSet(pageParam, filterSort, LoginInfo.getUuid(), storeQry, ifPower, beginDateQry, endDateQry, matCodeQry, matNameQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getSumInfo")
    public Object getSumInfo() {
        String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
        String matNameQry = request.getParameter("matNameQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String storeQry = request.getParameter("storeQry");

        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2726);

        try {
            List<MStock> list = mStockService.getInOutStockList(LoginInfo.getUuid(), storeQry, ifPower, beginDateQry, endDateQry, matCodeQry, matNameQry);
            DataCantainer<MStock> stockDc = new DataCantainer<>((ArrayList)list);

            MStock mStock = new MStock();
            mStock.setQqBalaText(Data.normalToFinal(stockDc.getSum("qq_bala")));
            mStock.setBqrBalaText(Data.normalToFinal(stockDc.getSum("bqr_bala")));
            mStock.setBqcBalaText(Data.normalToFinal(stockDc.getSum("bqc_bala")));
            mStock.setJyBalaText(Data.normalToFinal(stockDc.getSum("jy_bala")));

            return mStock;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String beginDateQry = XDate.dateTo8(request.getParameter("beginDateQry"));
            String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
            String matNameQry = request.getParameter("matNameQry");
            String matCodeQry = request.getParameter("matCodeQry");
            String storeQry = request.getParameter("storeQry");

            boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2726);

            List<MStock> list = mStockService.getInOutStockList(LoginInfo.getUuid(), storeQry, ifPower, beginDateQry, endDateQry, matCodeQry, matNameQry);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("仓库进出存表");
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
            tableHederCell.setCellValue("物料分类");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("分类描述");
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("物料编码");
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("物料描述");
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("单位");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("期初库存");
            tableHederCell = tableHeaderRow.createCell(6);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(7);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("本期收入");
            tableHederCell = tableHeaderRow.createCell(8);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(9);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("本期支出");
            tableHederCell = tableHeaderRow.createCell(10);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(11);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("期末结存");
            tableHederCell = tableHeaderRow.createCell(12);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));

            tableHeaderRow = sheet.createRow(1);
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(2);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(3);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell = tableHeaderRow.createCell(4);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
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

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));

            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFRow tableContentRow = null;
            HSSFCell tableContentCell = null;
            for(int i=0; i<list.size(); i++){
                MStock mStock = list.get(i);

                tableContentRow = sheet.createRow(i + 2);
                tableContentRow.setHeightInPoints(20.0F);

                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getErpType());
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getTypeName());
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                String matCode = mStock.getMatCode();
                tableContentCell.setCellValue("X".equals(matCode.substring(0, 1)) ? matCode.substring(1) : matCode);
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getMatName());
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getMatUnit());
                tableContentCell = tableContentRow.createCell(5);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getQqAmount().doubleValue());
                tableContentCell = tableContentRow.createCell(6);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getQqBala().doubleValue());
                tableContentCell = tableContentRow.createCell(7);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getBqrAmount().doubleValue());
                tableContentCell = tableContentRow.createCell(8);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getBqrBala().doubleValue());
                tableContentCell = tableContentRow.createCell(9);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getBqcAmount().doubleValue());
                tableContentCell = tableContentRow.createCell(10);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getBqcBala().doubleValue());
                tableContentCell = tableContentRow.createCell(11);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getJyAmount().doubleValue());
                tableContentCell = tableContentRow.createCell(12);
                tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(mStock.getJyBala().doubleValue());
            }

            DataCantainer<MStock> stockDc = new DataCantainer<>((ArrayList)list);

            tableContentRow = sheet.createRow(list.size() + 2);
            tableContentRow.setHeightInPoints(20.0F);

            tableContentCell = tableContentRow.createCell(0);
            tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue("合计");
            tableContentCell = tableContentRow.createCell(1);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(2);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(3);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(4);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(5);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(6);
            tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(stockDc.getSum("qq_bala"));
            tableContentCell = tableContentRow.createCell(7);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(8);
            tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(stockDc.getSum("bqr_bala"));
            tableContentCell = tableContentRow.createCell(9);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(10);
            tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(stockDc.getSum("bqc_bala"));
            tableContentCell = tableContentRow.createCell(11);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell = tableContentRow.createCell(12);
            tableContentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
            tableContentCell.setCellValue(stockDc.getSum("jy_bala"));

            ServletOutputStream os = null;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("仓库进出存表".getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
