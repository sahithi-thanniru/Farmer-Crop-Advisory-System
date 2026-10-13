package service;

public class CropAdvisoryService {

    public static String checkSuitability(
            String soilType,
            String season,
            String irrigation,
            String waterAvailability,
            double temperature,
            double landArea,
            int cropDuration,
            double expectedYield,
            String cropSeason,
            double minTemperature,
            double maxTemperature) {

        int score = 0;

        // Season
        if (season.equalsIgnoreCase(cropSeason)) {
            score += 2;
        }

        // Soil
        if (soilType.equalsIgnoreCase("Red Soil")
                || soilType.equalsIgnoreCase("Black Soil")
                || soilType.equalsIgnoreCase("Alluvial Soil")) {
            score += 2;
        }

        // Irrigation
        if (irrigation.equalsIgnoreCase("Borewell")
                || irrigation.equalsIgnoreCase("Canal")
                || irrigation.equalsIgnoreCase("Drip")) {
            score += 1;
        }

        // Water availability
        if (waterAvailability.equalsIgnoreCase("Good")) {
            score += 1;
        } else if (waterAvailability.equalsIgnoreCase("Moderate")) {
            score += 1;
        }

        // Temperature
        if (temperature >= minTemperature
                && temperature <= maxTemperature) {
            score += 2;
        }

        // Land area
        if (landArea >= 1.0) {
            score += 1;
        }

        // Crop duration
        if (cropDuration > 0 && cropDuration <= 180) {
            score += 1;
        }

        // Expected yield
        if (expectedYield > 0) {
            score += 1;
        }

        if (score >= 10) {
            return "Highly Suitable";
        } else if (score >= 7) {
            return "Suitable";
        } else if (score >= 4) {
            return "Moderately Suitable";
        } else {
            return "Not Recommended";
        }
    }

    public static String generateReason(
            String soilType,
            String season,
            String irrigation,
            String waterAvailability,
            double temperature,
            double landArea,
            int cropDuration,
            double expectedYield,
            String cropSeason,
            double minTemperature,
            double maxTemperature,
            String suitability) {

        if (suitability.equals("Highly Suitable")) {
            return "Soil, season, irrigation, water, temperature, land area, crop duration and yield conditions are favorable.";
        }

        if (suitability.equals("Suitable")) {
            return "Most land and crop conditions are favorable for cultivation.";
        }

        if (suitability.equals("Moderately Suitable")) {
            return "Some conditions are suitable, but improvement or additional care may be required.";
        }

        return "The current land and crop conditions are not favorable.";
    }
}