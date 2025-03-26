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
                if (parts.length >= 5) {
                    String countryName = parts[8].equals("CA") ? "Canada" : "USA";
                    cities.add(new City(parts[0], parts[2], parts[4], parts[5], countryName, parts[8], parts[9]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
