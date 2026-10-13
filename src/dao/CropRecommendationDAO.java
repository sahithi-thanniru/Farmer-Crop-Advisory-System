package dao;

import model.CropRecommendation;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CropRecommendationDAO {

    // Save recommendation
    public boolean addRecommendation(CropRecommendation recommendation) {

        String sql = "INSERT INTO crop_recommendations " +
                "(farmer_id, land_id, crop_id, suitability, reason, recommendation_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, recommendation.getFarmerId());
            statement.setInt(2, recommendation.getLandId());
            statement.setInt(3, recommendation.getCropId());
            statement.setString(4, recommendation.getSuitability());
            statement.setString(5, recommendation.getReason());
            statement.setDate(6, recommendation.getRecommendationDate());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error saving recommendation: " + e.getMessage());
            return false;
        }
    }

    // View all recommendations
    public List<CropRecommendation> getAllRecommendations() {

        List<CropRecommendation> recommendations = new ArrayList<>();

        String sql = "SELECT * FROM crop_recommendations";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                CropRecommendation recommendation =
                        new CropRecommendation();

                recommendation.setRecommendationId(
                        result.getInt("recommendation_id"));

                recommendation.setFarmerId(
                        result.getInt("farmer_id"));

                recommendation.setLandId(
                        result.getInt("land_id"));

                recommendation.setCropId(
                        result.getInt("crop_id"));

                recommendation.setSuitability(
                        result.getString("suitability"));

                recommendation.setReason(
                        result.getString("reason"));

                recommendation.setRecommendationDate(
                        result.getDate("recommendation_date"));

                recommendations.add(recommendation);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error viewing recommendations: " + e.getMessage());
        }

        return recommendations;
    }
}