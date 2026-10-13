package ui;

import dao.CropDAO;
import dao.CropRecommendationDAO;
import dao.FarmerDAO;
import dao.LandDAO;

import model.Crop;
import model.CropRecommendation;
import model.Farmer;
import model.Land;

import service.CropAdvisoryService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CropAdvisoryMenu {

    private static Scanner scanner = new Scanner(System.in);

    private static CropDAO cropDAO = new CropDAO();
    private static LandDAO landDAO = new LandDAO();
    private static FarmerDAO farmerDAO = new FarmerDAO();
    private static CropRecommendationDAO recommendationDAO =
            new CropRecommendationDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("              CROP ADVISORY");
            System.out.println("==============================================");
            System.out.println("1. Add Crop");
            System.out.println("2. View All Crops");
            System.out.println("3. Search Crop");
            System.out.println("4. Recommend Crops");
            System.out.println("5. View Recommendations");
            System.out.println("6. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addCrop();
                    break;

                case 2:
                    viewCrops();
                    break;

                case 3:
                    searchCrop();
                    break;

                case 4:
                    recommendCrops();
                    break;

                case 5:
                    viewRecommendations();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCrop() {

        System.out.println();
        System.out.println("---------- ADD CROP ----------");

        System.out.print("Crop Name: ");
        String cropName = scanner.nextLine();

        System.out.print("Season: ");
        String season = scanner.nextLine();

        System.out.print("Duration (days): ");
        int duration = scanner.nextInt();

        System.out.print("Expected Yield: ");
        double expectedYield = scanner.nextDouble();

        System.out.print("Minimum Temperature (°C): ");
        double minTemperature = scanner.nextDouble();

        System.out.print("Maximum Temperature (°C): ");
        double maxTemperature = scanner.nextDouble();

        scanner.nextLine();

        Crop crop = new Crop(
                cropName,
                season,
                duration,
                expectedYield
        );

        crop.setMinTemperature(minTemperature);
        crop.setMaxTemperature(maxTemperature);

        if (cropDAO.addCrop(crop)) {
            System.out.println("Crop added successfully.");
        } else {
            System.out.println("Failed to add crop.");
        }
    }

    private static void viewCrops() {

        System.out.println();
        System.out.println("---------- ALL CROPS ----------");

        List<Crop> crops = cropDAO.getAllCrops();

        if (crops.isEmpty()) {
            System.out.println("No crops found.");
            return;
        }

        for (Crop crop : crops) {

            System.out.println("----------------------------------------");
            System.out.println("Crop ID          : " + crop.getCropId());
            System.out.println("Crop Name        : " + crop.getCropName());
            System.out.println("Season           : " + crop.getSeason());
            System.out.println("Duration         : " + crop.getDurationDays()
                    + " days");
            System.out.println("Expected Yield   : " + crop.getExpectedYield());
            System.out.println("Temperature      : "
                    + crop.getMinTemperature()
                    + " - "
                    + crop.getMaxTemperature()
                    + " °C");
        }

        System.out.println("----------------------------------------");
    }

    private static void searchCrop() {

        System.out.println();
        System.out.println("---------- SEARCH CROP ----------");

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        Crop crop = cropDAO.getCropById(cropId);

        if (crop == null) {
            System.out.println("Crop not found.");
            return;
        }

        System.out.println();
        System.out.println("Crop ID          : " + crop.getCropId());
        System.out.println("Crop Name        : " + crop.getCropName());
        System.out.println("Season           : " + crop.getSeason());
        System.out.println("Duration         : "
                + crop.getDurationDays() + " days");
        System.out.println("Expected Yield   : "
                + crop.getExpectedYield());
        System.out.println("Temperature      : "
                + crop.getMinTemperature()
                + " - "
                + crop.getMaxTemperature()
                + " °C");
    }

    private static void recommendCrops() {

        System.out.println();
        System.out.println("---------- CROP RECOMMENDATION ----------");

        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();

        System.out.print("Enter Land ID: ");
        int landId = scanner.nextInt();

        scanner.nextLine();

        Farmer farmer = farmerDAO.getFarmerById(farmerId);

        if (farmer == null) {
            System.out.println("Farmer not found.");
            return;
        }

        Land land = landDAO.getLandById(landId);

        if (land == null) {
            System.out.println("Land not found.");
            return;
        }

        if (land.getFarmerId() != farmerId) {
            System.out.println(
                    "This land does not belong to the selected farmer.");
            return;
        }

        System.out.print("Enter Current Season: ");
        String season = scanner.nextLine();

        System.out.println();
        System.out.println("Farmer Name        : " + farmer.getName());
        System.out.println("Land ID            : " + land.getLandId());
        System.out.println("Land Area          : " + land.getArea());
        System.out.println("Soil Type          : " + land.getSoilType());
        System.out.println("Irrigation         : " + land.getIrrigation());
        System.out.println("Water Availability : "
                + land.getWaterAvailability());
        System.out.println("Temperature        : "
                + land.getTemperature() + " °C");

        System.out.println();
        System.out.println("==============================================");
        System.out.println("              RECOMMENDED CROPS");
        System.out.println("==============================================");

        List<Crop> crops = cropDAO.getAllCrops();

        if (crops.isEmpty()) {
            System.out.println("No crops available.");
            return;
        }

        for (Crop crop : crops) {

            String suitability =
                    CropAdvisoryService.checkSuitability(
                            land.getSoilType(),
                            season,
                            land.getIrrigation(),
                            land.getWaterAvailability(),
                            land.getTemperature(),
                            land.getArea(),
                            crop.getDurationDays(),
                            crop.getExpectedYield(),
                            crop.getSeason(),
                            crop.getMinTemperature(),
                            crop.getMaxTemperature()
                    );

            String reason =
                    CropAdvisoryService.generateReason(
                            land.getSoilType(),
                            season,
                            land.getIrrigation(),
                            land.getWaterAvailability(),
                            land.getTemperature(),
                            land.getArea(),
                            crop.getDurationDays(),
                            crop.getExpectedYield(),
                            crop.getSeason(),
                            crop.getMinTemperature(),
                            crop.getMaxTemperature(),
                            suitability
                    );

            CropRecommendation recommendation =
                    new CropRecommendation(
                            farmerId,
                            landId,
                            crop.getCropId(),
                            suitability,
                            reason,
                            Date.valueOf(LocalDate.now())
                    );

            recommendationDAO.addRecommendation(recommendation);

            System.out.println();
            System.out.println("Crop Name        : "
                    + crop.getCropName());
            System.out.println("Suitability      : "
                    + suitability);
            System.out.println("Reason           : "
                    + reason);
            System.out.println("Duration         : "
                    + crop.getDurationDays() + " days");
            System.out.println("Expected Yield   : "
                    + crop.getExpectedYield());
            System.out.println("Temperature Range: "
                    + crop.getMinTemperature()
                    + " - "
                    + crop.getMaxTemperature()
                    + " °C");
            System.out.println("----------------------------------------------");
        }

        System.out.println();
        System.out.println("Crop recommendations generated successfully.");
    }

    private static void viewRecommendations() {

        System.out.println();
        System.out.println("---------- ALL RECOMMENDATIONS ----------");

        List<CropRecommendation> recommendations =
                recommendationDAO.getAllRecommendations();

        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found.");
            return;
        }

        for (CropRecommendation recommendation : recommendations) {

            System.out.println("----------------------------------------");
            System.out.println("Recommendation ID : "
                    + recommendation.getRecommendationId());
            System.out.println("Farmer ID         : "
                    + recommendation.getFarmerId());
            System.out.println("Land ID           : "
                    + recommendation.getLandId());
            System.out.println("Crop ID           : "
                    + recommendation.getCropId());
            System.out.println("Suitability       : "
                    + recommendation.getSuitability());
            System.out.println("Reason            : "
                    + recommendation.getReason());
            System.out.println("Date              : "
                    + recommendation.getRecommendationDate());
        }

        System.out.println("----------------------------------------");
    }
}