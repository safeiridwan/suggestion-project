package com.suggestion.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private String geonameId;
    private String name;
    private String latitude;
    private String longitude;
    private String countryName;
    private String countryCode;
    private String altCountryCode;

    public City() {

    }

    public City(String geonameId, String name, String latitude, String longitude, String countryName, String countryCode, String altCountryCode) {
        this.geonameId = geonameId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.altCountryCode = altCountryCode;
    }
}
