package net.gittab.async.service;

import net.gittab.async.AsyncControllerSampleApplicationTests;
import net.gittab.async.model.OrderDTO;
import net.gittab.async.model.PayRequest;
import net.gittab.async.model.PayResponse;
import net.gittab.async.rabbitmq.sender.RequestRabbitMQSender;
import net.gittab.async.rabbitmq.sender.ResponseRabbitMQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RabbitMQServiceTest.
 *
 * @author rookiedev
 * @date 2020/8/22 17:40
 **/
public class RabbitMQServiceTest extends AsyncControllerSampleApplicationTests {

    @Autowired
    private RequestRabbitMQSender requestRabbitMQSender;

    @Autowired
    private ResponseRabbitMQSender responseRabbitMQSender;

    @Test
    void requestSenderTest(){
        PayRequest request = new PayRequest();
        request.setProfileId(1L);
        this.requestRabbitMQSender.send(request);
    }

    @Test
    void responseSenderTest(){
        PayResponse response = new PayResponse();
        response.setPayId(2L);
        this.responseRabbitMQSender.send(response);
    }
}
