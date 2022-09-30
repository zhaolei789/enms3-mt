package cn.ewsd.base.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class PoiUtils {
    public PoiUtils() {
    }

    public static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontName("宋体");
        headerFont.setFontHeightInPoints((short) 20);
        headerFont.setBoldweight((short) 700);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment((short) 2);
        headerStyle.setVerticalAlignment((short) 1);
        headerStyle.setLocked(true);
        return headerStyle;
    }

    public static HSSFCellStyle getDateStyle(HSSFWorkbook workbook) {
        HSSFFont dateFont = workbook.createFont();
        dateFont.setFontName("宋体");
        dateFont.setFontHeightInPoints((short) 12);
        HSSFCellStyle dateStype = workbook.createCellStyle();
        dateStype.setFont(dateFont);
        dateStype.setAlignment((short) 1);
        dateStype.setVerticalAlignment((short) 1);
        dateStype.setLocked(true);
        return dateStype;
    }

    public static HSSFCellStyle getColumnStyle(HSSFWorkbook workbook) {
        HSSFFont columnFont = workbook.createFont();
        columnFont.setFontName("宋体");
        columnFont.setColor((short) 30);
        columnFont.setFontHeightInPoints((short) 12);
        columnFont.setBoldweight((short) 700);
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setFont(columnFont);
        columnStyle.setBorderBottom((short) 1);
        columnStyle.setBorderLeft((short) 1);
        columnStyle.setBorderTop((short) 1);
        columnStyle.setBorderRight((short) 1);
        columnStyle.setAlignment((short) 2);
        columnStyle.setVerticalAlignment((short) 1);
        columnStyle.setFillBackgroundColor((short) 49);
        columnStyle.setWrapText(true);
        columnStyle.setLocked(true);
        return columnStyle;
    }

    public static HSSFCellStyle getCommonStyle(HSSFWorkbook workbook) {
        HSSFFont commonFont = workbook.createFont();
        commonFont.setFontName("宋体");
        commonFont.setFontHeightInPoints((short) 12);
        HSSFCellStyle commonStyle = workbook.createCellStyle();
        commonStyle.setBorderBottom((short) 1);
        commonStyle.setBorderLeft((short) 1);
        commonStyle.setBorderTop((short) 1);
        commonStyle.setBorderRight((short) 1);
        commonStyle.setFont(commonFont);
        commonStyle.setAlignment((short) 2);
        commonStyle.setWrapText(true);
        commonStyle.setVerticalAlignment((short) 1);
        return commonStyle;

    }

    public static HSSFCellStyle getCommonStyle(HSSFWorkbook workbook, HSSFFont hssfFont, HSSFCellStyle hssfCellStyle) {
//        HSSFFont commonFont = workbook.createFont();
        hssfFont.setFontName("宋体");
        hssfFont.setFontHeightInPoints((short) 12);
//        HSSFCellStyle commonStyle = workbook.createCellStyle();
        hssfCellStyle.setBorderBottom((short) 1);
        hssfCellStyle.setBorderLeft((short) 1);
        hssfCellStyle.setBorderTop((short) 1);
        hssfCellStyle.setBorderRight((short) 1);
        hssfCellStyle.setFont(hssfFont);
        hssfCellStyle.setAlignment((short) 2);
        hssfCellStyle.setWrapText(true);
        hssfCellStyle.setVerticalAlignment((short) 1);
        return hssfCellStyle;
    }

    public static void setColumnWidth(HSSFSheet sheet, String[] columnWidthParam) {
        for (int i = 0; i < columnWidthParam.length; ++i) {
            String[] params = columnWidthParam[i].split(",");
            Integer column = Integer.parseInt(params[0]);
            Integer width = Integer.parseInt(params[1]);
            sheet.setColumnWidth(column, width);
        }

        sheet.setDefaultRowHeight((short) 360);
    }

    public static <E> void exportExcel(HttpServletResponse response, String excelName, String colNameStr, String fieldNameStr, List<E> list, String userName) {
        String[] header = colNameStr.split(",");
        String[] fieldNames = fieldNameStr.split(",");
        String date = "导出人: " + userName + "   导出日期：" + DateUtils.format(new Date(),"yyyy-MM-dd");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(excelName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize((short) 9);
        sheet.setColumnWidth(0, 1400);

        for (int i = 1; i <= fieldNames.length; ++i) {
            sheet.setColumnWidth(i, 5000);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fieldNames.length));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(50.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(getHeaderStyle(workbook));
        cell.setCellValue(excelName);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, fieldNames.length));
        HSSFRow dateRow = sheet.createRow(1);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(getDateStyle(workbook));
        cell1.setCellValue(date);

        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        HSSFRow tableHeaderRow = sheet.createRow(2);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = null;
        tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellType(1);
        tableHederCell.setCellStyle(getColumnStyle(workbook));
        tableHederCell.setCellValue("序号");

        for (int i = 0; i < header.length; ++i) {
            tableHederCell = tableHeaderRow.createCell(i + 1);
            tableHederCell.setCellType(1);
            tableHederCell.setCellStyle(getColumnStyle(workbook));
            tableHederCell.setCellValue(header[i]);
        }
        HSSFRow tableHeaderFieldRow = sheet.createRow(3);
        tableHeaderFieldRow.setHeightInPoints(30.0F);
        HSSFCell tableHeaderField = null;
        tableHeaderField = tableHeaderFieldRow.createCell(0);
        tableHeaderField.setCellType(1);
        tableHeaderField.setCellStyle(getColumnStyle(workbook));
        tableHeaderField.setCellValue("0");

        for (int j = 0; j < fieldNames.length; ++j) {
            tableHeaderField = tableHeaderFieldRow.createCell(j + 1);
            tableHeaderField.setCellType(1);
            tableHeaderField.setCellStyle(getColumnStyle(workbook));
            tableHeaderField.setCellValue(fieldNames[j]);
        }


        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;

        try {
            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            for (int i = 0; i < list.size(); ++i) {

                tableContentRow = sheet.createRow(i + 4);
                tableContentRow.setHeightInPoints(20.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((double) (i + 1));
                E o = list.get(i);
                if (o != null) {
                    Class cls = o.getClass();
                    aaa:
                    for (int j = 0; j < fieldNames.length; ++j) {
                        String fieldName = fieldNames[j].substring(0, 1).toUpperCase() + fieldNames[j].substring(1);
                        Method getMethod = null;
                        //数据库有字段实体类没有字段处理
                        try {
                            getMethod = cls.getMethod("get" + fieldName);

                        } catch (NoSuchMethodException var45) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            continue aaa;
                        }
                        Object value = getMethod.invoke(o);
                        if (getMethod.getReturnType().getName().contains("Date")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value != null && !"".equals(value)) {
                                value = sdf.format(value);

                            }
                        }
                        if (value != null && !"".equals(value)) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(value.toString());
                        } else {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        }
                    }
                }
            }
        } catch (IllegalArgumentException var41) {
            var41.printStackTrace();
        } catch (IllegalAccessException var42) {
            var42.printStackTrace();
        } catch (InvocationTargetException var43) {
            var43.printStackTrace();
        } catch (SecurityException var44) {
            var44.printStackTrace();
        }

        ServletOutputStream os = null;

        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("gb2312"), "iso8859-1") + ".xls");
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
     * 导出Excel数据,不带英文字段名
     * 20210409
     * @param response
     * @param excelName
     * @param colNameStr
     * @param fieldNameStr
     * @param list
     * @param userName
     * @param <E>
     */
    public static <E> void exportExcel2(HttpServletResponse response, String excelName, String colNameStr, String fieldNameStr, List<E> list, String userName) {
        String[] header = colNameStr.split(",");
        String[] fieldNames = fieldNameStr.split(",");
        String date = "导出人: " + userName + "   导出日期：" + DateUtils.format(new Date(),"yyyy-MM-dd");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(excelName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize((short) 9);
        sheet.setColumnWidth(0, 1400);

        for (int i = 1; i <= fieldNames.length; ++i) {
            sheet.setColumnWidth(i, 5000);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fieldNames.length));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(50.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(getHeaderStyle(workbook));
        cell.setCellValue(excelName);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, fieldNames.length));
        HSSFRow dateRow = sheet.createRow(1);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(getDateStyle(workbook));
        cell1.setCellValue(date);

        HSSFRow tableHeaderRow = sheet.createRow(2);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellType(1);
        tableHederCell.setCellStyle(getColumnStyle(workbook));
        tableHederCell.setCellValue("No");

        for (int i = 0; i < header.length; ++i) {
            tableHederCell = tableHeaderRow.createCell(i + 1);
            tableHederCell.setCellType(1);
            tableHederCell.setCellStyle(getColumnStyle(workbook));
            tableHederCell.setCellValue(header[i]);
        }
        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;

        try {
            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            for (int i = 0; i < list.size(); ++i) {

                tableContentRow = sheet.createRow(i + 3);
                tableContentRow.setHeightInPoints(20.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                tableContentCell.setCellValue((double) (i + 1));
                E o = list.get(i);
                if (o != null) {
                    Class cls = o.getClass();
                    aaa:
                    for (int j = 0; j < fieldNames.length; ++j) {
                        String fieldName = fieldNames[j].substring(0, 1).toUpperCase() + fieldNames[j].substring(1);
                        Method getMethod = null;
                        //数据库有字段实体类没有字段处理
                        try {
                            getMethod = cls.getMethod("get" + fieldName);

                        } catch (NoSuchMethodException var45) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            continue aaa;
                        }
                        Object value = getMethod.invoke(o);
                        if (getMethod.getReturnType().getName().contains("Date")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value != null && !"".equals(value)) {
                                value = sdf.format(value);

                            }
                        }
                        if (value != null && !"".equals(value)) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(value.toString());
                        } else {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        }
                    }
                }
            }
        } catch (IllegalArgumentException var41) {
            var41.printStackTrace();
        } catch (IllegalAccessException var42) {
            var42.printStackTrace();
        } catch (InvocationTargetException var43) {
            var43.printStackTrace();
        } catch (SecurityException var44) {
            var44.printStackTrace();
        }

        ServletOutputStream os = null;

        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("gb2312"), "iso8859-1") + ".xls");
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

    //导出模板
    public static <E> void exportExcelTemplate(HttpServletResponse response, String excelName, String colNameStr, String fieldNameStr, List<E> list, String userName) {
        String[] header = colNameStr.split(",");
        String[] fieldNames = fieldNameStr.split(",");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(excelName);
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize((short) 9);

        for (int i = 1; i <= fieldNames.length; ++i) {
            sheet.setColumnWidth(i, 5000);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        HSSFRow tableHeaderRow = sheet.createRow(0);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = null;
        tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellType(1);
        tableHederCell.setCellStyle(getColumnStyle(workbook));
        tableHederCell.setCellValue("序号");

        for (int i = 0; i < header.length; ++i) {
            tableHederCell = tableHeaderRow.createCell(i + 1);
            tableHederCell.setCellType(1);
            tableHederCell.setCellStyle(getColumnStyle(workbook));
            tableHederCell.setCellValue(header[i]);
        }
        HSSFRow tableHeaderFieldRow = sheet.createRow(1);
        tableHeaderFieldRow.setHeightInPoints(30.0F);
        HSSFCell tableHeaderField = null;
        tableHeaderField = tableHeaderFieldRow.createCell(0);
        tableHeaderField.setCellType(1);
        tableHeaderField.setCellStyle(getColumnStyle(workbook));
//        tableHeaderField.setCellValue("");

        for (int j = 0; j < fieldNames.length; ++j) {
            tableHeaderField = tableHeaderFieldRow.createCell(j + 1);
            tableHeaderField.setCellType(1);
            tableHeaderField.setCellStyle(getColumnStyle(workbook));
            tableHeaderField.setCellValue(fieldNames[j]);
        }


        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;

        try {
            for (int i = 0; i < list.size(); ++i) {
                tableContentRow = sheet.createRow(i + 2);
                tableContentRow.setHeightInPoints(20.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(getCommonStyle(workbook));
                tableContentCell.setCellValue((double) (i + 1));
                E o = list.get(i);
                if (o != null) {
                    Class cls = o.getClass();

                    aaa:
                    for (int j = 0; j < fieldNames.length; ++j) {
                        String fieldName = fieldNames[j].substring(0, 1).toUpperCase() + fieldNames[j].substring(1);
                        Method getMethod = null;
                        //数据库有字段实体类没有字段处理
                        try {
                            getMethod = cls.getMethod("get" + fieldName);

                        } catch (NoSuchMethodException var45) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook));
                            continue aaa;
                        }
                        Object value = getMethod.invoke(o);
                        if (getMethod.getReturnType().getName().contains("Date")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value != null && !"".equals(value)) {
                                value = sdf.format(value);

                            }
                        }
                        if (value != null && !"".equals(value)) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook));
                            tableContentCell.setCellValue(value.toString());
                        } else {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook));
                        }
                    }
                }
            }
        } catch (IllegalArgumentException var41) {
            var41.printStackTrace();
        } catch (IllegalAccessException var42) {
            var42.printStackTrace();
        } catch (InvocationTargetException var43) {
            var43.printStackTrace();
        } catch (SecurityException var44) {
            var44.printStackTrace();
        }

        ServletOutputStream os = null;

        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("gb2312"), "iso8859-1") + ".xls");
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

    public static void exportExcelByListMap(HttpServletResponse response, String excelName, String colNameStr, String fieldNameStr, List<HashMap<String, Object>> list) {
        String[] header = colNameStr.split(",");
        String[] fieldNames = fieldNameStr.split(",");
        String date = "制表日期：" + DateUtils.format(new Date(),"yyyy-MM-dd");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(excelName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize((short) 9);
        sheet.setColumnWidth(0, 1400);

        for (int i = 1; i <= fieldNames.length; ++i) {
            sheet.setColumnWidth(i, 5000);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fieldNames.length));
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(50.0F);
        HSSFCell cell = headerRow.createCell(0);
        cell.setCellStyle(getHeaderStyle(workbook));
        cell.setCellValue(excelName);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, fieldNames.length));
        HSSFRow dateRow = sheet.createRow(1);
        dateRow.setHeightInPoints(30.0F);
        HSSFCell cell1 = dateRow.createCell(0);
        cell1.setCellStyle(getDateStyle(workbook));
        cell1.setCellValue(date);
        HSSFRow tableHeaderRow = sheet.createRow(2);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = null;
        tableHederCell = tableHeaderRow.createCell(0);
        tableHederCell.setCellType(1);
        tableHederCell.setCellStyle(getColumnStyle(workbook));
        tableHederCell.setCellValue("序号");

        for (int i = 0; i < header.length; ++i) {
            tableHederCell = tableHeaderRow.createCell(i + 1);
            tableHederCell.setCellType(1);
            tableHederCell.setCellStyle(getColumnStyle(workbook));
            tableHederCell.setCellValue(header[i]);
        }

        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;

        try {
            for (int i = 0; i < list.size(); ++i) {
                tableContentRow = sheet.createRow(i + 3);
                tableContentRow.setHeightInPoints(20.0F);
                tableContentCell = tableContentRow.createCell(0);
                tableContentCell.setCellStyle(getCommonStyle(workbook));
                tableContentCell.setCellValue((double) (i + 1));
                HashMap<String, Object> o = (HashMap) list.get(i);
                if (o != null) {
                    for (int j = 0; j < fieldNames.length; ++j) {
                        String fieldName = fieldNames[j];
                        Object value = o.get(fieldName);
                        if (value != null) {
                            tableContentCell = tableContentRow.createCell(j + 1);
                            tableContentCell.setCellStyle(getCommonStyle(workbook));
                            tableContentCell.setCellValue(value.toString());
                        }
                    }
                }
            }
        } catch (IllegalArgumentException var36) {
            var36.printStackTrace();
        } catch (SecurityException var37) {
            var37.printStackTrace();
        }

        ServletOutputStream os = null;

        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("gb2312"), "iso8859-1") + ".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os = response.getOutputStream();
            workbook.write(os);
        } catch (Exception var34) {
            var34.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException var33) {
                    var33.printStackTrace();
                }
            }

        }

    }

    public static List<Map<Integer, Object>> readExcel(HttpServletRequest request, String filePath) {
        ArrayList list = new ArrayList();

        try {
            String savePath = request.getServletContext().getRealPath("/");
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(savePath + filePath)));
            HSSFSheet sheet = wb.getSheetAt(0);
            Integer rowNum = sheet.getLastRowNum();

            for (int j = 1; j < rowNum + 1; ++j) {
                Map<Integer, Object> map = new HashMap();
                HSSFRow row = sheet.getRow(j);

                for (int i = 0; i < row.getLastCellNum(); ++i) {
                    String v = "";
                    HSSFCell cell = row.getCell(i);
                    if (cell != null) {
                        cell.setCellType(1);
                        v = cell.getRichStringCellValue().toString();
                    }

                    map.put(i, v);
                }

                list.add(map);
            }
        } catch (FileNotFoundException var13) {
            var13.printStackTrace();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

        return list;
    }



    public static <E> void exportExcelOld(HttpServletResponse response, String excelName, String colNameStr, String fieldNameStr, List<E> list) {
        String[] header = colNameStr.split(",");
        String[] fieldNames = fieldNameStr.split(",");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(excelName);
        sheet.setMargin((short) 2, sheet.getMargin((short) 2));
        sheet.setMargin((short) 3, sheet.getMargin((short) 3));
        sheet.setMargin((short) 0, sheet.getMargin((short) 0));
        sheet.setMargin((short) 1, sheet.getMargin((short) 1));
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize((short) 9);

        for (int i = 0; i <= fieldNames.length; ++i) {
            sheet.setColumnWidth(i, 5000);
        }

        HSSFRow tableHeaderRow = sheet.createRow(0);
        tableHeaderRow.setHeightInPoints(30.0F);
        HSSFCell tableHederCell = null;

        for (int i = 0; i < header.length; ++i) {
            tableHederCell = tableHeaderRow.createCell(i);
            tableHederCell.setCellType(1);
            tableHederCell.setCellStyle(getColumnStyle(workbook));
            tableHederCell.setCellValue(header[i]);
        }

        HSSFRow tableContentRow = null;
        HSSFCell tableContentCell = null;

        try {
            HSSFFont hssfFont = workbook.createFont();
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            for (int i = 0; i < list.size(); ++i) {

                tableContentRow = sheet.createRow(i + 1);
                tableContentRow.setHeightInPoints(20.0F);
                E o = list.get(i);
                if (o != null) {
                    Class cls = o.getClass();
                    aaa:
                    for (int j = 0; j < fieldNames.length; ++j) {
                        String fieldName = fieldNames[j].substring(0, 1).toUpperCase() + fieldNames[j].substring(1);
                        Method getMethod = null;
                        //数据库有字段实体类没有字段处理
                        try {
                            getMethod = cls.getMethod("get" + fieldName);

                        } catch (NoSuchMethodException var45) {
                            tableContentCell = tableContentRow.createCell(j);
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            continue aaa;
                        }
                        Object value = getMethod.invoke(o);
                        if (getMethod.getReturnType().getName().contains("Date")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (value != null && !"".equals(value)) {
                                value = sdf.format(value);

                            }
                        }
                        if (value != null && !"".equals(value)) {
                            tableContentCell = tableContentRow.createCell(j) ;
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                            tableContentCell.setCellValue(value.toString());
                        } else {
                            tableContentCell = tableContentRow.createCell(j );
                            tableContentCell.setCellStyle(getCommonStyle(workbook, hssfFont, hssfCellStyle));
                        }
                    }
                }
            }
        } catch (IllegalArgumentException var41) {
            var41.printStackTrace();
        } catch (IllegalAccessException var42) {
            var42.printStackTrace();
        } catch (InvocationTargetException var43) {
            var43.printStackTrace();
        } catch (SecurityException var44) {
            var44.printStackTrace();
        }

        ServletOutputStream os = null;

        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes("gb2312"), "iso8859-1") + ".xls");
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
