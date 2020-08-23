package net.gittab.async.rabbitmq.listener;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gittab.async.model.OrderDTO;
import net.gittab.async.model.PayRequest;
import net.gittab.async.model.PayResponse;
import net.gittab.async.rabbitmq.sender.ResponseRabbitMQSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * RequestRabbitMQListener.
 *
 * @author rookiedev
 * @date 2020/8/23 00:48
 **/
@Slf4j
@Service
public class RequestRabbitMQListener {

    @Autowired
    private ResponseRabbitMQSender responseRabbitMQSender;

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "${spring.rabbitmq.listener-request-queue}")
    public void onMessage(PayRequest message) {
        log.info("=============== request queue received message ->{}", message.toString());
        // 根据支付请求模拟网关支付
        log.info("发起网关支付 -> {}", message.toString());
        TimeUnit.SECONDS.sleep(3);
        // 然后将支付结果放到队列
        PayResponse response = new PayResponse();
        response.setSuccess(true);
        response.setPayId(1L);
        response.setOrderDTO(message.getOrderDTO());
        log.info("=============== 网关支付成功，将结果放到结果队列 ->{}", message.toString());
        this.responseRabbitMQSender.send(response);

    }
}
