package com.thoughtmechanix.licenses.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
