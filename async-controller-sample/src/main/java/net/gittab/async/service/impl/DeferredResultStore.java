package net.gittab.async.service.impl;

import net.gittab.async.model.PurchaseResultVO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DeferredResultStore.
 *
 * @author rookiedev
 * @date 2020/8/23 17:15
 **/
@Component
public class DeferredResultStore {

    private static final Map<String, DeferredResult> DEFERRED_RESULTS = new ConcurrentHashMap<>();

    public void put(Long userId, Long orderId, DeferredResult<PurchaseResultVO> deferredResult) {
        DEFERRED_RESULTS.putIfAbsent(assembleKey(userId, orderId), deferredResult);
    }

    public DeferredResult get(Long userId, Long orderId) {
        return DEFERRED_RESULTS.get(assembleKey(userId, orderId));
    }

    public void remove(Long userId, Long orderId) {
        DEFERRED_RESULTS.remove(assembleKey(userId, orderId));
    }

    private String assembleKey(Long userId, Long orderId){
        return "purchase:" + userId + ":" + orderId;
    }


}
