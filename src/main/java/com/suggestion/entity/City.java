package com.suggestion.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private String geonameId;
    private String name;
    private Double latitude;
    private Double longitude;
    private String countryName;
    private String countryCode;
    private String altCountryCode;
    private String geoName;

    public City() {

    }

    public City(String geonameId, String name, Double latitude, Double longitude, String countryName, String countryCode, String altCountryCode, String geoName) {
        this.geonameId = geonameId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.altCountryCode = altCountryCode;
        this.geoName = geoName;
    }
}
