
package dao;

import model.CropPrice;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CropPriceDAO {

    public boolean addCropPrice(CropPrice cropPrice) {

        String sql = "INSERT INTO crop_prices " +
                     "(market_id, crop_id, price_per_unit, unit, date_updated) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cropPrice.getMarketId());
            statement.setInt(2, cropPrice.getCropId());
            statement.setDouble(3, cropPrice.getPricePerUnit());
            statement.setString(4, cropPrice.getUnit());
            statement.setDate(5, cropPrice.getDateUpdated());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding crop price: " + e.getMessage());

            return false;
        }
    }

    public List<CropPrice> getAllCropPrices() {

        List<CropPrice> prices = new ArrayList<>();

        String sql = "SELECT * FROM crop_prices";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                CropPrice cropPrice = new CropPrice();

                cropPrice.setPriceId(
                        result.getInt("price_id"));

                cropPrice.setMarketId(
                        result.getInt("market_id"));

                cropPrice.setCropId(
                        result.getInt("crop_id"));

                cropPrice.setPricePerUnit(
                        result.getDouble("price_per_unit"));

                cropPrice.setUnit(
                        result.getString("unit"));

                cropPrice.setDateUpdated(
                        result.getDate("date_updated"));

                prices.add(cropPrice);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error viewing crop prices: " + e.getMessage());
        }

        return prices;
    }

    public List<CropPrice> getPricesByCrop(int cropId) {

        List<CropPrice> prices = new ArrayList<>();

        String sql =
                "SELECT * FROM crop_prices WHERE crop_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cropId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                CropPrice cropPrice = new CropPrice();

                cropPrice.setPriceId(
                        result.getInt("price_id"));

                cropPrice.setMarketId(
                        result.getInt("market_id"));

                cropPrice.setCropId(
                        result.getInt("crop_id"));

                cropPrice.setPricePerUnit(
                        result.getDouble("price_per_unit"));

                cropPrice.setUnit(
                        result.getString("unit"));

                cropPrice.setDateUpdated(
                        result.getDate("date_updated"));

                prices.add(cropPrice);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error searching crop prices: "
                            + e.getMessage());
        }

        return prices;
    }
}