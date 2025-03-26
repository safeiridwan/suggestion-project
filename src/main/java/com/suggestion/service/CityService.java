package com.suggestion.service;

import com.suggestion.dto.out.CityOut;

import java.util.List;

public interface CityService {
    List<CityOut> getSuggestionCities(String query, double latitude, double longitude);
}
