package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/system/test")
public class TestController extends SystemBaseController {

    @RequestMapping("/index")
    @ControllerLog(description = "打开Test模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "system/portal/index";
    }

}
