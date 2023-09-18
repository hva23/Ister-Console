package com.ister.domain;

public class Location extends BaseEntity<Long> {

    private String name;
    private Double latitude;
    private Double longitude;
    private Things thing;
    private String province;
    private String city;

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
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
