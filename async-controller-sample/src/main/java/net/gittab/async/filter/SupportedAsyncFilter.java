package net.gittab.async.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * SupportedAsyncFilter.
 *
 * @author rookiedev
 * @date 2020/8/30 16:22
 **/
@Slf4j
@WebFilter(filterName = "normalFilter", urlPatterns = "/*", asyncSupported = true)
public class SupportedAsyncFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("========== normal filter init execute");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("========== normal filter doFilter execute");
        chain.doFilter(request, response);
    }
}
