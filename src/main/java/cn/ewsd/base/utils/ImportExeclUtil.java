package cn.ewsd.base.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * excel读取工具类
 *
 * @author daochuwenziyao
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class
ImportExeclUtil
{

    /**
     * <p>
     * 导入Excel表格E <br>
     * </p>
     *
     * @param templetFile	文件
     * @param startrow		开始行号
     * @param startcol		开始列号
     * @param sheetnum 默认是0
     * @return
     */
    public static List<Map<String,String>> readExcel(MultipartFile templetFile, int startrow, int startcol, int sheetnum){
        List<Map<String,String>> varList = new ArrayList<Map<String,String>>();
        if(templetFile!=null&&templetFile.getSize()>0){
            String ofn=templetFile.getOriginalFilename();// 文件名
            String extName = ""; // 扩展名格式：
            if (ofn.lastIndexOf(".") >= 0){
                extName = ofn.substring(ofn.lastIndexOf("."));
            }
            if(".xls".equals(extName.toLowerCase())){
                varList=readExcelByXls(templetFile,startrow,startcol,sheetnum);
            }else if(".xlsx".equals(extName.toLowerCase())){
                varList=readExcelByXlsx(templetFile,startrow,startcol,sheetnum);
            }
        }
        return varList;
    }

    /**
     * 操作Excel2003以前（包括2003）的版本,扩展名是.xls
     * @param templetFile 文件
     * @param startrow 开始行号
     * @param startcol 开始列号
     * @param sheetnum sheet默认是0
     * @return list
     */
    public static List<Map<String, String>> readExcelByXls(MultipartFile templetFile, int startrow, int startcol, int sheetnum) {
        List<Map<String, String>> varList = new ArrayList<Map<String, String>>();
        try {
            HSSFWorkbook wb = new HSSFWorkbook(templetFile.getInputStream());
            HSSFSheet sheet = wb.getSheetAt(sheetnum); // sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号
            //标记字段
            String field ="";
            //字段是否处理
            boolean fieldProcessing =false;
            String[] fieldArr =null;
            for (int i = startrow; i < rowNum; i++) { // 行循环开始
                Map<String, String> varpd = new HashMap<String, String>();
                HSSFRow row = sheet.getRow(i); // 行
                int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置
                for (int j = startcol; j < cellNum; j++) { // 列循环开始
                    HSSFCell cell = row.getCell(Integer.parseInt(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                       // System.out.println("cellType--->"+cell.getCellType());
                        switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case 0:
//                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
//                                } else {
                                    cell.setCellType(1);
                                    cellValue = cell.getStringCellValue();
//                                }
                                break;
                            case 1:
                                cellValue = cell.getStringCellValue();
                                break;
                            case 2:
                                // cell.setCellType(1);
                                // cellValue = cell.getStringCellValue();
                                cellValue = cell.getNumericCellValue() + "";
                                //cellValue = String.valueOf(cell.getDateCellValue());
                                break;
                            case 3:
                                cellValue = "";
                                break;
                            case 4:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 5:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }
                    if (i == startrow){
                        varpd.put(cellValue, cellValue);
                        field +=cellValue+",";
                    }else {
                        if (fieldProcessing == false){
                            fieldArr = field.split(",");
                            fieldProcessing =true;
                        }
                        //System.out.println(fieldArr[j - startcol]+"------"+cellValue+"------"+cell.getCellType());
                        varpd.put(fieldArr[j-startcol], cellValue);
                    }
                }
                varList.add(varpd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return varList;
    }

    /**
     * 是操作Excel2007的版本，扩展名是.xlsx
     * @param templetFile 文件
     * @param startrow 开始行号
     * @param startcol 开始列号
     * @param sheetnum sheet
     * @return list
     */
    public static List<Map<String, String>> readExcelByXlsx(MultipartFile templetFile, int startrow, int startcol, int sheetnum) {
        List<Map<String, String>> varList = new ArrayList<Map<String, String>>();
        //标记字段
        String field ="";
        //字段是否处理
        boolean fieldProcessing =false;
        String[] fieldArr =null;
        try {
            XSSFWorkbook wb = new XSSFWorkbook(templetFile.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(sheetnum); // sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1; // 取得最后一行的行号
            for (int i = startrow; i < rowNum; i++) { // 行循环开始
                Map<String, String> varpd = new HashMap<String, String>();
                XSSFRow row = sheet.getRow(i); // 行
                int cellNum = row.getLastCellNum(); // 每行的最后一个单元格位置
                for (int j = startcol; j < cellNum; j++) { // 列循环开始
                    XSSFCell cell = row.getCell(Integer.parseInt(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellType()) { // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case 0:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                                } else {
                                    cell.setCellType(1);
                                    cellValue = cell.getStringCellValue();
                                }
                                break;
                            case 1:
                                cellValue = cell.getStringCellValue();
                                break;
                            case 2:
                                cellValue = cell.getStringCellValue();
                                // cellValue = cell.getNumericCellValue() + "";
                                // cellValue =
                                // String.valueOf(cell.getDateCellValue());
                                break;
                            case 3:
                                cellValue = "";
                                break;
                            case 4:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case 5:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                break;
                        }
                    } else {
                        cellValue = "";
                    }if (i == startrow){
                        varpd.put(cellValue, cellValue);
                        field +=cellValue+",";
                    }else {
                        if (fieldProcessing == false){
                            fieldArr = field.split(",");
                            fieldProcessing =true;
                        }
                        varpd.put(fieldArr[j-startcol], cellValue);
                    }
                }
                varList.add(varpd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return varList;
    }





}