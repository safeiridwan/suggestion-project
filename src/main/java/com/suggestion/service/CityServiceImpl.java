package com.suggestion.service;

import com.suggestion.dto.out.CityOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final FileService fileService;
    @Override
    public List<CityOut> getSuggestionCities(String query, String latitude, String longitude) {
        return fileService.getCities().stream()
                .limit(5)
                .map(CityOut::new)
                .toList();
    }
}
