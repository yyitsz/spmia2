package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.model.License;

import java.util.List;

public interface LicenseService {
    License getLicense(String organizationId, String licenseId);

    List<License> getLicensesByOrg(String organizationId);

    void saveLicense(License license);

    void updateLicense(License license);

    void deleteLicense(License license);
}
