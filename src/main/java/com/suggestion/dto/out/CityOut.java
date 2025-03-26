package com.suggestion.dto.out;

import com.suggestion.entity.City;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CityOut implements Serializable {
    private String name;
    private double latitude;
    private double longitude;
    private Double score;

    public CityOut() {}

    public CityOut(String name, double latitude, double longitude, Double score) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
    }

    public CityOut(City city) {
        this.name = city.getName();
        this.latitude = city.getLatitude();
        this.longitude = city.getLongitude();
        this.score = 0.0;
    }
}
