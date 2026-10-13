package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    public void farmerSummary() {

        String sql = "SELECT COUNT(*) AS total_farmers FROM farmers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                System.out.println("Total Farmers : "
                        + result.getInt("total_farmers"));
            }

        } catch (Exception e) {
            System.out.println("Error generating farmer report: "
                    + e.getMessage());
        }
    }

    public void landSummary() {

        String sql = "SELECT COUNT(*) AS total_lands, " +
                     "COALESCE(SUM(area), 0) AS total_area " +
                     "FROM lands";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                System.out.println("Total Lands   : "
                        + result.getInt("total_lands"));

                System.out.println("Total Area    : "
                        + result.getDouble("total_area"));
            }

        } catch (Exception e) {
            System.out.println("Error generating land report: "
                    + e.getMessage());
        }
    }

    public void cropSummary() {

        String sql = "SELECT COUNT(*) AS total_crops FROM crops";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                System.out.println("Total Crops   : "
                        + result.getInt("total_crops"));
            }

        } catch (Exception e) {
            System.out.println("Error generating crop report: "
                    + e.getMessage());
        }
    }

    public void marketSummary() {

        String sql = "SELECT COUNT(*) AS total_markets FROM markets";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                System.out.println("Total Markets : "
                        + result.getInt("total_markets"));
            }

        } catch (Exception e) {
            System.out.println("Error generating market report: "
                    + e.getMessage());
        }
    }

    public void priceSummary() {

        String sql = "SELECT COUNT(*) AS total_prices, " +
                     "COALESCE(AVG(price_per_unit), 0) AS average_price " +
                     "FROM crop_prices";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                System.out.println("Price Records : "
                        + result.getInt("total_prices"));

                System.out.println("Average Price  : "
                        + result.getDouble("average_price"));
            }

        } catch (Exception e) {
            System.out.println("Error generating price report: "
                    + e.getMessage());
        }
    }
}
