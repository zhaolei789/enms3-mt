package cn.ewsd.logistics.util;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;

/**
 * pdf生成-示例
 */

public class PDFReportTest2 {

    public static void main(String[] args) {
//        try{
//            reportCreate("C:\\Users\\Administrator\\Desktop\\pdt\\aaa.pdf");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }

    public static void reportCreate(String printUrl) throws Exception {

        Font Title1 = null;//标题一
        Font Title2 = null;//标题二
        Font TableFront = null;//表格
        Font TableNormalFront = null;//表格文字

        String SHSJ="    ";

        try {
        BaseFont bfChinese = BaseFont.createFont("/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Title1 = new Font(bfChinese, 20, Font.BOLD);
            Title2 = new Font(bfChinese, 20, Font.BOLD);
            TableFront = new Font(bfChinese, 12, Font.NORMAL);
            TableNormalFront = new Font(bfChinese, 11, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document document = new Document(PageSize.A4, 20, 20, 50, 20);// 建立一个Document对象,左右上下页边距
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(printUrl));

        //打开文档添加内容
        document.open();

        //标题一
        Paragraph pt1 = new Paragraph("鄂 尔 多 斯 市 转 龙 湾 煤 炭 有 限 公 司",Title1);
        pt1.setAlignment(1);//0靠左 1，居中 2，靠右
        document.add(pt1);

        //标题二
        Paragraph pt2 = new Paragraph("会议审批单",Title2);
        pt2.setAlignment(1);//0靠左 1，居中 2，靠右
        document.add(pt2);

        document.add(new Paragraph("\n\n"));

        PdfPTable table2 = new PdfPTable(4);
        int width[] = {25,35,20,20};//设置每列宽度比例
        table2.setWidths(width);

        //1
        PdfPCell cell1=new PdfPCell(new Paragraph("会议日期",TableFront));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell1.setMinimumHeight(35);
        table2.addCell(cell1);
        table2.addCell(new PdfPCell(Phrase.getInstance("")));

        PdfPCell cell2=new PdfPCell(new Paragraph("会议时间",TableFront));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell2.setMinimumHeight(35);
        table2.addCell(cell2);
        table2.addCell(new PdfPCell(Phrase.getInstance("")));

        //2
        PdfPCell cell3=new PdfPCell(new Paragraph("主持人(或召集人)",TableFront));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell3.setMinimumHeight(35);
        table2.addCell(cell3);
        table2.addCell(new PdfPCell(Phrase.getInstance("")));

        PdfPCell cell4=new PdfPCell(new Paragraph("经办人",TableFront));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell4.setMinimumHeight(35);
        table2.addCell(cell4);
        table2.addCell(new PdfPCell(Phrase.getInstance("")));

        //3
        PdfPCell cell5=new PdfPCell(new Paragraph("会议名称",TableFront));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell5.setMinimumHeight(35);
        table2.addCell(cell5);

        PdfPCell cell6=new PdfPCell(Phrase.getInstance(""));
        cell6.setColspan(3);
        table2.addCell(cell6);

        //4
        PdfPCell cell7=new PdfPCell(new Paragraph("会议地点",TableFront));
        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell7.setMinimumHeight(35);
        table2.addCell(cell7);
        table2.addCell(new PdfPCell(Phrase.getInstance("")));

        PdfPCell cell8=new PdfPCell(new Paragraph("会议规模",TableFront));
        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell8.setMinimumHeight(35);
        table2.addCell(cell8);
        table2.addCell(new PdfPCell(Phrase.getInstance("")));

        //5
        Paragraph pt4 = new Paragraph("参会人员",TableFront);
        PdfPCell cell9=new PdfPCell(pt4);
        cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell9.setMinimumHeight(110);
        table2.addCell(cell9);

        PdfPCell cell10=new PdfPCell(Phrase.getInstance("123131"));
        cell10.setColspan(3);
        table2.addCell(cell10);

        //6
        Paragraph pt3 = new Paragraph("会议内容及\n有关事项",TableFront);
        PdfPCell cell11=new PdfPCell(pt3);
        cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell11.setMinimumHeight(110);
        cell11.setLeading(0,1.5f);
        cell11.setPaddingBottom(10);
        table2.addCell(cell11);

        PdfPCell cell12=new PdfPCell(Phrase.getInstance(""));
        cell12.setColspan(3);
        table2.addCell(cell12);


        //7
        Phrase pt5 = new Phrase("牵头或承办\n部门意见",TableFront);
        PdfPCell cell13=new PdfPCell(pt5);
        cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell13.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell13.setMinimumHeight(50);
        cell13.setLeading(0,1.5f);
        cell13.setPaddingBottom(10);
        table2.addCell(cell13);

        PdfPCell cell14=new PdfPCell(Phrase.getInstance(""));
        cell14.setColspan(3);
        table2.addCell(cell14);


        //8`
        Paragraph pt6 = new Paragraph("综合办公室\n初审意见",TableFront);
        PdfPCell cell15=new PdfPCell(pt6);
        cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell15.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell15.setMinimumHeight(50);
        cell15.setLeading(0,1.5f);
        cell15.setPaddingBottom(10);
        table2.addCell(cell15);

        PdfPCell cell16=new PdfPCell(Phrase.getInstance(""));
        cell16.setColspan(3);
        table2.addCell(cell16);


        //9
        Paragraph pt7 = new Paragraph("公司领导\n审批意见",TableFront);
        PdfPCell cell17=new PdfPCell(pt7);
        cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cell17.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell17.setMinimumHeight(50);
        cell17.setLeading(0,1.5f);
        cell17.setPaddingBottom(10);
        table2.addCell(cell17);

        PdfPCell cell18=new PdfPCell(Phrase.getInstance(""));
        cell18.setColspan(3);
        table2.addCell(cell18);


        document.add(table2);

        document.close();
    }
}
