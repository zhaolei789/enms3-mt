package cn.ewsd.system.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="generator") //接收application.yml中的myProps下面的属性
public class GeneratorConfig extends MCoreBase {
    private Map<String, String> map = new HashMap<>(); //接收prop1里面的属性值

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}