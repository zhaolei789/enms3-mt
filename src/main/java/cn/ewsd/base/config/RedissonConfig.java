package cn.ewsd.base.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置类
 * Created on 2018/6/19
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    //通过配置中心配置文件配置redisson
    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password)
                .setDatabase(7);

        Codec codec = new org.redisson.codec.JsonJacksonCodec();
        config.setCodec(codec);

        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});

        return Redisson.create(config);
    }

    //通过外部配置文件配置redisson：https://blog.csdn.net/lms1719/article/details/83652578
    /*@Bean
    public RedissonClient redisson() throws IOException {
        // 本例子使用的是yaml格式的配置文件，读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson.yml"));
        return Redisson.create(config);
    }*/

}