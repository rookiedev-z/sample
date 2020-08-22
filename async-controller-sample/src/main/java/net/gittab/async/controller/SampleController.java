package net.gittab.async.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;
import net.gittab.async.service.TaskService;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 * SampleController.
 *
 * @author rookiedev
 * @date 2020/8/13 5:21 下午
 **/
@Slf4j
@RestController
@RequestMapping("rookiedev")
public class SampleController {

    @Autowired
    private TaskService taskService;

    @GetMapping("blockEndpoint")
    public String blockEndpoint(){
        log.info("========== received block endpoint request");
        String result = this.taskService.execute();
        log.info("========== the servlet thread that received the request has been released");
        return result;
    }

    @GetMapping("asyncEndpoint")
    public void asyncEndpoint(){
        log.info("========== received async endpoint request");
        this.taskService.asyncExecute();
        log.info("========== the servlet thread that received the request has been released");
    }

    @GetMapping("callableEndpoint")
    public Callable<String> callableEndpoint(){
        log.info("========== received callable endpoint request");
        Callable<String> callable = () -> this.taskService.execute();
        log.info("========== the servlet thread that received the request has been released");
        return callable;
    }

    @GetMapping("webAsyncTaskEndpoint")
    public WebAsyncTask<String> webAsyncTaskEndpoint(){
        log.info("========== received web async task endpoint request");
        Callable<String> callable = () -> this.taskService.execute();
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(3000, callable);
        webAsyncTask.onCompletion(() -> log.info("========== execute finish"));

        webAsyncTask.onTimeout(() -> "execute timeout");

        webAsyncTask.onError(() -> "execute error");
        log.info("========== the servlet thread that received the request has been released");
        return webAsyncTask;
    }

    @GetMapping("deferredResultEndpoint")
    public DeferredResult<String> deferredResultEndpoint(){
        log.info("========== received deferred result endpoint request");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        CompletableFuture.supplyAsync(this.taskService ::execute)
                .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
        log.info("========== the servlet thread that received the request has been released");
        return deferredResult;
    }
}
