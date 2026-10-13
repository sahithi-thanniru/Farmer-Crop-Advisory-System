
package dao;

import model.Market;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarketDAO {

    public boolean addMarket(Market market) {

        String sql = "INSERT INTO markets " +
                     "(market_name, location, contact) " +
                     "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, market.getMarketName());
            statement.setString(2, market.getLocation());
            statement.setString(3, market.getContact());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error adding market: " + e.getMessage());

            return false;
        }
    }

    public List<Market> getAllMarkets() {

        List<Market> markets = new ArrayList<>();

        String sql = "SELECT * FROM markets";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Market market = new Market();

                market.setMarketId(
                        result.getInt("market_id"));

                market.setMarketName(
                        result.getString("market_name"));

                market.setLocation(
                        result.getString("location"));

                market.setContact(
                        result.getString("contact"));

                markets.add(market);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error viewing markets: " + e.getMessage());
        }

        return markets;
    }

    public Market getMarketById(int marketId) {

        String sql =
                "SELECT * FROM markets WHERE market_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, marketId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Market market = new Market();

                market.setMarketId(
                        result.getInt("market_id"));

                market.setMarketName(
                        result.getString("market_name"));

                market.setLocation(
                        result.getString("location"));

                market.setContact(
                        result.getString("contact"));

                return market;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error searching market: " + e.getMessage());
        }

        return null;
    }
}