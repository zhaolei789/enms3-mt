package cn.ewsd.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.datasource")
@RefreshScope
public class DatasourceProperties {

    private String driverClassName;

    private String url;

}
