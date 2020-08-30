package net.gittab.async.controller;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import net.gittab.async.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * AsyncServletController.
 *
 * @author rookiedev
 * @date 2020/8/30 13:51
 **/
@Slf4j
@RestController
@RequestMapping("rookiedev")
public class AsyncServletController {

    @Autowired
    private TaskService taskService;

    private ExecutorService executorService = new ThreadPoolExecutor(10, 50,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1000), new DefaultThreadFactory("async-"), new ThreadPoolExecutor.CallerRunsPolicy());

    @GetMapping("asyncServlet")
    public void asyncServlet(HttpServletRequest servletRequest){
        log.info("========== received async servlet endpoint request");
        AsyncContext asyncContext = servletRequest.startAsync();
        asyncContext.setTimeout(4000);
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                log.info("========== async servlet execute complete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                log.info("========== async servlet execute timeout");
                Throwable exx = asyncEvent.getThrowable();
                Throwable ex = exx != null ? exx : new IllegalStateException("Async operation timeout.");
                asyncContext.getResponse().getWriter().println(ex);
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                log.info("========== async servlet execute error");
                Throwable ex = asyncEvent.getThrowable();
                asyncContext.getResponse().getWriter().println(ex);
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                log.info("========== async servlet execute start");
            }
        });
        this.executorService.submit(() -> {
            try {
                String result = this.taskService.execute();
                asyncContext.getResponse().getWriter().println(result);
            } catch (Exception e) {
                try {
                    asyncContext.getResponse().getWriter().println(e.getCause());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }finally {
                asyncContext.complete();
            }
        });
        log.info("========== the servlet thread that received the request has been released");
    }
}
