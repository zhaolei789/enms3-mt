package cn.ewsd.base.utils.my;

import cn.ewsd.base.utils.StringUtils;

public class GenerateBatchInsetSql {
    //输入参数
    public static String tableName = "protective_grant_record";
    public static String Columns ="`uuid`, \n" +
            "\t\t\t`creator`, \n" +
            "\t\t\t`creator_id`, \n" +
            "\t\t\t`creator_org_id`, \n" +
            "\t\t\t`create_time`, \n" +
            "\t\t\t`modifier_id`, \n" +
            "\t\t\t`modifier`, \n" +
            "\t\t\t`modify_time`, \n" +
            "\t\t\t`user_uuid`, \n" +
            "\t\t\t`protective_type`, \n" +
            "\t\t\t`protective_type_name`, \n" +
            "\t\t\t`protective_ftype`, \n" +
            "\t\t\t`protective_ftype_name`, \n" +
            "\t\t\t`protective_unit`, \n" +
            "\t\t\t`post_id`, \n" +
            "\t\t\t`post_name`, \n" +
            "\t\t\t`pick_up_date`, \n" +
            "\t\t\t`expire_date`, \n" +
            "\t\t\t`protective_use_deadline`, \n" +
            "\t\t\t`grant_num_one`, \n" +
            "\t\t\t`grant_num_standard`, \n" +
            "\t\t\t`grant_num_true`, \n" +
            "\t\t\t`is_standby`, \n" +
            "\t\t\t`status`, \n" +
            "\t\t\t`is_del`, \n" +
            "\t\t\t`link_plan`, \n" +
            "\t\t\t`link_plan_id`";

    public static void main(String[] args) {
        System.out.println("mapper.xml==========================================================");
        String row1 = "<insert id=\"batchInsert\">";
        String row2 = "insert into "+tableName;
        String row3 = "(";
        String row4 = Columns;
        String row5 = ")";
        String row6 = "values";
        String row7 = "<foreach collection=\"list\" item=\"list\" index=\"index\" separator=\",\">";
        String row8 = "(";
        System.out.println(row1);
        System.out.println(row2);
        System.out.println(row3);
        System.out.println(row4);
        System.out.println(row5);
        System.out.println(row6);
        System.out.println(row7);
        System.out.println(row8);

        String columns_tmp = Columns.replace("\t","").replace("\n","").replace("`","").replace(" ","").replace("	", "");
        String columns_array [] = columns_tmp.split(",");
        for (int i = 0; i < columns_array.length; i++) {
            if(i == columns_array.length -1) {
                System.out.println("\t#{list."+ StringUtils.camelCaseName(columns_array[i])+"}");
            }else{
                System.out.println("\t#{list."+StringUtils.camelCaseName(columns_array[i])+"},");
            }
        }

        System.out.println(")");
        System.out.println("</foreach>");
        System.out.println("</insert>");

        String className_tmp = StringUtils.camelCaseName(tableName);
        String className = className_tmp.substring(0,1).toUpperCase().concat(className_tmp.substring(1,className_tmp.length()));
        System.out.println("mapper.java==========================================================");
        System.out.println("\tint batchInsert(@Param(\"list\") List<" +className+
                "> list);");

        System.out.println("service.java==========================================================");
        System.out.println("\tint batchInsert(List<" +
                className +
                "> list);");

        System.out.println("serviceImpl.java==========================================================");
        System.out.println("\t@Override\n" +
                "\tpublic int batchInsert(List<" +
                className +
                "> list) {\n" +
                "\t\treturn " +
                className_tmp+"Mapper" +
                ".batchInsert(list);\n" +
                "\t}");

    }
}
