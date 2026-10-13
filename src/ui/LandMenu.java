package ui;

import dao.LandDAO;
import dao.SoilTestDAO;
import model.Land;
import model.SoilTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LandMenu {

    private static Scanner scanner = new Scanner(System.in);

    private static LandDAO landDAO = new LandDAO();
    private static SoilTestDAO soilTestDAO = new SoilTestDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("          LAND & SOIL MANAGEMENT");
            System.out.println("==============================================");
            System.out.println("1. Add Land");
            System.out.println("2. View All Lands");
            System.out.println("3. Search Land");
            System.out.println("4. Update Land");
            System.out.println("5. Add Soil Test");
            System.out.println("6. View Soil Tests");
            System.out.println("7. Search Soil Test");
            System.out.println("8. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addLand();
                    break;

                case 2:
                    viewLands();
                    break;

                case 3:
                    searchLand();
                    break;

                case 4:
                    updateLand();
                    break;

                case 5:
                    addSoilTest();
                    break;

                case 6:
                    viewSoilTests();
                    break;

                case 7:
                    searchSoilTest();
                    break;

                case 8:
                    System.out.println("Exiting Land & Soil Management...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addLand() {

        System.out.println();
        System.out.println("----------- ADD LAND -----------");

        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();

        System.out.print("Enter Land Area: ");
        double area = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Soil Type: ");
        String soilType = scanner.nextLine();

        System.out.print("Enter Irrigation Facility: ");
        String irrigation = scanner.nextLine();

        System.out.print("Enter Water Availability: ");
        String waterAvailability = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        System.out.print("Enter Temperature (°C): ");
        double temperature = scanner.nextDouble();
        scanner.nextLine();

        Land land = new Land(
                farmerId,
                area,
                soilType,
                irrigation,
                waterAvailability,
                location,
                temperature
        );

        if (landDAO.addLand(land)) {
            System.out.println("Land added successfully!");
        } else {
            System.out.println("Failed to add land.");
        }
    }

    private static void viewLands() {

        System.out.println();
        System.out.println("----------- ALL LANDS -----------");

        List<Land> lands = landDAO.getAllLands();

        if (lands.isEmpty()) {
            System.out.println("No lands found.");
            return;
        }

        for (Land land : lands) {

            System.out.println("----------------------------------------------");
            System.out.println("Land ID            : " + land.getLandId());
            System.out.println("Farmer ID          : " + land.getFarmerId());
            System.out.println("Area               : " + land.getArea());
            System.out.println("Soil Type          : " + land.getSoilType());
            System.out.println("Irrigation         : " + land.getIrrigation());
            System.out.println("Water Availability : "
                    + land.getWaterAvailability());
            System.out.println("Location           : " + land.getLocation());
            System.out.println("Temperature        : "
                    + land.getTemperature() + " °C");
        }
    }

    private static void searchLand() {

        System.out.println();
        System.out.println("----------- SEARCH LAND -----------");

        System.out.print("Enter Land ID: ");
        int landId = scanner.nextInt();
        scanner.nextLine();

        Land land = landDAO.getLandById(landId);

        if (land == null) {
            System.out.println("Land not found.");
            return;
        }

        System.out.println("----------------------------------------------");
        System.out.println("Land ID            : " + land.getLandId());
        System.out.println("Farmer ID          : " + land.getFarmerId());
        System.out.println("Area               : " + land.getArea());
        System.out.println("Soil Type          : " + land.getSoilType());
        System.out.println("Irrigation         : " + land.getIrrigation());
        System.out.println("Water Availability : "
                + land.getWaterAvailability());
        System.out.println("Location           : " + land.getLocation());
        System.out.println("Temperature        : "
                + land.getTemperature() + " °C");
    }

    private static void updateLand() {

        System.out.println();
        System.out.println("----------- UPDATE LAND -----------");

        System.out.print("Enter Land ID: ");
        int landId = scanner.nextInt();
        scanner.nextLine();

        Land land = landDAO.getLandById(landId);

        if (land == null) {
            System.out.println("Land not found.");
            return;
        }

        System.out.println("Enter new details:");

        System.out.print("Farmer ID: ");
        land.setFarmerId(scanner.nextInt());

        System.out.print("Area: ");
        land.setArea(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Soil Type: ");
        land.setSoilType(scanner.nextLine());

        System.out.print("Irrigation Facility: ");
        land.setIrrigation(scanner.nextLine());

        System.out.print("Water Availability: ");
        land.setWaterAvailability(scanner.nextLine());

        System.out.print("Location: ");
        land.setLocation(scanner.nextLine());

        System.out.print("Temperature (°C): ");
        land.setTemperature(scanner.nextDouble());
        scanner.nextLine();

        if (landDAO.updateLand(land)) {
            System.out.println("Land updated successfully!");
        } else {
            System.out.println("Failed to update land.");
        }
    }

    private static void addSoilTest() {

        System.out.println();
        System.out.println("----------- ADD SOIL TEST -----------");

        System.out.print("Enter Land ID: ");
        int landId = scanner.nextInt();

        System.out.print("Enter Nitrogen: ");
        double nitrogen = scanner.nextDouble();

        System.out.print("Enter Phosphorus: ");
        double phosphorus = scanner.nextDouble();

        System.out.print("Enter Potassium: ");
        double potassium = scanner.nextDouble();

        System.out.print("Enter pH: ");
        double ph = scanner.nextDouble();
        scanner.nextLine();

        Date testDate = Date.valueOf(LocalDate.now());

        SoilTest soilTest = new SoilTest(
                landId,
                nitrogen,
                phosphorus,
                potassium,
                ph,
                testDate
        );

        if (soilTestDAO.addSoilTest(soilTest)) {
            System.out.println("Soil test added successfully!");
        } else {
            System.out.println("Failed to add soil test.");
        }
    }

    private static void viewSoilTests() {

        System.out.println();
        System.out.println("----------- ALL SOIL TESTS -----------");

        List<SoilTest> soilTests = soilTestDAO.getAllSoilTests();

        if (soilTests.isEmpty()) {
            System.out.println("No soil tests found.");
            return;
        }

        for (SoilTest soilTest : soilTests) {

            System.out.println("----------------------------------------------");
            System.out.println("Test ID     : " + soilTest.getTestId());
            System.out.println("Land ID     : " + soilTest.getLandId());
            System.out.println("Nitrogen    : " + soilTest.getNitrogen());
            System.out.println("Phosphorus  : " + soilTest.getPhosphorus());
            System.out.println("Potassium   : " + soilTest.getPotassium());
            System.out.println("pH          : " + soilTest.getPh());
            System.out.println("Test Date   : " + soilTest.getTestDate());
        }
    }

    private static void searchSoilTest() {

        System.out.println();
        System.out.println("----------- SEARCH SOIL TEST -----------");

        System.out.print("Enter Test ID: ");
        int testId = scanner.nextInt();
        scanner.nextLine();

        SoilTest soilTest = soilTestDAO.getSoilTestById(testId);

        if (soilTest == null) {
            System.out.println("Soil test not found.");
            return;
        }

        System.out.println("----------------------------------------------");
        System.out.println("Test ID     : " + soilTest.getTestId());
        System.out.println("Land ID     : " + soilTest.getLandId());
        System.out.println("Nitrogen    : " + soilTest.getNitrogen());
        System.out.println("Phosphorus  : " + soilTest.getPhosphorus());
        System.out.println("Potassium   : " + soilTest.getPotassium());
        System.out.println("pH          : " + soilTest.getPh());
        System.out.println("Test Date   : " + soilTest.getTestDate());
    }
}
