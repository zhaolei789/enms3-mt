package cn.ewsd.repository.controller;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/stockSum")
public class StockSumController extends RepositoryBaseController {
    @Autowired
    UtilService utilService;
    @Autowired
    MStoreService mStoreService;
    @Autowired
    MStockService mStockService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2732);
        List<MStore> list = mStoreService.getAllStockQryStore("r.storeLevel.1", ifPower);
        String storeNoQry = "";
        if(list.size() > 0){
            storeNoQry = list.get(0).getStoreNo();
        }
        request.setAttribute("storeNoQry", storeNoQry);

        String storeStr = "";
        for(int i=0; i<list.size(); i++){
            MStore mStore = list.get(i);
            storeStr += (i==0 ? "" : ",") + mStore.getStoreNo() + "_" + mStore.getStoreName();
        }
        request.setAttribute("storeStr", storeStr);

        return "repository/stockSum/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String xjFlagQry = request.getParameter("xjFlagQry");
        boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2732);

        try {
            String filterSort = "";
            filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
            return mStockService.getStockSumPageSet(pageParam, filterSort, matCodeQry, matNameQry, xjFlagQry, ifPower);
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
            String matCodeQry = request.getParameter("matCodeQry");
            String matNameQry = request.getParameter("matNameQry");
            String xjFlagQry = request.getParameter("xjFlagQry");
            boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2732);

            List<MStore> storeList = mStoreService.getAllStockQryStore("r.storeLevel.1", ifPower);
            List<HashMap<String, Object>> list = mStockService.getStockSumList(matCodeQry, matNameQry, xjFlagQry, ifPower);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("库存汇总统计");
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
            for(int i=0; i<storeList.size(); i++){
                sheet.setColumnWidth(6+i*2, 5000);
                sheet.setColumnWidth(7+i*2, 5000);
            }

            HSSFRow tableHeaderRow = sheet.createRow(0);
            tableHeaderRow.setHeightInPoints(30.0F);
            HSSFCell tableHederCell = null;
            tableHederCell = tableHeaderRow.createCell(0);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("物资编码");
            tableHederCell = tableHeaderRow.createCell(1);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("物资描述");
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
            tableHederCell.setCellValue("合计");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            for(int i=0; i<storeList.size(); i++){
                tableHederCell = tableHeaderRow.createCell(6+i*2);
                tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
                tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
                tableHederCell.setCellValue(storeList.get(i).getStoreName());
                tableHederCell = tableHeaderRow.createCell(7+i*2);
                tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            }

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
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("数量");
            tableHederCell = tableHeaderRow.createCell(5);
            tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
            tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
            tableHederCell.setCellValue("金额");
            for(int i=0; i<storeList.size(); i++){
                tableHederCell = tableHeaderRow.createCell(6+i*2);
                tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
                tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
                tableHederCell.setCellValue("数量");
                tableHederCell = tableHeaderRow.createCell(7+i*2);
                tableHederCell.setCellType(Cell.CELL_TYPE_STRING);
                tableHederCell.setCellStyle(PoiUtils.getColumnStyle(workbook));
                tableHederCell.setCellValue("金额");
            }

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
            for(int i=0; i<storeList.size(); i++){
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 6+i*2, 7+i*2));
            }

            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            HSSFRow tableContentRow = null;
            HSSFCell tableContentCell = null;

            for(int i=0; i<list.size(); i++){
                HashMap<String, Object> map = list.get(i);

                tableContentRow = sheet.createRow(i + 2);
                tableContentRow.setHeightInPoints(20.0F);

                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((String)map.get("mat_ode"));
                tableContentCell = tableContentRow.createCell(1);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((String)map.get("mat_name"));
                tableContentCell = tableContentRow.createCell(2);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((String)map.get("mat_unit"));
                tableContentCell = tableContentRow.createCell(3);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((String)map.get("mat_price"));
                tableContentCell = tableContentRow.createCell(4);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue(String.valueOf(map.get("sumAmount")));
                tableContentCell = tableContentRow.createCell(5);
                tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((String)map.get("sumBala"));
                for(int j=0; j<storeList.size(); j++){
                    String storeNo = storeList.get(j).getStoreNo();
                    tableContentCell = tableContentRow.createCell(6+j*2);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue(String.valueOf(map.get(storeNo+"Amt")));
                    tableContentCell = tableContentRow.createCell(7+j*2);
                    tableContentCell.setCellType(Cell.CELL_TYPE_STRING);
                    tableContentCell.setCellStyle(PoiUtils.getCommonStyle(workbook, hssfFont, hssfCellStyle));
                    tableContentCell.setCellValue((String)map.get(storeNo+"Bala"));
                }
            }

            ServletOutputStream os = null;
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存汇总统计".getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object getDetail(PageParam pageParam) {
        String storeNoQry = request.getParameter("storeNoQry");
        String matCodeQry = request.getParameter("matCodeQry");
        String matNameQry = request.getParameter("matNameQry");
        String xjFlagQry = request.getParameter("xjFlagQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        try {
            return mStockService.getStockSumDetailPageSet(pageParam, filterSort, matCodeQry, matNameQry, xjFlagQry, storeNoQry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
