package ui;

import dao.FarmerDAO;
import dao.PreviousCropDAO;

import model.Farmer;
import model.PreviousCrop;

import java.util.List;
import java.util.Scanner;

public class FarmerMenu {

    private static Scanner scanner = new Scanner(System.in);

    private static FarmerDAO farmerDAO = new FarmerDAO();
    private static PreviousCropDAO previousCropDAO = new PreviousCropDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("          FARMER REGISTRATION");
            System.out.println("==============================================");
            System.out.println("1. Add Farmer");
            System.out.println("2. View All Farmers");
            System.out.println("3. Search Farmer");
            System.out.println("4. Update Farmer");
            System.out.println("5. Add Previous Crop");
            System.out.println("6. View Previous Crops");
            System.out.println("7. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addFarmer();
                    break;

                case 2:
                    viewFarmers();
                    break;

                case 3:
                    searchFarmer();
                    break;

                case 4:
                    updateFarmer();
                    break;

                case 5:
                    addPreviousCrop();
                    break;

                case 6:
                    viewPreviousCrops();
                    break;

                case 7:
                    System.out.println("Exiting Farmer Registration...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addFarmer() {

        System.out.println();
        System.out.println("----------- ADD FARMER -----------");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter village: ");
        String village = scanner.nextLine();

        System.out.print("Enter district: ");
        String district = scanner.nextLine();

        System.out.print("Enter land ownership: ");
        String landOwnership = scanner.nextLine();

        Farmer farmer = new Farmer(
                name,
                age,
                gender,
                phone,
                village,
                district,
                landOwnership
        );

        if (farmerDAO.addFarmer(farmer)) {
            System.out.println("Farmer registered successfully!");
        } else {
            System.out.println("Failed to register farmer.");
        }
    }

    private static void viewFarmers() {

        System.out.println();
        System.out.println("----------- ALL FARMERS -----------");

        List<Farmer> farmers = farmerDAO.getAllFarmers();

        if (farmers.isEmpty()) {
            System.out.println("No farmers found.");
            return;
        }

        for (Farmer farmer : farmers) {

            System.out.println("----------------------------------------------");
            System.out.println("Farmer ID       : " + farmer.getFarmerId());
            System.out.println("Name            : " + farmer.getName());
            System.out.println("Age             : " + farmer.getAge());
            System.out.println("Gender          : " + farmer.getGender());
            System.out.println("Phone           : " + farmer.getPhone());
            System.out.println("Village         : " + farmer.getVillage());
            System.out.println("District        : " + farmer.getDistrict());
            System.out.println("Land Ownership  : " + farmer.getLandOwnership());
        }
    }

    private static void searchFarmer() {

        System.out.println();
        System.out.println("----------- SEARCH FARMER -----------");

        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        Farmer farmer = farmerDAO.getFarmerById(farmerId);

        if (farmer == null) {
            System.out.println("Farmer not found.");
            return;
        }

        System.out.println("----------------------------------------------");
        System.out.println("Farmer ID       : " + farmer.getFarmerId());
        System.out.println("Name            : " + farmer.getName());
        System.out.println("Age             : " + farmer.getAge());
        System.out.println("Gender          : " + farmer.getGender());
        System.out.println("Phone           : " + farmer.getPhone());
        System.out.println("Village         : " + farmer.getVillage());
        System.out.println("District        : " + farmer.getDistrict());
        System.out.println("Land Ownership  : " + farmer.getLandOwnership());
    }

    private static void updateFarmer() {

        System.out.println();
        System.out.println("----------- UPDATE FARMER -----------");

        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();
        scanner.nextLine();

        Farmer farmer = farmerDAO.getFarmerById(farmerId);

        if (farmer == null) {
            System.out.println("Farmer not found.");
            return;
        }

        System.out.println("Enter new details:");

        System.out.print("Name: ");
        farmer.setName(scanner.nextLine());

        System.out.print("Age: ");
        farmer.setAge(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Gender: ");
        farmer.setGender(scanner.nextLine());

        System.out.print("Phone: ");
        farmer.setPhone(scanner.nextLine());

        System.out.print("Village: ");
        farmer.setVillage(scanner.nextLine());

        System.out.print("District: ");
        farmer.setDistrict(scanner.nextLine());

        System.out.print("Land Ownership: ");
        farmer.setLandOwnership(scanner.nextLine());

        if (farmerDAO.updateFarmer(farmer)) {
            System.out.println("Farmer updated successfully!");
        } else {
            System.out.println("Failed to update farmer.");
        }
    }

    private static void addPreviousCrop() {

        System.out.println();
        System.out.println("----------- ADD PREVIOUS CROP -----------");

        System.out.print("Enter Farmer ID: ");
        int farmerId = scanner.nextInt();

        System.out.print("Enter Land ID: ");
        int landId = scanner.nextInt();

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Season: ");
        String season = scanner.nextLine();

        System.out.print("Enter Year: ");
        int year = scanner.nextInt();

        System.out.print("Enter Yield: ");
        double yield = scanner.nextDouble();
        scanner.nextLine();

        PreviousCrop previousCrop = new PreviousCrop(
                farmerId,
                landId,
                cropId,
                season,
                year,
                yield
        );

        if (previousCropDAO.addPreviousCrop(previousCrop)) {
            System.out.println("Previous crop record added successfully!");
        } else {
            System.out.println("Failed to add previous crop record.");
        }
    }

    private static void viewPreviousCrops() {

        System.out.println();
        System.out.println("----------- PREVIOUS CROPS -----------");

        List<PreviousCrop> previousCrops =
                previousCropDAO.getAllPreviousCrops();

        if (previousCrops.isEmpty()) {
            System.out.println("No previous crop records found.");
            return;
        }

        for (PreviousCrop previousCrop : previousCrops) {

            System.out.println("----------------------------------------------");
            System.out.println("Record ID  : " + previousCrop.getRecordId());
            System.out.println("Farmer ID  : " + previousCrop.getFarmerId());
            System.out.println("Land ID    : " + previousCrop.getLandId());
            System.out.println("Crop ID    : " + previousCrop.getCropId());
            System.out.println("Season     : " + previousCrop.getSeason());
            System.out.println("Year       : " + previousCrop.getYear());
            System.out.println("Yield      : " + previousCrop.getYield());
        }
    }
}