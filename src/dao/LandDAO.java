package dao;

import model.Land;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LandDAO {

    public boolean addLand(Land land) {

        String sql = "INSERT INTO lands " +
                "(farmer_id, area, soil_type, irrigation, " +
                "water_availability, location, temperature) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, land.getFarmerId());
            statement.setDouble(2, land.getArea());
            statement.setString(3, land.getSoilType());
            statement.setString(4, land.getIrrigation());
            statement.setString(5, land.getWaterAvailability());
            statement.setString(6, land.getLocation());
            statement.setDouble(7, land.getTemperature());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding land: " + e.getMessage());
            return false;
        }
    }

    public List<Land> getAllLands() {

        List<Land> lands = new ArrayList<>();

        String sql = "SELECT * FROM lands";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Land land = new Land();

                land.setLandId(result.getInt("land_id"));
                land.setFarmerId(result.getInt("farmer_id"));
                land.setArea(result.getDouble("area"));
                land.setSoilType(result.getString("soil_type"));
                land.setIrrigation(result.getString("irrigation"));
                land.setWaterAvailability(
                        result.getString("water_availability"));
                land.setLocation(result.getString("location"));
                land.setTemperature(result.getDouble("temperature"));

                lands.add(land);
            }

        } catch (Exception e) {
            System.out.println("Error viewing lands: " + e.getMessage());
        }

        return lands;
    }

    public Land getLandById(int landId) {

        String sql = "SELECT * FROM lands WHERE land_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, landId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Land land = new Land();

                land.setLandId(result.getInt("land_id"));
                land.setFarmerId(result.getInt("farmer_id"));
                land.setArea(result.getDouble("area"));
                land.setSoilType(result.getString("soil_type"));
                land.setIrrigation(result.getString("irrigation"));
                land.setWaterAvailability(
                        result.getString("water_availability"));
                land.setLocation(result.getString("location"));
                land.setTemperature(result.getDouble("temperature"));

                return land;
            }

        } catch (Exception e) {
            System.out.println("Error searching land: " + e.getMessage());
        }

        return null;
    }

    public boolean updateLand(Land land) {

        String sql = "UPDATE lands SET " +
                "farmer_id = ?, area = ?, soil_type = ?, irrigation = ?, " +
                "water_availability = ?, location = ?, temperature = ? " +
                "WHERE land_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, land.getFarmerId());
            statement.setDouble(2, land.getArea());
            statement.setString(3, land.getSoilType());
            statement.setString(4, land.getIrrigation());
            statement.setString(5, land.getWaterAvailability());
            statement.setString(6, land.getLocation());
            statement.setDouble(7, land.getTemperature());
            statement.setInt(8, land.getLandId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating land: " + e.getMessage());
            return false;
        }
    }
}