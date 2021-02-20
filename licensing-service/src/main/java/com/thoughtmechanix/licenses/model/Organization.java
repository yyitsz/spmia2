package com.thoughtmechanix.licenses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Organization {
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
