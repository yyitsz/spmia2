package com.thoughtmechanix.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ResponseFilter implements GlobalFilter, Ordered {
    private static final int FILTER_ORDER = 1;
    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).doFinally(s -> {
            FilterUtils filterUtils = new FilterUtils(exchange);

            logger.debug("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId());
            exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
            logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
        });
    }

    @Override
    public int getOrder() {
        return FILTER_ORDER;
    }
}
