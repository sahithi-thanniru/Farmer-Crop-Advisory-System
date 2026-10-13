
package dao;

import model.SoilTest;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SoilTestDAO {

    // Add soil test
    public boolean addSoilTest(SoilTest soilTest) {

        String sql = "INSERT INTO soil_tests " +
                "(land_id, nitrogen, phosphorus, potassium, ph, test_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, soilTest.getLandId());
            statement.setDouble(2, soilTest.getNitrogen());
            statement.setDouble(3, soilTest.getPhosphorus());
            statement.setDouble(4, soilTest.getPotassium());
            statement.setDouble(5, soilTest.getPh());
            statement.setDate(6, soilTest.getTestDate());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error adding soil test: " + e.getMessage());
            return false;
        }
    }

    // View all soil tests
    public List<SoilTest> getAllSoilTests() {

        List<SoilTest> soilTests = new ArrayList<>();

        String sql = "SELECT * FROM soil_tests";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                SoilTest soilTest = new SoilTest();

                soilTest.setTestId(result.getInt("test_id"));
                soilTest.setLandId(result.getInt("land_id"));
                soilTest.setNitrogen(result.getDouble("nitrogen"));
                soilTest.setPhosphorus(result.getDouble("phosphorus"));
                soilTest.setPotassium(result.getDouble("potassium"));
                soilTest.setPh(result.getDouble("ph"));
                soilTest.setTestDate(result.getDate("test_date"));

                soilTests.add(soilTest);
            }

        } catch (Exception e) {
            System.out.println("Error viewing soil tests: " + e.getMessage());
        }

        return soilTests;
    }

    // Search soil test by test ID
    public SoilTest getSoilTestById(int testId) {

        String sql = "SELECT * FROM soil_tests WHERE test_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, testId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                SoilTest soilTest = new SoilTest();

                soilTest.setTestId(result.getInt("test_id"));
                soilTest.setLandId(result.getInt("land_id"));
                soilTest.setNitrogen(result.getDouble("nitrogen"));
                soilTest.setPhosphorus(result.getDouble("phosphorus"));
                soilTest.setPotassium(result.getDouble("potassium"));
                soilTest.setPh(result.getDouble("ph"));
                soilTest.setTestDate(result.getDate("test_date"));

                return soilTest;
            }

        } catch (Exception e) {
            System.out.println("Error searching soil test: " + e.getMessage());
        }

        return null;
    }
}
