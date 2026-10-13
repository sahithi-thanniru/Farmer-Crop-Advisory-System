
package dao;

import model.Cultivation;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CultivationDAO {

    public boolean addCultivation(Cultivation cultivation) {

        String sql = "INSERT INTO cultivation " +
                     "(crop_id, cultivation_method, pest_control, harvest_duration) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cultivation.getCropId());
            statement.setString(2, cultivation.getCultivationMethod());
            statement.setString(3, cultivation.getPestControl());
            statement.setInt(4, cultivation.getHarvestDuration());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding cultivation: "
                            + e.getMessage());

            return false;
        }
    }

    public List<Cultivation> getAllCultivation() {

        List<Cultivation> cultivations = new ArrayList<>();

        String sql = "SELECT * FROM cultivation";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Cultivation cultivation = new Cultivation();

                cultivation.setCultivationId(
                        result.getInt("cultivation_id"));

                cultivation.setCropId(
                        result.getInt("crop_id"));

                cultivation.setCultivationMethod(
                        result.getString("cultivation_method"));

                cultivation.setPestControl(
                        result.getString("pest_control"));

                cultivation.setHarvestDuration(
                        result.getInt("harvest_duration"));

                cultivations.add(cultivation);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error viewing cultivation: "
                            + e.getMessage());
        }

        return cultivations;
    }

    public List<Cultivation> getCultivationByCrop(int cropId) {

        List<Cultivation> cultivations = new ArrayList<>();

        String sql =
                "SELECT * FROM cultivation WHERE crop_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cropId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Cultivation cultivation = new Cultivation();

                cultivation.setCultivationId(
                        result.getInt("cultivation_id"));

                cultivation.setCropId(
                        result.getInt("crop_id"));

                cultivation.setCultivationMethod(
                        result.getString("cultivation_method"));

                cultivation.setPestControl(
                        result.getString("pest_control"));

                cultivation.setHarvestDuration(
                        result.getInt("harvest_duration"));

                cultivations.add(cultivation);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error searching cultivation: "
                            + e.getMessage());
        }

        return cultivations;
    }
}