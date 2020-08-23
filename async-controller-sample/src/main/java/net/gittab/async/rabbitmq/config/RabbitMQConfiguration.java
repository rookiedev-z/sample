package net.gittab.async.rabbitmq.config;

import lombok.Data;

/**
 * RabbitMQConfiguration.
 *
 * @author rookiedev
 * @date 2020/8/23 00:07
 **/
@Data
public class RabbitMQConfiguration {

    private String queue;

    private String exchange;

    private String routingKey;
}
