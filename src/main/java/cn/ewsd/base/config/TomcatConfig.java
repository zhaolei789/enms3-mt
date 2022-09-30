package cn.ewsd.base.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    /*@Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个数据大小
        factory.setMaxFileSize(maxFileSize); //KB,MB
        //总上传数据大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }*/

}