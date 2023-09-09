package com.ister.domain;

import java.util.Map;

public class Things extends BaseEntity {

    private String name;
    private String serialNumber;
    private User user;
    private Location location;
    private Map<String,Object> attributes;

}
