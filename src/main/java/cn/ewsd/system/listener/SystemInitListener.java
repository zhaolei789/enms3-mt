package cn.ewsd.system.listener;

import cn.ewsd.common.common.Config;
import cn.ewsd.system.service.ChannelService;
import cn.ewsd.system.service.CodeItemService;
import cn.ewsd.system.service.ConfigService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

@Component
public class SystemInitListener implements ServletContextAware {

    @Resource
    private ConfigService configService;

    @Resource
    private ChannelService channelService;

    @Resource
    private CodeItemService codeItemService;

    @Override
    public void setServletContext(ServletContext sc) {
        // 把项目名称放到application中
        String ctxPath=sc.getContextPath();
        sc.setAttribute("ctxPath",ctxPath);

        sc.setAttribute("staticServer", Config.STATIC_SERVER);

        //初始化数据字典到application中
        configService.init(sc);
        //channelService.init(sc);
        //codeItemService.init(sc);
    }

}