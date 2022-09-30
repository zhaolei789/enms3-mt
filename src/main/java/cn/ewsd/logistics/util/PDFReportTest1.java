package cn.ewsd.logistics.util;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;

/**
 * pdf生成-示例
 */

public class PDFReportTest1 {

    public static void main(String[] args) {
        try{
            reportCreate("C:\\Users\\Administrator\\Desktop\\pdt\\aaa.pdf");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void reportCreate(String printUrl) throws Exception {

        Font Title1 = null;//标题一
        Font Title2 = null;//标题二
//        Font Section_Title = null;//章节标题
//        Font Text_Normal = null;//正文
        Font Table = null;//表格
        Font Footer = null;//页脚

        String SHSJ="    ";

        try {
        BaseFont bfChinese = BaseFont.createFont("/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Title1 = new Font(bfChinese, 18, Font.BOLD);
            Title2 = new Font(bfChinese, 18, Font.BOLD);
//            Section_Title = new Font(bfChinese, 14, Font.BOLD);
//            Text_Normal = new Font(bfChinese, 14, Font.NORMAL);
            Table = new Font(bfChinese, 12, Font.NORMAL);
            Footer = new Font(bfChinese, 9, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document document = new Document(PageSize.A4, 20, 20, 50, 20);// 建立一个Document对象,左右上下页边距
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(printUrl));
        //页脚
//        HeaderFooter footer = new HeaderFooter(new Phrase("",Footer),true);
//        footer.setBorder(Rectangle.NO_BORDER);
//        footer.setAlignment(Element.ALIGN_CENTER);//页脚居中
//        document.setFooter(footer);

        //打开文档添加内容
        document.open();

        Paragraph pt1 = new Paragraph("鄂尔多斯市转龙湾煤炭有限公司",Title1);
        pt1.setAlignment(1);//0靠左 1，居中 2，靠右
        document.add(pt1);
        Paragraph pt2 = new Paragraph("会议审批单",Title2);
        pt2.setAlignment(1);//0靠左 1，居中 2，靠右
        document.add(pt2);


        document.add(new Paragraph("\n"));


        Table table2 = new Table(4);
        table2.setBorderColor(new Color(0, 0, 0));
        table2.setPadding(5);
        //table2.setAlignment(Element.ALIGN_CENTER);//居中
        int width[] = {25,35,20,20};//设置每列宽度比例
        table2.setWidths(width);
        //table2.setAutoFillEmptyCells(true);//自动填满
        table2.setBorderWidth((float)0.1);//表格边框线条宽度

        //1
        Cell cell1=new Cell(new Paragraph("会议日期",Table));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        //cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        table2.addCell(cell1);
        table2.addCell(new Cell(""));

        Cell cell2=new Cell(new Paragraph("会议时间",Table));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        table2.addCell(cell2);
        table2.addCell(new Cell(""));

        //2
        Cell cell3=new Cell(new Paragraph("主持人(或召集人)",Table));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        table2.addCell(cell3);
        table2.addCell(new Cell(""));

        Cell cell4=new Cell(new Paragraph("经办人",Table));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        table2.addCell(cell4);
        table2.addCell(new Cell(""));

        //3
        Cell cell5=new Cell(new Paragraph("会议名称",Table));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        table2.addCell(cell5);

        Cell cell6=new Cell("");
        cell6.setColspan(3);
        table2.addCell(cell6);

        //4
        Cell cell7=new Cell(new Paragraph("会议地点",Table));
        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        table2.addCell(cell7);
        table2.addCell(new Cell(""));

        Cell cell8=new Cell(new Paragraph("会议规模",Table));
        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        table2.addCell(cell8);
        table2.addCell(new Cell(""));

        //5
        Paragraph pt4 = new Paragraph("参会人员\n ",Table);
        pt4.setLeading(30);
        pt4.setAlignment(1);//0靠左 1，居中 2，靠右
        Cell cell9=new Cell(pt4);
        cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell9.setLeading(35);
        table2.addCell(cell9);

//        Paragraph pt4 = new Paragraph("参会人员\n ",Table);
//        pt4.setLeading(30);
//        pt4.setAlignment(1);//0靠左 1，居中 2，靠右
//        PdfPCell cell9=new PdfPCell(pt4);
//        cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
//        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
//        cell9.setLeading(35);
//        table2.addCell(cell9);

        Cell cell10=new Cell("123131");
        cell10.setColspan(3);
        table2.addCell(cell10);

        //6
        Paragraph pt3 = new Paragraph("会议内容及\n有关事项\n ",Table);
        pt3.setLeading(20);
        pt3.setAlignment(1);//0靠左 1，居中 2，靠右
        Cell cell11=new Cell(pt3);
        cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell11.setLeading(30);
        table2.addCell(cell11);

        Cell cell12=new Cell("");
        cell12.setColspan(3);
        table2.addCell(cell12);


        //7
        Paragraph pt5 = new Paragraph("牵头或承办\n部门意见\n ",Table);
        pt5.setLeading(20);
        pt5.setAlignment(1);//0靠左 1，居中 2，靠右
        Cell cell13=new Cell(pt5);
        cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell13.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell13.setLeading(10);
        table2.addCell(cell13);

        Cell cell14=new Cell("");
        cell14.setColspan(3);
        table2.addCell(cell14);


        //8`
        Paragraph pt6 = new Paragraph("综合办公室\n初审意见\n",Table);
        pt6.setAlignment(Paragraph.ALIGN_CENTER);
        //pt6.setLeading(20);
        //pt6.set
        //pt6.setAlignment(1);//0靠左 1，居中 2，靠右
        Cell cell15=new Cell(pt6);
        cell15.setVerticalAlignment(PdfPCell.ALIGN_CENTER);//垂直居中
        cell15.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell15.setLeading(70);
        table2.addCell(cell15);

        Cell cell16=new Cell("");
        cell16.setColspan(3);
        table2.addCell(cell16);


        //9
        Paragraph pt7 = new Paragraph("公司领导\n审批意见\n ",Table);
        pt7.setLeading(20);
        pt7.setAlignment(1);//0靠左 1，居中 2，靠右
        Cell cell17=new Cell(pt7);
        cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell17.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell17.setLeading(20);
        table2.addCell(cell17);

        Cell cell18=new Cell("");
        cell18.setColspan(3);
        table2.addCell(cell18);


        document.add(table2);

        document.close();
    }
}
