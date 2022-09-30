package cn.ewsd.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"swagger测试接口"})
@RestController
@RequestMapping("/system/swagger")
public class SwaggerController {


    @ApiOperation(value = "/swagger/system方法")
    @GetMapping("/system")
    public String system() {
        return "system方法";
    }

}
