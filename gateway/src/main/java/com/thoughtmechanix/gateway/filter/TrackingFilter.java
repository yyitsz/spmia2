package com.thoughtmechanix.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class TrackingFilter implements GlobalFilter, Ordered {

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        FilterUtils filterUtils = new FilterUtils(exchange);

        boolean correlationIdPresent = filterUtils.getCorrelationId() != null;
        if (correlationIdPresent) {
            log.debug("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            log.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }

        log.debug("Processing incoming request for {}.", exchange.getRequest().getURI());
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 1;
    }
}