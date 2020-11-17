package com.px.membership.configuration;

import java.util.List;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * RedissonConfig.
 *
 * @author rookiedev 2020/10/26 17:33
 **/
@Component
@Configuration
@ConfigurationProperties(prefix = "spring.redis.aws")
@Data
public class RedissonConfig {

    private RedisConfigDomain master;

    private List<RedisConfigDomain> slaves;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config();
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
        masterSlaveServersConfig.setMasterAddress("redis://" + this.master.getHost() + ":" + this.master.getPort());
        this.slaves.forEach(slave -> masterSlaveServersConfig.addSlaveAddress("redis://" + slave.getHost() + ":" + slave.getPort()));
        return Redisson.create(config);
    }

}
