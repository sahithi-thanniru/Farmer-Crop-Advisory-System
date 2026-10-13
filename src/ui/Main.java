
package ui;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("   FARMER CROP ADVISORY & MARKET SYSTEM");
            System.out.println("==============================================");
            System.out.println("1. Farmer Registration");
            System.out.println("2. Land & Soil Management");
            System.out.println("3. Crop Advisory");
            System.out.println("4. Fertilizer & Cultivation");
            System.out.println("5. Market Information & Prices");
            System.out.println("6. Reports");
            System.out.println("7. Exit");
            System.out.println("==============================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    FarmerMenu.main(new String[]{});
                    break;

                case 2:
                    LandMenu.main(new String[]{});
                    break;

                case 3: 
                    CropAdvisoryMenu.main(new String[]{}); 
                    break;

                case 4:
                    FertilizerCultivationMenu.main(new String[]{});
                    break;

                case 5:
                    MarketMenu.main(new String[]{});
                    break;

                
case 6:
    ReportMenu.main(new String[]{});
    break;



                case 7:
                    System.out.println();
                    System.out.println("Thank you for using the system!");
                    scanner.close();
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
