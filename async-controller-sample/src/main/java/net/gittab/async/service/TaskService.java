package net.gittab.async.service;

import org.springframework.scheduling.annotation.Async;

/**
 * TaskService.
 *
 * @author rookiedev
 * @date 2020/8/13 5:33 下午
 **/
public interface TaskService {

    String execute();

    void asyncExecute();
}
