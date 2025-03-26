package com.suggestion.geospatial.service;

import com.suggestion.entity.City;
import com.suggestion.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoSpatialService {
    private final CityRepository cityRepository;

    public double getDistanceScore(String cityName, double queryLat, double queryLon, double cityLat, double cityLon) {
        double distance = haversineFormula(queryLon, queryLon, cityLat, cityLon);
        double maxDistance = getMaxdistance(cityRepository.findAll(), queryLat, queryLon);
        return 1 - (distance / maxDistance); // closer distance get score near 1
    }

    /*
        I'm using the ‘haversine’ formula to calculate the great-circle distance between two points
        source: https://www.movable-type.co.uk/scripts/latlong.html
     */
    public double haversineFormula(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = degToRad(lat2 - lat1);
        double dLon = degToRad(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public double degToRad(double val) {
        return val * Math.PI * 180;
    }

    public double getMaxdistance(List<City> cities, double queryLat, double queryLon) {
        return cities.stream()
                .mapToDouble(city -> haversineFormula(queryLat, queryLon, city.getLatitude(), city.getLongitude()))
                .max()
                .orElse(1); // 1km
    }

}
