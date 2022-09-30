package cn.ewsd.cost.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelStyle {
    //excel样式
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
    public static HSSFCellStyle getHeaderStyle2(HSSFWorkbook workbook) {
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontName("宋体");
        headerFont.setFontHeightInPoints((short) 15);
        headerFont.setBoldweight((short) 400);
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
    public static HSSFCellStyle getDateStyleRight(HSSFWorkbook workbook) {
        HSSFFont dateFont = workbook.createFont();
        dateFont.setFontName("宋体");
        dateFont.setFontHeightInPoints((short) 12);
        HSSFCellStyle dateStype = workbook.createCellStyle();
        dateStype.setFont(dateFont);
        dateStype.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        dateStype.setVerticalAlignment((short) 1);
        dateStype.setLocked(true);
        return dateStype;
    }
    public static HSSFCellStyle getDateStyleCenter(HSSFWorkbook workbook) {
        HSSFFont dateFont = workbook.createFont();
        dateFont.setFontName("宋体");
        dateFont.setFontHeightInPoints((short) 12);
        HSSFCellStyle dateStype = workbook.createCellStyle();
        dateStype.setFont(dateFont);
        dateStype.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dateStype.setVerticalAlignment((short) 1);
        dateStype.setLocked(true);
        return dateStype;
    }
    public static HSSFCellStyle getDateStyleCenter_B(HSSFWorkbook workbook) {
        HSSFFont dateFont = workbook.createFont();
        dateFont.setFontName("宋体");
        dateFont.setFontHeightInPoints((short) 12);
        dateFont.setBoldweight((short) 700);
        HSSFCellStyle dateStype = workbook.createCellStyle();
        dateStype.setFont(dateFont);
        dateStype.setAlignment(HSSFCellStyle.ALIGN_CENTER);
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


    public static HSSFCellStyle getCommonStyleLeft(HSSFWorkbook workbook, HSSFFont hssfFont, HSSFCellStyle hssfCellStyle) {
//        HSSFFont commonFont = workbook.createFont();
        hssfFont.setFontName("宋体");
        hssfFont.setFontHeightInPoints((short) 12);
//        HSSFCellStyle commonStyle = workbook.createCellStyle();
        hssfCellStyle.setBorderBottom((short) 1);
        hssfCellStyle.setBorderLeft((short) 1);
        hssfCellStyle.setBorderTop((short) 1);
        hssfCellStyle.setBorderRight((short) 1);
        hssfCellStyle.setFont(hssfFont);
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_FILL);
        hssfCellStyle.setWrapText(true);
        hssfCellStyle.setVerticalAlignment((short) 1);
        return hssfCellStyle;
    }

    public static HSSFCellStyle getCommonStyleRight(HSSFWorkbook workbook, HSSFFont hssfFont, HSSFCellStyle hssfCellStyle) {
//        HSSFFont commonFont = workbook.createFont();
        hssfFont.setFontName("宋体");
        hssfFont.setFontHeightInPoints((short) 12);
//        HSSFCellStyle commonStyle = workbook.createCellStyle();
        hssfCellStyle.setBorderBottom((short) 1);
        hssfCellStyle.setBorderLeft((short) 1);
        hssfCellStyle.setBorderTop((short) 1);
        hssfCellStyle.setBorderRight((short) 1);
        hssfCellStyle.setFont(hssfFont);
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        hssfCellStyle.setWrapText(true);
        hssfCellStyle.setVerticalAlignment((short) 1);
        return hssfCellStyle;
    }
}
