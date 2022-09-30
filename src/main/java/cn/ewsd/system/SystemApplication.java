package cn.ewsd.system;


import cn.ewsd.base.utils.JwtFilter;
import com.dtflys.forest.springboot.annotation.ForestScan;
import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;


/**
 * @className SystemApplication
 * @description 启动类
 */
@EnableSwagger2
@SpringBootApplication
@ComponentScan(
        basePackages = {"cn.ewsd.**.*"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {cn.ewsd.common.bean.UserInfo.class})
)
@MapperScan(basePackages = "cn.ewsd.*.mapper") //将项目中对应的mapper类的路径加进来
@EnableTransactionManagement //开启事务
@EnableCaching  //开启缓存
@ForestScan(basePackages = "cn.ewsd.forest.client")
public class SystemApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //注册JWT拦截器，可以在配置类中，也可以直接在SpringBoot的入口类中
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        //添加需要拦截的url
        List<String> urlPatterns = Lists.newArrayList();
        urlPatterns.add("*");
        registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
        return registrationBean;
    }

}
