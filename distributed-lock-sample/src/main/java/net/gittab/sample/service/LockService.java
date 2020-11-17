package net.gittab.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gittab.sample.domain.Order;
import net.gittab.sample.domain.Payment;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LockService.
 *
 * @author rookiedev 2020/10/27 15:11
 **/
@Slf4j
@Service
public class LockService {

    @Autowired
    private RedissonClient redissonClient;

    private static final List<Order> A_SERVICE_LIST = new ArrayList<>();

    public void save1(String userId, String txnId) {
        // simulate B service to verify the existence of uniqueId
        log.info("request--" + Thread.currentThread().getId() + "===A call B service to verify the existence of {}", txnId);
        String payId = this.findByTxnId(userId, txnId);

        if(payId == null){
            log.info("request--" + Thread.currentThread().getId() + "===transaction {} does not exist, perform insert operation", userId, txnId);
            // simulate call B service save pay data
            log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} start", txnId);
            payId = this.savePayment(userId, txnId);
            log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} finish", txnId);

            // save order data
            log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
            this.saveOrder(userId, payId);
            log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
        }else{
            // verify the existence of payId
            log.info("request--" + Thread.currentThread().getId() + "===A service to verify the existence of {}", payId);
            String orderId = this.findByPayId(userId, payId);
            if(orderId == null){
                // save order data
                log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
                this.saveOrder(userId, payId);
                log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
            }
        }

        this.printSaveResult();

    }

    public synchronized void save2(String userId, String txnId) {
        // simulate B service to verify the existence of uniqueId
        log.info("request--" + Thread.currentThread().getId() + "===A call B service to verify the existence of {}", txnId);
        String payId = this.findByTxnId(userId, txnId);

        if(payId == null){
            log.info("request--" + Thread.currentThread().getId() + "===transaction {} does not exist, perform insert operation", userId, txnId);
            // simulate call B service save pay data
            log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} start", txnId);
            payId = this.savePayment(userId, txnId);
            log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} finish", txnId);

            // save order data
            log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
            this.saveOrder(userId, payId);
            log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
        }else{
            // verify the existence of payId
            log.info("request--" + Thread.currentThread().getId() + "===A service to verify the existence of {}", payId);
            String orderId = this.findByPayId(userId, payId);
            if(orderId == null){
                // save order data
                log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
                this.saveOrder(userId, payId);
                log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
            }
        }

        this.printSaveResult();
    }

    public void save3(String userId, String txnId) {
        synchronized (userId.intern()){
            log.info("request--" + Thread.currentThread().getId() + "===A call B service to verify the existence of {}", txnId);
            String payId = this.findByTxnId(userId, txnId);

            if(payId == null){
                log.info("request--" + Thread.currentThread().getId() + "===transaction {} does not exist, perform insert operation", userId, txnId);
                // simulate call B service save pay data
                log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} start", txnId);
                payId = this.savePayment(userId, txnId);
                log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} finish", txnId);

                // save order data
                log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
                this.saveOrder(userId, payId);
                log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
            }else{
                // verify the existence of payId
                log.info("request--" + Thread.currentThread().getId() + "===A service to verify the existence of {}", payId);
                String orderId = this.findByPayId(userId, payId);
                if(orderId == null){
                    // save order data
                    log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
                    this.saveOrder(userId, payId);
                    log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
                }
            }

            this.printSaveResult();
        }
    }

    public void save4(String userId, String txnId) {

        RLock rLock = this.redissonClient.getLock(userId);
        // rLock.lock(5000, TimeUnit.MILLISECONDS);
         try {
             if(rLock.tryLock(500, 5000, TimeUnit.MILLISECONDS)){
                try {
                    log.info("request--" + Thread.currentThread().getId() + "===A call B service to verify the existence of {}", txnId);
                    String payId = this.findByTxnId(userId, txnId);

                    if(payId == null){
                        log.info("request--" + Thread.currentThread().getId() + "===transaction {} does not exist, perform insert operation", userId, txnId);
                        // simulate call B service save pay data
                        log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} start", txnId);
                        payId = this.savePayment(userId, txnId);
                        log.info("request--" + Thread.currentThread().getId() + "===A call B service to save transaction {} finish", txnId);

                        // save order data
                        log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
                        this.saveOrder(userId, payId);
                        log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
                    }else{
                        // verify the existence of payId
                        log.info("request--" + Thread.currentThread().getId() + "===A service to verify the existence of {}", payId);
                        String orderId = this.findByPayId(userId, payId);
                        if(orderId == null){
                            // save order data
                            log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order start", userId);
                            this.saveOrder(userId, payId);
                            log.info("request--" + Thread.currentThread().getId() + "===A service save {} user's order finish", userId);
                        }
                    }
                }finally {
                    rLock.unlock();
                }
            }else{
                throw new RuntimeException("transaction is being processing");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        this.printSaveResult();

    }

    private String findByPayId(String userId, String payId){
        Optional<Order> order = A_SERVICE_LIST.stream()
                .filter(item -> Objects.equals(item.getUserId(), userId) && Objects.equals(item.getPayId(), payId))
                .findFirst();
        return order.map(Order::getId).orElse(null);
    }

    @SneakyThrows
    private void saveOrder(String userId, String payId){
//        TimeUnit.SECONDS.sleep(3);
        // simulate B service insert data
        String id = UUID.randomUUID().toString();
        Order order = new Order();
        order.setId(id);
        order.setUserId(userId);
        order.setPayId(payId);
        A_SERVICE_LIST.add(order);
    }

    private void printSaveResult(){
        // after execution, print the saved results of A service and B service
        log.info("request--" + Thread.currentThread().getId() + "=============A service==========");
        A_SERVICE_LIST.forEach(item -> log.info(item.toString()));

        log.info("request--" + Thread.currentThread().getId() + "=============B service==========");
        B_SERVICE_LIST.forEach(item -> log.info(item.toString()));

    }

    public void clearData(){
        // clear data to next test
        A_SERVICE_LIST.clear();
        B_SERVICE_LIST.clear();
    }

    // simulate B service

    private static final List<Payment> B_SERVICE_LIST = new ArrayList<>();

    private String findByTxnId(String userId, String txnId) {
        B_SERVICE_LIST.forEach(item -> {

        });
        Optional<Payment> payment = B_SERVICE_LIST.stream()
                .filter(item -> Objects.equals(item.getUserId(), userId) && Objects.equals(item.getTxnId(), txnId))
                .findFirst();
        return payment.map(Payment::getId).orElse(null);
    }

    @SneakyThrows
    private String savePayment(String userId, String txnId)  {
        TimeUnit.SECONDS.sleep(3);
        // simulate B service insert data
        String id = UUID.randomUUID().toString();
        Payment payment = new Payment();
        payment.setId(id);
        payment.setUserId(userId);
        payment.setTxnId(txnId);
        B_SERVICE_LIST.add(payment);
        // simulate return primary key of the inserted data
        return id;
    }
}
