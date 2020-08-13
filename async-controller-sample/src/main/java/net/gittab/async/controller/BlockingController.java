package net.gittab.async.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;
import net.gittab.async.service.TaskService;

/**
 * BlockingController.
 *
 * @author rookiedev
 * @date 2020/8/13 5:21 下午
 **/
@Slf4j
@RestController
@RequestMapping("v1/rookiedev")
public class BlockingController {

    @Autowired
    private TaskService taskService;

    @GetMapping("blockEndpoint")
    public String blockEndpoint(){
        log.info("========== received block endpoint request");
        String result = this.taskService.executeTask();
        log.info("========== the servlet thread that received the request has been released");
        return result;
    }

    @GetMapping("callableEndpoint")
    public Callable<String> callableEndpoint(){
        log.info("========== received callable endpoint request");
        Callable<String> callable = this.taskService :: executeTask;
        log.info("========== the servlet thread that received the request has been released");
        return callable;
    }

    @GetMapping("deferredResultEndpoint")
    public DeferredResult<String> deferredResultEndpoint(){
        log.info("========== received deferred result endpoint request");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        CompletableFuture.supplyAsync(this.taskService :: executeTask)
                .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
        log.info("========== the servlet thread that received the request has been released");
        return deferredResult;
    }
}
