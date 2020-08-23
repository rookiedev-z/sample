package net.gittab.async.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.gittab.async.converter.OrderConverter;
import net.gittab.async.domain.Order;
import net.gittab.async.model.PayRequest;
import net.gittab.async.model.PurchaseResultVO;
import net.gittab.async.rabbitmq.sender.RequestRabbitMQSender;
import net.gittab.async.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * OrderServiceImpl.
 *
 * @author rookiedev
 * @date 2020/8/23 02:31
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RequestRabbitMQSender requestRabbitMQSender;

    @Autowired
    private DeferredResultStore deferredResultStore;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order purchase(Long productId, DeferredResult<PurchaseResultVO> deferredResult) {
        // FIXME 获取当前用户 id
        Long currentUserId = 1L;
        // FIXME 模拟查询商品，得到价格信息
        // 构建订单信息
        Order order = new Order();
        order.setId(1L);
        order.setStatus(0);
        order.setProductId(productId);
        order.setAmount(50L);
        order.setUserId(currentUserId);
        // FIXME 保存订单数据到数据库
        // 构建支付请求
        PayRequest payRequest = new PayRequest();
        payRequest.setGateway("we_chat");
        payRequest.setProfileId(1L);
        payRequest.setAmount(order.getAmount());
        payRequest.setOrderDTO(OrderConverter.INSTANCE.domain2DTO(order));
        // 发送支付请求到队列请求支付
        this.requestRabbitMQSender.send(payRequest);
        // 临时保存待响应的结果
        this.deferredResultStore.put(currentUserId, order.getId(), deferredResult);
        return order;
    }
}
