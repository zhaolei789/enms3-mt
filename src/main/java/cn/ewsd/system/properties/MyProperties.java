package cn.ewsd.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("my")
@RefreshScope
public class MyProperties {

    private String host;

    private Integer gatewayPort;

    private String configDic;

    private String uploadPath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(Integer gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getConfigDic() {
        return configDic;
    }

    public void setConfigDic(String configDic) {
        this.configDic = configDic;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
