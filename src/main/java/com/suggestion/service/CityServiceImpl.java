package com.suggestion.service;

import com.suggestion.dto.out.CityOut;
import com.suggestion.entity.City;
import com.suggestion.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<CityOut> getSuggestionCities(String query, String latitude, String longitude) {
        List<CityOut> out = cityRepository.findAll().stream()
                .filter(city -> city.getName().toLowerCase().contains(query.toLowerCase()))
                .map(city -> {
                    StringBuilder name = new StringBuilder();
                    name.append(city.getName());
                    if (city.getAltCountryCode() != null && !city.getAltCountryCode().isEmpty()) {
                        name.append(", ").append(city.getAltCountryCode());
                    }
                    name.append(", ").append(city.getCountryName());
                    System.out.println("name: " + name);

                    return new CityOut(
                            name.toString(),
                            city.getLatitude(),
                            city.getLongitude(),
                            calculateScore(city.getName(), query, latitude, longitude, city.getLatitude(), city.getLongitude())
                    );
                })
                .sorted(Comparator.comparingDouble(CityOut::getScore).reversed())
                .limit(10)
                .toList();

        return out;
    }

    /*
        Final Score = alpha * fullTextSearchScore + beta * distanceScore
        alpha = 0.7 (alpha greater than beta, assuming search by word is more important than proximity)
        beta = 0.3
     */
    private double calculateScore (String cityName, String query, String queryLat, String queryLon, double cityLat, double cityLon) {
        double fullTextSearchScore = calculateFullTextSearchScore(cityName, query);
        double parsedQueryLat = (queryLat == null || queryLat.isEmpty()) ? 0.0 : Double.parseDouble(queryLat);
        double parsedQueryLon = (queryLon == null || queryLon.isEmpty()) ? 0.0 : Double.parseDouble(queryLon);
        double distanceScore = calculateDistanceScore(cityName, parsedQueryLat, parsedQueryLon, cityLat, cityLon);
        System.out.println("city name: " + cityName + ", fullTextSearchScore: " + fullTextSearchScore + ", distanceScore: " + distanceScore);
        System.out.println();
        return 0.7 * fullTextSearchScore + 0.3 * distanceScore;
    }

    private double calculateFullTextSearchScore(String cityName, String query) {
        String cityNameLowerCase = cityName.toLowerCase();
        String queryLowerCase = query.toLowerCase();

        if (cityNameLowerCase.contains(queryLowerCase)) {
            return 1.0;
        }

        int matchWord = 0;
        String[] splittedQuery = cityNameLowerCase.split("\\s+");
        for (String queryWord : splittedQuery) {
            if (cityNameLowerCase.contains(queryWord)) {
                matchWord++;
            }
        }

        return (double) matchWord / splittedQuery.length;
    }

    private double calculateDistanceScore(String cityName, double queryLat, double queryLon, double cityLat, double cityLon) {
        double distance = haversineFormula(queryLon, queryLon, cityLat, cityLon);
        double maxDistance = getMaxdistance(cityRepository.findAll(), queryLat, queryLon);
        return 1 - (distance / maxDistance); // closer distance get score near 1
    }

    /*
        I'm using the ‘haversine’ formula to calculate the great-circle distance between two points
        source: https://www.movable-type.co.uk/scripts/latlong.html
     */
    private double haversineFormula(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = degToRad(lat2 - lat1);
        double dLon = degToRad(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private double degToRad(double val) {
        return val * Math.PI * 180;
    }

    private double getMaxdistance(List<City> cities, double queryLat, double queryLon) {
        return cities.stream()
                .mapToDouble(city -> haversineFormula(queryLat, queryLon, city.getLatitude(), city.getLongitude()))
                .max()
                .orElse(1); // 1km
    }
}
