package cn.ewsd.system.controller;

import cn.ewsd.common.annotation.ControllerLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/export")
public class ExportController extends SystemBaseController {

    @RequestMapping("/index")
    @ControllerLog(description = "打开Export模块管理页面")
    public String index() {
        return "system/user/index";
    }

    @RequestMapping("/getPageSet")
    @ControllerLog(description = "打开Export模块管理页面")
    public String getPageSet() {
        return "system/user/index";
    }

}
