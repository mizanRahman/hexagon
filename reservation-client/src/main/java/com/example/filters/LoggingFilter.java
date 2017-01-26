package com.example.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mac on 1/15/17.
 */
@Slf4j
public class LoggingFilter extends ZuulFilter {

    private static final String ZUUL_PRE_FILTER = "pre";

    @Override
    public String filterType() {
        return ZUUL_PRE_FILTER;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("Gateway: {} {}", request.getMethod(), request.getRequestURI());
        return null;
    }
}
