package dao;

import model.Fertilizer;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FertilizerDAO {

    public boolean addFertilizer(Fertilizer fertilizer) {

        String sql = "INSERT INTO fertilizers " +
                     "(fertilizer_name, quantity, application_stage, crop_id) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, fertilizer.getFertilizerName());
            statement.setString(2, fertilizer.getQuantity());
            statement.setString(3, fertilizer.getApplicationStage());
            statement.setInt(4, fertilizer.getCropId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding fertilizer: " + e.getMessage());

            return false;
        }
    }

    public List<Fertilizer> getAllFertilizers() {

        List<Fertilizer> fertilizers = new ArrayList<>();

        String sql = "SELECT * FROM fertilizers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Fertilizer fertilizer = new Fertilizer();

                fertilizer.setFertilizerId(
                        result.getInt("fertilizer_id"));

                fertilizer.setFertilizerName(
                        result.getString("fertilizer_name"));

                fertilizer.setQuantity(
                        result.getString("quantity"));

                fertilizer.setApplicationStage(
                        result.getString("application_stage"));

                fertilizer.setCropId(
                        result.getInt("crop_id"));

                fertilizers.add(fertilizer);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error viewing fertilizers: " + e.getMessage());
        }

        return fertilizers;
    }

    public List<Fertilizer> getFertilizersByCrop(int cropId) {

        List<Fertilizer> fertilizers = new ArrayList<>();

        String sql =
                "SELECT * FROM fertilizers WHERE crop_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cropId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Fertilizer fertilizer = new Fertilizer();

                fertilizer.setFertilizerId(
                        result.getInt("fertilizer_id"));

                fertilizer.setFertilizerName(
                        result.getString("fertilizer_name"));

                fertilizer.setQuantity(
                        result.getString("quantity"));

                fertilizer.setApplicationStage(
                        result.getString("application_stage"));

                fertilizer.setCropId(
                        result.getInt("crop_id"));

                fertilizers.add(fertilizer);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error searching fertilizers: "
                            + e.getMessage());
        }

        return fertilizers;
    }
}