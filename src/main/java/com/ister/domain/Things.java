package com.ister.domain;

import java.util.Map;

public class Things extends BaseEntity<Long> implements Cloneable{

    private String name;
    private String serialNumber;
    private User user;
    private Location location;
    private Map<String,Object> attributes;

    private TelemetryData telemetryData;

    public TelemetryData getTelemetryData() {
        return telemetryData;
    }

    public void setTelemetryData(TelemetryData telemetryData) {
        this.telemetryData = telemetryData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
