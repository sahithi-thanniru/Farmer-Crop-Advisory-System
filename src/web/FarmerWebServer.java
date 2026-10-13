package web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import dao.FarmerDAO;
import model.Farmer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FarmerWebServer {

    public static void main(String[] args) throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/addFarmer", FarmerWebServer::addFarmer);

        server.start();

        System.out.println("Web server started!");
        System.out.println("Open: http://localhost:8080");
    }

    private static void addFarmer(HttpExchange exchange)
            throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, "Invalid request.");
            return;
        }

        String data = new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8
        );

        Map<String, String> formData = parseFormData(data);

        String name = formData.get("name");
        int age = Integer.parseInt(formData.get("age"));
        String gender = formData.get("gender");
        String phone = formData.get("phone");
        String village = formData.get("village");
        String district = formData.get("district");
        String landOwnership = formData.get("landOwnership");

        Farmer farmer = new Farmer(
                name,
                age,
                gender,
                phone,
                village,
                district,
                landOwnership
        );

        FarmerDAO farmerDAO = new FarmerDAO();

        boolean success = farmerDAO.addFarmer(farmer);

        if (success) {
            sendResponse(exchange,
                    "Farmer added successfully!");
        } else {
            sendResponse(exchange,
                    "Failed to add farmer.");
        }
    }

    private static Map<String, String> parseFormData(String data) {

        Map<String, String> map = new HashMap<>();

        String[] pairs = data.split("&");

        for (String pair : pairs) {

            String[] keyValue = pair.split("=", 2);

            if (keyValue.length == 2) {

                String key = URLDecoder.decode(
                        keyValue[0],
                        StandardCharsets.UTF_8
                );

                String value = URLDecoder.decode(
                        keyValue[1],
                        StandardCharsets.UTF_8
                );

                map.put(key, value);
            }
        }

        return map;
    }

    private static void sendResponse(
            HttpExchange exchange,
            String message) throws IOException {

        byte[] response =
                message.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                response.length
        );

        OutputStream output =
                exchange.getResponseBody();

        output.write(response);
        output.close();
    }
}