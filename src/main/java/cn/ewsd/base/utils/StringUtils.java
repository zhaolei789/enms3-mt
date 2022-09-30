package cn.ewsd.base.utils;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StringUtils {

    /**
     * 驼峰转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * 下划线转换为驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }
    /**
     * 判断字符串是否为null或空值
     *
     * @param str
     * @return boolean 为null或空值返回true
     */
    public static boolean isNullOrEmpty(String str) {
        return (str == null || "".equals(str)) ? true : false;
    }


    /**
     * @MethodName validString
     * @Description 验证字符串的有效性
     * @Param s
     * @Return boolean
     * @Author 宋景民<songjingmin@zuoyoutech.com>
     * @Date 2019/8/10 18:16
     */
    public static boolean valid(String  s){
        if(s == null || s.isEmpty()){
            return false;
        }else{
            return  true;
        }
    }

    /**
     * @MethodName stringToArrayAndNoRepet
     * @Description 将String装为数组并且去重
     * @Param s
     * @Return java.lang.String[]
     * @Author 宋景民<songjingmin@zuoyoutech.com>
     * @Date 2019/8/10 20:10
     */
    public static String[] stringToArrayAndNoRepet(String s){

        // 1. 将String转为数组
        if(!StringUtils.valid(s.trim())){
            return null;
        }
        String[] strArr = s.split(",");

        // 2. 将数组去重
        strArr = StringUtils.noRepet(strArr);

        return  strArr;
    }

    public static String[] noRepet(String [] arrStr) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<arrStr.length; i++) {
            if(!list.contains(arrStr[i])) {
                list.add(arrStr[i]);
            }
        }
        //返回一个包含所有对象的指定类型的数组
        return  list.toArray(new String[1]);

    }

    /**
     * @MethodName isJson
     * @Description 判断是否是JSON格式的数据
     * @Param content
     * @Return boolean
     * @Author 宋景民<songjingmin@zuoyoutech.com>
     * @Date 2019/8/8 16:40
     */
    public static boolean isJson(String content) {
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isContainInArray(String[] strArray, String containStr) {
        return Arrays.asList(strArray).contains(containStr);
    }

    /**
     * @MethodName 生成验证码
     * @Description TODO
     * @Param null
     * @Return
     * @Author 朱永敬<zhuyongjing@zuoyour.com>
     * @Date 2019-09-23 9:04
     */

    public static String genCodes(int length,long num){

        String results="";

        for(int j=0;j<num;j++){
            String val = "";

            Random random = new Random();
            for(int i = 0; i < length; i++)
            {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

                if("char".equalsIgnoreCase(charOrNum)) // 字符串
                {
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                    val += (char) (choice + random.nextInt(26));
                }
                else if("num".equalsIgnoreCase(charOrNum)) // 数字
                {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            val=val.toLowerCase();
            if(results.contains(val)){
                continue;
            }else{
                return val;
            }
        }
        return results;


    }
}
