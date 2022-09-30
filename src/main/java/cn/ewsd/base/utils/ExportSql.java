package cn.ewsd.base.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * 导出Excle数据sqk条件处理
 */
public class ExportSql {
    public static String whereSql(String ste) {
        if ("".equals(ste) || "[]".equals(ste)){
            return "";
        }
        JSONArray arry = JSONArray.fromObject(ste);
        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < arry.size(); i++) {
            JSONObject jsonObject = arry.getJSONObject(i);
            Map<String, Object> map = new HashMap<String, Object>();
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext(); ) {
                String key = (String) iter.next();
                Object value = jsonObject.get(key);
                map.put(key, value);
            }
            rsList.add(map);
        }
        String  sql ="";
        for(int i = 0; i < rsList.size(); i++){
            sql +=StringUtils.underscoreName(rsList.get(i).get("field").toString()) + " LIKE '%"+rsList.get(i).get("value")+"%' and  ";
        }
        sql =" where "+sql.substring(0, sql.length() - 5)+" ";

        return sql;
    }
}
