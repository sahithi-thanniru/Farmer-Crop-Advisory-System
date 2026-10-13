package ui;

import dao.MarketDAO;
import dao.CropPriceDAO;
import model.Market;
import model.CropPrice;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MarketMenu {

    private static Scanner scanner = new Scanner(System.in);

    private static MarketDAO marketDAO = new MarketDAO();

    private static CropPriceDAO cropPriceDAO =
            new CropPriceDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("       MARKET INFORMATION & PRICES");
            System.out.println("==============================================");
            System.out.println("1. Add Market");
            System.out.println("2. View All Markets");
            System.out.println("3. Search Market");
            System.out.println("4. Add Crop Price");
            System.out.println("5. View All Crop Prices");
            System.out.println("6. Search Prices by Crop");
            System.out.println("7. Compare Crop Prices");
            System.out.println("8. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addMarket();
                    break;

                case 2:
                    viewMarkets();
                    break;

                case 3:
                    searchMarket();
                    break;

                case 4:
                    addCropPrice();
                    break;

                case 5:
                    viewCropPrices();
                    break;

                case 6:
                    searchPricesByCrop();
                    break;

                case 7:
                    compareCropPrices();
                    break;

                case 8:
                    System.out.println(
                            "Exiting Market Information...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addMarket() {

        System.out.println();
        System.out.println("----------- ADD MARKET -----------");

        System.out.print("Enter Market Name: ");
        String marketName = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();

        Market market =
                new Market(
                        marketName,
                        location,
                        contact
                );

        if (marketDAO.addMarket(market)) {

            System.out.println(
                    "Market added successfully!");

        } else {

            System.out.println(
                    "Failed to add market.");
        }
    }

    private static void viewMarkets() {

        System.out.println();
        System.out.println("----------- ALL MARKETS -----------");

        List<Market> markets =
                marketDAO.getAllMarkets();

        if (markets.isEmpty()) {

            System.out.println(
                    "No market records found.");

            return;
        }

        for (Market market : markets) {

            System.out.println("----------------------------------------------");

            System.out.println(
                    "Market ID  : " + market.getMarketId());

            System.out.println(
                    "Market Name: " + market.getMarketName());

            System.out.println(
                    "Location   : " + market.getLocation());

            System.out.println(
                    "Contact    : " + market.getContact());
        }
    }

    private static void searchMarket() {

        System.out.println();
        System.out.println("----------- SEARCH MARKET -----------");

        System.out.print("Enter Market ID: ");
        int marketId = scanner.nextInt();
        scanner.nextLine();

        Market market =
                marketDAO.getMarketById(marketId);

        if (market == null) {

            System.out.println("Market not found.");
            return;
        }

        System.out.println("----------------------------------------------");

        System.out.println(
                "Market ID  : " + market.getMarketId());

        System.out.println(
                "Market Name: " + market.getMarketName());

        System.out.println(
                "Location   : " + market.getLocation());

        System.out.println(
                "Contact    : " + market.getContact());
    }

    private static void addCropPrice() {

        System.out.println();
        System.out.println("----------- ADD CROP PRICE -----------");

        System.out.print("Enter Market ID: ");
        int marketId = scanner.nextInt();

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();

        System.out.print("Enter Price per Unit: ");
        double pricePerUnit = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Unit (kg/quintal/etc.): ");
        String unit = scanner.nextLine();

        Date dateUpdated =
                Date.valueOf(LocalDate.now());

        CropPrice cropPrice =
                new CropPrice(
                        marketId,
                        cropId,
                        pricePerUnit,
                        unit,
                        dateUpdated
                );

        if (cropPriceDAO.addCropPrice(cropPrice)) {

            System.out.println(
                    "Crop price added successfully!");

        } else {

            System.out.println(
                    "Failed to add crop price.");
        }
    }

    private static void viewCropPrices() {

        System.out.println();
        System.out.println(
                "----------- ALL CROP PRICES -----------");

        List<CropPrice> prices =
                cropPriceDAO.getAllCropPrices();

        if (prices.isEmpty()) {

            System.out.println(
                    "No crop price records found.");

            return;
        }

        for (CropPrice price : prices) {

            displayPrice(price);
        }
    }

    private static void searchPricesByCrop() {

        System.out.println();
        System.out.println(
                "----------- SEARCH PRICES BY CROP -----------");

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        List<CropPrice> prices =
                cropPriceDAO.getPricesByCrop(cropId);

        if (prices.isEmpty()) {

            System.out.println(
                    "No prices found for this crop.");

            return;
        }

        for (CropPrice price : prices) {

            displayPrice(price);
        }
    }

    private static void compareCropPrices() {

        System.out.println();
        System.out.println(
                "----------- COMPARE CROP PRICES -----------");

        System.out.print("Enter Crop ID: ");
        int cropId = scanner.nextInt();
        scanner.nextLine();

        List<CropPrice> prices =
                cropPriceDAO.getPricesByCrop(cropId);

        if (prices.isEmpty()) {

            System.out.println(
                    "No prices available for this crop.");

            return;
        }

        double highest = prices.get(0).getPricePerUnit();
        double lowest = prices.get(0).getPricePerUnit();

        int highestMarketId = prices.get(0).getMarketId();
        int lowestMarketId = prices.get(0).getMarketId();

        for (CropPrice price : prices) {

            if (price.getPricePerUnit() > highest) {

                highest = price.getPricePerUnit();
                highestMarketId = price.getMarketId();
            }

            if (price.getPricePerUnit() < lowest) {

                lowest = price.getPricePerUnit();
                lowestMarketId = price.getMarketId();
            }
        }

        System.out.println("----------------------------------------------");

        System.out.println(
                "Highest Price : " + highest);

        System.out.println(
                "Highest Price Market ID : "
                        + highestMarketId);

        System.out.println();

        System.out.println(
                "Lowest Price  : " + lowest);

        System.out.println(
                "Lowest Price Market ID : "
                        + lowestMarketId);

        System.out.println("----------------------------------------------");
    }

    private static void displayPrice(CropPrice price) {

        System.out.println("----------------------------------------------");

        System.out.println(
                "Price ID       : " + price.getPriceId());

        System.out.println(
                "Market ID      : " + price.getMarketId());

        System.out.println(
                "Crop ID        : " + price.getCropId());

        System.out.println(
                "Price per Unit : "
                        + price.getPricePerUnit());

        System.out.println(
                "Unit           : " + price.getUnit());

        System.out.println(
                "Date Updated   : "
                        + price.getDateUpdated());
    }
}