package cn.ewsd.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    // 返回easyui message json格式数据
    public static Map<String, Object> messageJson(int statusCode, String title, String message) {
        HashMap var3 = new HashMap();
        var3.put("statusCode", statusCode);
        var3.put("title", title);
        var3.put("message", message);
        return var3;
    }

    // 返回easyui message json格式数据
    public static Map<String, Object> messageJson(int statusCode, String title, String message, String icon) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        jsonObj.put("statusCode", statusCode);
        jsonObj.put("title", title);
        jsonObj.put("message", message);
        jsonObj.put("icon", icon);
        return jsonObj;
    }

    public static Object toJSON(Object obj) {
        return JSON.toJSON(obj);
    }

    public static Object toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static Object toJSONStringWithDateFormat(Object obj, String datePattern) {
        return JSON.toJSONStringWithDateFormat(obj, datePattern);
    }

    public static Object toJSONStringWithDateFormatByDate(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd");
    }

    public static Object toJSONStringWithDateFormatByDatetime(Object obj) {
        return JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm");
        //return JSON.toJSON(obj);
    }

    public static List list2JavaBean(List list, Class cls) {
        return JSON.parseArray(JSON.toJSON(list) + "", cls);
    }

    public static Object map2JavaBean(Map<String, Object> map, Class cls) {
        return JSON.parseObject(JSON.toJSON(map) + "", cls);
    }

    /**
     * 返回两个JsonArray的合并后的字符串
     *
     * @param mData
     * @param array
     * @return
     */
    public static String joinJSONArray(JSONArray mData, JSONArray array) {
        StringBuffer buffer = new StringBuffer();
        try {
            int len = mData.size();
            for (int i = 0; i < len; i++) {
                JSONObject obj1 = (JSONObject) mData.get(i);
                if (i == len - 1)
                    buffer.append(obj1.toString());
                else
                    buffer.append(obj1.toString()).append(",");
            }
            len = array.size();
            if (len > 0)
                buffer.append(",");
            for (int i = 0; i < len; i++) {
                JSONObject obj1 = (JSONObject) array.get(i);
                if (i == len - 1)
                    buffer.append(obj1.toString());
                else
                    buffer.append(obj1.toString()).append(",");
            }
//          buffer.insert(0, "[").append("]");
            return buffer.toString();
        } catch (Exception e) {
        }
        return null;
    }
    public static Map<String, Object> exceptionJson(Exception e) {
        return messageJson(300, "操作提示", "<div style='word-break:break-word;'>" + ExceptionUtils.getRootCauseMessage(e) + "</div>", "error");
    }

}
