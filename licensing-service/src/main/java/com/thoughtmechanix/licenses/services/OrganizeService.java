package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.model.Organization;

import java.util.concurrent.CompletableFuture;

public interface OrganizeService {

    Organization getOrganization(String organizationId);

    CompletableFuture<Organization> getOrganizationAsync(String organizationId);
}
