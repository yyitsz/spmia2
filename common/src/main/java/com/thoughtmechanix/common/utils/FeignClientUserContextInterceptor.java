package com.thoughtmechanix.common.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class FeignClientUserContextInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        log.info("Thread ID {}", Thread.currentThread().getId());

        template.header(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        template.header(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
    }
}