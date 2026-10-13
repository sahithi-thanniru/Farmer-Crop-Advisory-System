package ui;

import dao.FertilizerDAO;
import dao.CultivationDAO;
import model.Fertilizer;
import model.Cultivation;

import java.util.List;
import java.util.Scanner;

public class FertilizerCultivationMenu {

    private static Scanner scanner = new Scanner(System.in);

    private static FertilizerDAO fertilizerDAO =
            new FertilizerDAO();

    private static CultivationDAO cultivationDAO =
            new CultivationDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("       FERTILIZER & CULTIVATION");
            System.out.println("==============================================");
            System.out.println("1. Add Fertilizer");
            System.out.println("2. View All Fertilizers");
            System.out.println("3. Search Fertilizer by Crop");
            System.out.println("4. Add Cultivation Details");
            System.out.println("5. View All Cultivation Details");
            System.out.println("6. Search Cultivation by Crop");
            System.out.println("7. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addFertilizer();
                    break;

                case 2:
                    viewFertilizers();
                    break;

                case 3:
                    searchFertilizerByCrop();
                    break;

                case 4:
                    addCultivation();
                    break;

                case 5:
                    viewCultivation();
                    break;

                case 6:
                    searchCultivationByCrop();
                    break;

                case 7:
                    System.out.println(
                            "Exiting Fertilizer & Cultivation...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addFertilizer() {

        System.out.println();
        System.out.println("----------- ADD FERTILIZER -----------");

        System.out.print("Enter Fertilizer Name: ");
        String fertilizerName = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        String quantity = scanner.nextLine();

        System.out.print("Enter Application Stage: ");
        String applicationStage = scanner.nextLine();

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        Fertilizer fertilizer =
                new Fertilizer(
                        fertilizerName,
                        quantity,
                        applicationStage,
                        cropId
                );

        if (fertilizerDAO.addFertilizer(fertilizer)) {

            System.out.println(
                    "Fertilizer added successfully!");

        } else {

            System.out.println(
                    "Failed to add fertilizer.");
        }
    }

    private static void viewFertilizers() {

        System.out.println();
        System.out.println("----------- ALL FERTILIZERS -----------");

        List<Fertilizer> fertilizers =
                fertilizerDAO.getAllFertilizers();

        if (fertilizers.isEmpty()) {

            System.out.println(
                    "No fertilizer records found.");

            return;
        }

        for (Fertilizer fertilizer : fertilizers) {

            System.out.println("----------------------------------------------");

            System.out.println(
                    "Fertilizer ID     : "
                            + fertilizer.getFertilizerId());

            System.out.println(
                    "Fertilizer Name   : "
                            + fertilizer.getFertilizerName());

            System.out.println(
                    "Quantity          : "
                            + fertilizer.getQuantity());

            System.out.println(
                    "Application Stage : "
                            + fertilizer.getApplicationStage());

            System.out.println(
                    "Crop ID           : "
                            + fertilizer.getCropId());
        }
    }

    private static void searchFertilizerByCrop() {

        System.out.println();
        System.out.println(
                "----------- SEARCH FERTILIZER -----------");

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        List<Fertilizer> fertilizers =
                fertilizerDAO.getFertilizersByCrop(cropId);

        if (fertilizers.isEmpty()) {

            System.out.println(
                    "No fertilizer records found for this crop.");

            return;
        }

        for (Fertilizer fertilizer : fertilizers) {

            System.out.println("----------------------------------------------");

            System.out.println(
                    "Fertilizer ID     : "
                            + fertilizer.getFertilizerId());

            System.out.println(
                    "Fertilizer Name   : "
                            + fertilizer.getFertilizerName());

            System.out.println(
                    "Quantity          : "
                            + fertilizer.getQuantity());

            System.out.println(
                    "Application Stage : "
                            + fertilizer.getApplicationStage());

            System.out.println(
                    "Crop ID           : "
                            + fertilizer.getCropId());
        }
    }

    private static void addCultivation() {

        System.out.println();
        System.out.println(
                "----------- ADD CULTIVATION -----------");

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Cultivation Method: ");
        String cultivationMethod = scanner.nextLine();

        System.out.print("Enter Pest Control: ");
        String pestControl = scanner.nextLine();

        System.out.print("Enter Harvest Duration (days): ");
        int harvestDuration = scanner.nextInt();
        scanner.nextLine();

        Cultivation cultivation =
                new Cultivation(
                        cropId,
                        cultivationMethod,
                        pestControl,
                        harvestDuration
                );

        if (cultivationDAO.addCultivation(cultivation)) {

            System.out.println(
                    "Cultivation details added successfully!");

        } else {

            System.out.println(
                    "Failed to add cultivation details.");
        }
    }

    private static void viewCultivation() {

        System.out.println();
        System.out.println(
                "----------- ALL CULTIVATION DETAILS -----------");

        List<Cultivation> cultivations =
                cultivationDAO.getAllCultivation();

        if (cultivations.isEmpty()) {

            System.out.println(
                    "No cultivation records found.");

            return;
        }

        for (Cultivation cultivation : cultivations) {

            System.out.println("----------------------------------------------");

            System.out.println(
                    "Cultivation ID     : "
                            + cultivation.getCultivationId());

            System.out.println(
                    "Crop ID            : "
                            + cultivation.getCropId());

            System.out.println(
                    "Cultivation Method : "
                            + cultivation.getCultivationMethod());

            System.out.println(
                    "Pest Control       : "
                            + cultivation.getPestControl());

            System.out.println(
                    "Harvest Duration   : "
                            + cultivation.getHarvestDuration()
                            + " days");
        }
    }

    private static void searchCultivationByCrop() {

        System.out.println();
        System.out.println(
                "----------- SEARCH CULTIVATION -----------");

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        List<Cultivation> cultivations =
                cultivationDAO.getCultivationByCrop(cropId);

        if (cultivations.isEmpty()) {

            System.out.println(
                    "No cultivation records found for this crop.");

            return;
        }

        for (Cultivation cultivation : cultivations) {

            System.out.println("----------------------------------------------");

            System.out.println(
                    "Cultivation ID     : "
                            + cultivation.getCultivationId());

            System.out.println(
                    "Crop ID            : "
                            + cultivation.getCropId());

            System.out.println(
                    "Cultivation Method : "
                            + cultivation.getCultivationMethod());

            System.out.println(
                    "Pest Control       : "
                            + cultivation.getPestControl());

            System.out.println(
                    "Harvest Duration   : "
                            + cultivation.getHarvestDuration()
                            + " days");
        }
    }
}