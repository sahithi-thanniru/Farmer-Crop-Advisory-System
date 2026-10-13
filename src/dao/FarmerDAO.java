package dao;

import model.Farmer;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FarmerDAO {

    // Add farmer
    public boolean addFarmer(Farmer farmer) {

        String sql = "INSERT INTO farmers " +
                "(name, age, gender, phone, village, district, land_ownership) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, farmer.getName());
            statement.setInt(2, farmer.getAge());
            statement.setString(3, farmer.getGender());
            statement.setString(4, farmer.getPhone());
            statement.setString(5, farmer.getVillage());
            statement.setString(6, farmer.getDistrict());
            statement.setString(7, farmer.getLandOwnership());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding farmer: " + e.getMessage());
            return false;
        }
    }

    // View all farmers
    public List<Farmer> getAllFarmers() {

        List<Farmer> farmers = new ArrayList<>();

        String sql = "SELECT * FROM farmers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Farmer farmer = new Farmer();

                farmer.setFarmerId(result.getInt("farmer_id"));
                farmer.setName(result.getString("name"));
                farmer.setAge(result.getInt("age"));
                farmer.setGender(result.getString("gender"));
                farmer.setPhone(result.getString("phone"));
                farmer.setVillage(result.getString("village"));
                farmer.setDistrict(result.getString("district"));
                farmer.setLandOwnership(result.getString("land_ownership"));

                farmers.add(farmer);
            }

        } catch (Exception e) {
            System.out.println("Error viewing farmers: " + e.getMessage());
        }

        return farmers;
    }

    // Search farmer by ID
    public Farmer getFarmerById(int farmerId) {

        String sql = "SELECT * FROM farmers WHERE farmer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, farmerId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Farmer farmer = new Farmer();

                farmer.setFarmerId(result.getInt("farmer_id"));
                farmer.setName(result.getString("name"));
                farmer.setAge(result.getInt("age"));
                farmer.setGender(result.getString("gender"));
                farmer.setPhone(result.getString("phone"));
                farmer.setVillage(result.getString("village"));
                farmer.setDistrict(result.getString("district"));
                farmer.setLandOwnership(result.getString("land_ownership"));

                return farmer;
            }

        } catch (Exception e) {
            System.out.println("Error searching farmer: " + e.getMessage());
        }

        return null;
    }

    // Update farmer
    public boolean updateFarmer(Farmer farmer) {

        String sql = "UPDATE farmers SET name=?, age=?, gender=?, phone=?, " +
                "village=?, district=?, land_ownership=? WHERE farmer_id=?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, farmer.getName());
            statement.setInt(2, farmer.getAge());
            statement.setString(3, farmer.getGender());
            statement.setString(4, farmer.getPhone());
            statement.setString(5, farmer.getVillage());
            statement.setString(6, farmer.getDistrict());
            statement.setString(7, farmer.getLandOwnership());
            statement.setInt(8, farmer.getFarmerId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error updating farmer: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteFarmer(int farmerId) {

    String sql = "DELETE FROM farmers WHERE farmer_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, farmerId);

        int rows = statement.executeUpdate();

        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}