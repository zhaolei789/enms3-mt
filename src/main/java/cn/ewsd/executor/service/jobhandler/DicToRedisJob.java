package cn.ewsd.executor.service.jobhandler;

import cn.ewsd.system.model.DicSet;
import cn.ewsd.system.service.DicItemService;
import cn.ewsd.system.service.DicSetService;
import cn.ewsd.system.service.RedissonService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@JobHandler(value = "dicToRedisExecute")
@Component
public class DicToRedisJob extends IJobHandler {

    @Autowired
    private RedissonService redissonService;
    @Resource
    private DicSetService dicSetService;
    @Resource
    private DicItemService dicItemService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        String redissonName = "sysDicItem";
        RBucket<Map<String,List<Map<String,Object>>>> keyObj = redissonService.getRBucket(redissonName);
        if(!keyObj.isExists()){
            Map<String,List<Map<String,Object>>> dicMap = new HashMap<>();
            List<DicSet> dicSetList = dicSetService.getAllDicSet();
            for (int i = 0; i < dicSetList.size(); i++) {
                dicMap.put(dicSetList.get(i).getCode(),dicItemService.getListByPuuid(dicSetList.get(i).getUuid()));
            }
            keyObj.set(dicMap);
        }
        return ReturnT.SUCCESS;
    }
}
