package com.ister.domain;

public class Location extends BaseEntity {

    private String name;
    private Double latitude;
    private Double longitude;
    private Things thing;

    public Things getThing() {
        return thing;
    }
    public void setThing(Things thing) {
        this.thing = thing;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
