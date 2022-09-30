package cn.ewsd.base.utils;

import cn.ewsd.common.utils.BaseUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 页面过滤功能替换条件工具类
 */
public class FilterUtils {


    /**
     * 查询filterSort中是否过滤了字段
     * @param filterName
     * @param filterSort
     * @return
     */
    public static boolean checkHaveFilterField(String filterName,String filterSort){
        boolean have = false;
        String pattern = filterName+" LIKE \\'%(.*?)\\%'";
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(filterSort);
        while(m.find()) {
            have = true;
        }
        return have;
    }


    /**
     * 取出对应字段的输入值
     * @param filterName
     * @param filterSort
     * @return
     */
    public static String getFilterFieldValue(String filterName,String filterSort){
        //提取出字符串
        String pattern = filterName+" LIKE \\'%(.*?)\\%'";
        String text_1 = "";
        String text_2 = "";
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(filterSort);
        while(m.find()) {
            text_1 = m.group(0);
        }
        if(text_1.equals("")){
            return "";
        }
        Pattern p2= Pattern.compile("%.*%");
        Matcher m2=p2.matcher(text_1);
        while(m2.find()) {
            text_2 = m2.group(0).trim().replace("%","");
        }
        return text_2;
    }

    /**
     * 替换filterSort中的筛选字段值
     * @param filterName
     * @param filterSort
     * @param newValue
     * @return
     */
    public static String replaceFilterFieldValue(String filterName,String filterSort,String newValue){
        String filterSort_rp = filterSort;
        //提取出字符串
        String pattern = filterName+" LIKE \\'%(.*?)\\%'";
        String text_1 = "";//supplier_name LIKE '%1%'
        String text_2 = "";//%1%
        String text_3 = "";//1
        String text_4 = "";//supplier_name LIKE '%rp%'
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(filterSort);
        while(m.find()) {
            text_1 = m.group(0);
        }
        if(text_1.equals("")){
            return filterSort;
        }
        Pattern p2= Pattern.compile("%.*%");
        Matcher m2=p2.matcher(text_1);
        while(m2.find()) {
            text_2 = m2.group(0);
            text_3 = m2.group(0).trim().replace("%","");
        }
        if(text_2.equals("")){
            return filterSort;
        }
        text_4 = filterName +" LIKE '%"+newValue+"%'";
        filterSort_rp = filterSort_rp.replace(text_1,text_4);
        return filterSort_rp;
    }

    /**
     * 替换过滤条件中的字段
     * @param filterName 原字段名
     * @param filterSort 过滤sql
     * @param newFilterName 新字段名
     * @return
     */
    public static String replaceFilterFieldName(String filterName,String filterSort,String newFilterName){
        String filterSort_out = "";
        String filterSort_rp = filterSort;
        //提取出字符串
        String pattern = filterName+" LIKE \\'%(.*?)\\%'";
        String text_1 = "";//supplier_name LIKE '%1%'
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(filterSort);
        while(m.find()) {
            text_1 = m.group(0);
        }
        if(text_1.equals("")){
            return filterSort;
        }
        filterSort_rp = text_1.replace(filterName,newFilterName);
        filterSort_out = filterSort.replace(text_1,filterSort_rp);
        return filterSort_out;
    }

    /**
     * 替换过滤条件中的字段和值(通常用于数据字段的过滤)
     * @param filterName 字段名
     * @param filterSort 过滤sql
     * @param newFilterName 新字段名
     * @param newValue 新的值
     * @return
     */
    public static String replaceFilterFieldValueAndName(String filterName,String filterSort,String newFilterName,String newValue){
        String filterSort_rp = filterSort;
        //提取出字符串
        String pattern = filterName+" LIKE \\'%(.*?)\\%'";
        String text_1 = "";//supplier_name LIKE '%1%'
        String text_2 = "";//%1%
        String text_3 = "";//1
        String text_4 = "";//supplier_name LIKE '%rp%'
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(filterSort);
        while(m.find()) {
            text_1 = m.group(0);
        }
        if(text_1.equals("")){
            return filterSort;
        }
        Pattern p2= Pattern.compile("%.*%");
        Matcher m2=p2.matcher(text_1);
        while(m2.find()) {
            text_2 = m2.group(0);
            text_3 = m2.group(0).trim().replace("%","");
        }
        if(text_2.equals("")){
            return filterSort;
        }
        text_4 = filterName +" LIKE '%"+newValue+"%'";
        filterSort_rp = filterSort_rp.replace(text_1,text_4);
        filterSort_rp = filterSort_rp.replace(filterName,newFilterName);
        return filterSort_rp;
    }
//    public static void main(String[] args) {
//        String testSql1 = "1 = 1 AND supplier_name LIKE '%sheb%' and is_del != 1 ORDER BY create_time DESC";
//        String testSql2 = "1 = 1 AND supplier_name LIKE '%设备%' and is_del != 1 ORDER BY create_time DESC";
//        String testSql3 = "1 = 1 AND supplier_name LIKE '%1%' AND equipment_status LIKE '%3%' and is_del != 1 ORDER BY create_time DESC";
//
//        System.out.println("checkHaveFilterField===="+checkHaveFilterField("supplier_name",testSql3));
//        System.out.println("getFilterFieldValue===="+getFilterFieldValue("supplier_name",testSql3));
//        System.out.println("replaceFilterFieldValue===="+replaceFilterFieldValue("supplier_name",testSql3,"666"));
//
//    }


    /*
    //使用
    filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
    //过滤filterSort
        if(FilterUtils.checkHaveFilterField("f_month",filterSort)){
        filterSort = FilterUtils.replaceFilterFieldName("f_month",filterSort,"a.f_month");
    }
        if(FilterUtils.checkHaveFilterField("item_name",filterSort)){
        filterSort = FilterUtils.replaceFilterFieldName("item_name",filterSort,"a.item_name");
    }
        if(FilterUtils.checkHaveFilterField("team_no_name",filterSort)){
        filterSort = FilterUtils.replaceFilterFieldName("team_no_name",filterSort,"o1.text");
    }
        if(FilterUtils.checkHaveFilterField("report_team_name",filterSort)){
        filterSort = FilterUtils.replaceFilterFieldName("report_team_name",filterSort,"o2.text");
    }*/


}
