package cn.ewsd.base.config;

import cn.ewsd.system.interceptor.LoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class LoggerConfguration extends WebMvcConfigurerAdapter
{
    @Bean
    public LoggerInterceptor loggerInterceptor(){
        return new LoggerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor()).addPathPatterns("/**");
    }
}
