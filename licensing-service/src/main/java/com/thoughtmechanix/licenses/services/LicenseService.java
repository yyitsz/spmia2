package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.model.License;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LicenseService {

    public License getLicense(String licenseId) {
        return License.builder()
                .id(licenseId)
                .organizationId(UUID.randomUUID().toString())
                .productName("Test Product Name")
                .licenseType("PerSeat")
                .build();
    }

    public void saveLicense(License license) {

    }

    public void updateLicense(License license) {

    }

    public void deleteLicense(License license) {

    }

}
