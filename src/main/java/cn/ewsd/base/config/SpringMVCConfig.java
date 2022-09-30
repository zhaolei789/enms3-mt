package cn.ewsd.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    /*@Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/login/login", "/login/logout");
    }*/

}
