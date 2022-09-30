package cn.ewsd.base.utils.my;

import cn.ewsd.base.utils.StringUtils;

public class GenerateAddColumnsSql {
    public static String Columns = "maintain_fee_one";



    public static void main(String[] args) {
        String [] clumnsArray = Columns.split(",");
        System.out.println("SQL1==========================================================");
        for (int i = 0; i < clumnsArray.length; i++) {
            System.out.println("\t<result property=\"" +
                    clumnsArray[i] +
                    "\" column=\"" +
                    clumnsArray[i] +
                    "\"/>");
        }
        System.out.println("SQL2==========================================================");
        for (int i = 0; i < clumnsArray.length; i++) {
            System.out.println("\t,"+clumnsArray[i]);
        }
        System.out.println("SQL3==========================================================");
        for (int i = 0; i < clumnsArray.length; i++) {
            System.out.println("\t,`" +
                    clumnsArray[i] +
                    "`");
        }
        System.out.println("\n");
        for (int i = 0; i < clumnsArray.length; i++) {
            System.out.println("\t,#{" +
                    clumnsArray[i] +
                    "}");
        }
        System.out.println("SQL4==========================================================");
        for (int i = 0; i < clumnsArray.length; i++) {
            System.out.println("<if test=\"" +
                    clumnsArray[i] +
                    " != null\">,`" +
                    clumnsArray[i] +
                    "` = #{" +
                    clumnsArray[i] +
                    "}</if>");
        }
        System.out.println("FIELD==========================================================");
        for (int i = 0; i < clumnsArray.length; i++) {
            System.out.println(StringUtils.camelCaseName(clumnsArray[i]));
        }

    }
}
