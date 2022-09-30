package cn.ewsd.mdata.controller;

//import cn.ewsd.system.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小策一喋<xvpindex@qq.com>
 * @className IndexController
 * @description
 * @date 2018-03-28 20:40
 */
@Controller
@RequestMapping(value = "/mdata/index")
public class IndexController {

//    @Autowired
//    private GirlProperties girlProperties;

    /**
     * @return java.lang.String
     * @functionName say
     * @description
     * @author 小策一喋<xvpindex@qq.com>
     * @date 2018-03-28 20:41
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "ucenter/index/index";
    }

//    @ResponseBody
//    @RequestMapping(value = {"/hello/{id}", "/hi"}, method = RequestMethod.GET)
//    public String say(
//            @PathVariable("id") String id,
//            @RequestParam(value = "type", required = false, defaultValue = "product") String type
//    ) {
//        return girlProperties.getCupSize();
//    }

}
