package cn.ewsd.cost.util;

import cn.ewsd.base.bean.PageData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 偷懒找值Util
 */
public class LRfindValueUtil {


    /**
     * 从List<PageData>中根据条件找Double
     * @param data
     * @param fieldName
     * @param condMap
     * @return
     */
    public static double findDoubleByList(List<PageData> data, String fieldName, LinkedHashMap<String,String> condMap){
        Double res = 0.0;
        if(data != null){
            for (int i = 0; i < data.size(); i++) {
                PageData data_tmp = data.get(i);
                Double res_tmp = 0.0;
                boolean flag = true;
                for (Map.Entry<String, String> entry : condMap.entrySet()) {
                    if(!data_tmp.get(entry.getKey()).equals(entry.getValue())){
                        flag = false;
                        break;
                    }else{
                        res_tmp = data_tmp.get(fieldName)!=null ? Double.parseDouble(data_tmp.get(fieldName).toString()):0.0;
                    }
                }
                if(flag){
                    res = res_tmp;
                    break;
                }
            }
        }
        return res;
    }

    public static double findDouble(PageData data,String fieldName){
        Double res = 0.0;
        if(data!=null&&data.get(fieldName)!=null){
            res = Double.parseDouble(data.get(fieldName).toString());
        }
        return res;
    }


}
