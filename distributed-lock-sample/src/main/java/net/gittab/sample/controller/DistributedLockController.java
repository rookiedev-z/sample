package net.gittab.sample.controller;

import lombok.extern.slf4j.Slf4j;
import net.gittab.sample.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DistributedLockController.
 *
 * @author rookiedev 2020/10/27 15:12
 **/
@Slf4j
@RestController
@RequestMapping("distributed/lock/")
public class DistributedLockController {

    @Autowired
    private LockService lockService;

    @GetMapping("{userId}/callback")
    public void callback(@PathVariable("userId") String userId, @RequestParam("txnId") String txnId){
        log.info("request--" + Thread.currentThread().getId() + "===received the third-party service payment success callback");
        this.lockService.save4(userId, txnId);
    }

    @GetMapping("clear")
    public void clear(){
        this.lockService.clearData();
    }
}
