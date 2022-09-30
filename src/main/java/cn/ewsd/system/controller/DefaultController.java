package cn.ewsd.system.controller;

import cn.ewsd.system.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
    @RequestMapping("/")
    public class DefaultController extends SystemBaseController {

    @Resource
    private ConfigService configService;


    @RequestMapping("/")
    public String defaultIndex(@RequestParam(required = false) String portal) throws Exception {
        String title = "企业管理信息系统快速开发平台";
        request.setAttribute("title", title);
        Map<String, Map<String, String>> stringMapMap = configService.redisCacheConfig();
        Map<String, String> sysConfig = stringMapMap.get("sysConfig");
        request.setAttribute("sysConfig",sysConfig);
        return "system/login/index_bootstrap";
    }


}
