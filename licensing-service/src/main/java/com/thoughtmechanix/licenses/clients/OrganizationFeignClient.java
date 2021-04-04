package com.thoughtmechanix.licenses.clients;


import com.thoughtmechanix.licenses.model.Organization;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.CompletableFuture;

import static com.thoughtmechanix.licenses.clients.OrganizationFeignClient.ORGANIZATION_SERVICE;

@FeignClient(value = ORGANIZATION_SERVICE, fallback = OrganizationFeignClientFallback.class)
public interface OrganizationFeignClient {
    String ORGANIZATION_SERVICE = "organizationService";

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organizations/{organizationId}",
            consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);

    //@CircuitBreaker(name = ORGANIZATION_SERVICE)
    //@RateLimiter(name = ORGANIZATION_SERVICE)
    @Bulkhead(name = ORGANIZATION_SERVICE, type = Bulkhead.Type.THREADPOOL)
    //@Retry(name = ORGANIZATION_SERVICE)
    //@TimeLimiter(name = ORGANIZATION_SERVICE)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organizations/{organizationId}",
            consumes = "application/json")
    CompletableFuture<Organization> getOrganizationAsync(String organizationId);
}
