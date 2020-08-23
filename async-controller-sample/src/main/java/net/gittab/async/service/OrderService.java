package net.gittab.async.service;

import net.gittab.async.domain.Order;
import net.gittab.async.model.PurchaseResultVO;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * OrderService.
 *
 * @author rookiedev
 * @date 2020/8/23 02:30
 **/
public interface OrderService {

    Order purchase(Long productId, DeferredResult<PurchaseResultVO> deferredResult);
}
