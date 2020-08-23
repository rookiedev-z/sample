package net.gittab.async.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gittab.async.service.TaskService;

/**
 * TaskServiceImpl.
 *
 * @author rookiedev
 * @date 2020/8/13 5:34 下午
 **/
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @SneakyThrows
    @Override
    public String execute() {
        log.info("========== task execute start");
        TimeUnit.SECONDS.sleep(6);
//        if(true){
//            throw new IllegalAccessException();
//        }
        log.info("========== task execute end");
        return "task execute success";
    }

    @SneakyThrows
    @Async
    @Override
    public void asyncExecute() {
        log.info("========== task async execute start");
        TimeUnit.SECONDS.sleep(5);
        log.info("========== task async execute end");
    }
}
