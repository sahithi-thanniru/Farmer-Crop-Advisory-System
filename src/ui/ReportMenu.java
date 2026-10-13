package ui;

import dao.ReportDAO;

import java.util.Scanner;

public class ReportMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static ReportDAO reportDAO = new ReportDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("              REPORTS");
            System.out.println("==============================================");
            System.out.println("1. Farmer Summary");
            System.out.println("2. Land Summary");
            System.out.println("3. Crop Summary");
            System.out.println("4. Market Summary");
            System.out.println("5. Price Summary");
            System.out.println("6. Complete System Summary");
            System.out.println("7. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.println();
                    System.out.println("----------- FARMER SUMMARY -----------");
                    reportDAO.farmerSummary();
                    break;

                case 2:
                    System.out.println();
                    System.out.println("----------- LAND SUMMARY -----------");
                    reportDAO.landSummary();
                    break;

                case 3:
                    System.out.println();
                    System.out.println("----------- CROP SUMMARY -----------");
                    reportDAO.cropSummary();
                    break;

                case 4:
                    System.out.println();
                    System.out.println("----------- MARKET SUMMARY -----------");
                    reportDAO.marketSummary();
                    break;

                case 5:
                    System.out.println();
                    System.out.println("----------- PRICE SUMMARY -----------");
                    reportDAO.priceSummary();
                    break;

                case 6:
                    System.out.println();
                    System.out.println("----------- COMPLETE SYSTEM SUMMARY -----------");

                    reportDAO.farmerSummary();
                    reportDAO.landSummary();
                    reportDAO.cropSummary();
                    reportDAO.marketSummary();
                    reportDAO.priceSummary();

                    break;

                case 7:
                    System.out.println("Exiting Reports...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}