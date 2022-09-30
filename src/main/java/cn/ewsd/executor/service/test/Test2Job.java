package cn.ewsd.executor.service.test;

import cn.ewsd.forest.client.MtBaseClient;
import cn.ewsd.forest.model.getToken.getTokenData;
import cn.ewsd.forest.model.getToken.getTokenObj;
import cn.ewsd.system.model.DicSet;
import cn.ewsd.system.service.DicSetService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@JobHandler(value = "test2Job")
@Component
public class Test2Job extends IJobHandler {

//    @Resource
//    private DicSetService dicSetService;
//    @Autowired
//    private MtBaseClient mtBaseClient;



    @Override
    public ReturnT<String> execute(String s) throws Exception {
        System.out.println("==========================SUCCESS=======================");
        return ReturnT.SUCCESS;
    }
}
