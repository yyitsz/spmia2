package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.model.Organization;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrganizeServiceImpl implements OrganizeService {
    public static final String ORGANIZATION_SERVICE = "organizationService";

    @Autowired
    private OrganizationFeignClient organizationClient;

    @Override
    @CircuitBreaker(name = ORGANIZATION_SERVICE)
    //@RateLimiter(name = ORGANIZATION_SERVICE)
    @Bulkhead(name = ORGANIZATION_SERVICE, type = Bulkhead.Type.THREADPOOL)
    //@Retry(name = ORGANIZATION_SERVICE)
    //@TimeLimiter(name = ORGANIZATION_SERVICE)
    public Organization getOrganization(String organizationId) {
        log.debug("Get Org for {} from remote.", organizationId);
        return organizationClient.getOrganization(organizationId);
    }

    @Override
    @CircuitBreaker(name = ORGANIZATION_SERVICE)
    //@RateLimiter(name = ORGANIZATION_SERVICE)
    @Bulkhead(name = ORGANIZATION_SERVICE, type = Bulkhead.Type.THREADPOOL)
    //@Retry(name = ORGANIZATION_SERVICE)
    //@TimeLimiter(name = ORGANIZATION_SERVICE)
    public CompletableFuture<Organization> getOrganizationAsync(String organizationId) {
        log.debug("Get Org for {} from remote.", organizationId);
        return CompletableFuture.completedFuture(organizationClient.getOrganization(organizationId));
    }
}
