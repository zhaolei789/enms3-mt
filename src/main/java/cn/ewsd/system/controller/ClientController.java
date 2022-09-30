package cn.ewsd.system.controller;

import cn.ewsd.mdata.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ClientController {

    /*//第1.2种方式
    @Autowired
    private LoadBalancerClient loadBalancerClient;*/

    //第1.3种方式
    /*@Autowired
    private RestTemplate restTemplate3;*/

    @Resource
    private UserService userService;

    @GetMapping("/system/client")
    public String getUser(String userNameId) {
        //一、第1种方式，使用restTemplate

        /*//第1.1种方式（直接使用restTemplate,url写死）
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8080/ucenter/user/server", String.class);
        log.info("response={}", response);
        return response;*/

        /*//第1.2种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate）
        RestTemplate restTemplate2 = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("MDATA");
        String url2 = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()+"/ucenter/user/server");
        String response2 = restTemplate2.getForObject(url2, String.class);
        //log.info("response={}", response2);
        return response2;*/

        //第1.3种方式（利用@LoadBalanced，配置RestTemplateConfig类，可在restTemplate里使用应用名字）
        //String response3 = restTemplate3.getForObject("http://MDATA/mdata/user/server", String.class);
        //log.info("response={}", response3);
        //return response3;

        //第2种方式：使用Feign
        String response4 = "user server";
        //log.info("response4={}", response4);
        return response4;
    }

    @GetMapping("/getUserByUserNameId")
    public Object getUserByUserNameId(String userNameId) {
        Object user = userService.getUserByUserNameId(userNameId);
        return user;
    }

}
