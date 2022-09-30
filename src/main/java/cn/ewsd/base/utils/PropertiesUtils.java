package cn.ewsd.base.utils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

public class PropertiesUtils {
    private static String BOOT_STRAP_YMP_NAME = "bootstrap.yml";

    public static Object getBootstrapYml(Object key){
        Resource resource = new ClassPathResource(BOOT_STRAP_YMP_NAME);
        Properties properties = null;
        try {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(resource);
            properties =  yamlFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties.get(key);
    }
}
