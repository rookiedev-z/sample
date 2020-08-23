package net.gittab.async.rabbitmq.listener;

import lombok.extern.slf4j.Slf4j;
import net.gittab.async.converter.OrderConverter;
import net.gittab.async.domain.Order;
import net.gittab.async.model.OrderDTO;
import net.gittab.async.model.PayResponse;
import net.gittab.async.model.PurchaseResultVO;
import net.gittab.async.service.impl.DeferredResultStore;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * ResponseRabbitMQListener.
 *
 * @author rookiedev
 * @date 2020/8/23 00:48
 **/
@Slf4j
@Service
public class ResponseRabbitMQListener {

    @Autowired
    private DeferredResultStore deferredResultStore;

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "${spring.rabbitmq.listener-response-queue}")
    public void onMessage(PayResponse message) {
        log.info("=============== response queue received message ->{}", message.toString());
        // 根据支付结果，查询并且更新数据库订单状态
        OrderDTO orderDTO = message.getOrderDTO();
        log.info("查询数据库订单信息 -> {}", orderDTO.toString());
        Order order = OrderConverter.INSTANCE.dto2Domain(orderDTO);
        boolean success = message.getSuccess();
        if(success){
            order.setStatus(1);
            order.setPayId(message.getPayId());
        }else{
            order.setStatus(-1);
            order.setErrorMsg(message.getErrorMsg());
        }
        log.info("保存订单最新状态信息 -> {}", order.toString());
        // 设置支付返回结果
        DeferredResult<PurchaseResultVO> deferredResult = this.deferredResultStore.get(order.getUserId(), order.getId());
        if(deferredResult == null){
            return;
        }
        PurchaseResultVO resultVO = new PurchaseResultVO();
        resultVO.setSuccess(success);
        resultVO.setProductId(order.getProductId());
        resultVO.setErrorMsg(message.getErrorMsg());
        log.info("设置支付结果返回 -> {}", resultVO.toString());
        deferredResult.setResult(resultVO);
    }
}
