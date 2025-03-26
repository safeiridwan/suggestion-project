package com.suggestion.contoller;

import com.suggestion.dto.out.CityOut;
import com.suggestion.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestions")
public class CityController {
    private final CityService cityService;

    @GetMapping()
    public List<CityOut> getSuggestionCities(
            @RequestParam(name = "q") String query,
            @RequestParam(name = "latitude", required = false) String latitude,
            @RequestParam(name = "longitude", required = false) String longitude
    ) {
        System.out.println("query: " + query + " | latitude: " + latitude + " | longitude: " + longitude);
        Double parsedQueryLat = (latitude == null || latitude.isEmpty()) ? null : Double.parseDouble(latitude);
        Double parsedQueryLon = (longitude == null || longitude.isEmpty()) ? null : Double.parseDouble(longitude);
        return cityService.getSuggestionCities(query, parsedQueryLat, parsedQueryLon);
    }
}
