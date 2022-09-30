package cn.ewsd.base.utils;//package cn.ewsd.system.utils;
//
//import com.fasterxml.jackson.databind.exc.InvalidFormatException;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ooxml.POIXMLDocument;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PushbackInputStream;
//
//=
//
//public class XlsImpUtil
//{
//    public static Workbook create(InputStream inp) throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
//        if(!inp.markSupported()){
//            inp=new PushbackInputStream(inp,8);
//        }
//        if(POIFSFileSystem.hasPOIFSHeader(inp)){
//            return new HSSFWorkbook(inp);
//        }
//        if(POIXMLDocument.hasOOXMLHeader(inp)){
//            return new XSSFWorkbook(OPCPackage.open(inp));
//        }
//        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
//    }
//}
//
