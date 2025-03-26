package com.suggestion.service;

import com.suggestion.entity.City;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class FileService {

    private final List<City> cities = new ArrayList<>();

    public void readFilesOnStartup() {
        System.out.println("Reading file on startup...");
        loadCities();
        System.out.println("Cities loaded: " + cities.size());
    }

    private void loadCities() {
        String filePath = "files/cities_canada-usa.tsv";
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 10) {
                    String countryName = parts[8].equals("CA") ? "Canada" : "USA";
                    double lat = 0.0;
                    if (parts[4] != null && !parts[4].isEmpty()) {
                        lat = Double.parseDouble(parts[4]);
                    }

                    double lon = 0.0;
                    if (parts[5] != null && !parts[5].isEmpty()) {
                        lon = Double.parseDouble(parts[5]);
                    }

                    StringBuilder geoName = new StringBuilder();
                    geoName.append(parts[2]);
                    if (parts[10] != null && !parts[10].isEmpty()) {
                        geoName.append(", ").append(parts[10]);
                    }
                    geoName.append(", ").append(countryName);

                    cities.add(new City(parts[0], parts[2], lat, lon, countryName, parts[8], parts[10], geoName.toString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
