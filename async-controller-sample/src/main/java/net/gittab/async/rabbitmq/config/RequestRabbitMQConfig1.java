package net.gittab.async.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RequestRabbitMQConfig.
 *
 * @author rookiedev
 * @date 2020/8/22 14:57
 **/
// @Configuration
public class RequestRabbitMQConfig1 {

    @Autowired
    @Qualifier("requestRabbitMQ")
    private RabbitMQConfiguration requestRabbitMQ;

    @Autowired
    @Qualifier("responseRabbitMQ")
    private RabbitMQConfiguration responseRabbitMQ;

    @Bean("requestRabbitMQ")
    @ConfigurationProperties(prefix = "spring.rabbitmq.request.template")
    public RabbitMQConfiguration requestRabbitMQConfiguration(){
        return new RabbitMQConfiguration();
    }

    @Bean("responseRabbitMQ")
    @ConfigurationProperties(prefix = "spring.rabbitmq.response.template")
    public RabbitMQConfiguration responseRabbitMQConfiguration(){
        return new RabbitMQConfiguration();
    }

    @Bean("requestQueue")
    Queue requestQueue() {
        return new Queue(this.requestRabbitMQ.getQueue(), true);
    }

    @Bean("responseQueue")
    Queue responseQueue() {
        return new Queue(this.responseRabbitMQ.getQueue(), true);
    }

    @Bean("requestExchange")
    DirectExchange requestExchange() {
        return new DirectExchange(this.requestRabbitMQ.getExchange());
    }

    @Bean("responseExchange")
    DirectExchange responseExchange() {
        return new DirectExchange(this.responseRabbitMQ.getExchange());
    }

    @Bean("requestBinding")
    Binding requestBinding(@Qualifier("requestQueue") Queue requestQueue, @Qualifier("requestExchange") DirectExchange requestExchange) {
        return BindingBuilder.bind(requestQueue).to(requestExchange).with(this.requestRabbitMQ.getRoutingKey());
    }

    @Bean("responseBinding")
    Binding responseBinding(@Qualifier("responseQueue") Queue responseQueue, @Qualifier("responseExchange") DirectExchange responseExchange) {
        return BindingBuilder.bind(responseQueue).to(responseExchange).with(this.responseRabbitMQ.getRoutingKey());
    }

//    @Bean
//    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//        simpleMessageListenerContainer.setQueues(queue());
//        simpleMessageListenerContainer.setMessageListener(this.rabbitMQListener);
//        return simpleMessageListenerContainer;
//
//    }

    /**
     * defined rabbitmq message converter.
     * @return jackson2json message converter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


}
