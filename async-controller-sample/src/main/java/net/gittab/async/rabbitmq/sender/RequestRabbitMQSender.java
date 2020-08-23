package net.gittab.async.rabbitmq.sender;

import lombok.extern.slf4j.Slf4j;
import net.gittab.async.model.OrderDTO;
import net.gittab.async.model.PayRequest;
import net.gittab.async.rabbitmq.config.RabbitMQConfiguration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * RabbitMQSender.
 *
 * @author rookiedev
 * @date 2020/8/22 15:00
 **/
@Slf4j
@Component
public class RequestRabbitMQSender {

    @Autowired
    @Qualifier("requestRabbitMQ")
    private RabbitMQConfiguration requestRabbitMQ;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(PayRequest payRequest) {
        this.rabbitTemplate.convertAndSend(this.requestRabbitMQ.getExchange(), this.requestRabbitMQ.getRoutingKey(), payRequest);
        log.info("=============== send message to request queue ->{}", payRequest.toString());

    }
}
