package net.gittab.async.controller;

import lombok.extern.slf4j.Slf4j;
import net.gittab.async.domain.Order;
import net.gittab.async.model.PurchaseResultVO;
import net.gittab.async.service.OrderService;
import net.gittab.async.service.impl.DeferredResultStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * OrderController.
 *
 * @author rookiedev
 * @date 2020/8/23 02:28
 **/
@Slf4j
@RestController
@RequestMapping("product/{productId}")
public class OrderController {

    @Autowired
    private DeferredResultStore deferredResultStore;

    @Autowired
    private OrderService orderService;

    @PostMapping("purchase")
    public DeferredResult<PurchaseResultVO> purchase(@PathVariable("productId") Long productId){
        log.info("========== received purchase request");
        DeferredResult<PurchaseResultVO> deferredResult = new DeferredResult<>();
        Order order = this.orderService.purchase(productId, deferredResult);
        deferredResult.onError((throwable) -> {
            log.error("purchase error", throwable);
            this.deferredResultStore.remove(order.getUserId(), order.getId());
        });
        deferredResult.onTimeout(() -> this.deferredResultStore.remove(order.getUserId(), order.getId()));
        deferredResult.onCompletion(() -> this.deferredResultStore.remove(order.getUserId(), order.getId()));
        log.info("========== the servlet thread that received purchase request has been released");
        return deferredResult;
    }
}
