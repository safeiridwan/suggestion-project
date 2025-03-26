package com.suggestion.repository;

import com.suggestion.entity.City;
import com.suggestion.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CityRepository {
    private final FileService fileService;
    public List<City> findAll() {
        return fileService.getCities();
    }
}
