package com.thoughtmechanix.gateway.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AbTestingRoute {
    String serviceName;
    String active;
    String endpoint;
    Integer weight;

}