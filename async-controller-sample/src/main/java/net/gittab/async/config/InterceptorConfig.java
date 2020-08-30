package net.gittab.async.config;

import net.gittab.async.interceptor.AsyncInterceptor;
import net.gittab.async.interceptor.NormalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig.
 *
 * @author rookiedev
 * @date 2020/8/30 16:28
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /// registry.addInterceptor(new NormalInterceptor());
        registry.addInterceptor(new AsyncInterceptor());
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // TODO register callable interceptor, default timeout, task executor
        // configurer.registerCallableInterceptors();
        // configurer.registerDeferredResultInterceptors();
        // configurer.setDefaultTimeout();
        // configurer.setTaskExecutor();

    }
}
