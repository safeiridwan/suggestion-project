package com.suggestion.fulltextsearch;

import org.springframework.stereotype.Service;

@Service
public class FullTextSearchService {

    public double getSearchScore(String cityName, String query) {
        String cityNameLowerCase = cityName.toLowerCase();
        String queryLowerCase = query.toLowerCase();

//        if (cityNameLowerCase.contains(queryLowerCase)) {
//            return 1.0;
//        }

        int matchWord = 0;
        String[] splittedQuery = queryLowerCase.split("\\s+");
        for (String queryWord : splittedQuery) {
            if (cityNameLowerCase.contains(queryWord)) {
                matchWord++;
            }
        }

        return (double) matchWord / splittedQuery.length;
    }

}
