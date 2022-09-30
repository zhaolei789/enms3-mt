package cn.ewsd.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("boy")
@RefreshScope
public class EnvConfig {

    private String name;

    private Integer age;
}
