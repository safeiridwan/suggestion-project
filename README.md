# Suggestion-Project
## Getting Started

```shell
# download the starter kit
git clone https://github.com/safeiridwan/suggestion-project.git
```

## Build and Run the Project
```shell
cd suggestion-project
./mvnw spring-boot:run
```

## Score Calculation

The scoring system combines a full-text search score (text similarity) and a location score (distance-based). The final score is calculated as follows:

### Formula:
```shell
finalScore = (textScore * 0.7) + (distanceScore * 0.3);
```
- Text Score (70%): Measures how well the search query matches city names using text similarity algorithms (e.g., Levenshtein Distance, TF-IDF, or Elasticsearch full-text search). Set to 70% assuming search by word is more important than proximity.
- Distance Score (30%): Uses the Haversine formula to compute geographical proximity between two locations.

## Haversine Formula (Distance Calculation)

The Haversine formula determines the great-circle distance between two points on a sphere given their latitudes and longitudes.

### Formula:
```shell
public double haversineFormula(double lat1, double lon1, double lat2, double lon2) {
    final int R = 6371; // Radius of the Earth in km
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
               Math.sin(dLon / 2) * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Distance in km
}
```

### Distance Score Calculation
```shell
public double getDistanceScore(double distance, double maxDistance) {
    return 1 - (distance / maxDistance);
}
```

---

# API Documentation
## 1. Search Cities with Score

## Endpoint: 
```
GET /api/v1/suggestions
```

### Query Parameters:
| Parameter  | Type  | Description | 
| ---------- | ----- | ----------- | 
| q  | string  | The search query for city name |
| latitude  | string  | User's current latitude (optional) |
| longitude  | string  | User's current longitude (optional) |

### Example Request:
```
GET /api/v1/suggestions?q=toronto&latitude=43.7&longitude=-79.42
```

### Example Response:
```
{
  "suggestions": [
    {
      "name": "Toronto, ON, Canada",
      "latitude": 43.70011,
      "longitude": -79.4163,
      "score": 0.92
    },
    {
      "name": "Toronto, KS, USA",
      "latitude": 38.2,
      "longitude": -95.92,
      "score": 0.78
    }
  ]
}
```

### Response Fields:
| Field  | Type  | Description | 
| ---------- | ----- | ----------- | 
| name  | string  | City name and country |
| latitude  | double  | City's latitude |
| longitude  | double  | City's longitude |
| score  | double  | Final calculated score |
