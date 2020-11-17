package net.gittab.sample.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * RedissonConfig.
 *
 * @author rookiedev 2020/10/26 17:33
 **/
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "spring")
public class RedissonConfig {

    private RedisDomain redis;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + this.redis.getHost() + ":" + this.redis.getPort());
        return Redisson.create(config);
    }

}
