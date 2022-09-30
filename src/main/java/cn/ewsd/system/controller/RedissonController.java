package cn.ewsd.system.controller;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.system.service.RedissonService;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/system/redisson")
public class RedissonController extends SystemBaseController {

    private static final Logger log = LoggerFactory.getLogger(RedissonController.class);

    @Autowired
    private RedissonService redissonService;

    @RequestMapping(value = "/getMapByName")
    @ResponseBody
    public Object getMap(String name) {
        RBucket<Map<String, Object>> keyObj = redissonService.getRBucket(name);
        Map<String, Object> map = new HashMap<>();
        map.put("userNameId", LoginInfo.getUserNameId());
        map.put("userName", LoginInfo.getUserName());
        map.put("timestamp", System.currentTimeMillis());
        keyObj.set(map);
        System.out.println(keyObj.get());

        RBucket<Map<String, Object>> keyObj1 = redissonService.getRBucket(name);
        System.out.println(keyObj1.get());
        return keyObj1.get();
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public void test(String recordId) {
        RLock lock = redissonService.getRLock(recordId);
        try {

            // 首先获取redis中的key-value对象，key不存在没关系
            RBucket<String> keyObject = redissonService.getRBucket("key");
            System.out.println(keyObject.get());
            // 如果key存在，就设置key的值为新值value
            // 如果key不存在，就设置key的值为value
            keyObject.set("value");

            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                // 业务代码
                log.info("进入业务代码: " + recordId);

                lock.unlock();
            } else {
                Thread.sleep(300);
            }
        } catch (Exception e) {
            log.error("", e);
            lock.unlock();
        }
    }

}