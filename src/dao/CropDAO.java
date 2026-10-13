package dao;

import model.Crop;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CropDAO {

    public boolean addCrop(Crop crop) {

        String sql = "INSERT INTO crops " +
                "(crop_name, season, duration_days, expected_yield, " +
                "min_temperature, max_temperature) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, crop.getCropName());
            statement.setString(2, crop.getSeason());
            statement.setInt(3, crop.getDurationDays());
            statement.setDouble(4, crop.getExpectedYield());
            statement.setDouble(5, crop.getMinTemperature());
            statement.setDouble(6, crop.getMaxTemperature());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding crop: "
                    + e.getMessage());
            return false;
        }
    }

    public List<Crop> getAllCrops() {

        List<Crop> crops = new ArrayList<>();

        String sql = "SELECT * FROM crops";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Crop crop = new Crop();

                crop.setCropId(result.getInt("crop_id"));
                crop.setCropName(result.getString("crop_name"));
                crop.setSeason(result.getString("season"));
                crop.setDurationDays(result.getInt("duration_days"));
                crop.setExpectedYield(
                        result.getDouble("expected_yield"));
                crop.setMinTemperature(
        result.getDouble("min_temperature"));

crop.setMaxTemperature(
        result.getDouble("max_temperature"));
                crops.add(crop);
            }

        } catch (Exception e) {
            System.out.println("Error viewing crops: "
                    + e.getMessage());
        }

        return crops;
    }

    public Crop getCropById(int cropId) {

        String sql = "SELECT * FROM crops WHERE crop_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cropId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Crop crop = new Crop();

                crop.setCropId(result.getInt("crop_id"));
                crop.setCropName(result.getString("crop_name"));
                crop.setSeason(result.getString("season"));
                crop.setDurationDays(result.getInt("duration_days"));
                crop.setExpectedYield(
                        result.getDouble("expected_yield"));
                crop.setMinTemperature(
                        result.getDouble("min_temperature"));
                crop.setMaxTemperature(
                        result.getDouble("max_temperature"));

                return crop;
            }

        } catch (Exception e) {
            System.out.println("Error searching crop: "
                    + e.getMessage());
        }

        return null;
    }
}
