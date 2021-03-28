package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.common.utils.UserContextHolder;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class LicenseServiceImpl implements LicenseService {


    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;

    @Autowired
    private OrganizeService organizeService;

//    @Autowired
//    private OrganizationRestTemplateClient organizationRestClient;


    @Override
    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        log.info("Thread ID {}", Thread.currentThread().getId());

        //Organization org = organizeService.getOrganization(organizationId);
        Organization org = null;
        try {
            org = organizeService.getOrganizationAsync(organizationId).get();
        } catch (InterruptedException e) {
            log.error("interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("error", e);
            //throw e.getCause();
        }
        if(org == null) {
            org = new Organization();
        }
        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
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
    @Override
    // @CircuitBreaker(name = ORGANIZATION_SERVICE
    //,fallbackMethod = "buildFallbackLicenseList"
    //   )
    //@RateLimiter(name = ORGANIZATION_SERVICE)
    //@Bulkhead(name = ORGANIZATION_SERVICE, type = Bulkhead.Type.THREADPOOL)
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

    @Override
    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        licenseRepository.save(license);
    }

    @Override
    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    @Override
    public void deleteLicense(License license) {
        licenseRepository.deleteById(license.getLicenseId());
    }

}
