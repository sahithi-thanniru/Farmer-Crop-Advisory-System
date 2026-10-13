package dao;

import model.PreviousCrop;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PreviousCropDAO {

    public boolean addPreviousCrop(PreviousCrop previousCrop) {

        String sql = "INSERT INTO previous_crops " +
                     "(farmer_id, land_id, crop_id, season, year, yield) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, previousCrop.getFarmerId());
            statement.setInt(2, previousCrop.getLandId());
            statement.setInt(3, previousCrop.getCropId());
            statement.setString(4, previousCrop.getSeason());
            statement.setInt(5, previousCrop.getYear());
            statement.setDouble(6, previousCrop.getYield());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding previous crop: "
                    + e.getMessage());

            return false;
        }
    }

    public List<PreviousCrop> getAllPreviousCrops() {

        List<PreviousCrop> previousCrops = new ArrayList<>();

        String sql = "SELECT * FROM previous_crops";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                PreviousCrop previousCrop = new PreviousCrop();

                previousCrop.setRecordId(
                        result.getInt("record_id"));

                previousCrop.setFarmerId(
                        result.getInt("farmer_id"));

                previousCrop.setLandId(
                        result.getInt("land_id"));

                previousCrop.setCropId(
                        result.getInt("crop_id"));

                previousCrop.setSeason(
                        result.getString("season"));

                previousCrop.setYear(
                        result.getInt("year"));

                previousCrop.setYield(
                        result.getDouble("yield"));

                previousCrops.add(previousCrop);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error viewing previous crops: "
                    + e.getMessage());
        }

        return previousCrops;
    }
}