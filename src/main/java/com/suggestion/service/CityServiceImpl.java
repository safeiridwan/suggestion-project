package com.suggestion.service;

import com.suggestion.dto.out.CityOut;
import com.suggestion.entity.City;
import com.suggestion.fulltextsearch.FullTextSearchService;
import com.suggestion.geospatial.service.GeoSpatialService;
import com.suggestion.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    private final CityRepository cityRepository;
    private final FullTextSearchService fullTextSearchService;
    private final GeoSpatialService geoSpatialService;

    @Override
    public List<CityOut> getSuggestionCities(String query, double latitude, double longitude) {
        return cityRepository.findAll().stream()
                .filter(city -> city.getName().toLowerCase().contains(query.toLowerCase()))
                .map(city -> new CityOut(
                        city.getGeoName(),
                        city.getLatitude(),
                        city.getLongitude(),
                        calculateScore(city, query, latitude, longitude)
                ))
                .sorted(Comparator.comparingDouble(CityOut::getScore).reversed())
                .limit(10)
                .toList();
    }

    /*
        Final Score = alpha * fullTextSearchScore + beta * distanceScore
        alpha = 0.7 (alpha greater than beta, assuming search by word is more important than proximity)
        beta = 0.3
     */
    private double calculateScore(City city, String query, double queryLat, double queryLon) {
        String cityName = city.getName();
        double cityLat = city.getLatitude();
        double cityLon = city.getLongitude();

        double searchScore = fullTextSearchService.getSearchScore(cityName, query);
        double distanceScore = geoSpatialService.getDistanceScore(cityName, queryLat, queryLon, cityLat, cityLon);

        logger.info("city name: {} | fullTextSearchScore: {} | distanceScore: {}", city.getGeoName(), searchScore, distanceScore);

        return 0.7 * searchScore + 0.3 * distanceScore;
    }

}
