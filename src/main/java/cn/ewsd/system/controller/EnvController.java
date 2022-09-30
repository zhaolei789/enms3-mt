package cn.ewsd.system.controller;

import cn.ewsd.system.properties.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/env")
public class EnvController extends SystemBaseController {

    @Autowired
    private EnvConfig envConfig;

    @Value("${env}")
    private String env;

    @RequestMapping("index")
    public String index() throws Exception {
        return display();
    }

    @RequestMapping(value = "print")
    public Object print() {
        return env;
    }

    @RequestMapping(value = "print2")
    public Object print2() {

        return "name:" + envConfig.getName() + " age:" + envConfig.getAge();
    }
}
