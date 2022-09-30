package cn.ewsd.logistics.util;

import cn.ewsd.base.utils.Data;
import cn.ewsd.base.utils.DataCantainer;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.repository.model.MOut;
import cn.ewsd.system.model.TCheck;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

/**
 * 领料单导出打印
 */

public class LLDReportTest {

    public static void main(String[] args) {
        try{
            //reportCreateA("C:\\Users\\Administrator\\Desktop\\pdt\\aaa.pdf");
            //reportCreateB("C:\\Users\\Administrator\\Desktop\\pdt\\bbb.pdf");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 仓储管理-出库管理-区队申请领料-查询明细
     * @param printUrl
     * @throws Exception
     */
    public static void reportCreateA(String printUrl, java.util.List<MOut> outList, DataCantainer<TCheck> checkDc, double jykzPrice) throws Exception {

        Font Title1 = null;//标题一
        Font Text_Normal = null;//正文
        Font Table = null;//表格
        Font Footer = null;//页脚
        try {
        BaseFont bfChinese = BaseFont.createFont("/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Title1 = new Font(bfChinese, 13, Font.BOLD);
            Text_Normal = new Font(bfChinese, 9, Font.NORMAL);
            Table = new Font(bfChinese, 10, Font.NORMAL);
            Footer = new Font(bfChinese, 8, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = new Document(PageSize.A4, 20, 20, 30, 20);// 建立一个Document对象,左右上下页边距
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(printUrl));
        //打开文档添加内容
        document.open();

        for(int j=0; j<outList.size(); j++){
            MOut mOut = outList.get(j);
            String matCode = mOut.getMatCode();

            int al = 3;//3联
            for (int i = 1; i <= al; i++) {
                Paragraph pt1 = new Paragraph("鄂尔多斯市转龙湾煤炭有限公司领料单",Title1);
                pt1.setAlignment(1);//0靠左 1，居中 2，靠右
                document.add(pt1);
                Paragraph pt2 = new Paragraph("领料部门："+ mOut.getTeamName() +"        日期："+ XDate.dateTo10(XDate.getDate()) +" "+ XDate.timeTo8(XDate.getTime()) +"    单号："+ mOut.getReserve2() +"    仓库："+ mOut.getStoreName() +" ",Text_Normal);
                pt2.setAlignment(1);//0靠左 1，居中 2，靠右
                document.add(pt2);

                //表格内容
                Table table2 = new Table(7);
                //table2.setBorderColor(new Color(0, 0, 0));
                table2.setPadding(3);
                table2.setAlignment(Element.ALIGN_CENTER);//居中
                int width[] = {18,15,16,15,16,15,5};//设置每列宽度比例
                table2.setWidths(width);

                //1行
                Cell cell1=new Cell(new Paragraph("物料编码",Table));
                cell1.setUseAscender(true);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell1);

                Cell cell2=new Cell(new Paragraph(matCode.substring(0, 1).equals("X") ? matCode.substring(1) : matCode,Table));
                cell2.setUseAscender(true);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell2);

                Cell cell3=new Cell(new Paragraph("物料描述",Table));
                cell3.setUseAscender(true);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell3);

                String matName = mOut.getMatName();

                Cell cell4=new Cell(new Paragraph(matName,Table));
                cell4.setUseAscender(true);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell4.setColspan(3);
                table2.addCell(cell4);

                String tableName = "①\n当\n月\n有\n效\n︵\n领\n用\n单\n位\n留\n存\n︶";
                if(i == 2){
                    tableName = "②\n当\n月\n有\n效\n︵\n考\n核\n留\n存\n︶";
                }else if(i == 3){
                    tableName = "③\n当\n月\n有\n效\n︵\n仓\n库\n留\n存\n︶";
                }
                Paragraph ptA = new Paragraph(tableName,Footer);
                ptA.setLeading(8);
                Cell cellA=new Cell(ptA);
                cellA.setLeading(8);
                cellA.setUseAscender(true);
                cellA.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cellA.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cellA.setRowspan(7);
                cellA.disableBorderSide(10);
                table2.addCell(cellA);

                //2行
                Cell cell5=new Cell(new Paragraph("计量单位",Table));
                cell5.setUseAscender(true);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell5);

                Cell cell6=new Cell(new Paragraph(mOut.getMatUnit(),Table));
                cell6.setUseAscender(true);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell6);

                Cell cell7=new Cell(new Paragraph("费用来源",Table));
                cell7.setUseAscender(true);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell7);

                Cell cell8=new Cell(new Paragraph(mOut.getItemName(),Table));
                cell8.setUseAscender(true);
                cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell8.setColspan(3);
                table2.addCell(cell8);

                //3行
                Cell cell9=new Cell(new Paragraph("计划数量",Table));
                cell9.setUseAscender(true);
                cell9.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell9);

