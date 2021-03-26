package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.common.utils.UserContextHolder;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class LicenseService {

    public static final String ORGANIZATION_SERVICE = "organizationService";
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;

//    @Autowired
//    private OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    private OrganizationFeignClient organizationRestClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = getOrganization(organizationId);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    @CircuitBreaker(name = ORGANIZATION_SERVICE)
    //@RateLimiter(name = ORGANIZATION_SERVICE)
    @Bulkhead(name = ORGANIZATION_SERVICE)
    //@Retry(name = ORGANIZATION_SERVICE)
    //@TimeLimiter(name = ORGANIZATION_SERVICE)
    private Organization getOrganization(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

    private void randomlyRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* @HystrixCommand(//fallbackMethod = "buildFallbackLicenseList",
             threadPoolKey = "licenseByOrgThreadPool",
             threadPoolProperties =
                     {@HystrixProperty(name = "coreSize", value = "30"),
                             @HystrixProperty(name = "maxQueueSize", value = "10")},
             commandProperties = {
                     @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                     @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                     @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                     @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                     @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}
     )*/
    @CircuitBreaker(name = ORGANIZATION_SERVICE
            //,fallbackMethod = "buildFallbackLicenseList"
    )
    //@RateLimiter(name = ORGANIZATION_SERVICE)
    @Bulkhead(name = ORGANIZATION_SERVICE, type = Bulkhead.Type.THREADPOOL)
    //@Retry(name = ORGANIZATION_SERVICE)
    //@TimeLimiter(name = ORGANIZATION_SERVICE)
    public List<License> getLicensesByOrg(String organizationId) {
        log.debug("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();

        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available");

        fallbackList.add(license);
        return fallbackList;
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.deleteById(license.getLicenseId());
    }

}