                Cell cell10=new Cell(new Paragraph(Data.trimDouble(mOut.getApplyAmount().doubleValue(), 4),Table));
                cell10.setUseAscender(true);
                cell10.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell10);

                Cell cell11=new Cell(new Paragraph("计划单价",Table));
                cell11.setUseAscender(true);
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell11);

                Cell cell12=new Cell(new Paragraph(Data.trimDouble(mOut.getMatPrice().doubleValue(), 2),Table));
                cell12.setUseAscender(true);
                cell12.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell12);

                Cell cell13=new Cell(new Paragraph("计划金额",Table));
                cell13.setUseAscender(true);
                cell13.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell13);

                Cell cell14=new Cell(new Paragraph(Data.trimDouble(mOut.getPlanBala().doubleValue(), 2),Table));
                cell14.setUseAscender(true);
                cell14.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell14);

                //4行
                Cell cell15=new Cell(new Paragraph("审批数量",Table));
                cell15.setUseAscender(true);
                cell15.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell15);

                Cell cell16=new Cell(new Paragraph(Data.trimDouble(mOut.getChkAmount().doubleValue(), 4),Table));
                cell16.setUseAscender(true);
                cell16.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell16.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell16);

                Cell cell17=new Cell(new Paragraph("系统单价",Table));
                cell17.setUseAscender(true);
                cell17.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell17);

                Cell cell18=new Cell(new Paragraph(Data.trimDouble(mOut.getMatPrice().doubleValue(), 2),Table));
                cell18.setUseAscender(true);
                cell18.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell18.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell18);

                Cell cell19=new Cell(new Paragraph("审批金额",Table));
                cell19.setUseAscender(true);
                cell19.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell19.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell19);

                Cell cell20=new Cell(new Paragraph(Data.trimDouble(mOut.getChkBala().doubleValue(), 2),Table));
                cell20.setUseAscender(true);
                cell20.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell20.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell20);

                //5行
                Cell cell21=new Cell(new Paragraph("实际数量",Table));
                cell21.setUseAscender(true);
                cell21.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell21.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell21);

                Cell cell22=new Cell(new Paragraph("",Table));
                cell22.setUseAscender(true);
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell22.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell22);

                Cell cell23=new Cell(new Paragraph("实际单价",Table));
                cell23.setUseAscender(true);
                cell23.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell23.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell23);

                Cell cell24=new Cell(new Paragraph(Data.trimDouble(mOut.getMatPrice().doubleValue(), 2),Table));
                cell24.setUseAscender(true);
                cell24.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell24.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell24);

                Cell cell25=new Cell(new Paragraph("实际金额",Table));
                cell25.setUseAscender(true);
                cell25.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell25.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell25);

                Cell cell26=new Cell(new Paragraph("",Table));
                cell26.setUseAscender(true);
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell26.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell26);

                //6行
                Cell cell27=new Cell(new Paragraph("使用地点及用途",Table));
                cell27.setUseAscender(true);
                cell27.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell27.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell27);

                Cell cell28=new Cell(new Paragraph(mOut.getUseAddr() + " " + mOut.getApplyInfo(),Table));
                cell28.setUseAscender(true);
                cell28.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell28.setColspan(2);
                table2.addCell(cell28);

                Cell cell283=new Cell(new Paragraph("工程项目",Table));
                cell283.setUseAscender(true);
                cell283.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell283.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell283);

                Cell cell284=new Cell(new Paragraph(mOut.getPrjName(),Table));
                cell284.setUseAscender(true);
                cell284.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell284.setColspan(2);
                table2.addCell(cell284);

                //7行
                Cell cell29=new Cell(new Paragraph("仓储货位",Table));
                cell29.setUseAscender(true);
                cell29.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell29.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell29);

                Cell cell30=new Cell(new Paragraph(mOut.getSiteCode(),Table));
                cell30.setUseAscender(true);
                cell30.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell30.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell30.setColspan(2);
                table2.addCell(cell30);

                Cell cell31=new Cell(new Paragraph("计划号",Table));
                cell31.setUseAscender(true);
                cell31.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell31.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell31);

                Cell cell32=new Cell(new Paragraph("".equals(mOut.getReserveNo()) ? mOut.getPlanNo() : mOut.getReserveNo(),Table));
                cell32.setUseAscender(true);
                cell32.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell32.setColspan(2);
                table2.addCell(cell32);

                document.add(table2);

                DataCantainer<TCheck> cdc = checkDc.findDataCantainer("check_no", mOut.getCheckNo());
                DataCantainer<TCheck> teamDc = cdc.findDataCantainer("step_code", "72021");
                DataCantainer<TCheck> znksDc = cdc.findDataCantainer("step_code", "72023");
                DataCantainer<TCheck> clspyDc = cdc.findDataCantainer("step_code", "72022");
                DataCantainer<TCheck> jykzDc = cdc.findDataCantainer("step_code", "72024");
                String teamUserName = teamDc.getRowCount()>0 ? teamDc.getRow(0).getTcUserName() : "";
                String znksUserName = znksDc.getRowCount()>0 ? znksDc.getRow(0).getTcUserName() : "";
                String clspyUserName = clspyDc.getRowCount()>0 ? clspyDc.getRow(0).getTcUserName() : "";
                String jykzUserName = jykzDc.getRowCount()>0 ? jykzDc.getRow(0).getTcUserName() : "";

                Paragraph pt3 = null;
                if(mOut.getMatPrice().doubleValue() >= jykzPrice){
                    pt3 = new Paragraph("领料主管："+ teamUserName +" 业务科室："+ znksUserName +" 经营管理科："+ clspyUserName + " 经营科长："+ jykzUserName +" 领料人：     保管员：     ", Text_Normal);
                }else{
                    pt3 = new Paragraph("领料主管："+ teamUserName +"   业务科室："+ znksUserName +"   经营管理科："+ clspyUserName +"   领料人：         保管员：         ", Text_Normal);
                }
                pt3.setAlignment(1);//0靠左 1，居中 2，靠右
                document.add(pt3);

                if(i != al) {
                    Paragraph ptB = new Paragraph("___________________________________________________________________________", Text_Normal);
                    ptB.setAlignment(1);//0靠左 1，居中 2，靠右
                    document.add(ptB);
                }
            }

            if(j!=outList.size()-1){
                document.newPage();
            }
        }

        document.close();
    }


    /**
     * 修旧利废-复用管理-区队申请旧料-查询明细
     * @param printUrl
     * @throws Exception
     */
    public static void reportCreateB(String printUrl, java.util.List<MOut> outList, DataCantainer<TCheck> checkDc) throws Exception {

        Font Title1 = null;//标题一
        Font Text_Normal = null;//正文
        Font Table = null;//表格
        Font Footer = null;//页脚
        try {
            BaseFont bfChinese = BaseFont.createFont("/fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Title1 = new Font(bfChinese, 13, Font.BOLD);
            Text_Normal = new Font(bfChinese, 9, Font.NORMAL);
            Table = new Font(bfChinese, 10, Font.NORMAL);
            Footer = new Font(bfChinese, 8, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = new Document(PageSize.A4, 20, 20, 30, 20);// 建立一个Document对象,左右上下页边距
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(printUrl));
        //打开文档添加内容
        document.open();

        for(int j=0; j<outList.size(); j++){
            MOut mOut = outList.get(j);
            String matCode = mOut.getMatCode();

            int al = 3;//3联
            for (int i = 1; i <= al; i++) {
                Paragraph pt1 = new Paragraph("鄂尔多斯市转龙湾煤炭有限公司复用料单",Title1);
                pt1.setAlignment(1);//0靠左 1，居中 2，靠右
                document.add(pt1);
                Paragraph pt2 = new Paragraph("领料部门："+ mOut.getTeamName() +"         日期："+ XDate.dateTo10(XDate.getDate()) +" "+ XDate.timeTo8(XDate.getTime()) +"    单号："+ mOut.getReserve2() +"    仓库："+ mOut.getStoreName() +" ",Text_Normal);
                pt2.setAlignment(1);//0靠左 1，居中 2，靠右
                document.add(pt2);

                //表格内容
                Table table2 = new Table(7);
                //table2.setBorderColor(new Color(0, 0, 0));
                table2.setPadding(3);
                table2.setAlignment(Element.ALIGN_CENTER);//居中
                int width[] = {18,15,16,15,16,15,5};//设置每列宽度比例
                table2.setWidths(width);

                //1行
                Cell cell1=new Cell(new Paragraph("物料编码",Table));
                cell1.setUseAscender(true);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell1);

                Cell cell2=new Cell(new Paragraph(matCode.substring(0, 1).equals("X") ? matCode.substring(1) : matCode,Table));
                cell2.setUseAscender(true);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell2);

                Cell cell3=new Cell(new Paragraph("物料描述",Table));
                cell3.setUseAscender(true);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell3);

                Cell cell4=new Cell(new Paragraph(mOut.getMatName(),Table));
                cell4.setUseAscender(true);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell4.setColspan(3);
                table2.addCell(cell4);

                String tableName = "①\n当\n月\n有\n效\n︵\n领\n用\n单\n位\n留\n存\n︶";
                if(i == 2){
                    tableName = "②\n当\n月\n有\n效\n︵\n考\n核\n留\n存\n︶";
                }else if(i == 3){
                    tableName = "③\n当\n月\n有\n效\n︵\n仓\n库\n留\n存\n︶";
                }
                Paragraph ptA = new Paragraph(tableName,Footer);
                ptA.setLeading(8);
                Cell cellA=new Cell(ptA);
                cellA.setLeading(8);
                cellA.setUseAscender(true);
                cellA.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cellA.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cellA.setRowspan(7);
                cellA.disableBorderSide(10);
                table2.addCell(cellA);

                //2行
                Cell cell5=new Cell(new Paragraph("计量单位",Table));
                cell5.setUseAscender(true);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell5);

                Cell cell6=new Cell(new Paragraph(mOut.getMatUnit(),Table));
                cell6.setUseAscender(true);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell6);

                Cell cell7=new Cell(new Paragraph("费用来源",Table));
                cell7.setUseAscender(true);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell7);

                Cell cell8=new Cell(new Paragraph(mOut.getItemName(),Table));
                cell8.setUseAscender(true);
                cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell8.setColspan(3);
                table2.addCell(cell8);

                //3行
                Cell cell9=new Cell(new Paragraph("计划数量",Table));
                cell9.setUseAscender(true);
                cell9.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell9);

                Cell cell10=new Cell(new Paragraph(Data.trimDouble(mOut.getApplyAmount().doubleValue(), 4),Table));
                cell10.setUseAscender(true);
                cell10.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell10);

                Cell cell11=new Cell(new Paragraph("计划单价",Table));
                cell11.setUseAscender(true);
                cell11.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell11);

                Cell cell12=new Cell(new Paragraph(Data.trimDouble(mOut.getMatPrice().doubleValue(), 2),Table));
                cell12.setUseAscender(true);
                cell12.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell12);

                Cell cell13=new Cell(new Paragraph("计划金额",Table));
                cell13.setUseAscender(true);
                cell13.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell13);

                Cell cell14=new Cell(new Paragraph(Data.trimDouble(mOut.getPlanBala().doubleValue(), 2),Table));
                cell14.setUseAscender(true);
                cell14.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell14);

                //4行
                Cell cell15=new Cell(new Paragraph("审批数量",Table));
                cell15.setUseAscender(true);
                cell15.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell15);

                Cell cell16=new Cell(new Paragraph(Data.trimDouble(mOut.getChkAmount().doubleValue(), 4),Table));
                cell16.setUseAscender(true);
                cell16.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell16.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell16);

                Cell cell17=new Cell(new Paragraph("系统单价",Table));
                cell17.setUseAscender(true);
                cell17.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell17);

                Cell cell18=new Cell(new Paragraph(Data.trimDouble(mOut.getMatPrice().doubleValue(), 2),Table));
                cell18.setUseAscender(true);
                cell18.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell18.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell18);

                Cell cell19=new Cell(new Paragraph("审批金额",Table));
                cell19.setUseAscender(true);
                cell19.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell19.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell19);

                Cell cell20=new Cell(new Paragraph(Data.trimDouble(mOut.getChkBala().doubleValue(), 2),Table));
                cell20.setUseAscender(true);
                cell20.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell20.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell20);

                //5行
                Cell cell21=new Cell(new Paragraph("实际数量",Table));
                cell21.setUseAscender(true);
                cell21.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell21.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell21);

                Cell cell22=new Cell(new Paragraph(Data.trimDouble(mOut.getOutAmount().doubleValue(), 4),Table));
                cell22.setUseAscender(true);
                cell22.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell22.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell22);

                Cell cell23=new Cell(new Paragraph("实际单价",Table));
                cell23.setUseAscender(true);
                cell23.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell23.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell23);

                Cell cell24=new Cell(new Paragraph(Data.trimDouble(mOut.getMatPrice().doubleValue(), 2),Table));
                cell24.setUseAscender(true);
                cell24.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell24.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell24);

                Cell cell25=new Cell(new Paragraph("实际金额",Table));
                cell25.setUseAscender(true);
                cell25.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell25.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell25);

                Cell cell26=new Cell(new Paragraph(Data.trimDouble(mOut.getChkBala().doubleValue(), 2),Table));
                cell26.setUseAscender(true);
                cell26.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell26.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell26);

                //6行
                Cell cell27=new Cell(new Paragraph("使用地点及用途",Table));
                cell27.setUseAscender(true);
                cell27.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell27.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell27);

                Cell cell28=new Cell(new Paragraph(mOut.getUseAddr() + " " +mOut.getApplyInfo(),Table));
                cell28.setUseAscender(true);
                cell28.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell28.setColspan(2);
                table2.addCell(cell28);

                Cell cell283=new Cell(new Paragraph("工程项目",Table));
                cell283.setUseAscender(true);
                cell283.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell283.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell283);

                Cell cell284=new Cell(new Paragraph("",Table));
                cell284.setUseAscender(true);
                cell284.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell284.setColspan(2);
                table2.addCell(cell284);

                //7行
                Cell cell29=new Cell(new Paragraph("备注",Table));
                cell29.setUseAscender(true);
                cell29.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cell29.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                table2.addCell(cell29);

                Cell cell30=new Cell(new Paragraph(mOut.getApplyInfo(),Table));
                cell30.setUseAscender(true);
                cell30.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cell30.setColspan(5);
                table2.addCell(cell30);

                document.add(table2);

                DataCantainer<TCheck> cdc = checkDc.findDataCantainer("check_no", mOut.getCheckNo());
                DataCantainer<TCheck> teamDc = cdc.findDataCantainer("step_code", "72021");
                DataCantainer<TCheck> znksDc = cdc.findDataCantainer("step_code", "72023");
                DataCantainer<TCheck> clspyDc = cdc.findDataCantainer("step_code", "72022");
                String teamUserName = teamDc.getRowCount()>0 ? teamDc.getRow(0).getTcUserName() : "";
                String znksUserName = znksDc.getRowCount()>0 ? znksDc.getRow(0).getTcUserName() : "";
                String clspyUserName = clspyDc.getRowCount()>0 ? clspyDc.getRow(0).getTcUserName() : "";

                Paragraph pt3 = new Paragraph("领料主管："+ teamUserName +"   业务科室："+ znksUserName +"   经营管理科："+ clspyUserName +"   领料人：         保管员：         ", Text_Normal);
                pt3.setAlignment(1);//0靠左 1，居中 2，靠右
                document.add(pt3);

                if(i != al) {
                    Paragraph ptB = new Paragraph("___________________________________________________________________________", Text_Normal);
                    ptB.setAlignment(1);//0靠左 1，居中 2，靠右
                    document.add(ptB);
                }
            }

            if(j!=outList.size()-1){
                document.newPage();
            }
        }

        document.close();
    }


    //controller示例
    //@RequestMapping(value = "/printApplicationForm")
    //    public void printApplicationForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //        String uuid = request.getParameter("uuid");
    //        HqMeeting hqMeeting = hqMeetingService.queryObject(uuid);
    //
    //        Map<String,String> pdfParam = new HashMap<>();//自定义参数
    //        pdfParam.put("HYRQ",hqMeeting.getApplyTimeStart().substring(0,10));
    //        pdfParam.put("HYSJ",hqMeeting.getApplyTimeStart().substring(11,16));
    //        pdfParam.put("ZCR",hqMeeting.getCalledUserName());
    //        pdfParam.put("JBR",hqMeeting.getAgentName());
    //        pdfParam.put("HYMC",hqMeeting.getMeetingName());
    //        pdfParam.put("HYDD",hqMeeting.getArrangeRoomName());
    //        pdfParam.put("HYGM",hqMeeting.getMeetingScale());
    //        pdfParam.put("CHRY",personStr);
    //        pdfParam.put("HYNR",hqMeeting.getMeetingContent());
    //
    //        //文件路径及名称定义
    //        String uploadDir = getUploadDir("pdf");
    //        File fileZ = new File(uploadDir);
    //        if (!fileZ.exists()) {
    //            fileZ.mkdirs();
    //        }
    //        String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + ".pdf";
    //        String filePath = uploadDir + "/" + newFileName;
    //        //文件生成
    //        MeetingReportUtil.reportCreate(filePath,pdfParam);//生成文件
    //
    //        Thread.sleep(500);
    //
    //        //返回PDF文件到浏览器,浏览器会直接打开
    //        File file = new File(filePath);
    //        byte[] data = null;
    //        try {
    //            // 解决请求头跨域问题（IE兼容性  也可使用该方法）
    //            response.setHeader("Access-Control-Allow-Origin", "*");
    //            response.setContentType("application/pdf");
    //            FileInputStream input = new FileInputStream(file);
    //            data = new byte[input.available()];
    //            input.read(data);
    //            response.getOutputStream().write(data);
    //            input.close();
    //        } catch (Exception e) {
    //        }
    //    }
}
